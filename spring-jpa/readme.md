# Spring - JPA


## Spring  Data JPA

cung cấp repository hỗ trợ cho JPA. Nó giúp giảm các thao tác cần thiết (amount of boilerplate code) để có thể truy cập JPA data sources. Mà mục tiêu của Repository là giúp đơn giản, giảm bớt các mã cấu hình cần thiết để implement data access layer cho các persistence stores.

## Spring JPA hỗ trợ cho ta điều gì

- Reduce boilerplate code for JPA: Spring Data JPA cung cấp sẵn các implementation cho mỗi method được định nghĩa trong interface repository. Điều này có nghĩa là bạn không cần phải viết nhiều code, ít dẫn đến những sai lầm khi tương tác với database.

- Support QueryDSL and JPA queries

- Support for batch loading, sorting, dynamical queries
- Generated queries.

## Bootstrap mode

Mặc định thì Spring Data Jpa repositories được định nghĩa là Spring Bean với scope là Singleton và eagerly initialized. Spring Framework hỗ trợ việc tạo lập JPA EntityManagerFactory in background thread vì chúng cần nhiều thời gian để start up. Do đó để đảm bảo rằng chương trình khởi động một cách hiệu quả thì để  JPA repositories được khởi tạo một cách trễ nhất.

Từ Spring Data JPA 2.1 thì ta có thể cấu hình với
- DEFAULT (default): Repository được khởi tạo với eagerly.
- LAZY: với cấu hình này thì Repository sẽ tạo ra các proxy inject vào các client bean. Chỉ hữu hiệu khi không có client bean nào sử dụng nó trong quá trình khởi tạo và để chúng trong field.
- DEFERRED: Hoạt động giống như Lazy nhưng bị khởi tạo trước khi application thực hiện start. Nên được sử dụng với Jpa asynchronously. Vì đảm bảo chúng được khởi tạo cuối cùng khi các component khác đã được hoàn thành.

## Persisting Entity

### Saving Entities

Phương thức đó được thực hiện thông qua CrudRepository.save(…) method. Nó sẽ persist hoặc merge() tùy vào trạng thái của Entities. Để check được trạng thái của Entity thì nó dựa vào một cờ như sau
```java
@MappedSuperclass
public abstract class AbstractEntity<ID> implements Persistable<ID> {

  @Transient
  private boolean isNew = true; 

  @Override
  public boolean isNew() {
    return isNew; 
  }

  @PrePersist 
  @PostLoad
  void markNotNew() {
    this.isNew = false;
  }

  // More code…
}
```

Nó thực hiện như sau
- Khai báo cờ là New State với trạng thái new khi bắt đầu
- Trả về cờ trong implementation `Persistable.isNew()`, dựa vào đó để biết là nên persist() hay merge().
- Khai báo method sử dụng Jpa entity  callback để chuyển đổi trạng thái của nó sau khi repository gọi save(). bằng cách sử dụng ` @PrePersist `.

### Query Method

Mô tả các cách tạo các câu query với Spring Data Jpa. Để có thể convert các câu query cụ thể từ method name thì repository proxy có hai cách
- bằng cách lấy trực tiếp từ tên các method
- bằng cách sử dụng các câu truy vấn được định nghĩa

#### Query Lookup Strategies

- Create: cố gắng tạo ra các câu store-specific query từ method name mà không quan tâm đến các câu sql đã được khai báo trước đó.;
- USE_DECLARED_QUERY: cố gắng tìm kiếm và sử dụng các câu query đã được khai báo và throw ra exception nếu không tìm được. Các câu query đó có thể được định nghĩa bằng annotaion hoặc bằng XML.
- CREATE_IF_NOT_FOUND (default): kết hợp cả hai, nếu không tìm thấy sẵn các câu SQL đã được khai báo sẵn thì sẽ tạo ra các câu store-specific query từ method name.

ví dụ với default
```java
@Entity
@NamedQuery(name = "User.findByEmailAddress",
  query = "select u from User u where u.emailAddress = ?1")
public class User {
}

//--------------------

public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findByLastname(String lastname);

  User findByEmailAddress(String emailAddress);
}
```

Thay vì tạo câu SQL mới với method `  User findByEmailAddress(String emailAddress)` thì nó sẽ sử dụng câu SQL đã được định nghĩa trong class.

#### Query Creation

Đầu tiên trên các method, nó loại loại bỏ các tiền tố như find..by, read..by khỏi các method và phân tích các thành phần con lại. Các mệnh đề phía sau như Distinct sẽ bật cờ distinct flag trong câu query của mình. Sau từ By là dấu phân cách để bắt đầu xác định các tiêu chí, các tiêu chí này có thể dựa trên các property của các entity và nối chúng với nhau thông qua `Or` hoặc `And`. Với các keyword đã được định nghĩa trong method thì chúng chỗ trợ convert thành JPQL theo đường link sau

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

Không những dùng các thuộc tính của các property đó làm tiêu chí, ta còn có thể sử dụng các property lòng nhau bên trong đó. Ví dụ như ta có `Person` có `Address` với Address có thuộc tính là Zipcode, có thể được thể hiện như sau: x.address.zipCode

####  Property Expressions
Ví dụ ta có một phương thức sau `List<Person> findByAddressZipCode(ZipCode zipCode)`

Đầu tiên thuật toán sẽ sử dụng toàn bộ `AddressZipCode` như một thuộc tính của Entity, nếu được thì nó sẽ thực hiện, nếu lần một nó sẽ tách ra thành các phần đầu và đuôi dựa vào Camel Case, trong ví dụ của ta là `AddressZip` và `Code`, nếu vẫn tiếp tục thất bại thì nó sẽ đổi lại cách split thnahf `Address` và `ZipCode`, cứ tiếp tục như vậy.

#### Special parameter handling

Spring Data Jpa còn hỗ trợ thêm các kiểu Pageable, Sort, Slice vào các câu truy vấn, ví dụ như 

```java
Page<User> findByLastname(String lastname, Pageable pageable);

Slice<User> findByLastname(String lastname, Pageable pageable);

List<User> findByLastname(String lastname, Sort sort);

List<User> findByLastname(String lastname, Pageable pageable);
```

Với Pageable: ta có thể duyệt hết tất cả các phần tử và các pages được lưu trữ trong database

Với Slice: 

Với Sort: ta có thể định nghĩa thông qua properties name của Entity và kết hợp nhiều điều kiện với nhau trong một biểu thức, sau đây ta sử dụng type-safe API

```java
TypedSort<Person> person = Sort.sort(Person.class);

TypedSort<Person> sort = person.by(Person::getFirstname).ascending()
  .and(person.by(Person::getLastname).descending());
```

#### Limit Query Result 

Ta cũng có thể limited các câu query bằng cách sử dụng `first` hoặc `top` keyword. nếu mặc định phía sau top and first không có con số cụ thể thì mặc định là 1. Ví dụ như 

```java
Slice<User> findTop3ByLastname(String lastname, Pageable pageable);

List<User> findFirst10ByLastname(String lastname, Sort sort);

List<User> findTop10ByLastname(String lastname, Pageable pageable);
```

#### Streaming query results

Sử dụng Stream để tăng tốc độ xử lí được cung cấp bằng java 8, nhưng có một số kiểu không hỗ trợ Steam.

```java
@Query("select u from User u")
Stream<User> streamAllPaged(Pageable pageable);

//---- Lưu ý khi xử lí Stream ta cần close resource sau khi sử dụng bằng cách try with resource

try (Stream<User> stream = repository.findAllByCustomQueryAndStream()) {
  stream.forEach(…);
}

```

#### Async query results
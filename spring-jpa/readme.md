- [Spring - JPA](#spring---jpa)
  - [Spring Data JPA](#spring-data-jpa)
  - [Cách sử dụng Spring Data JPA](#c%c3%a1ch-s%e1%bb%ad-d%e1%bb%a5ng-spring-data-jpa)
    - [Spring data Commons](#spring-data-commons)
    - [Spring Data JPA Interface](#spring-data-jpa-interface)
  - [Spring JPA hỗ trợ cho ta điều gì](#spring-jpa-h%e1%bb%97-tr%e1%bb%a3-cho-ta-%c4%91i%e1%bb%81u-g%c3%ac)
  - [Bootstrap mode](#bootstrap-mode)
  - [Persisting Entity](#persisting-entity)
    - [Saving Entities](#saving-entities)
    - [Query Method](#query-method)
      - [Query Lookup Strategies](#query-lookup-strategies)
      - [Query Creation](#query-creation)
      - [Property Expressions](#property-expressions)
      - [Returning Values From Query Methods](#returning-values-from-query-methods)
      - [Passing Method Parameters to Query Methods](#passing-method-parameters-to-query-methods)
        - [@Modyfi đi kèm với @Query](#modyfi-%c4%91i-k%c3%a8m-v%e1%bb%9bi-query)
        - [Position based parameter binding](#position-based-parameter-binding)
        - [named parameters](#named-parameters)
      - [Special parameter handling](#special-parameter-handling)
      - [Limit Query Result](#limit-query-result)

# Spring - JPA

Bài viết tìm hiểu về  Spring data JPA.

Sau khi đọc xong bài viết bài bạn có thể trả lời các câu hỏi dưới đây.

1. Spring Data JPA là gì?
2. Spring giúp mình những gì?
3. Cách sử dụng JPA Repository
4. Làm sao mình khai báo query bằng method name, mà Spring hiểu và query được?

## Spring  Data JPA

Spring Data JPA is not a JPA provider.  It is a library / framework that adds an extra layer of abstraction on the top of our JPA provider.  the repository layer of our application contains three layers that are described in the following:
- Spring Data JPA: hỗ trợ cho việc tạo ra các JPA repository bằng cách kế thừa Spring Data repository interface
- Spring data commoms: provides the infrastructure that is shared by the datastore-specific Spring Data projects.
- The JPA Provider: implement Java Persistence APi.

Spring Data JPA adds a layer on top of JPA. That means it uses all features defined by the JPA specification, especially the entity and association mappings, the entity lifecycle management, and JPA’s query capabilities. On top of that, Spring Data JPA adds its own features like a no-code implementation of the repository pattern and the creation of database queries from method names.

## Cách sử dụng Spring Data JPA 

Chỉ cần một dependency với antifactid là `spring-boot-starter-data-jpa` và JDBC driver trong maven, ví dụ như
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>test</scope>
</dependency>
```
Tiếp theo cần config file application.properties với các thông tin về datasource

```
spring.datasource.url = jdbc:postgresql://localhost:5432/recipes
spring.datasource.username = postgres
spring.datasource.password = postgres
```

### Spring data Commons

là một phần của Spring data, nó cung cấp (shared infrastructure) trên toàn Spring Data project. Chứa một số interface như
- `Repository<T, ID extends Serializable> interface`: quản lí loại Entity được quản lí và loại dữ liệu của ID. Giúp Spring Container nhận biết được nó là repository trong khi classpath scanning
- `CrudRepository<T, ID extends Serializable> interface`: cung cấp các operation thêm, xóa, sửa cho việc quản lí Entity.
- `PagingAndSortingRepository<T, ID extends Serializable> interface`: interface cung cấp các method nhằm sử dụng để sort và paginate entities được truy vấn từ database.
- QueryDslPredicateExecutor interface: không phải là  “repository interface”. Dùng để truy vấn các Entity từ database bằng cách sử dụng QueryDSL.

### Spring Data JPA Interface

Cung cấp một số interface 
- `JpaRepository<T, ID extends Serializable> `: kết hợp các phương thức được khai báo của commoms repository bằng một interface duy nhất.
-  `JpaSpecificationExecutor<T> `: đây là interface không phải `repository interface`, truy vấn các Entity từ database  bằng cách sử dụng Criteria API.

![X](https://www.petrikainulainen.net/wp-content/uploads/springdatajrepositories.png)

## Spring JPA hỗ trợ cho ta điều gì

- Reduce boilerplate code for JPA: Spring Data JPA cung cấp sẵn các implementation cho mỗi method được định nghĩa trong interface repository. Điều này có nghĩa là bạn không cần phải viết nhiều code khi implement các data acess layer, ít dẫn đến những sai lầm khi tương tác với database.

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

#### Returning Values From Query Methods

Các câu query có thể được trả về ở các kiểu sau đây
- Basic Type: có thể trả về các loại dữ liệu cơ bản hoặc null
- Entity: Trả về các Entity hoặc null
- Optional<T>: trả về Optional hoặc Empty Optional.

Nếu các câu query trả về hơn một kết quả ta có thể sử dụng
- List<T>
- Stream<T>

Sử dụng Stream để tăng tốc độ xử lí được cung cấp bằng java 8, nhưng có một số kiểu không hỗ trợ Steam.

```java
@Query("select u from User u")
Stream<User> streamAllPaged(Pageable pageable);

//---- Lưu ý khi xử lí Stream ta cần close resource sau khi sử dụng bằng cách try with resource

try (Stream<User> stream = repository.findAllByCustomQueryAndStream()) {
  stream.forEach(…);
}

```

#### Passing Method Parameters to Query Methods

Với các câu query được tạo từ annotation `@Query`,  ta có thể truyền vào các tham số bằng thứ vị vị trí hoặc bằng thêm các tham số được được quy định sẵn

##### @Modyfi đi kèm với @Query

https://www.baeldung.com/spring-data-jpa-modifying-annotation

Với việc các câu lệnh update và xóa database bằng `@Query`, thì có thể xảy ra tình huống entity của chúng ta trên persistence context bị outdated (do  dưới database đã xóa mà trên persistence context vẫn còn hoặc không cập nhật theo).

Do đó để khắc phục việc này thì ta có thể sử dụng `@Modifying(clearAutomatically = true)` làm cho persistence context được clear sau khi câu lệnh được thực thi.

Nhưng với điều đó thì có thể xảy ra vấn đề là mất các thao tác update khác khi các thao tác đó chưa kịp persist vào BD.

##### Position based parameter binding

Ví dụ về truyền tham số theo thứ tự như sau
```java
interface TodoRepository extends Repository<Todo, Long> { 
 
    public Optional<Todo> findByTitleAndDescription(String title, String description);
     
    @Query("SELECT t FROM Todo t where t.title = ?1 AND t.description = ?2")
    public Optional<Todo> findByTitleAndDescription(String title, String description);
     
    @Query(value = "SELECT * FROM todos t where t.title = ?0 AND t.description = ?1", 
        nativeQuery=true
    )
    public Optional<Todo> findByTitleAndDescription(String title, String description);
}
```

##### named parameters

```java
interface TodoRepository extends Repository<Todo, Long> { 
     
    @Query("SELECT t FROM Todo t where t.title = :title AND t.description = :description")
    public Optional<Todo> findByTitleAndDescription(@Param("title") String title, 
                                                    @Param("description") String description);
     
    @Query(
        value = "SELECT * FROM todos t where t.title = :title AND t.description = :description", 
        nativeQuery=true
    )
    public Optional<Todo> findByTitleAndDescription(@Param("title") String title, 
                                                    @Param("description") String description);
}
```

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


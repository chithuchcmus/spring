- [JPA](#jpa)
  - [What is JPA](#what-is-jpa)
  - [JPA - Architecture](#jpa---architecture)
  - [JPA - ORM Components](#jpa---orm-components)
  - [JPA Entity](#jpa-entity)
    - [JPA Entity LifeCycle](#jpa-entity-lifecycle)
    - [persistence Context](#persistence-context)
    - [Retireving Jpa Entity Object](#retireving-jpa-entity-object)
    - [Retrieval by Class and Primary Key](#retrieval-by-class-and-primary-key)
    - [Retrieval by Eager Fetch](#retrieval-by-eager-fetch)
  - [JPA Entity Manager](#jpa-entity-manager)
  - [JPA Cascade Types](#jpa-cascade-types)
  - [Generated Identifiers](#generated-identifiers)
    - [AUTO Generation](#auto-generation)
    - [IDENTITY](#identity)
    - [SEQUENCE Generation](#sequence-generation)
  - [JPA - JPQL](#jpa---jpql)
  - [JPA - Advanced Mapping](#jpa---advanced-mapping)
  - [JPA - Criteria API](#jpa---criteria-api)
  - [JPA - Transaction](#jpa---transaction)
  - [Hibernate as JPA Provider nghĩa là thế nào](#hibernate-as-jpa-provider-ngh%c4%a9a-l%c3%a0-th%e1%ba%bf-n%c3%a0o)
  - [Hibernate](#hibernate)

# JPA

Sử dụng JPA để giảm các thao tác với DB, là cầu nối giữa các Object trong chương trình với relational models. Thông thường thì giữa hai định dạng đó có một số điểm khác nhau nhất định như relation model được trình bày theo dạng bảng, còn object được trình bày dưới dạng các thuộc tính kết nối với nhau nên việc liên kết giữa chúng là không đơn giản.

## What is JPA

The Java Persistence API (JPA) là It’s a specification which is part of Java EE and defines an API for object-relational mappings and for managing persistent objects. Nó được sử dụng để persist data giữ các Object và các bảng trong CSDL, cho phép ánh xạ các table/các mối quan hệ giữa các table trong database sang các class/mối quan hệ giữa các object. Ta có thể truy vấn đến database thông qua các thao các với class.

Jpa  là một kĩ thuật chứa các interface, bản thân nó không thực hiện bất cứ operation nào, do đó cần có các implementation cụ thể ta mới có thể sử dụng như Hibernate, TopLink and iBatis... là các implement JPA specification cho data persistence.

## JPA - Architecture

Java Persistence APi là source để định nghĩa và quản lí các object (PLAIN OLD JAVA OBJECT (POJO)) như là relation model trong database. Sơ đồ kiến thúc như sau

- EntityManagerFactory: nó vai trò khởi tạo và quản lí các instance của EntityManager.

- EntityManager: là một giao diện (interface) cung cấp các API cho việc tương tác với các Entity như Persist (lưu một đối tượng mới), merge (cập nhật một đối tượng), remove (xóa 1 đối tượng).

- Entity: : là các đối tượng thể hiện tương ứng 1 table trong cơ sở dữ liệu. Khi lập trình, entity thường là các class POJO đơn giản, chỉ gồm các method getter, setter.

- EntityTransaction	: với mỗi EntityManager gắn liền với một EntityTransaction. các hoạt động trên mỗi EntityManager được duy trì bởi EntityTransaction.

- Persistence: là class chứa statis method  để lấy ra instance của các EntityManagerFactory.

![x](https://stackjava.com/wp-content/uploads/2017/11/jpa-arch.png)

## JPA - ORM Components

ORM (Object Relational Mapping) được sử dụng để phát triển và duy trì mối quan hệ giữ các object và relational database bằng cách mapping object state với các cột trong bảng. Nó có khả năng xử lí các database operations khác nhau một cách dễ dàng như insert, update,...

![x](https://www.javatpoint.com/jpa/images/jpa-object-relational-mapping.png)

Các loại Mapping:
- One to one: được thể hiện bằng `@OneToOne`, mỗi entity liên quan đến instance của entity khác.
- One to many: được thể hiện bằng `@OneToMany`, mỗi instance của entity này có thể  liên kết nhiều hơn một instance của các entity khác.
- Many to one: được thể hiện bằng `@ManyToOne`, nhiều instance của entity này được liên kết với một instance của entity khác.
- Many to many: được thể hiện bằng `@ManyToMany`, nhiều instance của entity này được liên kết với nhiều instance của entity khác.

## JPA Entity 

### JPA Entity LifeCycle

với Entity Manager

![x](https://vladmihalcea.com/wp-content/uploads/2014/07/jpaentitystates.png)

Với Session

![x](https://vladmihalcea.com/wp-content/uploads/2014/07/hibernateentitystates1.png)

- Null: object chưa tồn tại
- New: object được khởi tạo và chưa liên quan đến EntityManager và DB
- Persistent (Managed): ở trạng thái này, object đã được persisted và được quản lí bởi EntityManager (Persistence Context). bằng cách sử dụng phương thức persist. hoặc là các object được truy vấn từ DB.Khi persist thì có thể xảy ra các exception sau
    - IllegalArgumentException: persist các object không phải entity
    - TransactionRequiredException: không có transaction khi thay đổi db
    - EntityExistsException:  nếu entity đã tồn tại trong database với cùng primary key.
- Detached: ở trạng thái này Entity bị xóa khỏi sự quản lí của Persistence Context nhưng vẫn còn tồn tại trong DB. Và không còn được cập nhật ở database. Để có thể liên kết lại với database thì ta có thể sử dụng Merging.


- Merging: tìm object attached với cùng id và update chúng, nếu tồn tại object đó, nó sẽ update và trả về attached update. nếu không có tồn tại thì nó sẽ  insert mới vào DB.

Ví dụ về merge() và persist()

```java
{
    AnyEntity newEntity;
    AnyEntity nonAttachedEntity;
    AnyEntity attachedEntity;

    // Create a new entity and persist it        
    newEntity = new AnyEntity();
    em.persist(newEntity);

    // Save 1 to the database at next flush
    newEntity.setValue(1);

    // Create a new entity with the same Id than the persisted one.
    AnyEntity nonAttachedEntity = new AnyEntity();
    nonAttachedEntity.setId(newEntity.getId());

    // Save 2 to the database at next flush instead of 1!!!
    nonAttachedEntity.setValue(2);
    attachedEntity = em.merge(nonAttachedEntity);

    // This condition returns true
    // merge has found the already attached object (newEntity) and returns it.
    if(attachedEntity==newEntity) {
            System.out.print("They are the same object!");
    }

    // Set 3 to value
    attachedEntity.setValue(3);
    // Really, now both are the same object. Prints 3
    System.out.println(newEntity.getValue());

    // Modify the un attached object has no effect to the entity manager
    // nor to the other objects
    nonAttachedEntity.setValue(42);
}
```

- Remove: xóa Object khỏi DB, cần để trong transaction

Với lifecycle như vậy thì chúng gắn liền với một số annotation sau
- @PrePersist/@PostPersist
- @PreRemove/@PostRemove
- @PreUpdate/@PostUpdate
- @PostLoad

Nếu bất kì pre-post method xảy ra exception thì transaction sẽ rollback.

### persistence Context

tập hợp tất cả các object được quản lí của EntityManager. Nếu object được truy vấn đã tồn tại trong persistenceContext, thì object được trả về mà không cần thực sự truy cập vào DB (ngoại từ refresh).

Nguyên lí của nó là đảm bảo không có entity Object nào được thể hiện hơn một lần trong memory trong cùng EntityManager. Mỗi EntityManager có riêng persistenceContext. Do đó với các EntityManager khác nhau có  thể có các Object Entity trong memory khác nhau
Do đó Các entity Object có thể bị dọn dẹp bởi garbage collector khi lâu không được sử dụng. Ta có thể cấu hình để có thể thay đổi thông qua `strong references`.

Persistence Context có thể được xóa thông qua phương thức `clear()`. Khi đó bất kì thay đổi trên các entity object được quản lí bởi instance EntityManager đó sẽ không đồng bộ với database. 

### Retireving Jpa Entity Object

Không yêu cầu bật transaction vì không modify dữ liệu. Persistence context hoạt động như cache các entity Objects với Db. khi một request object không được tìm thấy trong persistence context, object mới được tạo và dữ liệu sẽ được truy vấn từ database. Object mới đó sẽ được thêm vào Persistence Context

Việc khởi tạo đó dựa vào constructor không có tham số, do đó hãy để cái hàm đó đơn giản nhất có thể để giảm thiểu các hoạt động giúp tăng tốc chương trình.

### Retrieval by Class and Primary Key

`em.find(Employee.class, 1)` thông qua tên class và primary key, nếu tồn tại trong persistence Context thì sẽ được trả về ngay mà không cần truy cập vào trong DB. nếu không DB không có thì sẽ trả về null.

`em.getReference(Employee.class, 1)` tương tự như find nhưng với các  object không được quản lí bởi EntityManager nó sẽ tạo ra các `hollow object`: các object này có các  primary key chính xác nhưng các thuộc tính sẽ chưa được khởi tạo, chỉ được load từ db khi được truy cập lần đầu tiên.

### Retrieval by Eager Fetch

Khi truy vấn object entity, thì mặc định thứ tự được gọi thường là các field bình thường, đến các non-collection và collection. Do vậy thường có thể gọi đến toàn bộ DB load vào memory mặc dù chúng không cần thiết. Ta có thể hạn chế điều này bằng cách sử dụng
```java
@Entity
class Employee {
   :
  @ManyToOne(fetch=FetchType.LAZY)
  private Employee manager;
   :
}
```
mặc định  non collection and map references  thì sẽ là FetchType.EAGER, nhưng với FetchType.Lazy thì nó có cơ chế tương tự như `Hollow object`. Với persistent collection and map fields là  FetchType.LAZY.


## JPA Entity Manager

vai trò của Entity 

- Entity manager implement các API và đóng gói tất cả chúng vào interface duy nhất
- Được dùng để đọc, xóa và viết trên các entity
- các đối tượng tham chiếu được quản lí bởi entity manager.

Entity manager được cung cấp thông qua EntityManagerFactory. mà EntityManagerFactory lại được tạo ra bằng persistence.

Việc khởi tạo EntityManagerFactory  cho ứng dụng rất tốn tài nguyên, nhưng việc khởi tạo Entity manager thì inexpensive. Lưu ý, với việc khởi tạo một instance Entity manager cho toàn ứng dụng sẽ dẫn đến không đảm bảo thread-safe, vì với mỗi Entity manager sẽ chỉ có một Entity transactional

Khi sử dụng tập tin Persistence.xml trong persistence-unit có sử dụng transaction-type:  có hai loại transaction-type là  RESOURCE_LOCAL và JTA
- RESOURCE_LOCAL:
    - Tự tạo và quản lí các Entity Manager.
    - Tự sử dụng EntityManagerFactory để tạo ra Entity Manager
    - Có thể sử dụng @PersistenceUnit  để lấy ra các EntityManagerFactory
- JTA:
    - Web container sẽ quản lí việc tạo và sử dụng EntityManager
    - Không thể dùng EntityManagerFactory để lấy EntityManager, mà phải thông qua web container
    - Có thể dùng @PersistenceContext để lấy EntityManager
Còn các định dạng phía sau như
- hibernate.format_sql: properties được dùng trong trường hợp các bạn cần hiển thị câu lệnh SQL của Hibernate ra console và chúng phải được định dạng để chúng ta có thể dễ dàng đọc được.
- hibernate.use_sql_comments: properties này được dùng với Hibernate trong trường hợp các bạn muốn biết câu lệnh SQL đang muốn làm gì.

## JPA Cascade Types

- CascadeType.PERSIST: Có nghĩa là các operation như persist() hoặc save() mới liên quan đến related entities, chúng sẽ lưu vào DB cùng 
- CascadeType.MERGE: Có nghĩa là operation merge() liên quan đến related entities, chúng sẽ cùng merge() với  owner entity mà nó liên kết
- CascadeType.REFRESH: các related entites  sẽ cùng được refesh với owner entity nó liên kết
- CascadeType.REMOVE: cascade type remove removes all related entities association with this setting when the owning entity is deleted.
- CascadeType.DETACH : cascade type detach detaches all related entities if a “manual detach” occurs.
- CascadeType.ALL : cascade type all is shorthand for all of the above cascade operations.

Ví dụ như 
```java
@OneToMany(cascade={CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
@JoinColumn(name="EMPLOYEE_ID")
private Set<AccountEntity> accounts;
```

##  Generated Identifiers

### AUTO Generation

Nếu để mặc định thì persistence provider sẽ dựa vào kiểu của ID để xác định chọn cho nó là UUID hoặc numerical.

Với numerical thì ID sẽ được tạo dựa trên sequence or table generator, còn UUID sẽ được tạo dựa trên UUIDGenerator.

### IDENTITY

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

```sql
INSERT INTO post (id, title)
VALUES (DEFAULT, 'High-Performance Java Persistence')
```

Giá trị dựa trên identity column trong database, tức  là chúng tự động tăng  và nó có chức năng `disables batch updates.`

### SEQUENCE Generation

khi sử dụng SEQUENCE identifier strategy, thì hibernate generates ra câu lệnh sql như sau

```sql
CALL NEXT VALUE FOR 'hibernate_sequence'
 
-- The post entity identifier is 1
 
-- Flush Persistence Context
 
INSERT INTO post (title, id)
VALUES ('High-Performance Java Persistence', 1)
```
generater này sử dụng sequences  nếu database có hỗ trợ và nếu không chúng sẽ tạo một bảng để generate.

## JPA - JPQL

Tính năng của JPQL
- Độc lập với query language.
- Đơn giản

https://thoughts-on-java.org/jpql/

JPA defines its own query language, called JPQL. Khi ta thực thi câu query đó bằng ngôn ngữ JPQL dựa trên các model entity thay vì các Database table, nó sẽ generate ra các câu SQL. Dựa vào các DB mà mình sử dụng  nó sẽ tạo ra tương ứng, giúp cho Application ta có thể dễ dàng thay đổi DB.

Ví dụ sau đây ta có một số Entity như StudentEntity thì một số câu lệnh JPQL như sau
```java
Query query = em.createQuery("Select s.s_name from StudentEntity s");  
```

https://thoughts-on-java.org/jpa-native-queries/

JPQL không thể hỗ trợ hết các tính năng cho các câu lệnh SQL, hoặc với các câu lệnh sql đã được optimize ta cần thực thi trực tiếp, Khi cần thì ta có thể thực thi các  câu lệnh native SQL query với Entity Manager. Ví dụ như sau
```java
Query q = em.createNativeQuery("SELECT a.id, a.version, a.firstname, a.lastname FROM Author a", Author.class);
List<Author> authors = q.getResultList();
 
for (Author a : authors) {
    System.out.println("Author "
            + a.getFirstName()
            + " "
            + a.getLastName());
}
```

## JPA - Advanced Mapping

Trong OOP thì có khả năng kế thừa giữa các class, thì JPA với kế thừa thì có các loại với việc kế thừa như sau
- SINGLE_TABLE: tất cả các entity và sub-entity đều được gom chung vào một bảng, với các sub-entity khác nhau sẽ có các field khác nhau và các field đó sẽ có giá trị null.
ví dụ ta xem xét 3 class với sơ đồ kế thừa như sau
![x](https://www.tutorialspoint.com/jpa/images/inheritance_strategy.png)

- với SINGLE_TABLE: 
![X](./image/SINGLE_TABLE.png)

- với JOIN_TABLE:
![X](./image/JOIN_TABLE.png)

- với TABLE_PER_CONCRETE_CLASS: cũng tạo ra ba bảng, nhưng STAFF thì các record có giá trị null, thuộc tính của entity staff sẽ chuyển xuống các sub-entity.

## JPA - Criteria API

tương tự như JPQL cũng có các thao tác với DB. nhưng có vài điểm khác biệt là ta có thể sử dụng với dynamic các câu sql.

## JPA - Transaction

Đối với việc sử dụng EntityManagerFactory giữa môi trường multi-thread, nó sẽ đảm bảo thread-safe cho mình. Nhưng với EntityManager thì không. Giả sử nhiều thread cùng sử dụng 1 instance của EntityManager và cùng thực hiện các operation trên đó thì sẽ không đảm bảo thread-safe. Do đó khuyên rằng với mỗi business process thì sử dụng 1 instance của EntityManager để thực hiện và sau đó close nó, vì việc tạo instance của EntityManager có chi phí rất thất, việc tạo instance của EntityManagerFactory thì chi phí cao.

## Hibernate as JPA Provider nghĩa là thế nào

JPA  Its purpose is to provide a set of guidelines that can be followed by JPA providers to create an ORM implementation in a standardized manner.

Hibernate is a JPA Provider, là implementation các API của JPA cung cấp. việc đi như vậy ta có thể dễ dàng chuyển đổi các implementation của JPA interface khi cần thiết.

## Hibernate

Hibernate là một framwork ORM (Object relation mapping) cho phép người lập trình có thể tương tác với database thông qua các class (object).

Hibernate là cài đặt chi tiết của JPA (JPA cung cấp các interface còn hibernate implement các interface đó một cách chi tiết).

Kiến trúc của hibernate
![hibernate architecture](https://stackjava.com/wp-content/uploads/2017/11/hibernate-arch.png)



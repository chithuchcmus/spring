# JPA

Sử dụng JPA để giảm các thao tác với DB, là cầu nối giữa các Object trong chương trình với relational models. Thông thường thì giữa hai định dạng đó có một số điểm khác nhau nhất định như relation model được trình bày theo dạng bảng, còn object được trình bày dưới dạng các thuộc tính kết nối với nhau nên việc liên kết giữa chúng là không đơn giản.

## What is JPA
 
JPA một tập hợp các class vầ phương thức để lưu trữ truy vấn lượng lớn dữ liệu vào cơ sở dữ liệu, được cung cấp bởi Oracle Corporation. Để giảm lượng code cần thiết cho việc quản lí các relational object, có thể sử dụng ‘JPA Provider’ framework.

## JPA - Architecture

Java Persistence APi là source để định nghĩa và quản lí các object (PLAIN OLD JAVA OBJECT (POJO)) như là relation model trong database. Sơ đồ kiến thúc như sau
- EntityManagerFactory: nó vai trò khởi tạo và quản lí các instance của EntityManager.
- EntityManager:  là một interface, quản lí các persistence operations trên các object. giống như là factory for Quert instance. ta có thể truy vấn và cập nhật dữ liệu trong the Persistence Context.
- Entity: là các persistence  object.
- EntityTransaction	: với mỗi EntityManager gắn liền với một EntityTransaction. các hoạt động trên mỗi EntityManager được duy trì bởi EntityTransaction.
- Persistence: là class chứa statis method  để đạt được EntityManagerFactory instance.

![x] (https://www.tutorialspoint.com/jpa/images/jpa_class_level_architecture.png)

## JPA - ORM Components

ORM là một chương trình có khả năng convert dữ liệu từ object sang relational object và ngược lại. Có kiến trúc như sau, chúng giải thích làm thế nào mà object data được lưu trữ vào relational database thông qua 3 phases.

![x](https://www.tutorialspoint.com/jpa/images/object_relational_mapping.png)

Phases 1: chứa các POJO class, service interface và các class  gồm các business component layer chứa các logic  operation và các thuộc tính.
Phases 2: còn được gọi là mapping hay persistence phases,  chứa các JPA provider, mapping filem JPA loader và Object Gird
Phases 3: nó chứa relational data được  kết nối với business component. Chỉ khi business component commit data, nó sẽ được lưu trữ vào database physicallly. Cho đến khi đó thì các dữ liệu thay đổi được lưu ở cache  memory.

các annotation được sử dụng trong ORM

| Annotation         | Description                                                                                                                                                                                     |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| @Entity            | This annotation specifies to declare the class as entity or a table.                                                                                                                            |
| @Table             | This annotation specifies to declare table name.                                                                                                                                                |
| @Basic             | This annotation specifies non constraint fields explicitly.                                                                                                                                     |
| @Embedded          | This annotation specifies the properties of class or an entity whose value instance of an embeddable class.                                                                                     |
| @Id                | This annotation specifies the property, use for identity (primary key of a table) of the class.                                                                                                 |
| @GeneratedValue    | This annotation specifies, how the identity attribute can be initialized such as Automatic, manual, or value taken from sequence table.                                                         |
| @Transient         | This annotation specifies the property which in not persistent i.e. the value is never stored into database.                                                                                    |
| @Column            | This annotation is used to specify column or attribute for persistence property.                                                                                                                |
| @SequenceGenerator | This annotation is used to define the value for the property which is specified in @GeneratedValue annotation. It creates a sequence.                                                           |
| @TableGenerator    | This annotation is used to specify the value generator for property specified in @GeneratedValue annotation. It creates a table for value generation.                                           |
| @AccessType        | This type of annotation is used to set the access type. If you set @AccessType(FIELD) then Field wise access will occur. If you set @AccessType(PROPERTY) then Property wise assess will occur. |
| @JoinColumn        | This annotation is used to specify an entity association or entity collection. This is used in many- to-one and one-to-many associations.                                                       |
| @UniqueConstraint  | This annotation is used to specify the field, unique constraint for primary or secondary table.                                                                                                 |
| @ColumnResult      | This annotation references the name of a column in the SQL query using select clause.                                                                                                           |
| @ManyToMany        | This annotation is used to define a many-to-many relationship between the join Tables.                                                                                                          |
| @ManyToOne         | This annotation is used to define a many-to-one relationship between the join Tables.                                                                                                           |
| @OneToMany         | This annotation is used to define a one-to-many relationship between the join Tables.                                                                                                           |
| @OneToOne          | This annotation is used to define a one-to-one relationship between the join Tables.                                                                                                            |
| @NamedQueries      | This annotation is used for specifying list of named queries.                                                                                                                                   |
| @NamedQuery        | This annotation is used for specifying a Query using static name.                                                                                                                               | 

## JPA Entity Managers


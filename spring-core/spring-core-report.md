# Spring

- [Spring](#spring)
  - [What is IoC?](#what-is-ioc)
  - [What is IoC container?](#what-is-ioc-container)
  - [What is Application Context?](#what-is-application-context)
  - [What is Bean?](#what-is-bean)
  - [Scopes of Bean? Default Scope?](#scopes-of-bean-default-scope)
  - [What is Dependency?](#what-is-dependency)
  - [What is Dependency Injection?](#what-is-dependency-injection)
    - [Constructor-based Dependency Injection](#constructor-based-dependency-injection)
    - [Setter-based Dependency Injection](#setter-based-dependency-injection)
    - [Dependency Resolution Process](#dependency-resolution-process)
    - [Circular dependencies](#circular-dependencies)
  - [@Autowired](#autowired)
    - [@Autowired on Properties](#autowired-on-properties)
    - [@Autowired on Setters](#autowired-on-setters)
    - [@Autowired on Constructors](#autowired-on-constructors)

## What is IoC?

Inversion of Control (IoC) là một nguyên lý thiết kế trong công nghệ phần mềm với các đoạn code khi đưa vào một framework sẽ nhận được luồng điều khiển từ framework hay nói một cách khác là được framework điều khiển. Kiến trúc phần mềm với thiết kế này sẽ đảo ngược quyền điều khiển so với lập trình hướng thủ tục truyền thống. Trong lập trình truyền thống các đoạn code thêm vào sẽ gọi các thư viện nhưng với IoC, framework sẽ gọi các mã thêm vào.


https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring


## What is IoC container?

IoC Container là thành phần thực hiện IoC. Trong Spring, Spring Container (IoC Container) sẽ tạo các đối tượng, lắp rắp chúng lại với nhau, cấu hình các đối tượng và quản lý vòng đời của chúng từ lúc tạo ra cho đến lúc bị hủy bằng cách đọc các configuration metadata như file XML, Java Annotations hoặc Java Code.

Để tạo đối tượng, cấu hình, lắp rắp chúng, Spring Container sẽ đọc thông tin từ các Configuration Metadata và thực thi chúng.

![x](https://docs.spring.io/spring/docs/current/spring-framework-reference/images/container-magic.png)

Có một số cách để cung cấp Configuration Metadata:
- Thông qua file XML: cách sử dụng truyền thống
- Annotation-based configuration: được hỗ trợ từ phiên bản Spring 2.5
- Java-based configuration: được hỗ trợ từ phiên bản Spring 3.0, có nhiều tính năng được cung cấp bởi Spring JavaConfig và trở thành một phần của Spring Framework.

## What is Application Context?

ApplicationContext cũng tương tự như BeanFactory , cũng được sử dụng để mô tả Spring Container. Nó được xây dựng trên interface BeanFactory. Nó cung cấp các tính năng của BeanFactory như lấy đối tượng cần sử dụng trong Spring container bằng phương thức getBean() và cung cấp thêm các tính năng mới.

Điểm khác nhau nổi bật giữa ApplicationContext và BeanFactory: BeanFactory sử dụng cách lazy-instantiation đối tượng được khởi tạo khi được yêu cầu lần đầu, nhưng đối với ApplicationContext sẽ tạo hết tất cả các đối tượng chúng ta cần ngay khi bạn gọi đến Spring container.   

Ngày nay người ta thường sử dụng ApplicationContext đối với các doanh nghiệp, nhưng BeanFactory cũng có thể được sử dụng trong các ứng dụng vừa và nhỏ đối với các thiết bị mobile  hoặc ứng dụng  trên applet.

Các cách sử dụng ApplicationContext:
- ClassPathXmlApplicationContext: dùng để load cấu hình XML nằm trong đường dẫn classpath.
- FileSystemXmlApplicationContext:  dùng để load cấu hình XML trong hệ thống tập tin với đường dẫn tuyệt đối.
- XmlWebApplicationContext:  dùng để load cấu hình XML chứa trong một ứng dụng web.
- AnnotationConfigApplicationContext: Lớp AnnotationConfigApplicationContext  được sử dụng với Java-based configuration cho các định nghĩa bean thay vì sử dụng các file Xml .

## What is Bean?

Bean được quản lí bởi Spring Ioc container. Các bean được khởi tạo bằng configuration metadata mà ta đã cung cấp cho container. Trong bản thân container, các bean được đại diện bởi BeanDefinition.

## Scopes of Bean? Default Scope?

Khi định nghĩa Bean ta có thể tùy chọn khai báo phạm vi cho bean đó, điều này giúp ích rất nhiều trong quá trình phát triển ứng dụng. Spring Framework hỗ trợ 6 scopes, trong đó có 4 scopes chỉ hỗ trợ khi bạn sử dụng web.
- Singleton: là Bean Scope mặc định trong Spring, chỉ có một instance duy nhất được khởi tạo trong toàn bộ Spring IOC Container.
- Prototype: Mỗi khi được gọi thì sẽ có một instance mới được khởi tạo.
- Request: Với mỗi request thì sẽ có một bean tương ứng được khởi tạo, chỉ có giá trị trong ứng dụng Web Spring ApplicationContext.
- Session: Với mỗi vòng đời session thì sẽ có một bean tương ứng được khởi tạo, chỉ có giá trị trong ứng dụng Web Spring ApplicationContext.
- Application: Với mỗi vòng đời của ServletContext thì sẽ có một bean tương ứng được khởi tạo, chỉ có giá trị trong ứng dụng Web Spring ApplicationContext.
- Websocket: Với mỗi vòng đời của Websocket thì sẽ có một bean tương ứng được khởi tạo, chỉ có giá trị trong ứng dụng Web Spring ApplicationContext.

## What is Dependency?

Các object trong cùng một ứng dụng hợp tác dùng nhau để xây dựng và phát triển lên các tính năng, Component A dependency vào Component B khi Component B có sự thay đổi thì Component A cũng phải có sự thay đổi theo để phù hợp với sự thay đổi trước đó. Ví dụ như class Drawing chứa Instance là HCN, bây giờ muốn vẽ thêm các hình vuông và hình tròn thì ta phải thay đổi code mới có thể vẽ được.

## What is Dependency Injection?

Dependency Injection là một mẫu design pattern implement inversion of control cho việc giải quyết các dependencies. Injection là việc truyền các dependency đến dependent object nơi mà chúng cần dùng nó. Là quá trình Object định nghĩa các dependencies của chính nó (là các object mà chúng cần sử dụng để thực hiện chung trình) thông quá các đối số của constructors hoặc property của setter method. Sau đó Container sẽ inject các dependency vào và tạo ra bean.


Lợi ích:
- Tăng khả năng bảo trì, thay đổi code diễn ra dễ dàng bởi vì việc thay đổi logic ít ảnh hưởng đến các module hơn.
- Tăng khả năng tái sử dụng.
- Giảm sự phụ thuộc lẫn nhau giữa các module
- Cho phép ta có thể phát triển đồng thời các tính năng một cách độc lập với nhau.
- Test có thể được thực hiện bằng cách sử dụng mock object

### Constructor-based Dependency Injection

```java
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on a MovieFinder
    private MovieFinder movieFinder;

    // a constructor so that the Spring container can inject a MovieFinder
    public SimpleMovieLister(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
```

Constructor-based DI được hoàn thành khi container gọi constructor với các đối số là các denpendency của class.

Constructor arguments resolution: đôi khi có sự không rõ ràng đối với các tham số truyền vào, trong trường hợp này bean definition  có thể hỗ trợ để cung cấp các đối số theo thứ tự đối với constructor.

- Constructor argument type matching
```java
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg type="int" value="7500000"/>
    <constructor-arg type="java.lang.String" value="42"/>
</bean>
```
- Constructor argument index
```java
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
</bean>
```
- Constructor argument name
```java
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```

### Setter-based Dependency Injection

Tương tự như constructor-base Dependency Injection, thông qua method setter để có thể inject các dependency của class vào, dưới đây là code ví dụ.

```java
public class TextEditor {
   private SpellChecker spellChecker;

   // a setter method to inject the dependency.
   public void setSpellChecker(SpellChecker spellChecker) {
      System.out.println("Inside setSpellChecker." );
      this.spellChecker = spellChecker;
   }
   // a getter method to return spellChecker
   public SpellChecker getSpellChecker() {
      return spellChecker;
   }
   public void spellCheck() {
      spellChecker.checkSpelling();
   }
}
```

Trong file cấu hình ta sử dụng thuộc tính property thay cho constructor-arg ở constructor
```xml
   <bean id = "textEditor" class = "com.tutorialspoint.TextEditor">
      <property name = "spellChecker" ref = "spellChecker"/>
   </bean>

   <!-- Definition for spellChecker bean -->
   <bean id = "spellChecker" class = "com.tutorialspoint.SpellChecker"></bean>
```

Dùng Constructor-based hay setter-based DI? Tùy vào nhu cầu sử dụng của project mà ta sử dụng chúng, có sự phân biệt giũa Constructor và setter như sau:
- Constructor: Readability, Immutability, State Safety
- Setter: Worst Readability, No Immutability, And possibility miss calling one of the setters or call same setter twice with different value (copy-paste bugs)

Người ta khuyến nghị nên sử dụng Constructor vì nó có thể khiến component mà immutable object và đảm bảo rằng không có dependency nào bị null, có trạng thái khởi tạo đầy đủ.


### Dependency Resolution Process

Quy trình xử lí Denpendency 
- ApplicationContext được tạo ra với các cấu hình metadata được mô tả bởi tất cả các bean.
- Với mỗi bean, các dependency được thể hiện dưới dạng các thuộc tính, đối số truyền vào trong constructor. Các dependency này được cung cấp cho bean khi bean được tạo ra.
- Mỗi thuộc tính hoặc đối số của constructor là các giá trị hoặc ref đến các bean khác trong container.
- Mỗi thuộc tính hoặc đối số của constructor là giá trị được chuyển đổi từ kiểu dữ liệu của nó sang kiểu dữ liệu cần thiết của các thuộc tính hoặc đối số. Mặc định thì Spring có thể convert string đến các loại giá trị cơ bản khác  như int, long, String, boolean,...

###  Circular dependencies

Khi ta sử dụng chủ yếu constructor injection, có thể dẫn đến một số vấn đề circular dependency không thể giải quyết. Ví dụ class A yêu cầu instance của class B thông qua constructor insjection và Class B yêu cầu instance của class A thông qua consrtructor injection, nếu cấu hình beans cho class A và B inject lẫn nhau thì Spring IOC sẽ nhận ra vấn đề circular reference lúc chạy chương trình và Throws BeanCurrentlyInCreationException.

Để khắc phục điều này thì ta  có thể sử dụng setter injection thay cho constructor.

## @Autowired

Được sử dụng cho properties, setters, and constructors. @Autowired annotation is part of the Spring framework.

### @Autowired on Properties

Có thể sử dụng trên các thuộc tính của Class. Và sẽ inject khi thuộc tính đó được khởi tạo.
```java
@Component("fooFormatter")
public class FooFormatter {
 
    public String format() {
        return "foo";
    }
}

@Component
public class FooService {
     
    @Autowired
    private FooFormatter fooFormatter;
 
}
```

### @Autowired on Setters

Tương tự như vậy thì @Autowire cũng sẽ được sử dụng trên setter method để tự động inject các Dependency vào khi nó được khởi tạo.
```java
public class FooService {
 
    private FooFormatter fooFormatter;
 
    @Autowired
    public void setFooFormatter(FooFormatter fooFormatter) {
            this.fooFormatter = fooFormatter;
    }
}
```

### @Autowired on Constructors

Tương tự như  trên, được Inject khi dependency được khởi tạo.

```java
public class FooService {
 
    private FooFormatter fooFormatter;
 
    @Autowired
    public FooService(FooFormatter fooFormatter) {
        this.fooFormatter = fooFormatter;
    }
}

```

Ví dụ khi chạy chương trình, nếu có một bean nào đó không được xây dựng trước, thì nó sẽ throw below-quoted exception và ngăn Spring container chạy. để tránh điều đó thì ta có thể sử dụng thuộc tính require= false.
```java
public class FooService {
 
    @Autowired(required = false)
    private FooDAO dataAccessor; 
     
}
```

# Spring boot

Spring boot là một trong các module cung cấp khả năng RAD (Rapid application development) cho Spring framework. Nó có sự phụ thuộc cao vào tính năng`starter templates`.

## What is Starter template?

là các templates chứa các phục thuộc có liên quan `collection of all the relevant transitive dependencies `, cái cần thiết để có thể bắt đầu một chức năng cụ thể. Ví dụ nếu ta muốn tạo ứng dụng Spring WebMVC theo cách thông thường thì ta cần tập hợp các yêu cầu thiết cho ứng dụng đó. Điều đó có thể dẫn đến các `version conflict` có thể dẫn đến nhiều exception hơn. Nhưng với Spring Boot ta chỉ cần cấu hình một cách đơn giản, không cần cung cấp thông tin version trong các dependencies con. Tất cả các phiên bản điều được xử lí và phù hợp với version parents starter.

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.6.RELEASE</version>
    <relativePath />
</parent>
 
<!-- Spring web brings all required dependencies to build web application. -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
Tình huống xảy ra conflict version dependencies:


Dependencies và DependencyManagament:
- Dependencies: các child module luôn bị include hết tất cả các dependency đã được khai báo
- DependencyManagament: chỉ được include khi ở module con chỉ định ra các dependencies đó trong file Pom của module con. Điều này có lợi thế vì ta có thể định nghĩa được version ở module cha và không cần chỉ định lại version, do đó không cần dẫn đến conflict.

Prolemb ![x](https://dzone.com/storage/temp/6202396-dependencies-example-1-e1473694110436.jpg)

Khi chạy chương trình, project không biết nên sử dụng version nào để sử dụng at run time. Vậy version nào sẽ được sử dụng.

dependency mechanism works:  library’s version gần X nhất trong cây dependency sẽ được sử dụng. Nếu cùng cấp với nhau, thì  library’s version nào được tìm thấy đầu tiên sẽ được sử dụng, dựa vào thứ tự được khai báo trong file POM.

How to Solve the Conflict
- import G before Y in file Pom, it work with dependency mechanism
- import Z version 2.0 same level with Y and G, it work if library G support backward compatibility.


Để detech được conflict trong project ta có thể sử dụng  Enforcer – the loving iron fist of Maven.  Nó sẽ  list tree showing all conflicts (if any) inside the project bằng lệnh mvc enforce:enforce.
 

## How does Spring do "auto-configuration"?

Autoconfiguration được kích hoạt với annotaion @EnableAutoConfiguration.  Tự động cấu hình các bean trong Spring context với các môi trường làm việc khác nhau. Dễ dàng làm việc và có thể override lại các cấu hình default đó.


Spring boot sẽ tự động configure ứng dụng Spring dựa trên các  dependencies trên classpath. Spring boot nhận diện các class trong classpath và khi nhận diện được, nó tải các cấu hình của class đã được nhận diện vào ứng d 	ụng.

Auto-configuration cố gắng và sẽ tự động sử dụng các cấu hình của riêng bạn.

Auto-configuration luôn được tự động áp dụng sau khi các beans được đăng kí thành thông.

Spring boot auto-configuration logic is implemented in spring-boot-autoconfigure.jar

### Understanding @Conditional Annotation

@Condition là annotaion cơ bản và được sử dụng bởi hầu hết các annotaion khác trong spring boot.

Auto-configuration đạt được là nhờ @Configuration 

### @RestController

Kết hợp giữa @Controller và @ReponseBody, để hạn chế việc khai báo @ReponseBody ở mỗi method. @ReponseBody sử dụng Jackson 2 để auto convert dữ liệu về dạng Json.  Hoặc ta có thể tùy chỉnh bằng cách sử dụng ResponseEntity.

 @Restcontroller thường kết hợp với RequestMapping để xác định được và handle Request .



## Which embedded server is included by default in SpringBoot?

### What is embedded server

Thông thường thì để deloy ứng dụng trên một máy ảo ta cần đi qua các bước như install java, install web/application server, và deloy chúng. Để đơn giản hóa các bước trên thì ý tưởng về embedded server được tạo ra. Chúng hỗ trợ việc triển khai một ứng dụng và kèm theo đó server bên trong ứng dụng đó.

Each Spring Boot web application includes an embedded web server. 

Spring boot mặc định sẽ include tomcat như một embedded server dependency. Có nghĩa là ta có thể chạy ứng dụng SpringBoot từ command prompt mà không cần các kiến trúc server phức tạp. Ta cố thể loại bỏ hoặc thêm các embbedded server khác tùy ý tùy thuộc vào cấu hình mình muốn. Khi ta muốn tạo một ứng dụng có thể deloy, ta cần embed server bên trong nó.

the spring-boot-starter-web includes Tomcat by including spring-boot-starter-tomcat, but it can use spring-boot-starter-jetty or spring-boot-starter-undertow instead.

Hướng dẫn sử dụng default embedded server tomcat : https://www.baeldung.com/spring-boot-configure-tomcat


###  What is tomcat

Tomcat là một ứng dụng máy chủ gọn nhẹ, thường dùng để deploy các ứng dụng Java Web. Nó được phát triển bởi Apache và hoàn toàn miễn phí.

Thay vì phải tự cài đặt thì java tự động nhúng Tomcat vào trong ứng dụng của mình và ta có thể tùy chỉnh cấu hình đó.


## Minimal config to bootstrap a SpringBoot application?

Để chạy một chương trình, ta cần sử dụng annotaion @SpringBootApplication, anotation này tương đương với @Configuration, @EnableAutoConfiguration, and @ComponentScan được sử dụng cùng nhau.

Bắt đầu bằng cách tải các file cấu hình, cấu hình chúng và bootstap ứng dngj dưa trên application.properties.



```java
// MyApplication.java

@SpringBootApplication
public class MyApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}

//application.properties

### Server port #########
server.port=8080
  
### Context root ########
server.contextPath=/home

```
Để chạy chương trình có thể sử dụng nút run trong IDE hoặc sử dụng câu lệnh `$ java -jar spring-boot-demo.jar`


## Tác dụng của Spring Boot

Giải quyết các vấn đề conflict giữa các dependency. Nó sẽ xác định và import chúng dùm bạn.
Chúng có các thông tin về các compatible version cho tất cả các dependenies. Hạn chế runtime classloader issues.
Tự động cấu hình các phần quan trọng dùm và chỉ cần override lại khi cần thiết. 
Cung cấp embbeded HTTP server Tomcat giúp phát triển và kiểm tra phần mềm một cách nhanh chóng
Tích hợp tốt với các IDE như intelliJ

## Externalized Configuration

Spring boot cho phép sử dụng các cấu hình bên ngoài  do đó ta có thể chạy ứng dụng trên nhiều môi trường khác nhau. Ta có thể sử dụng properties file, YAML files, enviroment variables hoặc command-line argument. 

Các cấu hình Spring boot được ghi đè theo thứ tự sau

### @TestPropertySoucre

Là một annotation có thể load config từ location of property file hoặc inline properties in Spring intergation test. Nó có độ ưu tiên cao nhất nên sẽ override lại tất cả các properties trước đó. Mặc định khi ta khai báo @TestPropertySoucre thì nó sẽ tự động quét trong packet mà class đang ở, lại lấy thuộc tính properties từ đó, nếu không có thì sẽ thrown là một lỗi IllegalStateException.

Nội bộ trong  @TesPropertiesSource thì inline properties có độ ưu tiên cai hơn location properties.

- inheritLocations: mặc định thì sẽ có kế thừa từ superclass các property source location. Do đó có thể load các source properties từ superclass.

```java
 @TestPropertySource("base.properties")
 @ContextConfiguration
 public class BaseTest {
   // ...
 }

 @TestPropertySource("extended.properties")
 @ContextConfiguration
 public class ExtendedTest extends BaseTest {
   // ...
 }
 
 ```
 Ví dụ trên thì BaseTest chỉ có thể load properties file base.properties và  ExtendedTest sử dụng cả base.properties và extended.properties.

- inheritProperties: tương tự như inheritLocation, mặc định sẽ load các properties resouce từ superclass. 

```java
 @TestPropertySource(properties = "key1 = value1")
 @ContextConfiguration
 public class BaseTest {
   // ...
 }
 @TestPropertySource(properties = "key2 = value2")
 @ContextConfiguration
 public class ExtendedTest extends BaseTest {
   // ...
 }

 ```
- properties inline: ví dụ phía dưới

Example to specify property file locations.

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource("/testauth.properties")
public class MyAppTest {
------
} 
```

Example to specify inline properties.
```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource(properties = { "login.user=krishna", "login.pwd=k12" })
public class MyAppTest {
------
}
```

Giả sử ta làm một ví dụ, load test properties file và override lại mains properties file

```xml
login.user=ram
login.pwd=r12
app.name=MyApp 
```

```java
@Configuration
@ComponentScan("com.concretepage")
@PropertySource("classpath:auth.properties")
public class AppConfig {
    @Autowired
    Environment env;
    
    @Bean
    public AuthService myService() {
	AuthService myService = new AuthService(
			env.getProperty("login.user"),
			env.getProperty("login.pwd"),
			env.getProperty("app.name")
		);
	return myService;
    }
}
```

Trong ví dụ trên nó sẽ sử dụng properties được load từ auth.properties

Ta tạo một properties test mới để override lại cái cũ:

testauth.properties
```xml
login.user=shyam
login.pwd=s12 
```

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource(properties = { "login.user=mahesh" })
public class MyAppTest4 extends AnotherTest {
	@Autowired
	private AuthService authService;

	@Test
	public void userTest() throws Exception {
		assertTrue("mahesh".equals(authService.getUser()));
		assertTrue("s12".equals(authService.getPwd()));
		assertTrue("MyApp".equals(authService.getAppName()));
	}
}


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource("/testauth.properties")
class AnotherTest {
	@Autowired
	private AuthService authService;

	@Test
	public void anotherUserTest() throws Exception {
		assertTrue("s12".equals(authService.getPwd()));
		assertTrue("MyApp".equals(authService.getAppName()));
	}
} 
```


Với ví dụ trên thì login.user sẽ được load từ inline properties, login.pwd được load từ testauth.properties file và value app.name được loaad từ auth.properties file


### @PropertySource

Tiện lợi và cung cấp cơ chế cho việc thêm PropertySource.

PropertySource có thể bị override lại trong quá trình đăng kí với application Context.

Spring đề nghị sử dụng Enviroment để có thể lấy các property value hoặc có thể lấy các giá trị bằng @Value

Thường được sử dụng đối với các file có tên không phải là application-[pattern].properties đầu tiên.

```java
@Configuration
@ComponentScan(basePackages = { "com.mkyong.*" })
@PropertySource("classpath:config.properties")
public class AppConfigMongoDB {
	//1.2.3.4
	@Value("${mongodb.url}")
	private String mongodbUrl;

	//hello
	@Value("${mongodb.db}")
	private String defaultDb;

	//To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	private Environment env;

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		String mongodbUrl = env.getProperty("mongodb.url");
		String defaultDb = env.getProperty("mongodb.db");
		
		return new MongoTemplate(mongoDbFactory);

	}
}
```

### Profile-specific Properties and YAML file

Thông thường ở điều kiện mặc định thì profile-specific sẽ sắt mặc định nếu không có active profile nào đang được chạy. profile-specific được load từ cùng location với chuẩn application.properties, nó sẽ luôn ovveride lại non-specific nếu có cùng properties trong đó.

Nếu các  profile được chỉ định thì thằng được applies sau cùng sẽ được áp dụng

YAML file: tất cả các quy tắc đều tương tự như các file .properties nhưng đối với nó thì có khai báo theo cấp bậc nên khi khao báo sẽ dễ dàng hơn.
Cần sử dụng SnakeYAML để parse YAML FILE, nó được cung cấp bởi spring-boot-starter. Kết hợp với @ConfigurationProperties để tự động load các cấu hình bên trong file YAML vào bean. ví dụ như `@ConfigurationProperties("server")`


external outsize packet:

`java -jar app.jar --spring.config.name=application,jdbc --spring.config.location=file:///Users/home/config`

hoặc

`java -jar app.jar --spring.config.location=file:///Users/home/config/jdbc.properties`

hoặc

`mvn spring-boot:run -Dspring.config.location="file:///Users/home/jdbc.properties"`

### Properties

SpringAplication tải tệp properties from application.properties file theo thứ tự các location như sau
- A /config subdirectory of the current directory
- The current directory
- A classpath /config package
- The classpath root


### PlaceHolder

Sau khi định nghĩa các giá trị trong file property, ta có thể dễ dàng sử dụng trong Spring Bean, dễ dàng Inject vào các property value bằng cách sử dụng `@Value`. Nhưng khi trong file property nếu ta inject vào thuộc tính không được định nghĩa thì chương trình bị crash và throw ra missing property causes an exception, để khắc phục thì ta có thể sử dụng `@Value("${sbpg.init.welcome-message:Hello world}")` nếu miss thì ta set giá trị default cho nó là Hello world.

Lưu ý để inject vào field, thì bean đó phải được khởi tạo trước, sau đó mới inject value vào property kế đó. Điều đó làm  the constructor injection is safer.

@Configuation có thể bị null.
complite project to jar
tìm hiểu annotation về việc test
DispatcherSerlet có phải là bean không, cách hoạt động
Verify Bean có tồn tại không


## Spring Boot start test

Chứa các thư viện sau
- Junit 4: tiêu chuẩn cho Unit testing
- Spring Test và Spring Boot test: hỗ trợ cho intergraton test  cho spring boot application
- AssertJ: thư viện Assert
- Mockito: framework Mocking
- Hamcrest: A library of matcher objects
- JsonPath: xpath for json
- JsonAssert

Ta sử dụng `@Mockbean` để có thể tạo ra các bean giả phục vụ cho việc unit test

@WebMvcTest giúp ta test với tầng controller, tự động cấu hình MVC cho ứng dụng, không cần chạy HTTp server.

## Build Java Project

`mvn clean package` build project thành file jar. trong đó có 3 folder phân biệt
- META-INF: chứa mainifest
- BOOT-INF: chứa folder class và lib (các independency và thư viện)
- org: chứa folder loader  trong đó chứa các class được được load.


để inject một project vào một project  khác như một dependency thì khuyến khích nên dùng module để inject vào. nếu dùng artifact thì ta cần phải chỉ định đâu project executable đâu là dependency bằng cách dùng `<classifier>` để chỉ định đâu là dependency

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <classifier>exec</classifier>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Có  4 cách để package  file Jar:
- Skin: chỉ chứa code chương trình
- Thin: chứa thêm thư viện + skin
- Hollow: app  runtime để thực thi chuong trình 
- Fat/uber: chứa thin + hollow
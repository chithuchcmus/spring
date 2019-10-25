
- [Transaction Management](#transaction-management)
  - [What is Global/Local Transaction?](#what-is-globallocal-transaction)
    - [Global transaction](#global-transaction)
    - [Local transaction](#local-transaction)
  - [What is declarative transaction management?](#what-is-declarative-transaction-management)
  - [What is PlatformTransactionManager and its responsibilities?](#what-is-platformtransactionmanager-and-its-responsibilities)
  - [What are @Transactional settings?](#what-are-transactional-settings)
    - [Isolation](#isolation)
    - [Propagation](#propagation)
      - [REQUIRED behavior](#required-behavior)
      - [REQUIRES_NEW behavior](#requiresnew-behavior)
      - [SUPPORTS](#supports)
      - [NOT_SUPPORTED](#notsupported)
      - [MANDATORY](#mandatory)
  - [How @Transactional work](#how-transactional-work)
  - [Aspect Oriented Programming with Spring](#aspect-oriented-programming-with-spring)
    - [AOP Concepts](#aop-concepts)
    - [AOP Proxies](#aop-proxies)
  - [Why do we should annotate the DAO with @Repository?](#why-do-we-should-annotate-the-dao-with-repository)
  - [By default, when will the transacation rollback?](#by-default-when-will-the-transacation-rollback)
  - [What are the values of Transaction Propagation? Example?](#what-are-the-values-of-transaction-propagation-example)
  - [Can we annotate private/protected methods with @Transactional? Do those methods get transactional behavior?](#can-we-annotate-privateprotected-methods-with-transactional-do-those-methods-get-transactional-behavior)
  - [Does @Transactional work with self-invocation?](#does-transactional-work-with-self-invocation)

# Transaction Management

## What is Global/Local Transaction?

### Global transaction

Global Transaction is an application server managed transaction, allowing to work with different transactional resources (this might be two different database, database and message queue). The application server manages global transactions through the JTA and to use JTA (Java Transaction API).


### Local transaction

Local transaction management can be useful in a centralized computing environment where application components and resources are located at a single site, and transaction management only involves a local data manager running on a single machine. Local transactions are easier to be implemented, but can't work with multiple transactional resources.


## What is declarative transaction management?

Spring resolve the disavantage of global and local transactions. it let  application developers use a `consistent programming model` in any environment. You write your code once, and it can benefit from different transaction management strategies in different environments. Spring provide both declarative and programmatic transaction management. 

Spring supports two types of transaction management
- Programmatic transaction management:  Programmatic transaction management approach allows you to manage the transaction with the help of programming in your source code. That gives you extreme flexibility, but it is difficult to maintain.

- Declarative transaction management: Declarative transaction management approach allows you to manage the transaction with the help of configuration instead of hard coding in your source code. This means that you can separate transaction management from the business code.

Declarative transaction management is more flexible than programmatic transaction management, which allows you to control transactions through your code. declarative transaction management can be modularized with the AOP approach. Spring supports declarative transaction management through the Spring AOP framework.


##  What is PlatformTransactionManager and its responsibilities?

In both global and local transaction, we have to manage the transaction by ourselves. If I am using JDBC, then the transaction management API is for JDBC. If I am using Hibernate, then the hibernate transaction API and JTA at application server is for global transactions.

This benefit alone makes Spring Framework transactions a worthwhile abstraction even when you work with JTA.Transactional code can be tested much more easily than if it used JTA directly. the Transaction Exception that can be thrown by any of the PlatformTransactionManager interface's methods is unchecked

Spring framework overcomes all of the above problems by providing an abstraction over the different transaction APIs, providing a consistent programming model. The abstraction is via org.springframework.transaction.PlatformTransactionManager interface. Here is the snippet of the interface:

có nhiều API transactional management thì với mỗi kiểu khác nhau, có API khác nhau khó kiểm soát, PlatformTransactionManager để thống nhất thành một chuẩn để với các loại API khác nhau chỉ cần implement interface đó. platformTransactionManager implementation cũng được coi như là một object hoặc bean khác và được quản lí bởi Spring IOC container.

PlatformTransactionManager API giúp bảo mật và tách biệt code với công nghệ truy cập data, nên có thể mix nhiều loại công nghệ truy cập data trong cùng 1 transaction. Khi cần thay đổi công nghệ truy cập data chỉ cần thay đổi bean definition của transactionManager.


```java
public interface PlatformTransactionManager {
   TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;
   void commit(TransactionStatus status) throws TransactionException;
   void rollback(TransactionStatus status) throws TransactionException;
}
```

The getTransaction(..) method returns a TransactionStatus object, depending on a TransactionDefinition parameter. The returned TransactionStatus might represent a new transaction or can represent an existing transaction.  if a matching transaction exists in the current call stack, a TransactionStatus is associated with a thread of execution.

`The TransactionDefinition` is the core interface of the transaction support in Spring and it is defined as follows

```java
public interface TransactionDefinition {
   int getPropagationBehavior();
   int getIsolationLevel();
   String getName();
   int getTimeout();
   boolean isReadOnly();
}
```

```java
public interface TransactionStatus extends SavepointManager {
   boolean isNewTransaction();
   boolean hasSavepoint();
   void setRollbackOnly();
   boolean isRollbackOnly();
   boolean isCompleted();
}
```
## What are @Transactional settings?

### Isolation

Xác định mối quan hệ của dữ liệu của các transaction với nhau.

`Dirty Read` – A Dirty read is the situation when a transaction reads a data that has not yet been committed. For example, Let’s say transaction 1 updates a row and leaves it uncommitted, meanwhile, Transaction 2 reads the updated row. If transaction 1 rolls back the change, transaction 2 will have read data that is considered never to have existed.

`Non Repeatable read` – Non Repeatable read occurs when a transaction reads same row twice, and get a different value each time. For example, suppose transaction T1 reads data. Due to concurrency, another transaction T2 updates the same data and commit, Now if transaction T1 rereads the same data, it will retrieve a different value.

`Phantom Read` – Phantom Read occurs when two same queries are executed, but the rows retrieved by the two, are different. For example, suppose transaction T1 retrieves a set of rows that satisfy some search criteria. Now, Transaction T2 generates some new rows that match the search criteria for transaction T1. If transaction T1 re-executes the statement that reads the rows, it gets a different set of rows this time.

`ISOLATION_READ_UNCOMMITTED` :Allows to read changes that haven’t yet been committed.It suffer from Scenario 1, Scenario 2, Scenario 3

`ISOLATION_READ_COMMITTED`:Allows reads from concurrent transactions that have been committed. It may suffer from Scenario 2 and Scenario 3. Because other transactions may be updating the data.

`ISOLATION_REPEATABLE_READ` :Multiple reads of the same field will yield the same results untill it is changed by itself.It may suffer from Scenario 3.Because other transactions may be inserting the data

`ISOLATION_SERIALIZABLE`: Scenario 1,Scenario 2,Scenario 3 never happens.It is complete isolation.It involves full locking.It affets performace because of locking.

### Propagation

Xác định mối quan hệ giữa các transaction với nhau:

#### REQUIRED behavior

Có nghĩa là cùng một transaction sẽ được sử dụng nếu chúng đã được tạo ra trong current bean method execution context. Nếu chúng chưa tồn tại thì Spring container sẽ tạo cái mới. Nếu có nhiều method cấu hình sử dụng REQUIRED được gọi lòng nhau thì they will be assigned `distinct logical transactions` but they will all share the same `physical transaction`. In short this means that if an inner method causes a transaction to rollback, the outer method will fail to commit and will also rollback the transaction. Let's see an example:


OUTER BEAN
```java
@Autowired
private TestDAO testDAO;

@Autowired
private InnerBean innerBean;

@Override
@Transactional(propagation=Propagation.REQUIRED)
public void testRequired(User user) {
  testDAO.insertUser(user);
  try{
    innerBean.testRequired();
  } catch(RuntimeException e){
    // handle exception
  }
}
```

INNER BEAN

```java
@Override
@Transactional(propagation=Propagation.REQUIRED)
public void testRequired() {
  throw new RuntimeException("Rollback this transaction!");
}
```

do chúng cùng trong 1 transaction nên nếu inner bean throw ra một exception thì outer transaction cũng sẽ bị fail và rollback()


Lưu ý
```
Note: The only exceptions that set a transaction to rollback state by default are the unchecked exceptions (like RuntimeException). If you want checked exceptions to also set transactions to rollback you must configure them to do so, but this will not be covered in this tutorial.

Note 2: When using declarative transactions, ie by using only annotations, and calling methods from the same bean directly (self-invocation), the @Transactional annotation will be ignored by the container. If you want to enable transaction management in self-invocations you must configure the transactions using aspectj, but this will not be covered in this tutorial.
```

#### REQUIRES_NEW behavior

REQUIRES_NEW behavior means that a new physical transaction will always be created by the container. In other words the inner transaction may commit or rollback independently of the outer transaction, i.e. the outer transaction will not be affected by the inner transaction result: they will run in distinct physical transactions.

OUTER BEAN
```java
@Autowired
private TestDAO testDAO;

@Autowired
private InnerBean innerBean;

@Override
@Transactional(propagation=Propagation.REQUIRED)
public void testRequired(User user) {
  testDAO.insertUser(user);
  try{
    innerBean.testRequired();
  } catch(RuntimeException e){
    // handle exception
  }
}
```

INNER BEAN

```java
@Override
@Transactional(propagation=Propagation.REQUIRES_NEW)
public void testRequired() {
  throw new RuntimeException("Rollback this transaction!");
}
```

The inner method is annotated with REQUIRES_NEW and throws a RuntimeException so it will set its transaction to rollback but will not affect the outer transaction. The outer transaction is paused when the inner transaction starts and then resumes after the inner transaction is concluded. They run independently of each other so the outer transaction may commit successfully.


#### SUPPORTS

nó sẽ thực thi trong phạm phi của transaction nếu được transaction dã được tồn tại trước đó. Nếu không nếu được gọi trực tiếp và transaction chưa được mở, thì nó sẽ thực thi với không có transaction nào

#### NOT_SUPPORTED 

The NOT_SUPPORTED behavior will execute outside of the scope of any transaction. If an opened transaction already exists it will be paused.

#### MANDATORY 

The MANDATORY behavior states that an existing opened transaction must already exist. If not an exception will be thrown by the container.

## How @Transactional work

@EnableTransactionManagement annotation activate annotation-based declarative transaction managemnet. Spring container scans các class bean được quản lí có @transactional annotation. Khi annotation được tìm thấy, nó tạo 1 proxy wrap instance bean lại, thế nên proxy instance sẽ trở thành 1 bean và được deliver bời Spring container khi được requested

Khi call 1 method trong bean, thì đầu tiên proxy instance intercept call đó và kiểm tra xem liệu transaction nên được start không, nếu có thì begin transacion. Sau đó nó sẽ call tới method trong target bean và execute business logic. Khi method của target bean return, proxy commit transaction và return nó.

Mặt khác, khi 1 exception được thrown ra từ bên trong transaction method, Spring check exception type để quyết định xem transaction sẽ commit hay rollback. Mặc định thì, java.lang.RuntimeException và exception kế thừa từ đó sẽ gây transaction rollback: system hay uncked exception. Tuy nhiên java.lang.Exception và các exception kế thừa từ đó sẽ gây commit: application hay checked exception.


## Aspect Oriented Programming with Spring

### AOP Concepts

what is AOP? https://gpcoder.com/5112-gioi-thieu-aspect-oriented-programming-aop/

https://stackoverflow.com/questions/1099025/spring-transactional-what-happens-in-background

là kĩ thuật phân tách một số thành phần của chương trình thành các module nhỏ hơn. Khi chạy chương trình thì sẽ kết hợp các module đó lại với nhau có thể ở thời điểm như compile, load, runtime. giúp tách các phần chương trình, dễ dàng thay đổi nhu chỉnh sửa chỉ thay đổi trên các module nhỏ, dễ bảo trì năng cấp chương trình.

Trong AOP, chương trình của chúng ta được chia thành 2 loại concern:

- Core concern/ Primary concern: là requirement, logic xử lý chính của chương trình.
- Cross-cutting concern: là những logic xử lý phụ cần được thực hiện của chương trình khi core concern được gọi như security, logging, tracing, monitoring, …

Một số thuật ngữ trong AOP
- Aspect: là module chứa toàn bộ cái cross-cutting cần thiết. Trong spring thì chúng được implement bangwf cách sử dụng regular class hoặc regular class annotated với @Aspect
- Join point: là điểm trong chương trình nơi có thể chèn các aspect vào.
- Pointcut: tập hợp một hoặc nhiều điểm nơi mà advice thực thi.
- Advice:những xử lý phụ (crosscutting concern) được thêm vào xử lý chính (core concern), code để thực hiện các xử lý đó được gọi Advice.
- Target Object : là những đối tượng mà advice được áp dụng.
- Weaving: kết nối các aspect với loại chương trình hoặc object để tạo advised object. có thể thực thi ở thời điểm compile time, load time, or at runtime.

Có các loại advice:
- Before advice: advice run before join point nhưng không có khả năng ngăn chặn việc thực thi luồng chương trình.
- After returning advice: run sau điểm join point nếu không xảy ra exception
- After throwing advice: thực thi nếu method xảy ra exception
- After (finally) advice:  được thực hiện sau join point.
- Around advice: 

### AOP Proxies

Spring AOP defaults to using standard `JDK dynamic proxies` for AOP proxies. This enables any interface (or set of interfaces) to be proxied.





## Why do we should annotate the DAO with @Repository?

This annotation also lets the component scanning support find and configure your DAOs and repositories without having to provide XML configuration entries for them.
This is because @Repository is used for translating your unchecked SQL exception to Sring Excpetion and the only exception you should deal is DataAccessException

## By default, when will the transacation rollback?

Mặc định, khi xảy ra runtime exception hoặc error thì transaction sẽ rollback (xảy ra unchecked exception không rollback). Nhưng ta có thể tùy chỉnh khi xảy ra checked exception rollback bằng cách sử dụng RollbackFor. Khi xảy ra exception tại class đó hoặc các subclass thì sẽ rollback. Ngược lại với noRollbackFor, ta có thể cấu hình với các exception cụ thể nào xảy ra, ta vẫn không cho nó rollback.

## What are the values of Transaction Propagation? Example?

Mặc định Propagation ở @transactional là Require.

Example [here](#required-behavior)

## Can we annotate private/protected methods with @Transactional? Do those methods get transactional behavior?

khi khai báo @transactional ở các method private và protected thì method đó sẽ vẫn  chạy bình thường mà không có cơ chế transaction.

Bởi vì: https://stackoverflow.com/questions/34197964/why-doesnt-springs-transactional-work-on-protected-methods

`Due to the proxy-based nature of Spring's AOP framework, protected methods are by definition not intercepted, neither for JDK proxies (where this isn't applicable) nor for CGLIB proxies (where this is technically possible but not recommendable for AOP purposes). As a consequence, any given pointcut will be matched against public methods only!`

## Does @Transactional work with self-invocation?

Cụ thể trong trường hợp này khi self-invocation thì thay vì gọi thông qua được AOP proxy, nó sẽ gọi trực tiếp trong class, giữa các method với nhau. Do không thông qua được AOP Proxy nên không thể thêm được transaction advisor vào các method do đó không thể có behavior transaction.

![x](https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/images/tx.png)


`In proxy mode (which is the default), only 'external' method calls coming in through the proxy will be intercepted. This means that 'self-invocation', i.e. a method within the target object calling some other method of the target object, won't lead to an actual transaction at runtime even if the invoked method is marked with @Transactional!`


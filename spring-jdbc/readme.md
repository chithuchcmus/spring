# JDBC

Dưới đây trình bài về  Core của JDBC, các thao tác, các câu query để tương tác với database, quản lí transaction, và vấn đề SQL Injection.

- [JDBC](#jdbc)
  - [What is JDBC? JDBC Driver?](#what-is-jdbc-jdbc-driver)
    - [What](#what)
    - [JDBC Driver](#jdbc-driver)
  - [Connecting to db](#connecting-to-db)
  - [What is Statement, PreparedStatement, CallableStatement? How/When to use them?](#what-is-statement-preparedstatement-callablestatement-howwhen-to-use-them)
    - [Statement](#statement)
    - [PreparedStatement](#preparedstatement)
    - [CallableStatement](#callablestatement)
  - [What is ResultSet? Updateable ResultSet?](#what-is-resultset-updateable-resultset)
    - [ResultSet interface](#resultset-interface)
    - [Updatable ResultSet](#updatable-resultset)
  - [metadata](#metadata)
  - [Transaction](#transaction)
  - [Close connection](#close-connection)
  - [Connection pool](#connection-pool)
  - [SQL injection](#sql-injection)

## What is JDBC? JDBC Driver?

### What 

JDBC (Java Database Connectivity) là một api cung cấp khả năng try cập và try vấn đối với các db. JDBC có thể work với bất kì DB nào nếu có driver thích hợp.

### JDBC Driver

JDBC Driver là JDBC Api implementation được sử dụng để cho việc kết nối với các loại db khác nhau. Có một số loại JDBC Drivers như:
- loại 1: chứa ánh xạ đến data access API khác. ví dụ như JDBC-ODBC driver
- loại 2: là một implementation được dùng cho client-side libraries of target db. Được gọi là native-api driver.
- loại 3: dùng như middleware để convert JDBC gọi đến db cụ thể. được biết đến như network protocol driver.
- loại 4: kết nối trực tiếp đến db bằng cách convert JDBC call vào db-specify call. database protocol drivers or thin driver.

Loai 4 là loại được sử dụng nhiều vì nó có lợi thế là độc lập với nền tảng. Kết nối trực tiếp đến db server cung cấp hiệu năng tốt hơn khi so sánh với các loại khác. downsite là database-specific given each database has its own specific protocol.

## Connecting to db

Để kết nối DB thì ta cần init driver bằng cách thêm dependency và open db connection.

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>6.0.6</version>
</dependency>
```

Create connection

To open a connection, we can use the getConnection() method of DriverManager class. This method requires a connection URL String parameter:

```java
Connection con = DriverManager
  .getConnection("jdbc:mysql://localhost:3306/myDb", "user1", "pass");
```
hoặc với Spring boot thì ta có thể config trong application.properties, với các loại db khác nhau thì ta cần cấu hình khác nhau

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/restapi
spring.datasource.username=root
spring.datasource.password=
```

## What is Statement, PreparedStatement, CallableStatement? How/When to use them?

Để gửi các câu lệnh SQL đến DB ta có thể sử dụng Statement, PreparedStatement, CallableStatement.

### Statement 

stament interface chứa các hàm cần thiết để  thực hiện các câu lệnh sql. 
```java
Statement stmt = con.createStatement();
```
Thực thi các câu lệnh SQL có thể thông qua 3 loại method:
- executeQuery() for SELECT instructions
- executeUpdate() for updating the data or the database structure
- execute() can be used for both cases above when the result is unknown

Exmalple:
```java
String tableSql = "CREATE TABLE IF NOT EXISTS employees"
  + "(emp_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30),"
  + "position varchar(30), salary double)";
stmt.execute(tableSql);

String insertSql = "INSERT INTO employees(name, position, salary)"
  + " VALUES('john', 'developer', 2000)";
stmt.executeUpdate(insertSql);

String selectSql = "SELECT * FROM employees";
ResultSet resultSet = stmt.executeQuery(selectSql);

```

Thường được sử dụng với các câu lệnh sql có tham số đầu vào cố định không thay đổi các tình huống khác nhau.

### PreparedStatement

chứa các câu lệnh SQL được bên dịch trước chương trình, ta có thể tryền một hoặc nhiều tham số  vào đó.

Example:
```java
String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
PreparedStatement pstmt = con.prepareStatement(updatePositionSql);

pstmt.setString(1, "lead developer");
pstmt.setInt(2, 1);

int rowsAffected = pstmt.executeUpdate();
```

các method thực thi tương tự như Statement. Thường được dùng với các câu lệnh sql có tham số truyền vào không cố định, lấy kết quả từ nơi khác để truyền vào câu lệnh sql.

### CallableStatement

Cho phép gọi các stored procedures, để sử dụng callableStatement thì ta cần sử dụng thêm prepareCall() sau đó có thể setvalue cho các parameter bằng cách sử dụng SetX().

Thường được dùng để thao tác với store procedure trong CSDL.

Example:
```java
String preparedSql = "{call insertEmployee(?,?,?,?)}";
CallableStatement cstmt = con.prepareCall(preparedSql);

cstmt.setString(2, "ana");
cstmt.setString(3, "tester");
cstmt.setDouble(4, 2000);

//If the stored procedure has output parameters, we need to add them using the registerOutParameter() method:

cstmt.registerOutParameter(1, Types.INTEGER);

//Then let's execute the statement and retrieve the returned value using a corresponding getX() method:

cstmt.execute();
int new_id = cstmt.getInt(1);
```

Để làm Được thì cần cấp quyền user để connect với storeProcedure trong db.

```java
con = DriverManager.getConnection(
  "jdbc:mysql://localhost:3306/myDb?noAccessToProcedureBodies=true", 
  "user1", "pass");
```

Lợi ích
- tăng băng thông hơn so với transaction đối với nhiều câu lệnh đơn, ta có thể sử dụng transaction vì nó tự tối ưu trình tự xử lí của chúng, phân tích cú pháp, cải thiện câu lệnh tốt hơn
- nhanh hơn: vì nó cache lại trong db, không cần cải thiện trong phân tích và tối ưu hóa mỗi lần sử dụng
- bảo mật: ta có thể cấp quyền cho việc truy cập
- dynamic: có thể thay đổi độc lập với chương trình, và có thể được sử dụng số lần bất kì.

## What is ResultSet? Updateable ResultSet?

Sau khi thực hiện các câu lệnh Sql, nếu có trả về kq thì nó là một ResultSet Object. có cấu trúc giống như table với các dòng và các cột.

### ResultSet interface

là một bảng dữ liệu mà biểu diễn tập kết quả từ cơ sở dữ liệu mà được trả về bởi các lệnh SQL. Một đối tượng ResultSet duy trì một con trỏ trỏ tới hàng dữ liệu hiện tại của nó. Theo mặc định, đối tượng ResultSet chỉ có thể được di chuyển thuận về trước và nó là không thể cập nhật.

ResultSet có phương thức next() để move đến các line tiếp theo.

example:
```java
String selectSql = "SELECT * FROM employees";
ResultSet resultSet = stmt.executeQuery(selectSql);
         
List<Employee> employees = new ArrayList<>();
         
while (resultSet.next()) {
    Employee emp = new Employee();
    emp.setId(resultSet.getInt("emp_id"));
    emp.setName(resultSet.getString("name"));
    emp.setPosition(resultSet.getString("position"));
    emp.setSalary(resultSet.getDouble("salary"));
    employees.add(emp);
}
```

### Updatable ResultSet

Với resultSet ta có thể duyệt qua ResultSet object mà không thể modify lại các object. Để có thể update Resultset và duyệt theo nhiều cách khác nhau thì ta có thể sử dụng thêm optional với statment.

```java
stmt = con.createStatement(
  ResultSet.TYPE_SCROLL_INSENSITIVE, 
  ResultSet.CONCUR_UPDATABLE
);
```
có một số loại option như sau

- `ResultSet.TYPE_FORWARD_ONLY`: Thiết lập hằng này làm cho con trỏ chỉ có thể di chuyển thuận về trước trong result set.
- `ResultSet.TYPE_SCROLL_INSENSITIVE`: Thiết lập hằng này làm cho con trỏ có thể cuốn về trước hoặc về sau, và result set là không nhạy với các thay đổi tới cơ sở dữ liệu mà được thực hiện sau khi result set được tạo.
- `ResultSet.TYPE_SCROLL_SENSITIVE`: Thiết lập hằng này làm cho con trỏ có thể cuốn về trước hoặc về sau, và result set là nhạy với các thay đổi tới cơ sở dữ liệu mà được thực hiện sau khi result set được tạo.
- `ResultSet.CONCUR_READ_ONLY`: Đây là giá trị mặc định của ResultSet. Làm cho result set là read-only.
- `ResultSet.CONCUR_UPDATABLE`: Làm cho ResultSet là có thể cập nhật.

Khi thêm vào có các method mới như
- first(), last(), beforeFirst(), beforeLast() – to move to the first or last line of a ResultSet or to the line before these
- next(), previous() – to navigate forward and backward in the ResultSet
- getRow() – to obtain the current row number
- moveToInsertRow(), moveToCurrentRow() – to move to a new empty row to insert and back to the current one if on a new row
- absolute(int row) – to move to the specified row
- relative(int nrRows) – to move the cursor the given number of rows

Thông thường nếu sử dụng updateX() chỉ có thể update trên ResultSet mà không thay đổi trong db, có một số hàm thay đổi để persist với db như sau

- updateRow() – to persist the changes to the current row to the database
- insertRow(), deleteRow() – to add a new row or delete the current one from the database
- refreshRow() – to refresh the ResultSet with any changes in the database
- cancelRowUpdates() – to cancel changes made to the current row

example
```java
Statement updatableStmt = con.createStatement(
  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
ResultSet updatableResultSet = updatableStmt.executeQuery(selectSql);
 
updatableResultSet.moveToInsertRow();
updatableResultSet.updateString("name", "mark");
updatableResultSet.updateString("position", "analyst");
updatableResultSet.updateDouble("salary", 2000);
updatableResultSet.insertRow();
```

## metadata

là thông tin, dữ liệu từ database được gọi là metadata
- DatabaseMetadata: chứa các thông tin chung về database như bảng, stored procedure.

VÍ dụ lấy tên các bảng:
```java
DatabaseMetaData dbmd = con.getMetaData();
ResultSet tablesResultSet = dbmd.getTables(null, null, "%", null);
while (tablesResultSet.next()) {
    LOG.info(tablesResultSet.getString("TABLE_NAME"));
}
```

- ResultSetMetadata: được dùng để  tìm thông tin về một số ResultSet như số dòng và tên các cột của chúng.

```java
ResultSetMetaData rsmd = rs.getMetaData();
int nrColumns = rsmd.getColumnCount();
 
IntStream.range(1, nrColumns).forEach(i -> {
    try {
        LOG.info(rsmd.getColumnName(i));
    } catch (SQLException e) {
        e.printStackTrace();
    }
});
```

## Transaction

Mặc định thì mỗi transaction sẽ tự động commit sau khi hoàn thành câu lệnh của nó. ta có thể modyfi lại bằng cách set autoCommit of connection là false và sử dụng method commit() và rollback() để điều khiển transaction

exmalple:
```java
String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
PreparedStatement pstmt = con.prepareStatement(updatePositionSql);
pstmt.setString(1, "lead developer");
pstmt.setInt(2, 1);
 
String updateSalarySql = "UPDATE employees SET salary=? WHERE emp_id=?";
PreparedStatement pstmt2 = con.prepareStatement(updateSalarySql);
pstmt.setDouble(1, 3000);
pstmt.setInt(2, 1);
 
boolean autoCommit = con.getAutoCommit();
try {
    con.setAutoCommit(false);
    pstmt.executeUpdate();
    pstmt2.executeUpdate();
    con.commit();
} catch (SQLException exc) {
    con.rollback();
} finally {
    con.setAutoCommit(autoCommit);
}
```
## Close connection

```java
con.close();
```
Khi không đống kết nối connection, thì trong connection pool, nó sẽ để trạng thái là tiếp tục sử dụng và không để reuse các connection đó cho các thread hoặc process khác được, nó phải cấp cái mới mà chi phí cho việc cấp đó rất tốn kém.

Nếu không có trong connectionPool thì socket kết nối với db sẽ mở mãi, tốn tài nguyên của máy, tồn tại cho đến khi socket đó bị lỗi hoặc restart lại máy

It is important to close a JDBC Connection once you are done with it. A database connection takes up an amount of resources, both inside your own application, but especially on the database server. Therefore, keeping database connections open that are unused will require the database to keep unnecessary resources allocated for the connection.



## Connection pool

https://examples.javacodegeeks.com/core-java/apache/commons/dbcp/dbcp-connection-pooling-example/


## SQL injection

Lợi dụng kẻ hở khi viết đến câu lệnh sql để lấy dữ liệu, thông tin từ db từ đó có thể lộ thông tin khách hàng và chiếm quyền kiểm soát db.

ví dụ 

```sql
var sql = "SELECT * FROM Users WHERE Username = '" + username + "' AND Password = '" + password + "'";
```

nếu password là ` ' OR '' = ''` thì câu lệnh trên luôn luôn đúng.

Một số biện pháp để phòng tránh sql injection
- lọc dữ liệu từ người dùng: lọc ra các câu lệnh có dấu chấm câu như `'` và `;`
- Không cộng chuỗi để tạo sql
- không hiển thị message và lỗi của db với người dùng
- phân quyền rõ ràng: nếu user chỉ cần lấy dữ liệu từ một số bản thì cấp quyền cho họ chi truy cập một số bản như vậy, phòng chống chiếm quyền kiểm soát tất cả db.
- Sử dụng framework có sẵn, các framework tự tạo câu lệnh sql để sử dụng. 
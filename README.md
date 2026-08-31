# Student Management System (Java + JDBC + MySQL)

A console-based Student Management System that performs **Add, Update, Delete,
and View** operations on student records stored persistently in a MySQL
database, using plain JDBC.

## Project Structure

```
StudentManagementSystem/
├── schema.sql              # Creates the database & students table
├── src/
│   ├── Student.java        # Model class (POJO)
│   ├── DBConnection.java   # JDBC connection handler
│   ├── StudentDAO.java     # CRUD database operations
│   └── Main.java           # Console menu / entry point
└── README.md
```

## Prerequisites

1. **Java JDK** 8 or higher installed.
2. **MySQL Server** installed and running.
3. **MySQL Connector/J** (JDBC driver) — download the `.jar` from:
   https://dev.mysql.com/downloads/connector/j/
   (or via Maven artifact `com.mysql:mysql-connector-j`)

## Setup Steps

### 1. Create the database

Run the provided SQL script in MySQL:

```bash
mysql -u root -p < schema.sql
```

This creates a `student_db` database with a `students` table.

### 2. Configure the connection

Open `src/DBConnection.java` and update these three constants to match your
MySQL setup:

```java
private static final String DB_URL =
        "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password_here";
```

### 3. Compile

Place the MySQL Connector/J `.jar` (e.g. `mysql-connector-j-8.4.0.jar`) in a
`lib/` folder, then compile:

**Linux / macOS:**
```bash
javac -d bin src/*.java
```

**Windows:**
```cmd
javac -d bin src\*.java
```

### 4. Run

**Linux / macOS:**
```bash
java -cp "bin:lib/mysql-connector-j-8.4.0.jar" Main
```

**Windows:**
```cmd
java -cp "bin;lib\mysql-connector-j-8.4.0.jar" Main
```

> Tip: If you're using an IDE (IntelliJ IDEA, Eclipse, NetBeans), just add
> the connector `.jar` to your project's build path/libraries and run
> `Main.java` directly.

## Features

| Module | Description |
|--------|-------------|
| **Add**    | Insert a new student record (name, age, course, email, phone) |
| **View**   | List all students, or look up a single student by ID |
| **Update** | Edit an existing student's details (blank input keeps the current value) |
| **Delete** | Remove a student record after confirmation |

## Notes

- `PreparedStatement` is used throughout to prevent SQL injection.
- `email` is set as `UNIQUE` in the schema — inserting a duplicate email will
  fail with a clear error message rather than crash the app.
- The DAO layer (`StudentDAO`) is decoupled from the UI (`Main`), so you can
  later swap the console interface for a GUI (Swing/JavaFX) or REST API
  without touching the database logic.
- Feel free to extend `Student` with more fields (e.g. address, department)
  — just remember to update `schema.sql`, `StudentDAO`, and the `Main` menu
  prompts accordingly.

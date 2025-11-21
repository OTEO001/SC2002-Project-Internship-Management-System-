# SC2002 Internship Placement Management System (IPMS)

[![Java](https://img.shields.io/badge/Java-SE-blue)](https://www.oracle.com/java/)
[![OOP](https://img.shields.io/badge/Design-OOP-green)](https://en.wikipedia.org/wiki/Object-oriented_programming)
[![SOLID](https://img.shields.io/badge/Principles-SOLID-orange)](https://en.wikipedia.org/wiki/SOLID)

**UML Class Diagram | Sequence Diagrams | SOLID Design Principles | OOP Concepts | Java**

**Team:** Pang Lu Hiang Victor | Lim Kwan Yit, Calvin | Teo Kok Wei, Oscar | Kannaiyan Ishwaryaa | Low Hong Wei

**Docs:** [Report](new_folder/) | [UML Class Diagram](diagrams/) | [JavaDocs](docs/) | [Test Cases](#test-cases--results)

---

## Project Overview

The **SC2002 Internship Management System (IMS)** is a Java-based application developed as part of the SC2002 Object-Oriented Design & Programming course. The system streamlines the internship placement workflow for Students, Company Representatives, and Career Centre Staff, providing features such as:

- ✅ User authentication & role-based menus
- ✅ Internship opportunity creation & approval
- ✅ Student internship application
- ✅ Visibility controls for internship listings
- ✅ Validation, approval workflows, and business rules enforcement

The system is architected with strong emphasis on **encapsulation**, **abstraction**, **polymorphism**, **inheritance**, and **SOLID principles**, ensuring modularity, maintainability, and extensibility.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [System Design](#system-design)
  - [OOP Principles](#oop-principles)
  - [SOLID Principles](#solid-principles)
  - [Design Trade-offs](#design-trade-offs)
- [UML & Sequence Diagrams](#uml--sequence-diagrams)
- [Test Cases & Results](#test-cases--results)
- [Additional Features](#additional-features)
- [Reflection](#reflection)
- [Future Improvements](#future-improvements)

---

## Project Structure

```
2002Project/
├── src/
│   ├── entity/                 # Domain models
│   │   ├── User.java
│   │   ├── Student.java
│   │   ├── CompanyRepresentative.java
│   │   ├── CareerCenterStaff.java
│   │   ├── InternshipOpportunity.java
│   │   ├── Application.java
│   │   └── [Enums]
│   ├── repository/            # Data access interfaces & implementations
│   │   ├── IUserRepository.java
│   │   ├── UserRepository.java
│   │   ├── IInternshipRepository.java
│   │   ├── InternshipRepository.java
│   │   ├── IApplicationRepository.java
│   │   └── ApplicationRepository.java
│   ├── service/               # Business logic
│   │   ├── IAuthenticationService.java
│   │   ├── AuthenticationService.java
│   │   ├── IStudentService.java
│   │   ├── StudentService.java
│   │   ├── ICompanyRepService.java
│   │   ├── CompanyRepService.java
│   │   ├── IStaffService.java
│   │   └── StaffService.java
│   ├── controller/            # Request handlers
│   │   ├── AuthenticationController.java
│   │   ├── StudentController.java
│   │   ├── CompanyRepController.java
│   │   └── StaffController.java
│   ├── boundary/              # User interface (CLI menus)
│   │   ├── MainMenu.java
│   │   ├── StudentMenu.java
│   │   ├── CompanyRepMenu.java
│   │   └── StaffMenu.java
│   └── Main.java              # Main application entry point
├── data/
│   ├── sample_staff_list.csv
│   ├── sample_student_list.csv
│   └── sample_company_representative_list.csv
├── diagrams/
│   └── [UML & Sequence Diagrams]
└── README.md                  # This file
```

---

## Setup Instructions

### Using Terminal:

1. **Navigate to the project directory:**
   ```bash
   cd /path/to/2002Project
   ```

2. **Compile the Java files:**
   ```bash
   cd src
   javac -d ../bin **/*.java
   ```

3. **Run the application:**
   ```bash
   cd ..
   java -cp bin Main
   ```

You should now be able to access the IPMS system through the command-line interface.

---

## System Design

### OOP Principles

#### 1. **Encapsulation**
The system follows the **Boundary–Controller–Entity (BCE)** model:
- **Boundary classes:** Handle CLI user interaction
- **Controller classes:** Coordinate user requests and delegate to services
- **Entity classes:** Represent structured domain data
- **Service classes:** Contain business logic

This separation prevents unintended cross-layer interference and maintains loose coupling.

#### 2. **Inheritance**
A base abstract `User` class stores common attributes (ID, name, password). Subclasses extend it:
- `Student`
- `CompanyRepresentative`
- `CareerCenterStaff`

This promotes code reuse and consistency.

#### 3. **Polymorphism**
Controllers rely on service interfaces (`IStudentService`, `ICompanyRepService`, `IStaffService`). Actual behaviour is determined by concrete subclass implementations at runtime, e.g., during login.

#### 4. **Abstraction**
Interfaces (e.g., `IUserRepository`, `IAuthenticationService`) hide storage/logic implementation details, enabling easy extension such as switching from CSV to database in the future.

---

### SOLID Principles

#### **Single Responsibility Principle (SRP)**
Each layer has one job:
- **Boundary** → display & read input
- **Controller** → coordinate
- **Service** → business logic
- **Repository** → data persistence
- **Entity** → data structure only

#### **Open/Closed Principle (OCP)**
Interfaces allow new implementations without modifying existing code (e.g., new repository formats).

#### **Liskov Substitution Principle (LSP)**
All `User` subclasses behave predictably when treated as a `User`, supporting safe polymorphic substitution throughout flows (e.g., authentication).

#### **Interface Segregation Principle (ISP)**
Interfaces are role-specific and concise (no "fat" interfaces). E.g., `IStudentService` only includes methods relevant to students.

#### **Dependency Inversion Principle (DIP)**
- Controllers depend only on abstract services, not concrete implementations.
- Services depend on repository interfaces, not CSV-based implementations.

---

### Design Trade-offs

- **Trade-off:** Introducing service and repository interfaces increased the number of files.
- **Benefit:** Extensibility, testability, and modularity drastically improved.
- Adding a service layer centralised workflow logic, preventing duplicated rules across controllers.

---

## UML & Sequence Diagrams

### Class Diagram
The system consists of 5 main packages:
- **Boundary**
- **Controller**
- **Entity**
- **Repository**
- **Service**

![Class Diagram](diagrams/Class%20Diagram.vpp)

### Sequence Diagrams
1. **Company Representative registers for a new account**
2. **Company Representative creates an internship opportunity, & Staff approval**
3. **Student Applies for Internship & Company Representative View Application**

![Sequence Diagrams](diagrams/)

---

## Test Cases & Results

A comprehensive test suite was conducted covering:

### 1. **Staff Login**
- ✅ Valid login → success
- ❌ Wrong password / wrong user ID → correct error messages

### 2. **Staff Approves Company Representative**
- ✅ Approval updates status
- ❌ Invalid UserID → appropriate error

### 3. **Internship Creation by Company Representative**
- ✅ Valid: success
- ❌ Invalid input formats → errors

### 4. **Staff Approves Internship Opportunity**
- ✅ Valid approval → success
- ❌ Invalid ID → error
- ❌ Opportunity not pending → error

Additional test cases are provided in the repository.

---

## Additional Features

### 1. **Password Strength Validation**
- Implemented in `AuthenticationService.validatePasswordStrength()`.
- Minimum length: 6 characters.

### 2. **Comprehensive Input Validation**
- `User.validateUserId()` is overridden in subclasses
- Staff emails must be `@ntu.edu.sg`
- Company emails must match corporate domains
- Students must follow NTU matriculation regex: `U\d{7}[A-Z]`

### 3. **Duplicate Prevention System**
- Duplicate application prevention for students
- Duplicate email prevention for company reps
- Both throw runtime exceptions during validation.

---

## Reflection

### **Difficulties Encountered:**
- Confusion between controller vs. service responsibilities
- Maintaining consistency across workflows (e.g., auto-withdrawing applications after acceptance)
- Resolved by re-aligning with BCE principles and centralising business logic in services

### **Knowledge Gained:**
- Importance of initial system design before coding
- Deepened understanding of OOP and SOLID
- Improved ability to design reusable, modular components
- UML and BCE modelling reinforced structural clarity

---

## Future Improvements

- Implementing a **GUI** to replace CLI for better UX
- Adding **automated testing (JUnit)** for earlier bug detection
- Migrating from **CSV to database** for better data management
- Enhanced **security features** (password hashing, session management)

---

## Contributors

| Name | Role |
|------|------|
| Pang Lu Hiang Victor | Developer |
| Lim Kwan Yit, Calvin | Developer |
| Teo Kok Wei, Oscar | Developer |
| Kannaiyan Ishwaryaa | Developer |
| Low Hong Wei | Developer |

---

## License

This project is developed for educational purposes as part of the SC2002 course at Nanyang Technological University.

---

**Last Updated:** November 2025


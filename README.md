SC2002 Internship Placement Management System (IPMS)

UML Class Diagram | Sequence Diagrams | SOLID Design Principles | OOP Concepts | Java

Team: Pang Lu Hiang Victor  | Lim Kwan Yit, Calvin | Teo Kok Wei, Oscar | Kannaiyan Ishwaryaa | Low Hong Wei
 
 Docs: Report | UML Class Diagram | JavaDocs | Test Cases


Project Overview
The SC2002 Internship Management System (IMS) is a Java-based application developed as part of the SC2002 Object-Oriented Design & Programming course. The system streamlines the internship placement workflow for Students, Company Representatives, and Career Centre Staff, providing features such as:
User authentication & role-based menus




Polymorphism
Controllers rely on service interfaces (IStudentService, ICompanyRepService, IStaffService).
 Actual behaviour is determined by concrete subclass implementations at runtime, e.g., during login.
Abstraction
Interfaces (e.g., IUserRepository, IAuthenticationService) hide storage/logic implementation details, enabling easy extension such as switching from CSV to database in the future.
SOLID Design Principles
Single Responsibility Principle (SRP)
Each layer has one job:
Boundary → display & read input
Controller → coordinate
Service → business logic
Repository → data persistence
Entity → data structure only
Open/Closed Principle (OCP)
Interfaces allow new implementations without modifying existing code (e.g., new repository formats).

Liskov Substitution Principle (LSP)
All User subclasses behave predictably when treated as a User, supporting safe polymorphic substitution throughout flows (e.g., authentication).

Interface Segregation Principle (ISP)
Interfaces are role-specific and concise (no “fat” interfaces). E.g., IStudentService only includes methods relevant to students.

Dependency Inversion Principle (DIP)
Controllers depend only on abstract services, not concrete implementations.Services depend on repository interfaces, not CSV-based implementations.
2. Design Trade-offs
Introducing service and repository interfaces increased the number of files
However, extensibility, testability, and modularity drastically improved
Adding a service layer centralised workflow logic, preventing duplicated rules across controllers
3. UML Class Diagram & Sequence Diagram

Class Diagram:
The system consists of 5 main packages:
Boundary
Controller
Entity
Repository
Service

Sequence Diagrams:
Company Representative registers for a new account
Company Representative creates an internship opportunity, & Staff approval
Student Applies for Internship & Company Representative View Application

4. Test Cases & Results
A comprehensive test suite was conducted covering:

1. Staff Login
Valid login → success
Wrong password / wrong user ID → correct error messages

2. Staff Approves Company Representative
Approval updates status
Invalid UserID → appropriate error

3. Internship Creation by Company Representative
Valid: success
Invalid input formats → errors

4. Staff Approves Internship Opportunity
Valid approval → success
Invalid ID → error
Opportunity not pending → error
Additional test cases are provided in the GitHub repository.

5. Additional Features

1. Password Strength Validation
Implemented in AuthenticationService.validatePasswordStrength().
Minimum length: 6 characters.

2. Comprehensive Input Validation
User.validateUserId() is overridden in subclasses
Staff emails must be @ntu.edu.sg
Company emails must match corporate domains
Students must follow NTU matriculation regex: U\d{7}[A-Z]

3. Duplicate Prevention System
Duplicate application prevention for students
Duplicate email prevention for company reps
Both throw runtime exceptions during validation.


6. Reflection
Difficulties Encountered: 
Confusion between controller vs. service responsibilities
Maintaining consistency across workflows (e.g., auto-withdrawing applications after acceptance)
Resolved by re-aligning with BCE principles and centralising business logic in services

Knowledge Gained: 
Importance of initial system design before coding
Deepened understanding of OOP and SOLID
Improved ability to design reusable, modular components
UML and BCE modelling reinforced structural clarity

Future Improvements: 
Implementing a GUI to replace CLI for better UX
Adding automated testing (JUnit) for earlier bug detection


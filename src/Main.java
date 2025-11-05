import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Superclass User
class User {
    String name;
    int id;
    String role;

    public User(String name, int id, String role) {
        this.name = name;
        this.id = id;
        this.role = role;
    }

    public void login() {
        System.out.println(name + " logged in as " + role);
    }

    public void logout() {
        System.out.println(name + " logged out.");
    }
}


class Employee extends User {
    boolean isClockedIn;
    List<AttendanceRecord> attendanceRecords;

    public Employee(String name, int id) {
        super(name, id, "Employee");
        this.isClockedIn = false;
        this.attendanceRecords = new ArrayList<>();
    }

    public void clockIn() {
        if (!isClockedIn) {
            isClockedIn = true;
            AttendanceRecord record = new AttendanceRecord(id, LocalDateTime.now(), null);
            attendanceRecords.add(record);
            System.out.println(name + " clocked in at " + LocalDateTime.now());
        } else {
            System.out.println(name + " is already clocked in.");
        }
    }

    public void clockOut() {
        if (isClockedIn) {
            isClockedIn = false;
            AttendanceRecord lastRecord = attendanceRecords.get(attendanceRecords.size() - 1);
            lastRecord.setCheckOutTime(LocalDateTime.now());
            System.out.println(name + " clocked out at " + LocalDateTime.now());
        } else {
            System.out.println(name + " is not clocked in.");
        }
    }

    public void viewAttendance() {
        System.out.println("Attendance records for " + name + ":");
        for (AttendanceRecord record : attendanceRecords) {
       System.out.println("Check In: " + record.getCheckInTime() + ", Check Out: " + record.getCheckOutTime() + ", Hours Worked: " + record.calculateHoursWorked());
        }
    }
}


class Manager extends User {
    List<Employee> teamMembers;

    public Manager(String name, int id) {
        super(name, id, "Manager");
        this.teamMembers = new ArrayList<>();
    }

    public void generateReport() {
        System.out.println("Generating attendance report for team:");
        for (Employee employee : teamMembers) {
            employee.viewAttendance();
        }
    }

    public void approveLeave(Employee employee) {
        System.out.println("Leave approved for " + employee.name);
    }
}


class Admin extends User {
    List<User> allUsers;
    AttendanceSystem system; 

    public Admin(String name, int id) {
        super(name, id, "Admin");
        this.allUsers = new ArrayList<>();
        this.system = new AttendanceSystem(); 
    }
public void addUser(User user) {
 for (User existingUser : allUsers) {
        if (existingUser.id == user.id) {
            System.out.println("Error: User ID " + user.id + " already exists. Cannot add user.");
            return; 
        }
    }
    allUsers.add(user); 
    system.addUser(user); 
    System.out.println(user.name + " added to the system.");
}
public void deleteUser(int userId) {
    for (int i = 0; i < allUsers.size(); i++) {
        if (allUsers.get(i).id == userId) {
            allUsers.remove(i);
            break;
        }
    }
    for (int i = 0; i < system.users.size(); i++) {
        if (system.users.get(i).id == userId) {
            system.users.remove(i);
            break;
        }
    }
    System.out.println("User with ID " + userId + " removed from the system.");
}
    public void generateMonthlyReport() {
    System.out.println("Generating monthly report for all users:");
    for (User user : allUsers) {
        if (user instanceof Employee) {
            Employee employee = (Employee) user;
            System.out.println("Attendance records for Employee: " + employee.name);
            employee.viewAttendance();
        } else if (user instanceof Manager) {
            Manager manager = (Manager) user;
            System.out.println("Attendance records for Manager: " + manager.name);
            System.out.println(manager.name + " currently has no specific attendance records.");
        }
    }
}
    public void trackAttendance(Employee employee) {
        system.trackAttendance(employee); 
    }
}

class AttendanceRecord {
    int userId;
    LocalDateTime checkInTime;
    LocalDateTime checkOutTime;

    public AttendanceRecord(int userId, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        this.userId = userId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public long calculateHoursWorked() {
        if (checkOutTime != null) {
            return java.time.Duration.between(checkInTime, checkOutTime).toHours();
        }
        return 0;
    }
}


    class AttendanceSystem {
    List<User> users;
    List<AttendanceRecord> records;

    public AttendanceSystem() {
        this.users = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void trackAttendance(Employee employee) {
        if (employee.isClockedIn) {
            employee.clockOut();
        } else {
            employee.clockIn();
        }
    }

    public void sendNotification(String message) {
        for (User user : users) {
            System.out.println("Notification to " + user.name + " (" + user.role + "): " + message);
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Admin admin = new Admin("Sara Admin", 1);
        Manager manager = new Manager("Saly Manager", 2);
        Employee employee1 = new Employee("Salwa Employee1", 3);
        Employee employee2 = new Employee("Samar Employee2", 4);

        admin.addUser(admin);
        admin.addUser(manager);
        admin.addUser(employee1);
        admin.addUser(employee2);

        manager.teamMembers.add(employee1);
        manager.teamMembers.add(employee2);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Attendance System =====");
            System.out.println("1. Admin - Add User");
            System.out.println("2. Admin - Delete User");
            System.out.println("3. Employee1 - Clock In/Out");
            System.out.println("4. Admin - Generate Monthly Report");
            System.out.println("5. Manager - Generate Attendance Report");
            System.out.println("6. Employee - Request Leave");
            System.out.println("7. Manager - Approve Leave");
            System.out.println("8. Admin - Send Notification");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter name for new user: ");
                    String name = scanner.next();
                    System.out.print("Enter ID for new user: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter role (Employee/Manager): ");
                    String role = scanner.next();

                    User newUser = role.equalsIgnoreCase("Manager") ?
                            new Manager(name, id) :
                            new Employee(name, id);
                    admin.addUser(newUser);
                 if (newUser instanceof Employee) {
            manager.teamMembers.add((Employee) newUser);
        }
                    break;

                case 2:
                    System.out.print("Enter ID of user to delete: ");
                    int userId = scanner.nextInt();
                    admin.deleteUser(userId);
                    break;
                    
                    case 3:  
            System.out.print("Enter Employee ID for Clock In/Out: ");
            int empId = scanner.nextInt();

            boolean found = false;
            for (User user : admin.allUsers) {
                if (user instanceof Employee && user.id == empId) {
                    Employee employee = (Employee) user;
                    admin.trackAttendance(employee); 
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Error: Employee with ID " + empId + " not found.");
            }
            break;

                case 4:
                    admin.generateMonthlyReport();
                    break;

                case 5:
                    manager.generateReport();
                    break;


                case 6: 
              System.out.print("Enter employee ID to request leave: ");
              int empIdRequestLeave = scanner.nextInt();
              scanner.nextLine(); 
              boolean employeeFoundForLeave = false;

      for (User user : admin.allUsers) {
        if (user instanceof Employee && user.id == empIdRequestLeave) {
            Employee employee = (Employee) user;
            System.out.print("Enter leave reason: ");
            String leaveReason = scanner.nextLine();
            System.out.println(employee.name + " requested leave for reason: " + leaveReason);
            employeeFoundForLeave = true;
            break;
        }
    }
    if (!employeeFoundForLeave) {
        System.out.println("No employee found with ID " + empIdRequestLeave);
    }
    break;

   case 7: 
    System.out.print("Enter employee ID to approve leave: ");
    int empIdApproveLeave = scanner.nextInt();
    boolean employeeFoundForApproval = false;
    
 
    for (User user : admin.allUsers) {
        if (user instanceof Employee && user.id == empIdApproveLeave) {
            Employee employee = (Employee) user;
            System.out.println("Leave approved for " + employee.name);
            employee.clockOut(); 
            employeeFoundForApproval = true;
            break;
        }
    }

    if (!employeeFoundForApproval) {
        System.out.println("No employee found with ID " + empIdApproveLeave);
    }
    break;
                case 8: 
                    System.out.print("Enter notification message: ");
                    scanner.nextLine(); // Clear the buffer
                    String message = scanner.nextLine();
                    admin.system.sendNotification(message);
                    break;

                case 9:
                    System.out.println("Exiting system...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
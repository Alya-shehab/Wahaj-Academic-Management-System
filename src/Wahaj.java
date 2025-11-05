package Wahaj;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Main program logic here
    }
}

class Project {
    private String name;
    private String description;
    private List<Task> tasks;
    private List<Student> members;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addMember(Student student) {
        members.add(student);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Student> getMembers() {
        return members;
    }
}

class Task {
    private String taskName;
    private LocalDate deadline;
    private Student assignedStudent;
    private boolean completed;

    public Task(String taskName, LocalDate deadline, Student assignedStudent) {
        this.taskName = taskName;
        this.deadline = deadline;
        this.assignedStudent = assignedStudent;
        this.completed = false;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public String getTaskName() {
        return taskName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Student getAssignedStudent() {
        return assignedStudent;
    }

    public boolean isCompleted() {
        return completed;
    }
}

class Student {
    private String studentName;
    private String studentId;

    public Student(String studentName, String studentId) {
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }
}

class Course {
    private String courseName;
    private List<Grade> grades;

    public Course(String courseName) {
        this.courseName = courseName;
        this.grades = new ArrayList<>();
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Grade> getGrades() {
        return grades;
    }
}

class Grade {
    private double score;
    private String assignmentName;

    public Grade(double score, String assignmentName) {
        this.score = score;
        this.assignmentName = assignmentName;
    }

    public double getScore() {
        return score;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}

class Reminder {
    private String reminderText;
    private LocalDate reminderDate;

    public Reminder(String reminderText, LocalDate reminderDate) {
        this.reminderText = reminderText;
        this.reminderDate = reminderDate;
    }

    public String getReminderText() {
        return reminderText;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }
}

class LoginSystem {
    private String userName;
    private String password;

    public LoginSystem() {}

    public boolean login(String username, String password) {
        return true;
    }

    public void register(String username, String password) {}
}

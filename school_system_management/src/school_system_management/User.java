package school_system_management;

public class User  {
    private int ID;
    private String name;
    private String email;
    private String password;
    private String department;

    public User(int ID, String name, String email, String password) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

class Admin extends User {
    public Admin(int ID, String name, String email, String password) {
        super(ID, name, email, password);
    }
}

class Student extends User {
    public Student(int ID, String name, String email, String password, String department) {
        super(ID, name, email, password);
        setDepartment(department);
    }
}

class Teacher extends User {
    private String subject;

    public Teacher(int ID, String name, String email, String password, String department, String subject) {
        super(ID, name, email, password);
        setDepartment(department);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
}

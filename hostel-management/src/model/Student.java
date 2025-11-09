package model;

public class Student {
    private int id;
    private String name;
    private String rollno;
    private String course;
    private String year;
    private String phone;

    public Student() {}

    public Student(int id, String name, String rollno, String course, String year, String phone) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.course = course;
        this.year = year;
        this.phone = phone;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollno() { return rollno; }
    public void setRollno(String rollno) { this.rollno = rollno; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}

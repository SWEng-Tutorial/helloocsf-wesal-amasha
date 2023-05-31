package il.cshaifasweng.OCSFMediatorExample.entities.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import static il.cshaifasweng.OCSFMediatorExample.entities.App.session;

@Entity
@Table(name = "students")
public class Student implements Serializable {

    @Id
    private int student_id;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades = new ArrayList<>();


    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int id) {
        this.student_id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Grade> getGrades() {
        return grades;
    }



    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public void addGrade(Course course,int grade)
    {
        Grade grade1 = new Grade(this,course, grade);
        session.save(grade1);
        session.flush();
        grades.add(grade1);

    }

    public Student() {

    }

    public Student(int id ,String firstName, String lastName) {
        this.student_id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grades = new ArrayList<Grade>();
    }



}
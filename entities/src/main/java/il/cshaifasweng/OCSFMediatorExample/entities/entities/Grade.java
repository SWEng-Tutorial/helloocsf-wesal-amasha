package il.cshaifasweng.OCSFMediatorExample.entities.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Grades")
public class Grade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int grade_id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

   @ManyToOne
   @JoinColumn(name = "student_id")
    private Student student;

    private int grade;





    public String getCourseName() {
       // System.out.println("getCourseName "+ course.getName());
        return course.getName();
    }

    public int getStudent_id()
    {
       // System.out.println("getStudent_id "+ student.getStudent_id());
        return student.getStudent_id();
    }


    public int getGrade() {
       // System.out.println("getGrade "+grade);
        return grade;
    }

    public int getGrade_id() {
        return grade_id;
    }


    public int getCourseid()
    {
        return course.getId();
    }


    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Grade()
    {

    }
    public Grade(Student student, Course course,int grade)
    {

        setStudent(student);
        setCourse(course);
        this.grade =grade;
    }
}

package il.cshaifasweng.OCSFMediatorExample.entities.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Pay attention to this one: name is a reserved keyword in MySQL.
    @Column(name = "course_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;


    //////////////
    @OneToMany(mappedBy = "course")
    private List<Grade> grades = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
        lecturer.getCourses().add(this); // IMPORTANT!
    }


    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, Lecturer lecturer) {
        this.name = name;
        setLecturer(lecturer); // IMPORTANT: Why are we doing this?
        //this.students = new ArrayList<Student>();
    }


}

package il.cshaifasweng.OCSFMediatorExample.entities.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lecturers")
public class Lecturer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    // Note that in this case, choosing CascadeType.ALL would mean that deleting a lecturer would also delete all his or her courses.
    // This is a design choice, and may not be ideal.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecturer")
    private List<Course> courses;

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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course)
    {
        this.courses.add(course);
    }

    public void deleteCourseByName(Course course)
    {
        for(int i=0;i< courses.size();i++)
        {
            if(courses.get(i).getId()==course.getId())
            {
                courses.remove(i);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lecturer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = new ArrayList<Course>();
    }

    public Lecturer() {

    }

    public void setStudentGrade(Student student, float grade, int course_id){

    }


}
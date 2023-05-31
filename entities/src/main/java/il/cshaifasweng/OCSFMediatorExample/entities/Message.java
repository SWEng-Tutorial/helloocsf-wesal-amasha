package il.cshaifasweng.OCSFMediatorExample.entities;

import il.cshaifasweng.OCSFMediatorExample.entities.entities.Grade;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Student;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class Message implements Serializable {
    int id;
    LocalDateTime timeStamp;
    String message;
    String data;
    int studentId;

    int course_id;

    int grade_to_change;

    private List<Student> students_list_from_server;
    private List<Grade> grades_list_from_server;

    public int getCourse_id()
    {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getGrade_to_change() {
        return grade_to_change;
    }

    public void setGrade_to_change(int grade_to_change) {
        this.grade_to_change = grade_to_change;
    }


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int number) {
        this.studentId = number;
    }

    public Message(String message, List<Student> students, List<Grade> grades) {
        this.students_list_from_server=students;
        this.grades_list_from_server=grades;
        this.message = message;
    }

    public List<Grade> getGrades_list_from_server() {
        return grades_list_from_server;
    }

    public List<Student> getStudents_list_from_server() {

         return this.students_list_from_server;

    }

    public void setGrades_list_from_server(List<Grade> grades_list_from_server) {

        this.grades_list_from_server = grades_list_from_server;
    }

    public void setStudents_list_from_server(List<Student> students_list_from_server) {

       this.students_list_from_server=students_list_from_server;

    }

    public Message(int id, LocalDateTime timeStamp, String message) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public Message(int id, String message) {
        this.id = id;
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.data = null;
    }

    public Message(int id, String message,String data) {
        this.id = id;
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

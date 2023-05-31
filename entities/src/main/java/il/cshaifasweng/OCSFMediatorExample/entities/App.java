package il.cshaifasweng.OCSFMediatorExample.entities;

import java.util.List;
import java.util.Random;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;


import il.cshaifasweng.OCSFMediatorExample.entities.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Grade;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Lecturer;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Student;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import org.hibernate.Transaction;

public class App {

    public static Session session;
    public static SessionFactory sessionFactory = getSessionFactory();

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();

        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Grade.class);
        configuration.addAnnotatedClass(Lecturer.class);
        configuration.addAnnotatedClass(Student.class);


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }


    private static void generateLecturers() throws Exception {

        Lecturer lect = new Lecturer("Ethan", "Simmons");
        session.save(lect);
        session.flush();
        Lecturer lect0 = new Lecturer("Sophia", "Hayes");
        session.save(lect0);
        session.flush();
        Lecturer lect1 = new Lecturer("Caleb", "Parker");
        session.save(lect1);
        session.flush();
        Lecturer lect2 = new Lecturer("Lily", "Turner");
        session.save(lect2);
        session.flush();
        Lecturer lect3 = new Lecturer("Lucas", "Edwards");
        session.save(lect3);
        session.flush();
        Lecturer lect4 = new Lecturer("Ava", "Mitchell");
        session.save(lect4);
        session.flush();
        Lecturer lect5 = new Lecturer("Benjamin", "Martinez");
        session.save(lect5);
        session.flush();
        Lecturer lect6 = new Lecturer("Olivia", "Thompson");
        session.save(lect6);
        session.flush();
        Lecturer lect7 = new Lecturer("Gabriel", "Johnson");
        session.save(lect7);
        session.flush();
        Lecturer lect8 = new Lecturer("Emma", "Anderson");
        session.save(lect8);
        session.flush();


    }

    private static void generateCourses() throws Exception {

        Course course0 = new Course("Biology" );
        session.save(course0);
        session.flush();
        Course course1 = new Course("Alchemy" );
        session.save(course1);
        session.flush();
        Course course2 = new Course("Swimming" );
        session.save(course2);
        session.flush();
        Course course3 = new Course("HorseRiding" );
        session.save(course3);
        session.flush();
        Course course4 = new Course("Drawing");
        session.save(course4);
        session.flush();
        Course course5 = new Course("VolleyBall" );
        session.save(course5);
        session.flush();
        Course course6 = new Course("Guitar" );
        session.save(course6);
        session.flush();
        Course course7 = new Course("Flute" );
        session.save(course7);
        session.flush();
        Course course8 = new Course("Piano" );
        session.save(course8);
        session.flush();
        Course course9 = new Course("Violin" );
        session.save(course9);
        session.flush();
    }

    private static void generateStudents() throws Exception {

        Student std0 = new Student(123456789,"Emma", "Johnson");
        session.save(std0);
        session.flush();
        Student std1 = new Student(987654321,"Noah", "Williams");
        session.save(std1);
        session.flush();
        Student std2 = new Student(456789123,"Olivia", "Jones");
        session.save(std2);
        session.flush();
        Student std3 = new Student(321654987,"Liam", "Smith");
        session.save(std3);
        session.flush();
        Student std4 = new Student(987123456,"Ava", "Brown");
        session.save(std4);
        session.flush();
        Student std5 = new Student(654321789,"Isabella", "Davis");
        session.save(std5);
        session.flush();
        Student std6 = new Student(789456123,"Sophia", "Miller");
        session.save(std6);
        session.flush();
        Student std7 = new Student(159753468,"Mia", "Wilson");
        session.save(std7);
        session.flush();
        Student std8 = new Student(369852147,"Jackson", "Moore");
        session.save(std8);
        session.flush();
        Student std9 = new Student(852741963,"Aiden", "Taylor");
        session.save(std9);
        session.flush();

    }

    private static <T> List<T> getAll(Class<T> object) {


        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
        Root<T> rootEntry = criteriaQuery.from(object);
        CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
        return allQuery.getResultList();
    }




    public static List<Student> getAllStudents() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        query.from(Student.class);
        List<Student> data = session.createQuery(query).getResultList();
        return data;
    }



    public static List<Grade> getGradesByStudentId(int studentId) throws Exception{

       // System.out.println("getGradesByStudentId Begin");

        boolean found=false;

        List<Student> students = getAll(Student.class);
        for(int i =0;i<students.size() && !found ;i++)
        {
            if(students.get(i).getStudent_id()==studentId) {
                found=true;
            }
        }
        if(found)
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Grade> query = builder.createQuery(Grade.class);
            Root<Grade> gradeRoot = query.from(Grade.class);
            Join<Grade, Student> studentJoin = gradeRoot.join("student");
            query.select(gradeRoot).where(builder.equal(studentJoin.get("student_id"), studentId));
            List<Grade> grades = session.createQuery(query).getResultList();
            return grades;
        }
        return null;

       // System.out.println("getGradesByStudentId End");

    }



    public static void  changeGrade(int studentId, int courseId, int newGrade) throws Exception {

      //  System.out.println("changeGrade");


        List<Grade> student_grades = getGradesByStudentId(studentId);
        try {

          //  System.out.println("start");
            // Find the corresponding Grade entity
            Grade grade = null;
            for (Grade g : student_grades) {
              //  System.out.println(g.getCourseid());
                if (g.getCourseid() == courseId) {
                    grade = g;
                 //   System.out.println("it is valid grade.");
                    break;
                }
            }

            if (grade == null) {
                throw new Exception("No grade found for the specified student and course.");
            }

            //System.out.println("it is valid grade.");

            // Update the grade
            grade.setGrade(newGrade);
            session.update(grade);

            session.getTransaction().commit(); // Save everything..commit();
          //  System.out.println("Grade updated successfully.");
        } catch (Exception e) {
            session.getTransaction().rollback();
          //  System.out.println("Error updating grade: " + e.getMessage());
        } finally {
            session.close();
        }
    }


    public static void printAllStudents() throws Exception {
        List<Student> students = getAllStudents();

            for(int i =0;i<students.size();i++)
            {
                System.out.print("student "+ students.get(i).getStudent_id()+": Grades: ");
                System.out.print('\n');

                for(int j=0;j<students.get(i).getGrades().size();j++) {
                    System.out.print(" "+students.get(i).getGrades().get(j).getGrade());
                }
                System.out.print('\n');
            }
    }

    public static void main(String[] args) {
        try {
            try {
                session = sessionFactory.openSession();
                session.beginTransaction();
            }catch (HibernateException e){
                e.printStackTrace();
            }
            generateCourses();
            generateLecturers();


            generateStudents();

            List<Student> students = getAll(Student.class);
            List<Course> courses = getAll(Course.class);
            List<Lecturer> lecturers = getAll(Lecturer.class);

            lecturers.get(0).addCourse(courses.get(8));
            lecturers.get(0).addCourse(courses.get(6));
            lecturers.get(1).addCourse(courses.get(0));
            lecturers.get(2).addCourse(courses.get(7));
            lecturers.get(3).addCourse(courses.get(5));
            lecturers.get(4).addCourse(courses.get(2));
            lecturers.get(5).addCourse(courses.get(1));
            lecturers.get(6).addCourse(courses.get(4));
            lecturers.get(7).addCourse(courses.get(3));
            lecturers.get(8).addCourse(courses.get(9));
            lecturers.get(9).addCourse(courses.get(8));



            for(int i=0;i<10;i++)
            {
                students.get(i).addGrade(courses.get(0),i+90);
                students.get(i).addGrade(courses.get(1),i+40);
            }

          //  printAllStudents();

            session.getTransaction().commit(); // Save everything.

        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occured, changes have been rolled back.");
            exception.printStackTrace();
        } finally {
            assert session!=null;
            session.close();
        }

       // System.out.println("App main end");
    }
}

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import il.cshaifasweng.OCSFMediatorExample.entities.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Grade;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javafx.scene.input.MouseEvent;

import javax.swing.*;


/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */


public class PrimaryController {

    private int msgId;

    @FXML
    private Button change_but;

    @FXML
    private Label choosestudent_gradeupdate_label;

    @FXML
    private Label choosestudent_view_label;


    @FXML
    private Label insert_new_grade_label;

    @FXML
    private TextField new_grade_txf;

    @FXML
    private Button show_grade_but;

    @FXML
    private Button show_student_after_update_but;

    @FXML
    private Label showallstudent_label;

    @FXML
    private Button showallstudents_but;

    @FXML
    private Label showstudent_grades_after_update_label;


    @FXML
    private Label time_label;

    @FXML
    private TextField time_txf;


    @FXML
    private TableView<Student> show_all_students_table;
    @FXML
    private TableColumn<Student, String> first_name_column;

    @FXML
    private TableColumn<Student, String> last_name_column;


    @FXML
    private TableColumn<Student, Integer> student_id_column;

    @FXML
    private TableView<Grade> course_grade_table;

    @FXML
    private TableColumn<Grade, String> course_column;


    @FXML
    private TableColumn<Grade, Integer> grade_column;

    private  Student student_save;

    @FXML
  //  private TableColumn<Grade, Integer> student_id_gradestable_column;




    void sendMessage(String messageBody) {
        try {
            Message message = new Message(msgId++, messageBody);

            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void sendMessage(Message message) {
        try {
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @FXML
    void on_button_show_after_update_pressed(ActionEvent event) {


        if (student_save != null) {
            showstudent_grades_after_update_label.setVisible(false);
            show_student_after_update_but.setVisible(false);
            Message message = new Message(msgId++, "give me the student grades");
            message.setStudentId(student_save.getStudent_id());
            sendMessage(message);

        }


    }

    @FXML
    void on_show_all_students_pressed(ActionEvent event) throws SQLException {

        show_all_students_table.setVisible(true);
        show_grade_but.setVisible(true);
        choosestudent_view_label.setVisible(true);
        sendMessage("give me the students");


    }

    @FXML
    void on_button_show_grades_pressed(ActionEvent event) {
        Student selectedItem = show_all_students_table.getSelectionModel().getSelectedItem();
        student_save=selectedItem;
        if (selectedItem != null) {
            // Handle the selected item here
           // System.out.println("Selected: " + selectedItem.getFirstName());

            Message message = new Message(msgId++, "give me the student grades");
            message.setStudentId(selectedItem.getStudent_id());
            sendMessage(message);

        }else {
           // System.out.println("select student to show his grades");
            sendMessage("");
        }
    }

    @FXML
    void on_button_change_clicked(ActionEvent event) {

        Grade selectedItem = course_grade_table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Handle the selected item here
          //  System.out.println("Selected: " + selectedItem.getCourseName());

            if(!new_grade_txf.getText().isBlank())
            {
                course_grade_table.setVisible(false);
                Message message = new Message(msgId++, "change the student grade");
                message.setStudentId(selectedItem.getStudent_id());
                message.setCourse_id(selectedItem.getCourseid());
                message.setGrade_to_change( Integer.parseInt(new_grade_txf.getText()));


                choosestudent_gradeupdate_label.setVisible(false);
                insert_new_grade_label.setVisible(false);
                new_grade_txf.setVisible(false);
                change_but.setVisible(false);


                sendMessage(message);
            }
            else {
                    ///entergrade
               // System.out.println("enterGrade");
                sendMessage("");
            }

        }
        else{
          //  System.out.println("select course and grade to change");
            sendMessage("");
        }
    }

    @Subscribe
    public void errorEvent(ErrorEvent event){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    String.format("Message:\nId: %d\nData: %s\nTimestamp: %s\n",
                            event.getMessage().getId(),
                            event.getMessage().getMessage(),
                            event.getMessage().getTimeStamp().format(dtf))
            );
            alert.setTitle("Error!");
            alert.setHeaderText("Error:");
            alert.show();
        });
    }

    @Subscribe
    public void setDataFromServerTF(MessageEvent event) {

       // System.out.println("setDataFromServerTF");

        if (event.getMessage().getMessage().equals("i will give you the students")) {
         //   System.out.println("i will give you the students");
            List<Student> students_from_server = event.getMessage().getStudents_list_from_server();

            show_all_students_table.getItems().clear();
            show_all_students_table.refresh();

            // Set up the columns
            student_id_column.setCellValueFactory(new PropertyValueFactory<>("student_id"));
            last_name_column.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            first_name_column.setCellValueFactory(new PropertyValueFactory<>("firstName"));

            for (int i = 0; i < students_from_server.size(); i++) {
                // Set the data to the table
                show_all_students_table.getItems().add(students_from_server.get(i));
            }

        } else if (event.getMessage().getMessage().equals("i will give you the student grades")) {

           // System.out.println("i will give you the student grades");

            course_grade_table.setVisible(true);
            choosestudent_gradeupdate_label.setVisible(true);
            insert_new_grade_label.setVisible(true);
            new_grade_txf.setVisible(true);
            change_but.setVisible(true);
            showstudent_grades_after_update_label.setVisible(false);
            show_student_after_update_but.setVisible(false);

           // student_id_gradestable_column.setVisible(false);



            List<Grade> grades_from_server = event.getMessage().getGrades_list_from_server();


            course_grade_table.getItems().clear();
            course_grade_table.refresh();

            // Set up the columns
           // student_id_gradestable_column.setCellValueFactory(new PropertyValueFactory<>("student_id"));
            grade_column.setCellValueFactory(new PropertyValueFactory<>("grade"));
            course_column.setCellValueFactory(new PropertyValueFactory<>("courseName"));

            for (int i = 0; i < grades_from_server.size(); i++) {
                // Set the data to the table
                course_grade_table.getItems().add(grades_from_server.get(i));
            }

            ///System.out.println("end");


        } else if (event.getMessage().getMessage().equals("i changed the grade")) {
           // System.out.println("i changed the grade");

            new_grade_txf.setText("");
            showstudent_grades_after_update_label.setVisible(true);
            show_student_after_update_but.setVisible(true);

        } else {
          //  System.out.println("else");

        }

    }

//    @Subscribe
//    public void setSubmittersTF(UpdateMessageEvent event) {
//
//        System.out.println("setSubmittersTF");
//
//    }
//
//    @Subscribe
//    public void getStarterData(NewSubscriberEvent event) {
//
//    }
//
//    @Subscribe
//    public void errorEvent(ErrorEvent event) {
//
//    }

    @FXML
    void initialize() {

        EventBus.getDefault().register(this); /// this line did lot of problems to me!!

        msgId = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            time_txf.setText(currentTime.format(dtf));
        }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        try {
            Message message = new Message(msgId, "add client");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        show_all_students_table.setVisible(false);
        change_but.setVisible(false);
        course_grade_table.setVisible(false);
//        choose_grade_update_label.setVisible(false);
//        choosegrade_update_choisebox.setVisible(false);
        insert_new_grade_label.setVisible(false);
        new_grade_txf.setVisible(false);
        change_but.setVisible(false);
        showstudent_grades_after_update_label.setVisible(false);
        show_student_after_update_but.setVisible(false);
       // choosestudent_choisebox.setVisible(false);
//        choosestudent_update_choisebox.setVisible(false);
        show_grade_but.setVisible(false);
        choosestudent_gradeupdate_label.setVisible(false);
        choosestudent_view_label.setVisible(false);
    }


}


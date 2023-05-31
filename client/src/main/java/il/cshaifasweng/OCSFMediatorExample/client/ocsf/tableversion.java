//package il.cshaifasweng.OCSFMediatorExample.client;
//
//import java.sql.*;
//import java.io.IOException;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//import il.cshaifasweng.OCSFMediatorExample.entities.Message;
//import il.cshaifasweng.OCSFMediatorExample.entities.entities.Student;
//import javafx.animation.Animation;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.util.Duration;
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.awt.event.MouseEvent;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//
///**
// * Sample Skeleton for 'primary.fxml' Controller Class
// */
//
//
//
//public class PrimaryController {
//
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/school?serverTimezone=UTC";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "wesal123";
//
//    private Connection connection;
//
//    public PrimaryController() throws SQLException {
//        try {
//            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML // fx:id="change_but"
//    private Button change_but; // Value injected by FXMLLoader
//
//    @FXML // fx:id="choose_grade_update_label"
//    private Label choose_grade_update_label; // Value injected by FXMLLoader
//
//    @FXML // fx:id="choosegrade_update_choisebox"
//    private ChoiceBox<?> choosegrade_update_choisebox; // Value injected by FXMLLoader
//
//    @FXML // fx:id="choosestudent_choisebox"
//    private ChoiceBox<?> choosestudent_choisebox; // Value injected by FXMLLoader
//
//    @FXML // fx:id="choosestudent_gradeupdate_label"
//    private Label choosestudent_gradeupdate_label; // Value injected by FXMLLoader
//
//    @FXML // fx:id="choosestudent_update_choisebox"
//    private ChoiceBox<?> choosestudent_update_choisebox; // Value injected by FXMLLoader
//
//    @FXML // fx:id="choosestudent_view_label"
//    private Label choosestudent_view_label; // Value injected by FXMLLoader
//
//    @FXML // fx:id="course_grade_table"
//    private TableView<?> course_grade_table; // Value injected by FXMLLoader
//
//    @FXML // fx:id="first_name_column"
//    private TableColumn<Student, Integer> first_name_column; // Value injected by FXMLLoader
//
//    @FXML // fx:id="insert_new_grade_label"
//    private Label insert_new_grade_label; // Value injected by FXMLLoader
//
//    @FXML // fx:id="last_name_column"
//    private TableColumn<Student, Integer> last_name_column; // Value injected by FXMLLoader
//
//    @FXML // fx:id="new_grade_txf"
//    private TextField new_grade_txf; // Value injected by FXMLLoader
//
//    @FXML // fx:id="show_all_students_table"
//    private TableView<Student> show_all_students_table; // Value injected by FXMLLoader
//
//    @FXML // fx:id="show_grade_but"
//    private Button show_grade_but; // Value injected by FXMLLoader
//
//    @FXML // fx:id="show_student_after_update_but"
//    private Button show_student_after_update_but; // Value injected by FXMLLoader
//
//    @FXML // fx:id="showallstudent_label"
//    private Label showallstudent_label; // Value injected by FXMLLoader
//
//    @FXML // fx:id="showallstudents_but"
//    private Button showallstudents_but; // Value injected by FXMLLoader
//
//    @FXML // fx:id="showstudent_grades_after_update_label"
//    private Label showstudent_grades_after_update_label; // Value injected by FXMLLoader
//
//    @FXML // fx:id="student_id_column"
//    private TableColumn<Student, Integer> student_id_column; // Value injected by FXMLLoader
//
//    @FXML // fx:id="time_label"
//    private Label time_label; // Value injected by FXMLLoader
//
//    @FXML // fx:id="time_txf"
//    private TextField time_txf; // Value injected by FXMLLoader
//
//    private ObservableList<Student> students_obserev;
//    @FXML
//    void on_button_show_after_update_pressed(ActionEvent event) {
//
//    }
//
//    @FXML
//    void on_button_show_grades_pressed(ActionEvent event) {
//
//    }
//
//    public void populateTable() {
//        ObservableList<Student> data = FXCollections.observableArrayList(
//                new Student("Item 1", "Description 1"),
//                new Student("Item 2", "Description 2"),
//                new Student("Item 3", "Description 3")
//        );
//
//        student_id_column.setCellValueFactory(new PropertyValueFactory<Student, String>("item"));
//        first_name_column.setCellValueFactory(new PropertyValueFactory<Student, String>("item"));
//        last_name_column.setCellValueFactory(new PropertyValueFactory<Student, String>("description"));
//        myTable.setItems(data);
//    }
//
//    public void addItem(Student student) {
//
//        students_obserev = FXCollections.observableArrayList();
//        students_obserev.add(student);
//    }
//
//
//    @FXML
//    void on_show_all_students_pressed(ActionEvent event) throws SQLException {
//
//        show_all_students_table.setVisible(true);
//
//        try {
//            String query = "SELECT * FROM students";
//            PreparedStatement statement = connection.prepareStatement(query);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                // Process retrieved data
//                int id = resultSet.getInt("student_id");
//                String first_name = resultSet.getString("firstName");
//                String last_name =resultSet.getString("lastName");
//                addItem(new Student(id,first_name,last_name));
//            }
//
//            resultSet.close();
//
//            statement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void initialize() {
//
//        //EventBus.getDefault().register(this); /// this line did lot of problems to me!!
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
//        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
//            LocalTime currentTime = LocalTime.now();
//            time_txf.setText(currentTime.format(dtf));
//        }),
//                new KeyFrame(Duration.seconds(1))
//        );
//
//        clock.setCycleCount(Animation.INDEFINITE);
//        clock.play();
//
//
//        change_but.setVisible(false);
//        show_all_students_table.setVisible(false);
//        course_grade_table.setVisible(false);
//        choose_grade_update_label.setVisible(false);
//        choosegrade_update_choisebox.setVisible(false);
//        insert_new_grade_label.setVisible(false);
//        new_grade_txf.setVisible(false);
//        change_but.setVisible(false);
//        showstudent_grades_after_update_label.setVisible(false);
//        show_student_after_update_but.setVisible(false);
//    }
//
//}
//
//
//
////////////
////
////public class PrimaryController {
////
////    private static final String DB_URL = "jdbc:mysql://localhost:3306/school?serverTimezone=UTC";
////    private static final String DB_USERNAME = "root";
////    private static final String DB_PASSWORD = "wesal123";
////
////   private Connection connection;
////
////    public PrimaryController() throws SQLException {
////        try {
////            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
////
////    @FXML
////    private Button change_but;
////
////    @FXML
////    private Label choose_grade_update_label;
////
////    @FXML
////    private ChoiceBox<?> choosegrade_update_choisebox;
////
////    @FXML
////    private ChoiceBox<?> choosestudent_choisebox;
////
////    @FXML
////    private Label choosestudent_gradeupdate_label;
////
////    @FXML
////    private ChoiceBox<?> choosestudent_update_choisebox;
////
////    @FXML
////    private Label choosestudent_view_label;
////
////    @FXML
////    private TableView<?> course_grade_table;
////
////    @FXML
////    private Label insert_new_grade_label;
////
////    @FXML
////    private TextField new_grade_txf;
////
////    @FXML
////    private Button show_grade_but;
////
////    @FXML
////    private Button show_student_after_update_but;
////
////    @FXML
////    private Label showallstudent_label;
////
////    @FXML
////    private Button showallstudents_but;
////
////    @FXML
////    private Label showstudent_grades_after_update_label;
////
////    @FXML
////    private ListView<String> students_list;
////
////    @FXML
////    private Label time_label;
////
////    @FXML
////    private TextField time_txf;
////
////
////
////    @FXML
////    void on_button_show_after_update_pressed(ActionEvent event) {
////
////    }
////
////    @FXML
////    void on_button_show_grades_pressed(ActionEvent event) {
////
////    }
////
////    public void addItem(String student) {
////        ObservableList<String> items = students_list.getItems();
////        items.add(student);
////    }
////
////    @FXML
////    void on_show_all_students_pressed(ActionEvent event) throws SQLException {
////        students_list.setVisible(true);
////
////        try {
////           String query = "SELECT * FROM students";
////            PreparedStatement statement = connection.prepareStatement(query);
////           ResultSet resultSet = statement.executeQuery();
////
////            while (resultSet.next()) {
////               // Process retrieved data
////                int id = resultSet.getInt("student_id");
////                String name = resultSet.getString("firstName");
////
////                addItem(name);
////            }
////
////            resultSet.close();
////
////            statement.close();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
////
////    @FXML
////    void initialize() {
////
////        //EventBus.getDefault().register(this); /// this line did lot of problems to me!!
////
////        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
////        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
////            LocalTime currentTime = LocalTime.now();
////            time_txf.setText(currentTime.format(dtf));
////        }),
////                new KeyFrame(Duration.seconds(1))
////        );
////
////        clock.setCycleCount(Animation.INDEFINITE);
////        clock.play();
////
////
////        change_but.setVisible(false);
////        students_list.setVisible(false);
////        course_grade_table.setVisible(false);
////        choose_grade_update_label.setVisible(false);
////        choosegrade_update_choisebox.setVisible(false);
////        insert_new_grade_label.setVisible(false);
////        new_grade_txf.setVisible(false);
////        change_but.setVisible(false);
////        showstudent_grades_after_update_label.setVisible(false);
////        show_student_after_update_but.setVisible(false);
////    }
////}
////

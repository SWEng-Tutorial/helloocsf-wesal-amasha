package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Grade;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Lecturer;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Student;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import il.cshaifasweng.OCSFMediatorExample.entities.App;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static il.cshaifasweng.OCSFMediatorExample.entities.App.*;

public class SimpleServer extends AbstractServer {
    private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

    //private static Session session;

    public SimpleServer(int port) {
        super(port);

    }


    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Message message = (Message) msg;
            String request = message.getMessage();

            try {
                //we got an empty message, so we will send back an error message with the error details
                if (request.isBlank()) {
                    message.setMessage("Error! we got an empty message"); //see simpleclient handle messagefromserver
                    client.sendToClient(message);
                } else if (request.equals("add client")) {
                    SubscribedClient connection = new SubscribedClient(client);
                    SubscribersList.add(connection);
                    message.setMessage("client added successfully");
                    client.sendToClient(message);
                } else if (request.equals("give me the students")) {

                    message.setMessage("i will give you the students");
                    message.setStudents_list_from_server(getAllStudents());
                    client.sendToClient(message);

                } else if (request.equals("give me the student grades")) {

                    //  System.out.println("i am here");


                    //  System.out.println("the id is :" + message.getStudentId());

                    message.setMessage("i will give you the student grades");

                    message.setGrades_list_from_server(getGradesByStudentId(message.getStudentId()));

                    // System.out.println("back from getgradesbystudent");

                    client.sendToClient(message);


                } else if (request.equals("change the student grade")) {

                    ////
                   // System.out.println("i am here");
                   // System.out.println("the student to change his grade is:" + message.getStudentId());
                  //  System.out.println("the course id that we want to change it's grade is:" + message.getCourse_id());
                 //   System.out.println("change grade to:" + message.getGrade_to_change());

                    changeGrade(message.getStudentId(), message.getCourse_id(), message.getGrade_to_change());

                  //  System.out.println("i finished");

                    message.setMessage("i changed the grade");
                    client.sendToClient(message);


                } else {
                    sendToAllClients(message);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            assert session != null;
            session.close();
        }
    }

    public void sendToAllClients(Message message) {
        try {
            for (SubscribedClient SubscribedClient : SubscribersList) {
                SubscribedClient.getClient().sendToClient(message);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}

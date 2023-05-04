package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message) msg;
		String request = message.getMessage();

		try {
			//we got an empty message, so we will send back an error message with the error details
			if (request.isBlank()){
				message.setMessage("Error! we got an empty message"); //see simpleclient handle messagefromserver
				client.sendToClient(message);
			}
			//we got a request to change submitters IDs with the updated IDs at the end of the string, so we save
			// the IDs at data field in Message entity and send back to all subscribed clients a request to update
			//their IDs text fields. An example of use of observer design pattern.
			//message format: "change submitters IDs: 123456789, 987654321"
			else if(request.startsWith("change submitters IDs:")){
				message.setData(request.substring(23));
				message.setMessage("update submitters IDs");
				sendToAllClients(message);
			}
			//we got a request to add a new client as a subscriber.
			else if (request.equals("add client")){
				SubscribedClient connection = new SubscribedClient(client);
				SubscribersList.add(connection);
				message.setMessage("client added successfully");
				client.sendToClient(message);
			}
			//we got a message from client requesting to echo Hello, so we will send back to client Hello world!
			else if(request.startsWith("echo Hello")){

				message.setMessage("Hello World!");
				client.sendToClient(message);

			}

			///////////////////START EDITING///////////////////////

			else if (request.startsWith("send Submitters IDs")) {
				//add code here to send submitters IDs to client
				///////////////////////////////////////
				//what is the message format?? I just suppose that it is:
				//FORMAT:"send Submitters IDs 123456789 012345678"
				///////////////////////////////////////

				String ids = request.substring(20);
				String id1 = ids.substring(0,9);
				String id2 = ids.substring(11,19);
				ids = ""+id1+ ", "+id2;
				message.setMessage(ids);
				client.sendToClient(message);
			}

			else if (request.startsWith("send Submitters")){
				//add code here to send submitters names to client
				///////////////////////////////////////
				//what is the message format?? I just suppose that it is:
				//FORMAT:"send Submitters Alice Bob"
				///////////////////////////////////////

				String[] myArray = request.split(" ", 4);

				String name1 = myArray[2];
				String name2 =myArray[3];
				String names = ""+name1+ ", "+name2;
				message.setMessage(names);
				client.sendToClient(message);

//				String names = request.substring(16);
//
//				String name1 = names.substring(0,9);
//				String name2 = names.substring(11,19);
//				names = ""+name1+ ", "+name2;
//				message.setMessage(names);
//				client.sendToClient(message);

			}
			else if (request.equals("what’s the time?")) {

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
				LocalTime currentTime = LocalTime.now();

				message.setMessage(currentTime.format(dtf));
				client.sendToClient(message);

				//add code here to send the time to client
			}
			else if (request.startsWith("multiply")){

				int n=0,m=0,i=9;
				String result;

				char token = '0';
				while(token!='*')
				{
					n=n*10;
					n=n+(token-48);
					token = request.charAt(i);
					i++;
				}

				do {
					token = request.charAt(i);
					m=m*10;
					m=m+(token-48);
					i++;
				}while(i<request.length());


				result=""+n+""+"*"+m+""+"= "+(n*m);
				message.setMessage(result);
				client.sendToClient(message);


				//add code here to multiply 2 numbers received in the message and send result back to client
				//(use substring method as shown above)
				//message format: "multiply n*m"


			}else{

				//add code here to send received message to all clients.
				//The string we received in the message is the message we will send back to all clients subscribed.
				//Example:
					// message received: "Good morning"
					// message sent: "Good morning"
				//see code for changing submitters IDs for help

				sendToAllClients(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
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

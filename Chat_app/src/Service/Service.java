/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import app.MessageType;
import event.EventFileReceiver;
import event.PublicEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Model_File_Receiver;
import model.Model_File_Sender;
import model.Model_Receive_Message;
import model.Model_Send_Message;
import model.Model_User_Account;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mrtru
 */
public class Service {

    private static Service instance;
    private Socket client;
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";
    private Model_User_Account user;
    private List<Model_File_Sender> fileSender;
    private List<Model_File_Receiver> fileReceiver;

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Service() {
        fileSender = new ArrayList<>();
        fileReceiver = new ArrayList<>();
    }

    public void startServer() {
        try {
            client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
            client.on("list_user", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    //  list user
                    List<Model_User_Account> users = new ArrayList<>();
                    for (Object o : os) {
                        Model_User_Account u = new Model_User_Account(o);
                        if (u.getUserID() != user.getUserID()) {
                            users.add(u);
                        }
                    }
                    PublicEvent.getInstance().getEventMenuLeft().newUser(users);
                }
            });
            client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer) os[0];
                    boolean status = (boolean) os[1];
                    if (status) {
                        //connect
                        PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
                    } else {
                        //disconnect
                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
                    }
                }
            });
            client.on("receive_ms", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    Model_Receive_Message message = new Model_Receive_Message(os[0]);
                    PublicEvent.getInstance().getEventChat().receiveMessage(message);
                }
            });
            client.on("file_transfer", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String fileName = (String) args[0];
                    byte[] fileData = (byte[]) args[1];
                    try (FileOutputStream fos = new FileOutputStream("client_data/" + fileName)) {
                        fos.write(fileData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            client.on("receive_messages", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONArray jsonMessages = (JSONArray) args[0]; // Retrieve the JSONArray
                    List<Model_Send_Message> messages = new ArrayList<>();
                    for (int i = 0; i < jsonMessages.length(); i++) {
                        try {
                            JSONObject jsonMessage = jsonMessages.getJSONObject(i);
                            Model_Send_Message message = new Model_Send_Message();
                            int messageTypeValue = jsonMessage.getInt("messageType");
                            message.setMessageType(MessageType.toMessageType(messageTypeValue));
                            message.setFromUserID(jsonMessage.getInt("fromUserID"));
                            message.setToUserID(jsonMessage.getInt("toUserID"));
                            if (message.getMessageType() == MessageType.TEXT || message.getMessageType() == MessageType.EMOJI) {
                                message.setText(jsonMessage.getString("text"));
                            } else if (message.getMessageType() == MessageType.FILE || message.getMessageType() == MessageType.IMAGE) {
                                message.setFileid(jsonMessage.getInt("fileID"));
                                message.setFileName(jsonMessage.getString("fileName"));
                                File file = new File("client_data/" + message.getFileName());
                                Model_File_Sender data = new Model_File_Sender(file, client, message);
                                message.setFile(data);
                            }
                            if (jsonMessage.has("time")) {
                                message.setTime(jsonMessage.getString("time"));
                            }
                            messages.add(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException ex) {
                            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    // Now pass the list to your receiveMessages method
                    PublicEvent.getInstance().getEventChat().receiveMessages(messages);
                }
            });

            client.open();
        } catch (URISyntaxException e) {
            error(e);
        }
    }

    public Model_File_Sender addFile(File file, Model_Send_Message message) throws IOException {
        Model_File_Sender data = new Model_File_Sender(file, client, message);
        message.setFile(data);
        fileSender.add(data);
        //  For send file one by one
        if (fileSender.size() == 1) {
            data.initSend();
        }
        return data;
    }

    public void fileSendFinish(Model_File_Sender data) throws IOException {
        fileSender.remove(data);
        if (!fileSender.isEmpty()) {
            //  Start send new file when old file sending finish
            fileSender.get(0).initSend();
        }
    }

    public void fileReceiveFinish(Model_File_Receiver data) throws IOException {
        fileReceiver.remove(data);
        if (!fileReceiver.isEmpty()) {
            fileReceiver.get(0).initReceive();
        }
    }

    public void addFileReceiver(int fileID, EventFileReceiver event) throws IOException {
        Model_File_Receiver data = new Model_File_Receiver(fileID, client, event);
        fileReceiver.add(data);
        if (fileReceiver.size() == 1) {
            data.initReceive();
        }
    }

    public Socket getClient() {
        return client;
    }

    private void error(Exception e) {
        System.err.println(e);
    }

    public Model_User_Account getUser() {
        return user;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }

}

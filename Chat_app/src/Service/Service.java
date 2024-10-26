/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import app.MessageType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Model_Box_Chat;
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
    private List<Model_Box_Chat> listBoxChat;
    private List<Model_User_Account> listuser;
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
        listuser = new ArrayList<>();
        listBoxChat = new ArrayList<>();
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

                    listuser = users;
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

            client.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Disconnected from server.");
                    // Hiển thị thông báo cho người dùng
                }
            });

            client.on("update_user_info", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    // Nhận thông tin người dùng được cập nhật từ server
                    Model_User_Account updatedUser = new Model_User_Account(os[0]);
                    PublicEvent.getInstance().getEventMenuLeft().userUpdate(updatedUser);
                    /*PublicEvent.getInstance().addEventUserUpdate(new EventUserUpdate(){
                        @Override
                        public Model_User_Account setUserUpdate(Model_User_Account user) {
                            user = updatedUser;
                            return user;
                        }
                    });
                   client.on("list_user", new Emitter.Listener() {
                        @Override
                        public void call(Object... os) {
                            //  list user
                            List<Model_User_Account> users = new ArrayList<>();
                            for (Object o : os) {
                                Model_User_Account u = new Model_User_Account(o);
                                if (u.getUserID() == user.getUserID()) {
                                    continue;
                                }
                                if(u.getUserID()== updatedUser.getUserID()){
                                    u =updatedUser;
                                }
                                users.add(u);
                            }
                            PublicEvent.getInstance().getEventMenuLeft().newUser(users);
                        }
                    });
                   
                     */
                    System.out.println("Received updated user info: " + updatedUser.getUserName());
                }
            });

            client.on("message_notification", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    int fromUserID = (int) args[0];
                    int toUserID = (int) args[1];
                    if (toUserID == Service.getInstance().getUser().getUserID()) {
                        //System.out.println("Bạn có tin nhắn mới từ user ID: " + fromUserID);  
                        PublicEvent.getInstance().getEventMenuLeft().BoldUser(fromUserID);
                    }
                }
            });
            client.on("List_Box_Chat", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    // The incoming data is expected to be a JSON string
                    String json = (String) os[0];

                    // Create a Gson instance
                    Gson gson = new Gson();

                    // Parse the JSON string into a List<Model_Box_Chat>
                    List<Model_Box_Chat> boxChats = gson.fromJson(json, new TypeToken<List<Model_Box_Chat>>() {
                    }.getType());
                    for (Model_Box_Chat boxChat : boxChats) {
                        // Kiểm tra xem memberId có tồn tại trong userid của boxChat hay không
                        if (Arrays.stream(boxChat.getUserid()).anyMatch(id -> id == user.getUserID())) {
                            // Nếu có, thêm boxChat vào danh sách listboxchat
                            listBoxChat.add(boxChat);
                        }
                    }
                    PublicEvent.getInstance().getEventMenuLeft().ShowGroup(listBoxChat);
                }
            });
            client.on("box_Chat", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    JSONObject jsonData = (JSONObject) os[0];
                    Model_Send_Message message = new Model_Send_Message();
                    try {
                        int messageTypeValue = jsonData.getInt("messageType");
                        message.setMessageType(MessageType.toMessageType(messageTypeValue));
                        message.setFromUserID(jsonData.getInt("fromUserID"));
                        message.setToUserID(jsonData.getInt("toUserID"));
                        if (message.getMessageType() == MessageType.TEXT || message.getMessageType() == MessageType.EMOJI) {
                            message.setText(jsonData.getString("text"));
                        } else if (message.getMessageType() == MessageType.FILE || message.getMessageType() == MessageType.IMAGE) {
                            message.setFileid(jsonData.getInt("fileID"));
                            message.setFileName(jsonData.getString("fileName"));
                            File file = new File("client_data/" + message.getFileName());
                            Model_File_Sender data = new Model_File_Sender(file, client, message);
                            message.setFile(data);
                        }
                        if (jsonData.has("time")) {
                            message.setTime(jsonData.getString("time"));
                        }
                        message.setBoxid(jsonData.getInt("boxid"));
                    } catch (JSONException e) {
                        e.printStackTrace(); // Xử lý lỗi nếu cần
                    } catch (IOException ex) {
                        Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    PublicEvent.getInstance().getEventChat().receiveMessage(message);
                }

            });

            client.on("receive_messages_box", new Emitter.Listener() {
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

    // Phát sự kiện để yêu cầu hình ảnh từ server
    public void requestUserAvatar(String userID) {
        client.emit("request_avatar", userID);
    }

    public Model_File_Sender addFile(File file, Model_Send_Message message) throws IOException {
        Model_File_Sender data = new Model_File_Sender(file, client, message);
        message.setFile(data);
        message.setFileName(message.getFile().getFile().getName());
        System.out.println(message.toJsonObject());
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

    public List<Model_User_Account> getListUsers() {
        return listuser;
    }

    public Model_User_Account getUser() {
        return user;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }

    public boolean isConnected() {
        return client != null && client.connected();
    }

    public List<Model_Box_Chat> getListBoxChat() {
        return listBoxChat;
    }
}

package form;

import Service.Service;
import component.Chat_Body;
import component.Chat_Bottom;
import component.Chat_Title;
import event.EventChat;
import event.EventMenuLeft;
import event.PublicEvent;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.util.List;
import event.EventSetUser;
import event.PublicEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLayeredPane;
import model.Model_Box_Chat;
import model.Model_Receive_Message;
import model.Model_Send_Message;
import model.Model_User_Account;
import net.miginfocom.swing.MigLayout;

public class Chat extends javax.swing.JPanel {

    private Chat_Title chatTitle;
    private Chat_Body chatBody;
    private Chat_Bottom chatBottom;
    private Menu_Right menuRight;
    private JLayeredPane layeredPane;
    private Home home;
    private boolean isMenuRightVisible = false;
    String lastMessageDate = "";

    public Chat() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[100%, fill]0[shrink 0]0"));
        chatTitle = new Chat_Title();
        chatBody = new Chat_Body();
        chatBottom = new Chat_Bottom();
        menuRight = new Menu_Right();

        chatTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (home != null) {
                    isMenuRightVisible = !isMenuRightVisible;
                    home.toggleMenuRight(isMenuRightVisible);
                    revalidate();
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                chatTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chatTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });

        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(Model_Send_Message data) {
                try {
                    chatBody.addItemRight(data);
                } catch (Exception ex) {
                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void receiveMessage(Model_Receive_Message data) {
                if (chatTitle.getUser().getUserID() == data.getFromUserID()) {
                    try {
                        chatBody.addItemLeft(data);
                    } catch (Exception ex) {
                        Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void receiveMessages(List<Model_Send_Message> messages) {
                for (Model_Send_Message message : messages) {
//                    if (message.getBoxid() == 0) {
//                        if (message.getFromUserID() == Service.getInstance().getUser().getUserID()) {
//                            chatBody.addItemRight(message);
//                        } else {
//                            chatBody.addItemLeft(message);
//                        }
//                        chatBody.scrollToBottom();
//                    }else{
//                        if (message.getFromUserID() == Service.getInstance().getUser().getUserID()) {
//                            chatBody.addItemRight(message);
//                        } else {
//                            chatBody.addItemLeft(message);
//                        }
//                        chatBody.scrollToBottom();
//                    }
                    if (message.getBoxid() == 0) {
                        String currentDate = message.getTime().substring(0, 10); // Lấy ngày từ thời gian

                        // Nếu ngày tin nhắn khác với ngày tin nhắn trước đó
                        if (!currentDate.equals(lastMessageDate)) {
                            chatBody.addDate(currentDate); // Gọi phương thức để in ra ngày
                            lastMessageDate = currentDate; // Cập nhật ngày tin nhắn trước
                        }

                        if (message.getFromUserID() == Service.getInstance().getUser().getUserID()) {
                            try {
                                chatBody.addItemRight(message);
                            } catch (Exception ex) {
                                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                chatBody.addItemLeft(message);
                            } catch (Exception ex) {
                                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        chatBody.scrollToBottom();
                    }
                }
            }

            @Override
            public void receiveMessage(Model_Send_Message data) {
                if (data.getBoxid() == chatTitle.getBoxChat().getIdBoxChat() && data.getFromUserID() != Service.getInstance().getUser().getUserID()) {
                    try {
                        chatBody.addItemLeft(data);
                    } catch (Exception ex) {
                        Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom, "h ::50%");
    }

    public void setUser(Model_User_Account user) {
        chatTitle.setUserName(user);
        chatBottom.setUser(user);
        chatBody.clearchat();
    }

    public void setGroup(Model_Box_Chat boxchat) {
        chatTitle.setBoxChat(boxchat);
        chatBottom.setBoxChat(boxchat);
        chatBody.clearchat();
    }

    public void updateUser(Model_User_Account user) {
        chatTitle.updateUser(user);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void setHome(Home home) {
        this.home = home;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 463, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

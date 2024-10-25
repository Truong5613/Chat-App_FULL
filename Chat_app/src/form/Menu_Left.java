/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import Service.Service;
import component.Chat_Body;
import component.Item_People;
import event.EventBoxChat;
import event.EventMenuLeft;
import event.PublicEvent;
import io.socket.emitter.Emitter;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import model.Model_Box_Chat;
import model.Model_User_Account;
import net.miginfocom.swing.MigLayout;
import swing.ScrollBar;

/**
 *
 * @author mrtru
 */
public class Menu_Left extends javax.swing.JPanel {

    /**
     * Creates new form Menu_Left1
     */
    public Menu_Left() {
        initComponents();
        init();
    }
    private final Chat_Body chatbody = new Chat_Body();
    private List<Model_User_Account> userAccount;
    private List<Model_Box_Chat> listBoxChat;
    private List<Model_Box_Chat> groupChats;

    private void init() {
        sp.setVerticalScrollBar(new ScrollBar());
        menuLis.setLayout(new MigLayout("fillx", "0[]0", "0[]0"));
        userAccount = new ArrayList<>();
        groupChats = new ArrayList<>();
        listBoxChat = new ArrayList<>();
        groupChats.add(new Model_Box_Chat(0, null, "Thêm Nhóm Chat", "plus.png"));
        PublicEvent.getInstance().addEventMenuLeft(new EventMenuLeft() {
            @Override
            public void newUser(List<Model_User_Account> users) {
                refreshMenuList();
                for (Model_User_Account d : users) {
                    userAccount.add(d);
                    menuLis.add(new Item_People(d), "wrap");
                    refreshMenuList();
                }
            }

            @Override
            public void userConnect(int userID) {
                for (Model_User_Account u : userAccount) {
                    if (u.getUserID() == userID) {
                        u.setStatus(true);
                        PublicEvent.getInstance().getEventMain().updateUser(u);
                        break;
                    }
                }
                if (MenuMessage.isSelected()) {
                    for (Component com : menuLis.getComponents()) {
                        Item_People item = (Item_People) com;
                        if (item.getUser().getUserID() == userID) {
                            item.updateStatus();
                            break;
                        }
                    }
                }
            }

            @Override
            public void userDisconnect(int userID) {
                for (Model_User_Account u : userAccount) {
                    if (u.getUserID() == userID) {
                        u.setStatus(false);
                        PublicEvent.getInstance().getEventMain().updateUser(u);
                        break;
                    }
                }
                if (MenuMessage.isSelected()) {
                    for (Component com : menuLis.getComponents()) {
                        Item_People item = (Item_People) com;
                        if (item.getUser().getUserID() == userID) {
                            item.updateStatus();
                            break;
                        }
                    }
                }
            }

            @Override
            public void userClick(int[] userID) {
                chatbody.clearchat();
                Service.getInstance().getClient().emit("user_click", userID);
            }

            @Override
            public void userUpdate(Model_User_Account user) {
                for (int i = 0; i < userAccount.size(); i++) {
                    if (userAccount.get(i).getUserID() == user.getUserID()) {
                        userAccount.remove(i);
                        break;
                    }
                }
                userAccount.add(user);
                PublicEvent.getInstance().getEventMain().updateUser(user);
                refreshMenuList();
                /*for (Model_User_Account u : userAccount) {
                    if (u.getUserID() == user.getUserID()) {                      
                        u = user;
                        PublicEvent.getInstance().getEventMain().updateUser(u);                      
                        break;
                    }
                }
                if (MenuMessage.isSelected()) {
                    for (Component com : menuLis.getComponents()) {
                        Item_People item = (Item_People) com;
                        if (item.getUser().getUserID() == user.getUserID()) {
                            item.updateUser(user);
                            break;
                        }
                    }
                }*/
                if (MenuMessage.isSelected()) {
                    menuLis.removeAll();
                    for (Model_User_Account u : userAccount) {
                        if (Service.getInstance().getUser().getUserID() == u.getUserID()) {
                            continue;
                        }
                        menuLis.add(new Item_People(u), "wrap");
                    }
                    refreshMenuList();
                }
            }

            @Override
            public List<Model_User_Account> getUsers() {
                return userAccount;
            }

            @Override
            public void BoldUser(int user) {
                if (MenuMessage.isSelected()) {
                    // Di chuyển người gửi lên đầu danh sách
                    for (int i = 0; i < userAccount.size(); i++) {
                        if (userAccount.get(i).getUserID() == user) {
                            Model_User_Account userr = userAccount.remove(i);
                            userr.setBold(true); // Đánh dấu là bôi đậm
                            userAccount.add(0, userr); // Thêm người gửi vào đầu danh sách
                            break;
                        }
                    }

                    // Cập nhật hiển thị
                    refreshMenuList();
                    addall();

                    // Bôi đậm người gửi trong giao diện và kiểm tra điều kiện để tắt bôi đậm ngay
                    for (Component com : menuLis.getComponents()) {
                        Item_People item = (Item_People) com;
                        if (item.getUser().getUserID() == user) {
                            item.BoldeUser();

                            // Kiểm tra nếu người dùng hiện tại đang ở trang của người gửi
                            if (PublicEvent.getInstance().getEventGetChatTitleUserName().isThisUser(item.getUser())) {
                                // Nếu có, đặt lại font về thường ngay lập tức
                                item.setNormalFont();
                                item.getUser().setBold(false); // Tắt trạng thái bôi đậm
                            }
                            break;
                        }
                    }
                }
            }
            public void ShowGroup(List<Model_Box_Chat> list) {
                for (Model_Box_Chat boxChat : list) {
                    if (!groupExists(boxChat.getIdBoxChat())) { 
                        groupChats.add(boxChat);
                    }
                }
                ShowGroup1();
            }

            @Override
            public void groupclick(int groupid) {
               chatbody.clearchat();
               Service.getInstance().getClient().emit("user_click", 18);
            }

        });
        ShowMessage();
    }

    private boolean groupExists(int groupId) {
        for (Model_Box_Chat boxChat : groupChats) {
            if (boxChat.getIdBoxChat() == groupId) {
                return true;
            }
        }
        return false;
    }

    private void ShowMessage() {
        menuLis.removeAll();
        for (Model_User_Account d : userAccount) {
            menuLis.add(new Item_People(d), "wrap");
        }
        refreshMenuList();
    }

    private void ShowGroup1() {
        menuLis.removeAll();
        for (Model_Box_Chat group : groupChats) {
            menuLis.add(new Item_People(group), "wrap");
        }
        refreshMenuList();
    }
//    private void ShowBox() {
//        menuLis.removeAll();
//        for (int i = 0; i < 3; i++) {
//            menuLis.add(new Item_People(null), "wrap 1");
//        }
//        refreshMenuList();
//    }

    private void refreshMenuList() {
        menuLis.repaint();
        menuLis.revalidate();
    }

    private void addall() {
        menuLis.removeAll();
        for (Model_User_Account user : userAccount) {
            Item_People item = new Item_People(user);
            if (user.isBold()) {
                item.BoldeUser();
            }
            menuLis.add(item, "wrap");
        }
        menuLis.repaint();
        menuLis.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        menu = new javax.swing.JLayeredPane();
        MenuMessage = new component.MenuButton();
        MenuGroup = new component.MenuButton();
        MenuBox = new component.MenuButton();
        sp = new javax.swing.JScrollPane();
        menuLis = new javax.swing.JLayeredPane();

        setBackground(new java.awt.Color(229, 229, 229));

        menu.setBackground(new java.awt.Color(229, 229, 229));
        menu.setOpaque(true);
        menu.setLayout(new java.awt.GridLayout(1, 3));

        MenuMessage.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/icon/message_selected.png"))); // NOI18N
        MenuMessage.setIconSimple(new javax.swing.ImageIcon(getClass().getResource("/icon/message.png"))); // NOI18N
        MenuMessage.setSelected(true);
        MenuMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuMessageActionPerformed(evt);
            }
        });
        menu.add(MenuMessage);

        MenuGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/group.png"))); // NOI18N
        MenuGroup.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/icon/group_selected.png"))); // NOI18N
        MenuGroup.setIconSimple(new javax.swing.ImageIcon(getClass().getResource("/icon/group.png"))); // NOI18N
        MenuGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuGroupActionPerformed(evt);
            }
        });
        menu.add(MenuGroup);

        MenuBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/box.png"))); // NOI18N
        MenuBox.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/icon/box_selected.png"))); // NOI18N
        MenuBox.setIconSimple(new javax.swing.ImageIcon(getClass().getResource("/icon/box.png"))); // NOI18N
        MenuBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBoxActionPerformed(evt);
            }
        });
        menu.add(MenuBox);

        sp.setBackground(new java.awt.Color(255, 255, 255));
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        menuLis.setBackground(new java.awt.Color(255, 255, 255));
        menuLis.setOpaque(true);

        javax.swing.GroupLayout menuLisLayout = new javax.swing.GroupLayout(menuLis);
        menuLis.setLayout(menuLisLayout);
        menuLisLayout.setHorizontalGroup(
            menuLisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        menuLisLayout.setVerticalGroup(
            menuLisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        sp.setViewportView(menuLis);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void MenuBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBoxActionPerformed
        // TODO add your handling code here:
        if (!MenuBox.isSelected()) {
            MenuMessage.setSelected(false);
            MenuGroup.setSelected(false);
            MenuBox.setSelected(true);
//            ShowBox();
        }
    }//GEN-LAST:event_MenuBoxActionPerformed

    private void MenuMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuMessageActionPerformed
        // TODO add your handling code here:
        if (!MenuMessage.isSelected()) {
            MenuMessage.setSelected(true);
            MenuGroup.setSelected(false);
            MenuBox.setSelected(false);
            ShowMessage();
        }

    }//GEN-LAST:event_MenuMessageActionPerformed

    private void MenuGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuGroupActionPerformed
        // TODO add your handling code here:
        if (!MenuGroup.isSelected()) {
            MenuMessage.setSelected(false);
            MenuGroup.setSelected(true);
            MenuBox.setSelected(false);
            Service.getInstance().getClient().emit("Request_Box_Chat_List");
            ShowGroup1();
        }

    }//GEN-LAST:event_MenuGroupActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.MenuButton MenuBox;
    private component.MenuButton MenuGroup;
    private component.MenuButton MenuMessage;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLayeredPane menu;
    private javax.swing.JLayeredPane menuLis;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}

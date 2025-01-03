/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import event.EventGetChatTitleUserName;
import event.PublicEvent;
import form.Menu_Right;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Base64;
import javax.swing.ImageIcon;
import model.Model_Box_Chat;
import model.Model_User_Account;

/**
 *
 * @author mrtru
 */
public class Chat_Title extends javax.swing.JPanel {

    /**
     * Creates new form Chat_Title
     */
    private Model_User_Account user;
    private Model_Box_Chat boxchat;

    public Model_User_Account getUser() {
        return user;
    }

    public Model_Box_Chat getBoxChat() {
        return boxchat;
    }

    public Chat_Title() {
        initComponents();

    }

    private Menu_Right menuRight;

    public void setUserName(Model_User_Account user) {
        this.user = user;
        lbname.setText(user.getUserName());
        if (user.isStatus()) {
            statusActive();
        } else {
            setStatusText("Offline");
        }
        if ( user.getImage()!= null || !user.getImage().isEmpty() || user.getImage() != ""){
            setAvatarImageFromBase64(user.getImage());
        }
        
        if ( user != null){
            PublicEvent.getInstance().addEventGetChatTitleUserName(new EventGetChatTitleUserName() {
                @Override
                public boolean isThisUser(Model_User_Account userr) {
                    return user.getUserID() == userr.getUserID() ? true : false;
                }
            });
        }
        imageAvatar1.setVisible(true);
    }

    public void setBoxChat(Model_Box_Chat boxchat) {
        this.boxchat = boxchat;
        lbname.setText(boxchat.getNameBoxChat());
        lbstatus.setVisible(false);
        imageAvatar1.setVisible(false);
    }

    public void updateUser(Model_User_Account user) {
        if (this.user == user) {
            lbname.setText(user.getUserName());
            if (user.isStatus()) {
                statusActive();
            } else {
                setStatusText("Offline");
            }
        }
    }

    public void statusActive() {
        lbstatus.setText("Active now");
        lbstatus.setForeground(new Color(110, 197, 49));
    }

    public void setStatusText(String text) {
        lbstatus.setText(text);
        lbstatus.setForeground(new Color(160, 160, 160));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Layer = new javax.swing.JLayeredPane();
        lbname = new javax.swing.JLabel();
        lbstatus = new javax.swing.JLabel();
        imageAvatar1 = new swing.ImageAvatar();

        setBackground(new java.awt.Color(229, 229, 229));

        Layer.setLayout(new java.awt.GridLayout(0, 1));

        lbname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbname.setForeground(new java.awt.Color(64, 64, 64));
        lbname.setText("Name");

        lbstatus.setForeground(new java.awt.Color(110, 197, 49));
        lbstatus.setText("Active Now");

        imageAvatar1.setBorderSize(0);
        imageAvatar1.setImage(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Layer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbstatus)
                    .addComponent(lbname, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120, 120, 120))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Layer)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbname)
                .addGap(0, 0, 0)
                .addComponent(lbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public ImageIcon decodeBase64ToImage(String base64Image) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            return new ImageIcon(getClass().getResource("/icon/user.png")); // Trả về icon mặc định
        }

        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return new ImageIcon(imageBytes);
        } catch (IllegalArgumentException e) {
            return new ImageIcon(getClass().getResource("/icon/user.png")); // Trả về icon mặc định nếu decode lỗi
        }
    }

    public void setAvatarImageFromBase64(String base64Image) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            setAvatarImage(new ImageIcon(getClass().getResource("/icon/user.png"))); // Icon mặc định
            return;
        }

        ImageIcon avatarIcon = decodeBase64ToImage(base64Image);
        if (avatarIcon != null) {
            imageAvatar1.setImage(avatarIcon);
            repaint();
        }
    }
    public void setAvatarImage(ImageIcon avatarIcon) {
        imageAvatar1.setImage(avatarIcon);
        repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane Layer;
    private swing.ImageAvatar imageAvatar1;
    private javax.swing.JLabel lbname;
    private javax.swing.JLabel lbstatus;
    // End of variables declaration//GEN-END:variables
}

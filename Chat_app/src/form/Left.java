/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import Service.Service;
import event.EventLeft;
import event.EventUpdateUser;
import event.EventUserUpdate;
import event.PublicEvent;
import io.socket.client.Ack;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Base64;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import main.Main;
import model.Model_User_Account;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author mrtru
 */
public class Left extends javax.swing.JPanel {
   
    public Left() {
        initComponents();
        init();
    }

    public void init() {
        PublicEvent.getInstance().addEventLeft(new EventLeft(){
            @Override
            public void setImage(Model_User_Account user) {
                if(user.getImage().trim().isEmpty()|| user.getImage() ==  null){
                    ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icon/user.png"));
                    setAvatarImage(defaultIcon);
                }
                else{
                    setAvatarImageFromBase64(user.getImage());
                }
                
            }
        });
        imageAvatar1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    toggleUserProfile();
                    PublicEvent.getInstance().getEventOverpanel().setOverpanel();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                imageAvatar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imageAvatar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }

        });

    }

    public ImageIcon decodeBase64ToImage(String base64Image) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            return null;
        }
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        return new ImageIcon(imageBytes);
    }

    public void setAvatarImageFromBase64(String base64Image) {
        ImageIcon avatarIcon = decodeBase64ToImage(base64Image);
        imageAvatar1.setImage(avatarIcon);
        repaint();
    }

    public void setAvatarImage(ImageIcon avatarIcon) {
        imageAvatar1.setImage(avatarIcon);
        repaint();
    }
    
    private void toggleUserProfile() {
        Model_User_Account user = Service.getInstance().getUser();

        JPanel overlayPanel = new JPanel();
        overlayPanel.setBackground(new java.awt.Color(0, 0, 0, 10));
        overlayPanel.setBounds(0, 0, getRootPane().getWidth(), getRootPane().getHeight());
        overlayPanel.setLayout(null);

        getRootPane().getLayeredPane().add(overlayPanel, JLayeredPane.DEFAULT_LAYER);

        JDialog userProfileDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thông tin người dùng", false);
        userProfileDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        userProfileDialog.setSize(500, 600);

        userProfileDialog.setUndecorated(true);
        
        UserProfile userProfile = new UserProfile(user);
        userProfileDialog.add(userProfile);

        PublicEvent.getInstance().addEventUpdateUser(new EventUpdateUser() {
            @Override
            public void updateUser(Model_User_Account user) {
                Service.getInstance().getClient().emit("update_user", user.toJsonObject(), new Ack() {
                    @Override
                    public void call(Object... os) {
                        if (os[0] != null && os[0].equals("Update successful")) {
                            Service.getInstance().setUser(new Model_User_Account(os[1]));
                            System.out.println("Cập nhật thông tin người dùng thành công");
                            javax.swing.JOptionPane.showMessageDialog(null, "Thông tin đã được lưu thành công", "Thông báo", javax.swing.JOptionPane.INFORMATION_MESSAGE);                    
                        } else {
                            System.out.println("Cập nhật thông tin người dùng thất bại");
                        }
                    }
                });

            }
        });

        userProfileDialog.setLocationRelativeTo(null);
        userProfileDialog.setVisible(true);

        overlayPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userProfileDialog.dispose();
                PublicEvent.getInstance().getEventOverpanel().hideOverpanel();
                getRootPane().getLayeredPane().remove(overlayPanel);
                getRootPane().revalidate();
                getRootPane().repaint();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        sp = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        imageAvatar1 = new swing.ImageAvatar();

        setBackground(new java.awt.Color(51, 153, 255));
        setBorder(new javax.swing.border.MatteBorder(null));

        sp.setBackground(new java.awt.Color(255, 255, 255));
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 539, Short.MAX_VALUE)
        );

        sp.setViewportView(jPanel1);

        imageAvatar1.setBackground(new java.awt.Color(51, 153, 255));
        imageAvatar1.setBorderSize(3);
        imageAvatar1.setImage(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(sp))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private swing.ImageAvatar imageAvatar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}

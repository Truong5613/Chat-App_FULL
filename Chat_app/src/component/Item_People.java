/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import Service.Service;
import event.PublicEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.Model_Box_Chat;
import java.util.Base64;
import javax.swing.ImageIcon;
import model.Model_User_Account;

/**
 *
 * @author mrtru
 */
public class Item_People extends javax.swing.JPanel {

    /**
     * Creates new form Item_People
     *
     * @param name
     */
    private boolean mouseOver;
    private final Model_User_Account user;
    private final Model_Box_Chat boxchat;

    public Model_User_Account getUser() {
        return user;
    }
    
    public Model_Box_Chat getBoxChat(){
        return boxchat;
    }

    public Item_People(Model_User_Account user) {
        this.user = user;
        this.boxchat = null;
        this.initComponents();
        lb.setText(user.getUserName());
        if (this.user.getImage().trim().isEmpty() || this.user.getImage() == null) {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icon/user.png"));
            setAvatarImage(defaultIcon);
        } else {
            setAvatarImageFromBase64(this.user.getImage());
        }
        activeStatus.setActive(user.isStatus());
        init();
    }

    public Item_People(Model_Box_Chat boxchat) {
        this.boxchat = boxchat;
        this.user = null;
        this.initComponents();
        lb.setText(boxchat.getNameBoxChat());
        if (this.boxchat.getImage().trim().isEmpty() || this.boxchat.getImage() == null) {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icon/user.png"));
            setAvatarImage(defaultIcon);
        } else {
            setAvatarImageFromBase64(this.boxchat.getImage());
        }
        if (boxchat.getImage() == "plus.png") {
            Icon icon = new ImageIcon(getClass().getResource("/icon/plus.png"));
            imageAvatar1.setImage(icon);
        }
        activeStatus.setActive(false);
        lbStatus.setVisible(false);
        init();
    }

    public void updateStatus() {
        activeStatus.setActive(user.isStatus());
    }

    public void updateUser(Model_User_Account user) {
        setAvatarImageFromBase64(user.getImage());
        lb.setText(user.getUserName());
    }

    public void BoldeUser() {
        try {
            lb.setFont(lb.getFont().deriveFont(Font.BOLD));
            // Kiểm tra nếu người dùng đang ở trong trang trò chuyện
            if (PublicEvent.getInstance().getEventGetChatTitleUserName().isThisUser(user)) {
                setNormalFont();  // Đặt font về thường ngay lập tức
                user.setBold(false); // Tắt trạng thái bôi đậm
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void setNormalFont() {
        lb.setFont(lb.getFont().deriveFont(Font.PLAIN));
    }

    private void init() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(new Color(242, 242, 242));
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(new Color(229, 229, 229));
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (mouseOver) {
                    if (user != null) {
                        if (user.isBold()) {
                            user.setBold(false);  
                            setNormalFont(); 
                            if (PublicEvent.getInstance().getEventGetChatTitleUserName().isThisUser(user))
                            {
                                lb.setFont(lb.getFont().deriveFont(Font.PLAIN));
                            } 
                        }
                        PublicEvent.getInstance().getEventMain().selectUser(user);
                        int fromUserID = Service.getInstance().getUser().getUserID(); // Current user ID
                        int toUserID = user.getUserID(); // Selected user ID
                        int[] temp = {fromUserID, toUserID};
                        PublicEvent.getInstance().getEventMenuLeft().userClick(temp);
                    } else {
                        if (boxchat.getNameBoxChat() == "Thêm Nhóm Chat") {
                            Create_ChatBox frame = new Create_ChatBox();
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        } else {
                            PublicEvent.getInstance().getEventMain().selectGroup(boxchat);
                            PublicEvent.getInstance().getEventMenuLeft().groupclick(boxchat.getIdBoxChat());
                        }
                    }
                }
            }

        });
    }

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageAvatar1 = new swing.ImageAvatar();
        lb = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        activeStatus = new swing.ActiveStatus();

        setBackground(new java.awt.Color(229, 229, 229));

        imageAvatar1.setBorderSize(0);
        imageAvatar1.setImage(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        lb.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lb.setText("Name");

        lbStatus.setForeground(new java.awt.Color(125, 123, 123));
        lbStatus.setText("New User");

        activeStatus.setActive(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(activeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lb)
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(activeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ActiveStatus activeStatus;
    private swing.ImageAvatar imageAvatar1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}

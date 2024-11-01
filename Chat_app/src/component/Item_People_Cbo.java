/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import Service.Service;
import event.PublicEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Base64;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.Model_Box_Chat;
import model.Model_User_Account;

/**
 *
 * @author mrtru
 */
public class Item_People_Cbo extends javax.swing.JPanel {

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

    public Item_People_Cbo(Model_User_Account user) {
        this.user = user;
        this.boxchat = null;
        this.initComponents();
        if (this.user.getImage() == null || this.user.getImage().trim().isEmpty()) {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icon/user.png"));
            setAvatarImage(defaultIcon);
        } else {
            setAvatarImageFromBase64(this.user.getImage());
        }
        lb.setText(user.getUserName());
        init();
    }

    public Item_People_Cbo(Model_Box_Chat boxchat) {
        this.boxchat = boxchat;
        this.user = null;
        this.initComponents();
        lb.setText(boxchat.getNameBoxChat());
        if (boxchat.getImage() == "plus.png") {
            Icon icon = new ImageIcon(getClass().getResource("/icon/plus.png"));
            imageAvatar1.setImage(icon);
        }

        init();
    }

    private void init() {
        CheckBox.setFocusable(false);
        CheckBox.setOpaque(false);
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
                    CheckBox.setSelected(!CheckBox.isSelected()); // Đổi trạng thái khi nhấp
                }
            }

        });
    }

    public ImageIcon decodeBase64ToImage(String base64Image) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            return new ImageIcon(getClass().getResource("/icon/user.png"));
        }

        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return new ImageIcon(imageBytes);
        } catch (IllegalArgumentException e) {
            return new ImageIcon(getClass().getResource("/icon/user.png"));
        }
    }

    public void setAvatarImageFromBase64(String base64Image) {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            setAvatarImage(new ImageIcon(getClass().getResource("/icon/user.png")));
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

    public boolean isSelected() {
        return CheckBox.isSelected();
    }

    public int getUserId() {
        return user != null ? user.getUserID() : -1; // Lấy ID của user nếu có
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
        CheckBox = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(229, 229, 229));

        imageAvatar1.setBorderSize(0);
        imageAvatar1.setImage(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        lb.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lb.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(CheckBox)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imageAvatar1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton CheckBox;
    private swing.ImageAvatar imageAvatar1;
    private javax.swing.JLabel lb;
    // End of variables declaration//GEN-END:variables
}

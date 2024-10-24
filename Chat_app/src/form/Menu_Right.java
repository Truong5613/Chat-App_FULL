/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Base64;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import model.Model_User_Account;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;

public class Menu_Right extends javax.swing.JPanel {

    private JLabel icon;

    public Menu_Right() {
        initComponents();
        init();
    }

    public void init() {
        imageAvatar.setPreferredSize(new Dimension(100, 100));
        backgroundImage.setPreferredSize(new Dimension(200, 168));
        backgroundImage.setLayout(new GridBagLayout());
        revalidate();
        repaint();
    }

    public void setUserName(Model_User_Account user) {
        this.userName.setText(user.getUserName());

        if ("1".equals(user.getGender())) {
            this.txtGender.setText("Male");
        } else if ("0".equals(user.getGender())) {
            this.txtGender.setText("Female");
        } else {
            this.txtGender.setText("Không xác định");
        }

        if (user.getImage() != "") {
            setAvatarImageFromBase64(user.getImage());
        } else {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icon/user.png"));
            setAvatarImage(defaultIcon);
        }

        if (user.getImageBackground() != "") {
            setIconImageFromBase64(user.getImageBackground());
        } else {
            setDefaultIcon(); 
        }

        this.txtBirthday.setText(user.getBirthDay());
        this.txtAddress.setText(user.getAddress());
        this.txtDescription.setText(user.getDescription());
        repaint();
    }

 
    private void setDefaultIcon() {
        if (icon == null) {
            icon = new JLabel();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            backgroundImage.add(icon, gbc);
        }
        ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icon/test/cat.png"));
        icon.setIcon(defaultIcon);  
        repaint();
    }

    public void setIconImageFromBase64(String base64Image) {
        if (icon == null) {
            icon = new JLabel();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            backgroundImage.add(icon, gbc);
        }

        if (base64Image == null || base64Image.isEmpty()) {
            setDefaultIcon();
        } else {
            ImageIcon iconImage = decodeBase64ToImage(base64Image);
            if (iconImage != null) {
                if (backgroundImage.getWidth() > 0 && backgroundImage.getHeight() > 0) {
                    int width = backgroundImage.getWidth() / 2;
                    int height = backgroundImage.getHeight();
                    Image scaledImage = iconImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    icon.setIcon(new ImageIcon(scaledImage));
                } else {
                   
                    backgroundImage.addComponentListener(new java.awt.event.ComponentAdapter() {
                        @Override
                        public void componentResized(java.awt.event.ComponentEvent e) {
                            setIconImageFromBase64(base64Image);  
                            backgroundImage.removeComponentListener(this);  
                        }
                    });
                }
            } else {
                setDefaultIcon();
            }
        }
        repaint();
    }

    public ImageIcon decodeBase64ToImage(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            return null;
        }
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        return new ImageIcon(imageBytes);
    }

    public void setAvatarImageFromBase64(String base64Image) {
        ImageIcon avatarIcon = decodeBase64ToImage(base64Image);
        imageAvatar.setImage(avatarIcon);
        repaint();
    }

    public void setAvatarImage(ImageIcon avatarIcon) {
        imageAvatar.setImage(avatarIcon);
        repaint();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundImage = new javax.swing.JPanel();
        userName = new javax.swing.JLabel();
        JGender = new javax.swing.JLabel();
        txtGender = new javax.swing.JTextField();
        JGender1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        JGender2 = new javax.swing.JLabel();
        txtBirthday = new javax.swing.JTextField();
        JGender3 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        imageAvatar = new swing.ImageAvatar();

        setBackground(new java.awt.Color(229, 229, 229));

        backgroundImage.setBackground(new java.awt.Color(153, 153, 153));
        backgroundImage.setBorder(new javax.swing.border.MatteBorder(null));
        backgroundImage.setPreferredSize(new java.awt.Dimension(200, 168));
        backgroundImage.setRequestFocusEnabled(false);

        javax.swing.GroupLayout backgroundImageLayout = new javax.swing.GroupLayout(backgroundImage);
        backgroundImage.setLayout(backgroundImageLayout);
        backgroundImageLayout.setHorizontalGroup(
            backgroundImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 389, Short.MAX_VALUE)
        );
        backgroundImageLayout.setVerticalGroup(
            backgroundImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );

        userName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        userName.setForeground(new java.awt.Color(0, 0, 0));
        userName.setText("Name User");

        JGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        JGender.setForeground(new java.awt.Color(0, 0, 0));
        JGender.setText("Gender");

        txtGender.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtGender.setEnabled(false);

        JGender1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        JGender1.setForeground(new java.awt.Color(0, 0, 0));
        JGender1.setText("Description");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        txtDescription.setEnabled(false);
        jScrollPane2.setViewportView(txtDescription);

        JGender2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        JGender2.setForeground(new java.awt.Color(0, 0, 0));
        JGender2.setText("Birthday");

        txtBirthday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtBirthday.setEnabled(false);

        JGender3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        JGender3.setForeground(new java.awt.Color(0, 0, 0));
        JGender3.setText("Address");

        txtAddress.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtAddress.setEnabled(false);
        txtAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressActionPerformed(evt);
            }
        });

        imageAvatar.setBackground(new java.awt.Color(255, 255, 255));
        imageAvatar.setImage(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userName)
                .addGap(15, 211, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JGender)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(JGender3)
                                .addComponent(JGender2)))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGender)
                            .addComponent(txtAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBirthday, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(JGender1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundImage, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(userName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JGender)
                    .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JGender2)
                    .addComponent(txtBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JGender3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JGender1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(103, Short.MAX_VALUE))
        );

        backgroundImage.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JGender;
    private javax.swing.JLabel JGender1;
    private javax.swing.JLabel JGender2;
    private javax.swing.JLabel JGender3;
    private javax.swing.JPanel backgroundImage;
    private swing.ImageAvatar imageAvatar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtBirthday;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtGender;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables

}

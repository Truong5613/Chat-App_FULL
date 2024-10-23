/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import event.PublicEvent;
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

public class UserProfile extends javax.swing.JPanel {

    private JLabel icon;
    private Model_User_Account userUpdate;

    public UserProfile(Model_User_Account user) {
        initComponents();
        init();
        this.userUpdate = new Model_User_Account(user.getUserID(), user.getUserName(), user.getGender(), user.getImage(), user.getImageBackground(), user.getBirthDay(), user.getAddress(), user.getDescription(), user.isStatus());
        setUserName(user);
    }

    public void init() {
        btnAvatar.setPreferredSize(new Dimension(50, 20));
        btnBackground.setPreferredSize(new Dimension(50, 20));
        btnConfirm.setVisible(false);
        btnBackground.setVisible(false);
        btnAvatar.setVisible(false);
        imageAvatar.setPreferredSize(new Dimension(100, 100));
        backgroundImage.setPreferredSize(new Dimension(200, 168));
        backgroundImage.setLayout(new GridBagLayout());

        // Thêm sự kiện lắng nghe khi form bị đóng
        this.addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                // Không cần xử lý
            }

            @Override
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                // Kiểm tra nếu btnConfirm vẫn đang hiện, thông báo cho người dùng
                if (btnConfirm.isVisible()) {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            "Bạn chưa lưu thông tin!",
                            "Thông báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                // Không cần xử lý
            }
        });
      
        // Ràng buộc chọn một checkbox duy nhất cho giới tính
        cbMale.addActionListener(e -> {
            if (cbMale.isSelected()) {
                cbFemale.setSelected(false);
            }
        });

        cbFemale.addActionListener(e -> {
            if (cbFemale.isSelected()) {
                cbMale.setSelected(false);
            }
        });

        revalidate();
        repaint();
    }

    public void setUserName(Model_User_Account user) {

        this.userName.setText(user.getUserName());

        if ("1".equals(user.getGender())) {
            cbMale.setSelected(true);
            cbFemale.setSelected(false);
        } else if ("0".equals(user.getGender())) {
            cbFemale.setSelected(true);
            cbMale.setSelected(false);
        }
        if (!user.getImage().isEmpty()) {
            setAvatarImageFromBase64(user.getImage());
        } else {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icon/user.png"));
            setAvatarImage(defaultIcon);
            
        }

        if (!user.getImageBackground().isEmpty()) {
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
            int panelWidth = backgroundImage.getWidth();
            int panelHeight = backgroundImage.getHeight();
            
            // Đảm bảo tỷ lệ khung hình khi điều chỉnh kích thước ảnh
            if (panelWidth > 0 && panelHeight > 0) {
                int originalWidth = iconImage.getIconWidth();
                int originalHeight = iconImage.getIconHeight();
                float aspectRatio = (float) originalWidth / originalHeight;

                int newWidth = panelWidth;
                int newHeight = (int) (newWidth / aspectRatio);

                // Điều chỉnh lại kích thước nếu vượt quá chiều cao của panel
                if (newHeight > panelHeight) {
                    newHeight = panelHeight;
                    newWidth = (int) (newHeight * aspectRatio);
                }

                // Đặt lại hình ảnh với kích thước mới đã tính toán
                Image scaledImage = iconImage.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                icon.setIcon(new ImageIcon(scaledImage));
            } else {
                // Chờ khi backgroundImage thay đổi kích thước lần đầu
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

    private String encodeImageToBase64(ImageIcon icon) {
        try {
            java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(
                    icon.getIconWidth(),
                    icon.getIconHeight(),
                    java.awt.image.BufferedImage.TYPE_INT_RGB);

            java.awt.Graphics g = bufferedImage.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();

            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundImage = new javax.swing.JPanel();
        userName = new javax.swing.JLabel();
        JGender = new javax.swing.JLabel();
        JGender1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        JGender2 = new javax.swing.JLabel();
        txtBirthday = new javax.swing.JTextField();
        JGender3 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        imageAvatar = new swing.ImageAvatar();
        editButton = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        btnAvatar = new javax.swing.JButton();
        btnBackground = new javax.swing.JButton();
        cbMale = new javax.swing.JCheckBox();
        cbFemale = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(229, 229, 229));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        backgroundImage.setBackground(new java.awt.Color(153, 153, 153));
        backgroundImage.setBorder(new javax.swing.border.MatteBorder(null));
        backgroundImage.setPreferredSize(new java.awt.Dimension(200, 168));
        backgroundImage.setRequestFocusEnabled(false);

        javax.swing.GroupLayout backgroundImageLayout = new javax.swing.GroupLayout(backgroundImage);
        backgroundImage.setLayout(backgroundImageLayout);
        backgroundImageLayout.setHorizontalGroup(
            backgroundImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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

        editButton.setText("Edit Profile");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnAvatar.setText("Change Avatar");
        btnAvatar.setPreferredSize(new java.awt.Dimension(50, 20));
        btnAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvatarActionPerformed(evt);
            }
        });

        btnBackground.setText("Change Background");
        btnBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackgroundActionPerformed(evt);
            }
        });

        cbMale.setText("Male");
        cbMale.setEnabled(false);

        cbFemale.setText("Female");
        cbFemale.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(btnConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                        .addGap(48, 48, 48)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(JGender1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnBackground)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(JGender)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(JGender3)
                                            .addComponent(JGender2)))
                                    .addGap(26, 26, 26)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtBirthday, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(cbMale)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cbFemale)
                                                .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundImage, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(userName))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnBackground)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JGender)
                    .addComponent(cbMale)
                    .addComponent(cbFemale))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(btnConfirm))
                .addContainerGap())
        );

        backgroundImage.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        //txtGender.setEnabled(true);
        cbMale.setEnabled(true);
        cbFemale.setEnabled(true);
        txtAddress.setEnabled(true);
        txtBirthday.setEnabled(true);
        txtDescription.setEnabled(true);
        btnConfirm.setVisible(true);
        btnBackground.setVisible(true);
        btnAvatar.setVisible(true);
    }//GEN-LAST:event_editButtonActionPerformed

    private void btnAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvatarActionPerformed
        // Tạo JFileChooser để cho phép người dùng chọn file
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Select an image");

        // Chỉ cho phép chọn các file hình ảnh
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg", "gif"));

        int result = fileChooser.showOpenDialog(this);

        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();

            // Kiểm tra file có hợp lệ không
            if (selectedFile != null && selectedFile.exists()) {
                // Hiển thị ảnh đã chọn lên imageAvatar
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                Image scaledImage = icon.getImage().getScaledInstance(imageAvatar.getWidth(), imageAvatar.getHeight(), Image.SCALE_SMOOTH);
                imageAvatar.setImage(new ImageIcon(scaledImage));
                repaint();
            }
        }
    }//GEN-LAST:event_btnAvatarActionPerformed

    private void btnBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackgroundActionPerformed
// Tạo JFileChooser để người dùng chọn file
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Select a background image");

        // Chỉ cho phép chọn file hình ảnh
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg", "gif"));

        int result = fileChooser.showOpenDialog(this);

        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();

            // Kiểm tra file có hợp lệ không
            if (selectedFile != null && selectedFile.exists()) {
                // Hiển thị ảnh đã chọn lên backgroundImage
                ImageIcon iconImage = new ImageIcon(selectedFile.getAbsolutePath());

                // Lấy kích thước gốc của hình ảnh
                int originalWidth = iconImage.getIconWidth();
                int originalHeight = iconImage.getIconHeight();

                // Lấy kích thước của backgroundImage
                int targetWidth = backgroundImage.getWidth();
                int targetHeight = backgroundImage.getHeight();

                // Tính toán tỷ lệ khung hình của hình ảnh gốc
                float aspectRatio = (float) originalWidth / originalHeight;

                // Tính toán kích thước mới giữ nguyên tỷ lệ khung hình
                int newWidth = targetWidth;
                int newHeight = (int) (targetWidth / aspectRatio);

                // Nếu chiều cao vượt quá chiều cao của backgroundImage, điều chỉnh lại
                if (newHeight > targetHeight) {
                    newHeight = targetHeight;
                    newWidth = (int) (targetHeight * aspectRatio);
                }

                // Tạo hình ảnh mới với kích thước đã tính toán
                Image scaledImage = iconImage.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                // Cập nhật icon cho JLabel
                if (icon == null) {
                    icon = new JLabel();
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.anchor = GridBagConstraints.CENTER;
                    backgroundImage.add(icon, gbc);
                }
                icon.setIcon(new ImageIcon(scaledImage));
                repaint();
            }
        }
    }//GEN-LAST:event_btnBackgroundActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed

        userUpdate.setUserName(userName.getText());
        //userUpdate.setGender(txtGender.getText());
        userUpdate.setBirthDay(txtBirthday.getText());
        userUpdate.setAddress(txtAddress.getText());
        userUpdate.setDescription(txtDescription.getText());

        // Set gender based on checkbox selection
        if (cbMale.isSelected()) {
            userUpdate.setGender("1");
        } else if (cbFemale.isSelected()) {
            userUpdate.setGender("0");
        }
          // Chuyển đổi imageAvatar thành base64 và lưu vào userUpdate
        ImageIcon avatarIcon = (ImageIcon) imageAvatar.getImage();
        if (avatarIcon != null) {
            String avatarBase64 = encodeImageToBase64(avatarIcon);
            userUpdate.setImage(avatarBase64);
        }

        // Chuyển đổi icon (background image) thành base64 và lưu vào userUpdate
        if (icon != null && icon.getIcon() != null) {
            ImageIcon backgroundIcon = (ImageIcon) icon.getIcon();
            if (backgroundIcon != null) {
                String backgroundBase64 = encodeImageToBase64(backgroundIcon);
                userUpdate.setImageBackground(backgroundBase64);
            }
        } 
        

        // Ẩn nút Confirm và nút chỉnh sửa
        btnConfirm.setVisible(false);
        btnBackground.setVisible(false);
        btnAvatar.setVisible(false);
        txtAddress.setEnabled(false);
        txtBirthday.setEnabled(false);
        txtDescription.setEnabled(false);
        cbFemale.setEnabled(false);
        cbMale.setEnabled(false);
        PublicEvent.getInstance().getEventUpdateUser().updateUser(userUpdate);

        // Thông báo lưu thành công
         PublicEvent.getInstance().getEventLeft().setImage(userUpdate);
    }//GEN-LAST:event_btnConfirmActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JGender;
    private javax.swing.JLabel JGender1;
    private javax.swing.JLabel JGender2;
    private javax.swing.JLabel JGender3;
    private javax.swing.JPanel backgroundImage;
    private javax.swing.JButton btnAvatar;
    private javax.swing.JButton btnBackground;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JCheckBox cbFemale;
    private javax.swing.JCheckBox cbMale;
    private javax.swing.JButton editButton;
    private swing.ImageAvatar imageAvatar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtBirthday;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables

}

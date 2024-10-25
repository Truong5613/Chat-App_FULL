package com.raven.form;

import com.raven.dialog.Message;
import com.raven.main.Mains;
import com.raven.model.ModelCard;
import com.raven.swing.icon.GoogleMaterialDesignIcons;
import com.raven.swing.icon.IconFontSwing;
import com.raven.swing.noticeboard.ModelNoticeBoard;
import com.raven.swing.table.EventAction;
import java.awt.Color;
import javax.swing.Icon;
import com.raven.main.Mains;
import connection.DatabaseConnection;
import event.TextUpdateListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import model.Model_User_Account;
import service.ServiceUser;

public class Form_Home extends javax.swing.JPanel {
    private int totalUserWhenActive = 0;
    private int newTotalUserWhenActive = 0;
    private long serverStartTime; 
    private Timer serverUptimeTimer;
    private Timer refreshFormTimer;
    public Form_Home() throws SQLException {
        initComponents();
        table1.fixTable(jScrollPane1);
        setOpaque(false);
        initNoticeBoard();
        initData();
        startAutoRefresh();
        serverStartTime = System.currentTimeMillis();
        startUptimeTracker();
    }
    
    private void initData() throws SQLException {
        initCardData();
        initTableData();
    }
   private void initTableData() throws SQLException {
    EventAction eventAction = new EventAction() {
        @Override
        public void delete(Model_User_Account user) {
            if (showMessage("Delete User: " + user.getUserName())) {
                System.out.println("User clicked OK");
                // Add logic for deletion
            } else {
                System.out.println("User clicked Cancel");
            }
        }

        @Override
        public void update(Model_User_Account user) {
            if (showMessage("Update User: " + user.getUserName())) {
                System.out.println("User clicked OK");
            } else {
                System.out.println("User clicked Cancel");
            }
        }
    };

    ServiceUser serviceUser = new ServiceUser();
    
    // Check for a valid connection before getting users
    if (DatabaseConnection.getInstance().getConnection() == null) {
        return;
    }
    List<Model_User_Account> registeredUsers = serviceUser.getUser(-1); 
    for (Model_User_Account user : registeredUsers) {
        table1.addRow(user.toRowTable(eventAction));
    }
}
    private void initCardData() throws SQLException {
    if (DatabaseConnection.getInstance().getConnection() == null) {
        return;
    }
    ServiceUser serviceUser = new ServiceUser();
    List<Model_User_Account> registeredUsers = serviceUser.getUser(-1);
    int totalUsers = registeredUsers.size();
    int percentage = Math.min((totalUsers * 100) / 100, 100); 
    
    
    Icon icon1 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PEOPLE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
    card1.setData(new ModelCard("Tổng Users         ", totalUsers, percentage, icon1));

    long onlineUsers = registeredUsers.stream().filter(Model_User_Account::isStatus).count();
    int percentageOnlineUsers = onlineUsers >= 50 ? 100 : (int) ((onlineUsers / 50.0) * 100);
    Icon icon2 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PEOPLE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
    card2.setData(new ModelCard("Số người Online", (int) onlineUsers, percentageOnlineUsers, icon2));
}

    private void initNoticeBoard() throws SQLException {
    if (DatabaseConnection.getInstance().getConnection() == null) {
        return;
    }
    ServiceUser serviceUser = new ServiceUser();
    List<Model_User_Account> registeredUsers = serviceUser.getUser(-1);
    int totalUsers = registeredUsers.size();
    totalUserWhenActive = totalUsers; 
    Icon icon3 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CREATE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
    card3.setData(new ModelCard("Số tài khoản mới", 0, 0, icon3));
    }

    private boolean showMessage(String message) {
        Message obj = new Message(Mains.getFrames()[0], true);
        obj.showMessage(message);
        return obj.isOk();
    }
    private void clearTableData() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);
    }
    private void refreshForm() throws SQLException {
        clearTableData(); 
        initData();
        ServiceUser serviceUser = new ServiceUser();
        List<Model_User_Account> registeredUsers = serviceUser.getUser(-1);
        newTotalUserWhenActive = registeredUsers.size();
        int newAccountsToday = newTotalUserWhenActive - totalUserWhenActive;
        Icon icon3 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CREATE, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card3.setData(new ModelCard("Số tài khoản mới", newAccountsToday, 100, icon3));
    }
    private void startUptimeTracker() {
        serverUptimeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUptimeCard();
            }
        });
        serverUptimeTimer.start();
    }
   private void updateUptimeCard() {
        long uptimeMillis = System.currentTimeMillis() - serverStartTime;
        long hours = uptimeMillis / 3600000;
        long minutes = (uptimeMillis % 3600000) / 60000;
        long seconds = (uptimeMillis % 60000) / 1000;
        
        String uptimeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        Icon icon4 = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.TIMER, 60, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        card4.setData(new ModelCard("Thời gian server mở", uptimeText, 0, icon4));
    }

    public void stopUptimeTracker() {
        if (serverUptimeTimer != null) {
            serverUptimeTimer.stop();
        }
    }
    private void startAutoRefresh() {
        refreshFormTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    refreshForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        refreshFormTimer.start();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new com.raven.component.Card();
        jLabel1 = new javax.swing.JLabel();
        card2 = new com.raven.component.Card();
        card3 = new com.raven.component.Card();
        card4 = new com.raven.component.Card();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new com.raven.swing.table.Table();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        card1.setColorGradient(new java.awt.Color(211, 28, 215));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Dashboard / Home");

        card2.setBackground(new java.awt.Color(10, 30, 214));
        card2.setColorGradient(new java.awt.Color(72, 111, 252));

        card3.setBackground(new java.awt.Color(194, 85, 1));
        card3.setColorGradient(new java.awt.Color(255, 212, 99));

        card4.setBackground(new java.awt.Color(60, 195, 0));
        card4.setColorGradient(new java.awt.Color(208, 255, 90));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 76, 76));
        jLabel5.setText("Người Dùng");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Gender", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(card2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(card4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 917, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.Card card1;
    private com.raven.component.Card card2;
    private com.raven.component.Card card3;
    private com.raven.component.Card card4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table table1;
    // End of variables declaration//GEN-END:variables
}

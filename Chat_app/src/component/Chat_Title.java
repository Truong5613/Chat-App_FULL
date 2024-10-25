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
    
    public Model_User_Account getUser() {
        return user;
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
        if ( user != null){
            PublicEvent.getInstance().addEventGetChatTitleUserName(new EventGetChatTitleUserName() {
                @Override
                public boolean isThisUser(Model_User_Account userr) {
                    return user.getUserID() == userr.getUserID() ? true : false;
                }
            });
        }
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
        lbstatus.setForeground(new Color(110,197,49));
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

        setBackground(new java.awt.Color(229, 229, 229));

        Layer.setLayout(new java.awt.GridLayout(0, 1));

        lbname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbname.setForeground(new java.awt.Color(64, 64, 64));
        lbname.setText("Name");
        Layer.add(lbname);

        lbstatus.setForeground(new java.awt.Color(110, 197, 49));
        lbstatus.setText("Active Now");
        Layer.add(lbstatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Layer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(332, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Layer)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane Layer;
    private javax.swing.JLabel lbname;
    private javax.swing.JLabel lbstatus;
    // End of variables declaration//GEN-END:variables
}

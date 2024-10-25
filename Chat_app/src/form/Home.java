package form;

import Service.Service;
import event.EventUserUpdate;
import event.PublicEvent;
import io.socket.client.Socket;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.Model_User_Account;
import net.miginfocom.swing.MigLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import model.Model_Box_Chat;

public class Home extends javax.swing.JLayeredPane {

    private Socket socket;
    private Chat chat;
    private Menu_Left menuLeft;
    private Menu_Right menuRight;
    private Model_User_Account user;
    private Model_Box_Chat boxchat;
    private Model_User_Account userProfile;
    private Left leftall;

    public Home() {
        initComponents();
        init();
    }

    public void getUser(Model_User_Account userr) {
        userProfile = userr;
    }

    private void init() {
        //setLayout(new MigLayout("fillx, filly", "0[fill,200!]5[fill,100%]0", "0[fill]0"));
        setLayout(new MigLayout("fillx, filly", "0[fill,50!]0[fill,200!]5[fill,100%]0", "0[fill]0"));
        menuLeft = new Menu_Left();
        chat = new Chat();
        menuRight = new Menu_Right();
        leftall = new Left();
        this.add(leftall);
        this.add(menuLeft);
        this.add(chat);
        chat.setVisible(false);
        chat.setHome(this);
    }

    public void toggleMenuRight(boolean show) {
        if (show) {
            setLayout(new MigLayout("fillx, filly", "0[fill,50!]0[fill,200!]5[fill,300!]5[fill,100%]0", "0[fill]0"));
            if (boxchat != null) {
                menuRight.setBoxChat(boxchat);
            } else {
                menuRight.setUserName(user);
            }
            this.add(menuRight, "grow");
        } else {
            setLayout(new MigLayout("fillx, filly", "0[fill,50!]0[fill,200!]5[fill,100%]0", "0[fill]0"));
            this.remove(menuRight);
        }
        revalidate();
        repaint();
    }

    public void setUser(Model_User_Account user) {
        chat.setUser(user);
        this.user = user;
        chat.setVisible(true);
    }

    public void setBoxChat(Model_Box_Chat boxchat) {
        chat.setGroup(boxchat);
        this.boxchat = boxchat;
        chat.setVisible(true);
    }

    public void updateUser(Model_User_Account user) {
        chat.updateUser(user);
    }

    public void showUserProfile(Model_User_Account user) {

    }

    public void closeUserProfile(JPanel overlayPanel) {
        this.remove(overlayPanel);
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 333, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

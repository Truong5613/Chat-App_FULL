/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import Service.Service;
import app.E2EEncryption;
import app.MessageType;
import event.PublicEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import model.Model_Box_Chat;
import model.Model_Send_Message;
import model.Model_User_Account;
import net.miginfocom.swing.MigLayout;
import swing.JIMSendTextPane;
import swing.ScrollBar;

/**
 *
 * @author mrtru
 */
public class Chat_Bottom extends javax.swing.JPanel {

    /**
     * Creates new form Chat_Title
     */
    private MigLayout mig;
    private Panel_More panelMore;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getFormattedDateTime() {
        return LocalDateTime.now().format(formatter);
    }

    private Model_User_Account user;
    private Model_Box_Chat boxchat;

    public Model_User_Account getUser() {
        return user;
    }

    public Model_Box_Chat getboxchat() {
        return boxchat;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
        this.boxchat = null;
        panelMore.setUser(user);
    }

    public void setBoxChat(Model_Box_Chat boxchat) {
        this.user = null;
        this.boxchat = boxchat;
        panelMore.setBoxChat(boxchat);
    }

    public Chat_Bottom() {
        initComponents();
        init();
    }

    private void init() {
        mig = new MigLayout("fillx, filly", "0[fill]0[]0[]2", "2[fill]2[]0");
        setLayout(mig);
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        JIMSendTextPane txt = new JIMSendTextPane();
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                refresh();
                if (ke.getKeyChar() == 10 && ke.isControlDown()) {
                    try {
                        //  user press controll + enter
                        eventSend(txt);
                    } catch (Exception ex) {
                        Logger.getLogger(Chat_Bottom.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        txt.setBorder(new EmptyBorder(5, 5, 5, 5));
        txt.setHintText("Write Message Here ...");
        scroll.setViewportView(txt);
        ScrollBar sb = new ScrollBar();
        sb.setBackground(new Color(229, 229, 229));
        sb.setPreferredSize(new Dimension(2, 10));
        scroll.setVerticalScrollBar(sb);
        add(sb);
        add(scroll, "w 100%");
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("filly", "0[fill,100%]3[fill,100%]0", "0[bottom]0"));
        panel.setPreferredSize(new Dimension(30, 28));
        panel.setBackground(Color.WHITE);
        JButton cmd = new JButton();
        cmd.setBorder(null);
        cmd.setContentAreaFilled(false);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setIcon(new ImageIcon(getClass().getResource("/icon/send.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    eventSend(txt);
                } catch (Exception ex) {
                    Logger.getLogger(Chat_Bottom.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JButton cmdMore = new JButton();
        cmdMore.setBorder(null);
        cmdMore.setContentAreaFilled(false);
        cmdMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdMore.setIcon(new ImageIcon(getClass().getResource("/icon/more_disable.png")));
        cmdMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (panelMore.isVisible()) {
                    cmdMore.setIcon(new ImageIcon(getClass().getResource("/icon/more_disable.png")));
                    panelMore.setVisible(false);
                    mig.setComponentConstraints(panelMore, "dock south,h 0!");
                    revalidate();
                } else {
                    cmdMore.setIcon(new ImageIcon(getClass().getResource("/icon/more.png")));
                    panelMore.setVisible(true);
                    mig.setComponentConstraints(panelMore, "dock south,h 170!");
                    revalidate();
                }
            }
        });
        panel.add(cmdMore);
        panel.add(cmd);
        add(panel, "wrap");
        panelMore = new Panel_More();
        panelMore.setVisible(false);
        add(panelMore, "dock south,h 0!");
        revalidate();  // Ensure the layout is updated after visibility change
        repaint();
    }

    private void eventSend(JIMSendTextPane txt) throws Exception {
        String text = txt.getText().trim();
        Model_Send_Message message;
        if (!text.equals("")) {
            String temp = getFormattedDateTime();
            if (boxchat == null) {
                message = new Model_Send_Message(MessageType.TEXT, Service.getInstance().getUser().getUserID(), user.getUserID(), text, temp, 0);
            } else {
                message = new Model_Send_Message(MessageType.TEXT, Service.getInstance().getUser().getUserID(), 0, text, temp, boxchat.getIdBoxChat());
            }
            send(message);
            PublicEvent.getInstance().getEventChat().sendMessage(message);
            txt.setText("");
            txt.grabFocus();
            refresh();
        } else {
            txt.grabFocus();
        }
    }

    private void send(Model_Send_Message data) throws Exception {
        data.setText(E2EEncryption.encrypt(data.getText()));
        Service.getInstance().getClient().emit("send_to_user", data.toJsonObject());
    }

    private void refresh() {
        revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(229, 229, 229));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

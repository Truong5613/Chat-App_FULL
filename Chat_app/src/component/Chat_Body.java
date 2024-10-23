/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import Emoji.Emoji;
import app.MessageType;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import model.Model_Receive_Message;
import model.Model_Send_Message;
import net.miginfocom.swing.MigLayout;
import swing.ScrollBar;

/**
 *
 * @author mrtru
 */
public class Chat_Body extends javax.swing.JPanel {

    /**
     * Creates new form Chat_Body
     */
    public Chat_Body() {
        initComponents();
        init();

    }
    
    private void init(){
        body.setLayout(new MigLayout("fillx","","5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }
    
    public void addItemLeft(Model_Receive_Message data) {
        if (data.getMessageType() == MessageType.TEXT) {
            Chat_left item = new Chat_left();
            item.setText(data.getText());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        } else if (data.getMessageType() == MessageType.EMOJI) {
            Chat_left item = new Chat_left();
            item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        } else if (data.getMessageType() == MessageType.IMAGE) {
            Chat_left item = new Chat_left();
            item.setText("");
            item.setImage(data.getDataImage());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        }
        repaint();
        revalidate();
    }
     
    public void addItemLeft(String text, String user, String[] image) {
        Chat_left_with_profile item = new Chat_left_with_profile();
        item.setText(text);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }
    
    public void addItemFile(String text, String user, String filename,String filesize) {
        Chat_left_with_profile item = new Chat_left_with_profile();
        item.setText(text);
        item.setFile(filename,filesize);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }
     
    public void addItemRight(Model_Send_Message data) {
        if (data.getMessageType() == MessageType.TEXT) {
            Chat_right item = new Chat_right();
            item.setText(data.getText());
            body.add(item, "wrap, al right, w 100::80%");
            item.setTime();
        } else if (data.getMessageType() == MessageType.EMOJI) {
            Chat_right item = new Chat_right();
            item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
            body.add(item, "wrap, al right, w 100::80%");
            item.setTime();
        } else if (data.getMessageType() == MessageType.IMAGE) {
            Chat_right item = new Chat_right();
            item.setText("");
            item.setImage(data.getFile());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");

        }
        repaint();
        revalidate();
        scrollToBottom();
    }
    
    public void addItemFileRight(String text, String filename,String filesize) {
        Chat_right item = new Chat_right();
        item.setText(text);
        item.setFile(filename,filesize);
        body.add(item, "wrap, al right, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
        scrollToBottom();
    }
    
    public void addDate(String date) {
        Chat_date item = new Chat_date();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }
     
    public void clearchat(){
        body.removeAll();
        revalidate();
    }
    
    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setOpaque(false);

        sp.setBackground(new java.awt.Color(255, 255, 255));
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setOpaque(false);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 678, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}

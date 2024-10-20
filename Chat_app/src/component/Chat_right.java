/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Icon;
import model.Model_File_Sender;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author mrtru
 */
public class Chat_right extends javax.swing.JLayeredPane {

    /**
     * Creates new form Chat_left
     */
    public Chat_right() {
        initComponents();
        setLayout(new MigLayout("wrap"));
        txt.setBackground(new Color(153, 206, 255));
    }

    public void setText(String text) {
        if (text.equals("")) {
            txt.hideText();
        } else {
            txt.setText(text);
        }
        txt.seen();
    }

    public void setImage(Model_File_Sender fileSender) {
        txt.setImage(true, fileSender);
    }

    public void setImage(String... image) {
        // txt.setImage(true, image);
    }

    public void setFile(Model_File_Sender fileSender) {
        txt.setFile(fileSender);
    }

    public void setEmoji(Icon icon) {
        txt.hideText();
        txt.setEmoji(true, icon);
    }

    public void setTime(String time) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Adjust as needed
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalDateTime temp = LocalDateTime.parse(time, inputFormatter);
        String formattedTime = temp.format(outputFormatter);
        txt.setTime(formattedTime);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt = new component.Chat_Item();

        setBackground(new java.awt.Color(255, 255, 255));

        setLayer(txt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.Chat_Item txt;
    // End of variables declaration//GEN-END:variables
}

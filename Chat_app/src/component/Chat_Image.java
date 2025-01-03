/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import event.PublicEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import model.Model_File_Sender;
import model.Model_Receive_Image;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author mrtru
 */
public class Chat_Image extends javax.swing.JLayeredPane {

    /**
     * Creates new form Chat_Image
     */
    public Chat_Image() {
        initComponents();
        setLayout(new MigLayout());
    }

    public Chat_Image(boolean right) {
        initComponents();
        setLayout(new MigLayout("", "0[" + (right ? "right" : "left") + "]0", "3[]3"));
    }

    public void addImage(Model_File_Sender fileSender) {
        Icon image = new ImageIcon(fileSender.getFile().getAbsolutePath());
        Image_item pic = new Image_item();
        pic.setPreferredSize(new Dimension(200, 200));
        pic.setImage(image, fileSender);
        addEvent(pic, image);
        add(pic, "wrap");

    }

    public void addImage(Model_Receive_Image dataImage) {
        Image_item pic = new Image_item();
        pic.setPreferredSize(new Dimension(dataImage.getWidth(), dataImage.getHeight()));
        pic.setImage(dataImage);
        try {
            File file = new File("client_data/"+dataImage.getFileName());
            if (file.exists()) {
                Image img = ImageIO.read(file); 
                Icon imageIcon = new ImageIcon(img);
                addEvent(pic, imageIcon);        
            } else {
                System.err.println("File not found: " + dataImage.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(pic, "wrap");
    }

    private void addEvent(Component com, Icon image) {
        com.setCursor(new Cursor(Cursor.HAND_CURSOR));
        com.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    PublicEvent.getInstance().getEventImageView().viewImage(image);
                }
            }
        });
    }

    private Dimension getAutoSize(Icon image, int w, int h) {
        if (w > image.getIconWidth()) {
            w = image.getIconWidth();
        }
        if (h > image.getIconHeight()) {
            h = image.getIconHeight();
        }
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

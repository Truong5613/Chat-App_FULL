/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

/**
 *
 * @author mrtru
 */
public class PictureBox extends JLayeredPane{

    public Icon getImage() {
        return Image;
    }

    public void setImage(Icon Image) {
        this.Image = Image;
        repaint();
    }
    private Icon Image;
    @Override
    protected void paintComponent(Graphics g) {
        if(Image!=null){
            Graphics2D g2 = (Graphics2D) g;
            Rectangle size = this.getAutoSize(Image);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(this.toImage(Image), size.getLocation().x, size.getLocation().y, size.getSize().width, size.getSize().height, (ImageObserver)null);
        }
        super.paintComponent(g);
        
    }
    
    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();
        int h = getHeight();
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
        int x = getWidth() / 2 - (width / 2);
        int y = getHeight() / 2 - (height / 2);
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }


    private Image toImage(Icon icon) {
        return ((ImageIcon)icon).getImage();
    }
}

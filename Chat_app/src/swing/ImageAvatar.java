/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class ImageAvatar extends JComponent {
    private Icon image;
    private int borderSize = 5;
    private Color borderColor = new Color(60, 60, 60);

    public ImageAvatar() {
    }

    public Icon getImage() {
        return this.image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }

    public int getBorderSize() {
        return this.borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void paint(Graphics g) {
        if (this.image != null) {
            int width = this.image.getIconWidth();
            int height = this.image.getIconHeight();
            int diameter = Math.min(width, height);
            BufferedImage mask = new BufferedImage(width, height, 2);
            Graphics2D g2d = mask.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.fillOval(0, 0, diameter - 1, diameter - 1);
            g2d.dispose();
            BufferedImage masked = new BufferedImage(diameter, diameter, 2);
            g2d = masked.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int x = (diameter - width) / 2;
            int y = (diameter - height) / 2;
            g2d.drawImage(this.toImage(this.image), x, y, (ImageObserver)null);
            g2d.setComposite(AlphaComposite.getInstance(6));
            g2d.drawImage(mask, 0, 0, (ImageObserver)null);
            g2d.dispose();
            Icon icon = new ImageIcon(masked);
            Rectangle size = this.getAutoSize(icon);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(this.toImage(icon), size.getLocation().x, size.getLocation().y, size.getSize().width, size.getSize().height, (ImageObserver)null);
            if (this.borderSize > 0) {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(this.borderColor);
                g2.setStroke(new BasicStroke((float)this.borderSize));
                g2.drawOval(size.x = this.borderSize / 2, size.y + this.borderSize / 2, size.width - this.borderSize, size.height - this.borderSize);
            }
        }

        super.paint(g);
    }

    private Rectangle getAutoSize(Icon image) {
        int w = this.getWidth();
        int h = this.getHeight();
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double)w / (double)iw;
        double yScale = (double)h / (double)ih;
        double scale = Math.max(xScale, yScale);
        int width = (int)(scale * (double)iw);
        int height = (int)(scale * (double)ih);
        int x = (w - width) / 2;
        int y = (h - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon)icon).getImage();
    }
}

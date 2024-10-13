/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Testing;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import swing.blurHash.BlurHash;

/**
 *
 * @author mrtru
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            try{
                BufferedImage image = ImageIO.read(new File("C:\\Users\\mrtru\\Desktop\\Chat App\\Chat App pre\\src\\icon\\tree.png"));
                String blurString = BlurHash.encode(image);
                   System.out.println(blurString);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.raven.swing.table.EventAction;
import com.raven.swing.table.ModelAction;
import com.raven.swing.table.ModelProfile;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author mrtru
 */
public class Model_User_Account {
    private int userID;
    private String userName;
    private String gender;
    private String image;
    private boolean status;
    private String imageString;
    private String imageBackground;
    private String birthDay;
    private String address;
    private String description;
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public Model_User_Account(int userID, String userName, String gender, String imageString,String imageBackground,String birthDay, String address, String Description ,boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.imageString = imageString;
        this.imageBackground = imageBackground;
        this.birthDay = birthDay;
        this.address = address;
        this.description = Description;
        this.status = status;
    }
    
     public Model_User_Account(String userName, String gender, boolean status, String imageString) {
        this.userName = userName;
        this.gender = gender;
        this.status = status;
        this.imageString = imageString;
    }

    public Model_User_Account() {
    }
    private ImageIcon decodeBase64Image(String base64Image) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            Image img = ImageIO.read(bis);
            if (img != null) {
                return new ImageIcon(img);
            } else {
                return new ImageIcon(getClass().getResource("/com/raven/icon/profile.jpg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon(getClass().getResource("/com/raven/icon/profile.jpg"));
        }
    }
    public Object[] toRowTable(EventAction event) {
        ImageIcon icon = decodeBase64Image(getImageString());
        String statusText = isStatus() ? "Active" : "Inactive";
        ModelProfile profile = new ModelProfile(icon, userName);
        return new Object[]{
            profile,
            gender, 
            statusText,
            new ModelAction(this, event)
        };
    }

    /**
     * @return the imageBackground
     */
    public String getImageBackground() {
        return imageBackground;
    }

    /**
     * @param imageBackground the imageBackground to set
     */
    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }

    /**
     * @return the birthDay
     */
    public String getBirthDay() {
        return birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

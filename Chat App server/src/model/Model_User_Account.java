
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
    private String imageBackground;
    private String birthDay;
    private String address;
    private String description;
    private boolean status;

    private boolean isBold = false;

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean isBold) {
        this.isBold = isBold;
    }
    
    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }
    
 
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

    public Model_User_Account(){
        
    }
    
    public Model_User_Account(int userID, String userName, String gender, String imageString,String imageBackground,String birthDay, String address, String Description ,boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.image = imageString;
        this.imageBackground = imageBackground;
        this.birthDay = birthDay;
        this.address = address;
        this.description = Description;
        this.status = status;
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
        ImageIcon icon = decodeBase64Image(getImage());
        String statusText = isStatus() ? "Active" : "Inactive";
        ModelProfile profile = new ModelProfile(icon, userName);
        return new Object[]{
            profile,
            gender, 
            statusText,
            new ModelAction(this, event)
        };
    }
}

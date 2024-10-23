/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.raven.swing.table.EventAction;
import com.raven.swing.table.ModelAction;
import com.raven.swing.table.ModelProfile;
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

    public Model_User_Account(int userID, String userName, String gender, String image, boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.status = status;
    }
     public Model_User_Account(String userName, String gender, boolean status, String image) {
        this.userName = userName;
        this.gender = gender;
        this.status = status;
        this.image = image;
    }

    public Model_User_Account() {
    }
    public Object[] toRowTable(EventAction event) {
        ImageIcon icon = new ImageIcon(getClass().getResource(image));
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

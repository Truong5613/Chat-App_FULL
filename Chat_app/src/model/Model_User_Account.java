package model;

import org.json.JSONObject;

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

    public Model_User_Account(int userID, String userName, String gender, String image, String imageBackground, String birthDay, String address, String Description, boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.image = image != null ? image : ""; // Đảm bảo không null
        this.imageBackground = imageBackground != null ? imageBackground : ""; // Đảm bảo không null
        this.birthDay = birthDay;
        this.address = address;
        this.description = Description;
        this.status = status;
    }

    public Model_User_Account(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            userID = obj.getInt("userID");
            userName = obj.getString("userName");
            gender = obj.getString("gender");
            image = obj.optString("image", ""); // Sử dụng optString để cung cấp giá trị mặc định
            imageBackground = obj.optString("imageBackground", ""); // Sử dụng optString để cung cấp giá trị mặc định
            birthDay = obj.optString("birthDay", "");
            address = obj.optString("address", "");
            description = obj.optString("description", "");
            status = obj.getBoolean("status");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public JSONObject toJsonObject() {
    JSONObject obj = new JSONObject();
    try {
        obj.put("userID", userID);
        obj.put("userName", userName);
        obj.put("gender", gender);
        // Kiểm tra null cho image và imageBackground
        obj.put("image", image != null ? image : "");  
        obj.put("imageBackground", imageBackground != null ? imageBackground : "");  
        obj.put("birthDay", birthDay != null ? birthDay : "");  
        obj.put("address", address != null ? address : ""); 
        obj.put("description", description != null ? description : ""); 
        obj.put("status", status);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return obj;
}


}

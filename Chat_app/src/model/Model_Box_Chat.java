/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mrtru
 */
public class Model_Box_Chat {

    private int idBoxChat;
    private int[] userid;
    private String nameBoxChat;
    private String image;

    public int getIdBoxChat() {
        return idBoxChat;
    }

    public void setIdBoxChat(int idBoxChat) {
        this.idBoxChat = idBoxChat;
    }

    public int[] getUserid() {
        return userid;
    }

    public void setUserid(int[] userid) {
        this.userid = userid;
    }

    public String getNameBoxChat() {
        return nameBoxChat;
    }

    public void setNameBoxChat(String nameBoxChat) {
        this.nameBoxChat = nameBoxChat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Model_Box_Chat(int idBoxChat, int[] userid, String nameBoxChat, String image) {
        this.idBoxChat = idBoxChat;
        this.userid = userid;
        this.nameBoxChat = nameBoxChat;
        this.image = image;
    }

    public Model_Box_Chat() {
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("idBoxChat", this.idBoxChat);
            json.put("nameBoxChat", this.nameBoxChat);
            json.put("image", this.image);
            JSONArray userArray = new JSONArray();
            for (int id : this.userid) {
                userArray.put(id);
            }
            json.put("userid", userArray);
            return json;
        } catch (JSONException e) {
            System.err.println("Lỗi khi tạo JSON: " + e.getMessage());
            return null;
        }
    }
}

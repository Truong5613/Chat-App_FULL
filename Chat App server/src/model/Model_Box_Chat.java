/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



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
    
}

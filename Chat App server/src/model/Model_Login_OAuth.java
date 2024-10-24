/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



/**
 *
 * @author ADMIN
 */
public class Model_Login_OAuth {
    private String userName;
    private String Token;
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Token;
    }

    public void setPassword(String password) {
        this.Token = password;
    }

    public Model_Login_OAuth( String userName, String password) {
        this.userName = userName;
        this.Token = password;
    }

    public Model_Login_OAuth() {
    }

}

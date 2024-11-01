/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

import java.util.List;
import model.Model_Box_Chat;
import model.Model_User_Account;

/**
 *
 * @author mrtru
 */
public interface EventMenuLeft {
    public void newUser(List<Model_User_Account> users);
    public void userConnect(int userID);
    public void userDisconnect(int userID);
    public void userClick(int[] UserID);
    public void userUpdate(Model_User_Account user);
    public List<Model_User_Account> getUsers();
    public void BoldUser ( int userID );
    public void ShowGroup(List<Model_Box_Chat> list);
    public void groupclick(int groupid);
    
}

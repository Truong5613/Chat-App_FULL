/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

import model.Model_Receive_Message;
import model.Model_Send_Message;

/**
 *
 * @author mrtru
 */
public interface EventChat {
    public void sendMessage(Model_Send_Message data);
    public void receiveMessage(Model_Receive_Message data);
}

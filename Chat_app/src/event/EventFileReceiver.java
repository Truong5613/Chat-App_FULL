/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

import java.io.File;

/**
 *
 * @author mrtru
 */
public interface EventFileReceiver {
    
    public void onReceiving(double percentage);

    public void onStartReceiving(long filesize,String name,String fileExtension);

    public void onFinish(File file);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mrtru
 */
public class Model_Receive_File {
    private int fileID;
    private long fileSize; // in bytes

    // Constructor with parameters
    public Model_Receive_File(int fileID, long fileSize) {
        this.fileID = fileID;
        this.fileSize = fileSize;
    }

    // Constructor to create a Model_File object from JSON
    public Model_Receive_File(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            fileID = obj.getInt("fileID");
            fileSize = obj.getLong("fileSize");
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e);
        }
    }

    // Getters and Setters
    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }



    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }



    

    // Method to convert the object to JSON
    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("fileSize", fileSize);
            return json;
        } catch (JSONException e) {
            System.err.println("Error creating JSON: " + e);
            return null;
        }
    }

}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import app.MessageType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mrtru
 */
public class Model_Receive_Message {

    private MessageType messageType;
    private int fromUserID;
    private String text;
    private Model_Receive_Image dataImage;
    private Model_Receive_File dataFile;
    private String time;
    private int boxid;

    public int getBoxid() {
        return boxid;
    }

    public void setBoxid(int boxid) {
        this.boxid = boxid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Model_Receive_File getDataFile() {
        return dataFile;
    }

    public void setDataFile(Model_Receive_File dataFile) {
        this.dataFile = dataFile;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Model_Receive_Image getDataImage() {
        return dataImage;
    }

    public void setDataImage(Model_Receive_Image dataImage) {
        this.dataImage = dataImage;
    }

    public Model_Receive_Message(MessageType messageType, int fromUserID, String text, Model_Receive_File dataFile, String time) {
        this.messageType = messageType;
        this.fromUserID = fromUserID;
        this.text = text;
        this.dataFile = dataFile;
        this.time = time;
    }

    public Model_Receive_Message(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
            fromUserID = obj.getInt("fromUserID");
            text = obj.getString("text");
            if (!obj.isNull("dataImage")) {
                dataImage = new Model_Receive_Image(obj.get("dataImage"));
            }
            if (!obj.isNull("dataFile")) {
                dataFile = new Model_Receive_File(obj.get("dataFile"));
            }
            time = obj.getString("time");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            if (dataImage != null) {
                json.put("dataImage", dataImage.toJsonObject());
            }
            if (dataFile != null) {
                json.put("dataFile", dataFile.toJsonObject());
            }
            json.put("time", time);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}

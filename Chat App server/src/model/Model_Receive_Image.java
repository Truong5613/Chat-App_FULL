/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mrtru
 */
public class Model_Receive_Image {
    private int fileID;
    private String image;
    private String fileName;
    private int width;
    private int height;
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Model_Receive_Image(int fileID, String image, String fileName, int width, int height) {
        this.fileID = fileID;
        this.image = image;
        this.fileName = fileName;
        this.width = width;
        this.height = height;
    }

    

    public Model_Receive_Image() {
    }


}

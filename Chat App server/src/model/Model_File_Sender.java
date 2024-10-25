/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author mrtru
 */
public class Model_File_Sender {

    private Model_File data;
    private File file;
    private RandomAccessFile accFile;
    private long fileSize;

    public Model_File getData() {
        return data;
    }

    public void setData(Model_File data) {
        this.data = data;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Model_File_Sender(Model_File data, File file) throws IOException {
        this.data = data;
        this.file = file;
        this.accFile = new RandomAccessFile(file, "r");
        this.fileSize = accFile.length();
    }

    public Model_File_Sender() {
    }

    public byte[] read(long currentLength) throws IOException {
        accFile.seek(currentLength); // Di chuyển đến vị trí hiện tại
        if (currentLength < fileSize) { // Kiểm tra xem currentLength có nhỏ hơn fileSize không
            int max = 2000;
            long length = currentLength + max >= fileSize ? fileSize - currentLength : max;

            // Kiểm tra length trước khi tạo mảng
            if (length <= 0) {
                return new byte[0]; // Trả về mảng rỗng nếu length không hợp lệ
            }

            byte[] b = new byte[(int) length]; // Tạo mảng byte với kích thước hợp lệ
            int bytesRead = accFile.read(b); // Đọc dữ liệu vào mảng
            if (bytesRead < length) {
                // Nếu số byte đọc ít hơn dự kiến, bạn có thể xử lý tình huống này
            }
            return b; // Trả về mảng đã đọc
        } else {
            return null; // Trả về null nếu currentLength bằng fileSize
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

/**
 *
 * @author mrtru
 */
public class E2EEncryption {
    private static final String AES_KEY_STRING = "fruoeMvskNH005nqGECKRg==";
    private static SecretKey getKey() {
        byte[] decodedKey = Base64.getDecoder().decode(AES_KEY_STRING);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
    public static String encrypt(String data) throws Exception {
        SecretKey key = getKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }
    public static String decrypt(String encryptedData) throws Exception {
        SecretKey key = getKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }

    
}

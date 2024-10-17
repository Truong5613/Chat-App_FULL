/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import connection.DatabaseConnection;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Model_Client;
import model.Model_Login;
import model.Model_Message;
import model.Model_Register;
import model.Model_User_Account;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import java.sql.ResultSet;
import com.google.api.services.oauth2.model.Userinfoplus;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import model.Model_Login_OAuth;

/**
 *
 * @author mrtru
 */
public class ServiceUser {

    private static final String CREDENTIALS_FILE_PATH = "src/path/to/client_secret.json";
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/userinfo.email");
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    
    //  SQL
    private final String LOGIN = "select UserID, user_account.UserName, Gender, ImageString from `user` join user_account using (UserID) "
            + "where `user`.UserName=BINARY(?) and `user`.`Password`=BINARY(?) and user_account.`Status`='1'";
    private final String SELECT_USER_ACCOUNT = "select UserID, UserName, Gender, ImageString from user_account where user_account.`Status`='1' and UserID<>?";
    private final String INSERT_USER = "insert into user (UserName, `Password`) values (?,?)";
    private final String INSERT_USER_ACCOUNT = "insert into user_account (UserID, UserName) values (?,?)";
    private final String CHECK_USER = "select UserID from user where UserName =? limit 1";
    //  Instance
    private final Connection con;

    public ServiceUser() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public Model_Message register(Model_Register data) {
        //  Check user exit
        Model_Message message = new Model_Message();
        try {
            PreparedStatement p = con.prepareStatement(CHECK_USER,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            p.setString(1, data.getUserName());
            ResultSet r = p.executeQuery();
            if (r.first()) {
                message.setAction(false);
                message.setMessage("User Already Exit");
            } else {
                message.setAction(true);
            }
            r.close();
            p.close();
            if (message.isAction()) {
                //  Insert User Register
                con.setAutoCommit(false);
                p = con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, data.getUserName());
                p.setString(2, data.getPassword());
                p.execute();
                r = p.getGeneratedKeys();
                r.first();
                int userID = r.getInt(1);
                r.close();
                p.close();
                //  Create user account
                p = con.prepareStatement(INSERT_USER_ACCOUNT);
                p.setInt(1, userID);
                p.setString(2, data.getUserName());
                p.execute();
                p.close();
                con.commit();
                con.setAutoCommit(true);
                message.setAction(true);
                message.setMessage("Ok");
                message.setData(new Model_User_Account(userID, data.getUserName(), " ", " ", true));
            }
        } catch (SQLException e) {
            message.setAction(false);
            message.setMessage("Server Error");
            System.out.println(e);
            try {
                if (con.getAutoCommit() == false) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {
            }
        }
        return message;
    }

    public Model_User_Account login(Model_Login login) throws SQLException {
        Model_User_Account data = null;
        PreparedStatement p = con.prepareStatement(LOGIN,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        p.setString(1, login.getUserName());
        p.setString(2, login.getPassword());
        ResultSet r = p.executeQuery();
        if (r.first()) {
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            data = new Model_User_Account(userID, userName, gender, image, true);
        }
        r.close();
        p.close();
        return data;
    }

    public boolean CheckUser(Model_User_Account user){
        try {
            boolean check = false;
            String name = user.getUserName();
            PreparedStatement p = con.prepareStatement(CHECK_USER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            p.setString(1, name);
            ResultSet r = p.executeQuery();
            if (r.first()) {
                // Tài khoản đã tồn tại, tiến hành đăng nhập              
                check = true;
                return check;
            }
            return check;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    
    public Model_User_Account loginOAuth(Model_Login_OAuth login) throws SQLException {
        try {
            Model_User_Account data = null;
            String email = login.getUserName(); // Email từ Google OAuth
            String userName = email; // Sử dụng email làm UserName
            String gender = ""; // Giới tính (có thể lấy nếu cần)

            // Kiểm tra xem tài khoản với email đã tồn tại trong cơ sở dữ liệu hay chưa
            PreparedStatement p = con.prepareStatement(CHECK_USER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            p.setString(1, email);
            ResultSet r = p.executeQuery();

            if (r.first()) {
                // Tài khoản đã tồn tại, tiến hành đăng nhập
                int userID = r.getInt("UserID");
                data = new Model_User_Account(userID, userName, gender, "", true);
                r.close();
                p.close();
                return data;
            } else {
                // Tài khoản chưa tồn tại, tiến hành tạo mới
                con.setAutoCommit(false);

                // Thêm thông tin vào bảng `user`
                p = con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, email); // Sử dụng email làm UserName
                p.setString(2, "oauth2"); // Để password một giá trị mặc định như "oauth2"
                p.executeUpdate();
                r = p.getGeneratedKeys();
                r.first();
                int newUserID = r.getInt(1);
                r.close();
                p.close();

                // Thêm thông tin vào bảng `user_account`
                p = con.prepareStatement(INSERT_USER_ACCOUNT);
                p.setInt(1, newUserID);
                p.setString(2, userName); // Sử dụng email làm UserName
                p.executeUpdate();
                p.close();

                // Xác nhận giao dịch
                con.commit();
                con.setAutoCommit(true);

                // Tạo đối tượng Model_User_Account mới cho tài khoản vừa tạo
                data = new Model_User_Account(newUserID, userName, gender, " ", true);
                return data;
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (!con.getAutoCommit()) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
    
    public List<Model_User_Account> getUser(int exitUser) throws SQLException {
        List<Model_User_Account> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement(SELECT_USER_ACCOUNT);
        p.setInt(1, exitUser);
        ResultSet r = p.executeQuery();
        while (r.next()) {
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            list.add(new Model_User_Account(userID, userName, gender, image, checkUserStatus(userID)));
        }
        r.close();
        p.close();
        return list;
    }

    private boolean checkUserStatus(int userID) {
        List<Model_Client> clients = Service.getInstance(null).getListClient();
        for (Model_Client c : clients) {
            if (c.getUser().getUserID() == userID) {
                return true;
            }
        }
        return false;
    }
    
    private Userinfoplus getUserInfo(Credential credential) throws IOException {
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), JSON_FACTORY, credential)
                .setApplicationName("Java Chapapplication")
                .build();
        Userinfoplus userInfo = oauth2.userinfo().get().execute();
        return userInfo;
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline") // Ensure a new token is fetched each time
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(12345).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    
}

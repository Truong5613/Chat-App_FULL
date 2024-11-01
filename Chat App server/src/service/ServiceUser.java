/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import connection.DatabaseConnection;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Model_Client;
import model.Model_Login;
import model.Model_Login_OAuth;
import model.Model_Message;
import model.Model_Register;
import model.Model_User_Account;

/**
 *
 * @author mrtru
 */
public class ServiceUser {

    //  SQL
    private final String LOGIN = "select UserID, user_account.UserName, Gender, ImageString, ImageBackgroundString, BirthDay, Address, Description from `user` join user_account using (UserID)"
            + "where `user`.UserName=BINARY(?) and `user`.`Password`=BINARY(?) and user_account.`Status`='1'";
    private final String SELECT_USER_ACCOUNT = "select UserID, UserName, Gender, ImageString, ImageBackgroundString, BirthDay, Address, Description from user_account where user_account.`Status`='1' and UserID<>?";
    private final String INSERT_USER = "insert into user (UserName, `Password`) values (?,?)";
    private final String INSERT_USER_ACCOUNT = "insert into user_account (UserID, UserName) values (?,?)";
    private final String CHECK_USER = "select UserID, UserName, Gender, ImageString, ImageBackgroundString, BirthDay, Address, Description from user_account where UserName =? limit 1";
    private final String UPDATE_USER_ACCOUNT = "UPDATE user_account SET UserName = ?, Gender = ?, ImageString = ?, ImageBackgroundString = ?, "
            + "BirthDay = ?, Address = ?, Description = ? WHERE UserID = ?";
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
                message.setData(new Model_User_Account(userID, data.getUserName(), " ", " ", " ", " ", " ", " ", true));
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
            String imageBackground = r.getString(5);
            String birthDay = r.getString(6);
            String address = r.getString(7);
            String description = r.getString(8);
            data = new Model_User_Account(userID, userName, gender, image, imageBackground, birthDay, address, description, true);
        }
        r.close();
        p.close();
        return data;
    }

    public boolean CheckUser(Model_User_Account user) {
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
                data = new Model_User_Account(
                    userID,
                    r.getString("UserName"),
                    r.getString("Gender"),
                    r.getString("ImageString"),
                    r.getString("ImageBackgroundString"),
                    r.getString("BirthDay"),
                    r.getString("Address"),
                    r.getString("Description"),
                    true
                );
                r.close();
                p.close();
                return data;
            } else {
                // Tài khoản chưa tồn tại, tiến hành tạo mới
                con.setAutoCommit(false);

                // Thêm thông tin vào bảng `user`
                p = con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, email); // Sử dụng email làm UserName
                p.setString(2, "JfMHXTgfkLp05Al"); // Để password một giá trị mặc định như "oauth2"
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
                data = new Model_User_Account(newUserID, userName, gender, " ", " ", " ", " ", " ", true);
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

    public boolean updateUserInfo(Model_User_Account user) throws SQLException {
        try (PreparedStatement p = con.prepareStatement(UPDATE_USER_ACCOUNT)) {
            p.setString(1, user.getUserName());
            p.setString(2, user.getGender());
            p.setString(3, user.getImage());
            p.setString(4, user.getImageBackground());
            p.setString(5, user.getBirthDay());
            p.setString(6, user.getAddress());
            p.setString(7, user.getDescription());
            p.setInt(8, user.getUserID());
            int rowsAffected = p.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updateUserInDatabase(Model_User_Account user) {
        String sql = "UPDATE user_account SET UserName = ?, Gender = ?, ImageString = ?, ImageBackgroundString = ?, "
                + "BirthDay = ?, Address = ?, Description = ? WHERE UserID = ?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, user.getUserName());
            p.setString(2, user.getGender());
            p.setString(3, user.getImage());
            p.setString(4, user.getImageBackground());
            p.setString(5, user.getBirthDay());
            p.setString(6, user.getAddress());
            p.setString(7, user.getDescription());
            p.setInt(8, user.getUserID());

            int rowsAffected = p.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            String imageBackground = r.getString(5);
            String birthDay = r.getString(6);
            String address = r.getString(7);
            String description = r.getString(8);
            list.add(new Model_User_Account(userID, userName, gender, image, imageBackground, birthDay, address, description, checkUserStatus(userID)));
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

    public List<Model_User_Account> getOnlineUsers() throws SQLException {
        List<Model_User_Account> onlineUsers = new ArrayList<>();
        List<Model_Client> clients = Service.getInstance(null).getListClient();
        for (Model_Client client : clients) {
            onlineUsers.add(client.getUser());
        }
        return onlineUsers;
    }
}

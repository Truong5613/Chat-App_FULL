package service;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Model_Box_Chat;

public class ServiceBox {
// SQL Queries

    private final String INSERT_BOX_CHAT = "INSERT INTO box_chat (name, image) VALUES (?, ?)";
    private final String SELECT_BOX_CHAT = "SELECT id, name, image FROM box_chat";
    private final String INSERT_USER_IN_BOX = "INSERT INTO box_chat_members (box_chat_id, user_id) VALUES (?, ?)";
    private final String SELECT_MEMBERS_IN_BOX = "SELECT user_id FROM box_chat_members WHERE box_chat_id = ?";
    // Instance
    private final Connection con;

    public ServiceBox() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public int addBoxChat(Model_Box_Chat boxChat) throws SQLException {
        int generatedId = -1; // Khởi tạo ID

        // Thực hiện chèn và lấy ID
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_BOX_CHAT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, boxChat.getNameBoxChat());
            pstmt.setString(2, boxChat.getImage());
            pstmt.executeUpdate();

            // Lấy ID được sinh ra
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Lấy ID từ ResultSet
                }
            }
        }

        return generatedId; // Trả về ID vừa tạo
    }

    public void addUserToBoxChat(int boxChatId, int userId) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_USER_IN_BOX)) {
            pstmt.setInt(1, boxChatId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }

    public List<Model_Box_Chat> getAllBoxChats() throws SQLException {
        List<Model_Box_Chat> boxChats = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SELECT_BOX_CHAT); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Model_Box_Chat boxChat = new Model_Box_Chat();
                boxChat.setIdBoxChat(rs.getInt("id"));
                boxChat.setNameBoxChat(rs.getString("name"));
                boxChat.setImage(rs.getString("image"));
                boxChats.add(boxChat);
            }
        }
        return boxChats;
    }

    // Method to get all members of a specific chat box
    public List<Integer> getMembersInBoxChat(int boxChatId) throws SQLException {
        List<Integer> members = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SELECT_MEMBERS_IN_BOX)) {
            pstmt.setInt(1, boxChatId); // Set the box chat ID
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    members.add(rs.getInt("user_id")); // Add user ID to the list
                }
            }
        }
        return members; // Return the list of member user IDs
    }
}

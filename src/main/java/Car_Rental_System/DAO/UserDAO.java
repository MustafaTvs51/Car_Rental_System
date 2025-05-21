package Car_Rental_System.DAO;
import Car_Rental_System.Models.User;
import Car_Rental_System.Utils.DB_util;

import java.sql.*;
public class UserDAO {

        public void saveUser(User user) throws SQLException {
            String sql = "INSERT INTO users (name, email, password_hash, age, is_corporate, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DB_util.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPasswordHash());
                stmt.setInt(4, user.getAge());
                stmt.setBoolean(5, user.isCorporate());
                stmt.setBoolean(6, user.isAdmin());
                stmt.executeUpdate();
            }
        }

        public User findByEmail(String email) throws SQLException {
            String sql = "SELECT * FROM users WHERE email = ?";
            try (Connection conn = DB_util.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setAge(rs.getInt("age"));
                    user.setCorporate(rs.getBoolean("is_corporate"));
                    user.setAdmin(rs.getBoolean("is_admin"));
                    return user;
                }
            }
            return null;
        }
}

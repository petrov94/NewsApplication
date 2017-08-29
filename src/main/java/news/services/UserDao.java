package news.services;

import news.models.User;
import java.sql.*;

/**
 * Created by Petar on 8/26/2017.
 */
public class UserDao {

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/TopNews", "root", "password");
    }

    public void insertEmployee(User emp) throws ClassNotFoundException {
        String sql = "INSERT INTO Users " +
                "(username, password,subscription) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, emp.getUsername());
            ps.setString(2, emp.getPassword());
            ps.setDate(3, emp.getSubscription());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public User getEmployeeByUsername(String username) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        Statement ps = getConnection().createStatement();
        ResultSet rs = ps.executeQuery(sql);
        User user = new User();
        while (rs.next()) {
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setSubscription(rs.getDate("subscription"));
            if(rs.getString("medias")!=null){
                user.setE_mail(rs.getString("email"));
                user.setMedias(rs.getString("medias"));
            }
        }
        return user;
    }

    public boolean getEmployeeByUsernameAndPass(String username, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2,password);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? true : false;
    }
}

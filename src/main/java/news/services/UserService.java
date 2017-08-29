package news.services;

import news.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.SQLException;

/**
 * Created by Petar on 8/26/2017.
 */
public class UserService {

    UserDao userDao = new UserDao();

    public void insertUser(User emp) throws ClassNotFoundException {
        userDao.insertEmployee(emp);
    }
    public boolean getUserByNameAndPassword(String username,String password) throws SQLException, ClassNotFoundException {
        return userDao.getEmployeeByUsernameAndPass(username,password);
    }

    public User getUserbyName(String username) throws SQLException, ClassNotFoundException {
        return userDao.getEmployeeByUsername(username);
    }
}

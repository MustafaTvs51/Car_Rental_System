package Car_Rental_System.Services;

import Car_Rental_System.DAO.UserDAO;
import Car_Rental_System.Utils.HashUtil;
import Car_Rental_System.Models.User;
import Car_Rental_System.Utils.HashUtil;

import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public boolean register(String name, String email, String password, int age, boolean isCorporate) throws SQLException {
        if (userDAO.findByEmail(email) != null) {
            System.out.println("Bu email ile kayıtlı bir kullanıcı zaten var.");
            return false;
        }

        String hash = HashUtil.sha256(password);
        User user = new User(name, email, hash, age, isCorporate, false);
        userDAO.saveUser(user);
        System.out.println("Kayıt başarılı!");
        return true;
    }

    public User login(String email, String password) throws SQLException {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            System.out.println("Kullanıcı bulunamadı.");
            return null;
        }

        String hash = HashUtil.sha256(password);
        if (!user.getPasswordHash().equals(hash)) {
            System.out.println("Şifre yanlış.");
            return null;
        }

        System.out.println("Giriş başarılı. Hoş geldiniz " + user.getName());
        return user;
    }
}

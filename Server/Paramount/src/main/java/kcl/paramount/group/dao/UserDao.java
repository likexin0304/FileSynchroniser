package kcl.paramount.group.dao;

public interface UserDao {
    Boolean login(String username, String password);
    Boolean chechUser(String username);
    Boolean addUser(String username, String password, String answer1, String answer2);
    Boolean changePassword(String username, String newPassword);
    Boolean checkAnswer(String username, String answer1, String answer2);
}

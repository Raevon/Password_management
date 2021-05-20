package utilities;

import models.Password;
import models.User;

import java.util.ArrayList;
import java.util.List;

public class UserStringConverter {
    public static String getAccountCvsData(User user){
        return user.getNickname() + ',' + user.getSalt() + ',' + user.getHash() +'\n';
    }

    public static String[] getPasswordCvsData(User user){
        List<Password> userPasswords = user.getPasswords();
        int passwordCount = userPasswords.size();
        String[] passwords = new String[passwordCount];

        for (int i = 0; i < passwordCount; i++){
            Password p = userPasswords.get(i);
            passwords[i] = p.getTitle() + ',' + p.getPassword() +',' + p.getUrl() + ',' + p.getComment() + '\n';
        }
        return passwords;
    }

    public static String getPasswordCvsData(Password p){
        return p.getTitle() + ',' + p.getPassword() +',' + p.getUrl() + ',' + p.getComment() + '\n';
    }

    public static List<User> getUsers(String[] cvsData) throws Exception {
        List<User> users = new ArrayList<>();
        for (String s : cvsData) {
            String[] data = s.split(",");
            if (data.length < 3){
                continue;
            }
            users.add(new User(data[0], data[1], data[2]));
        }
        return users;
    }

    public static List<Password> getPasswords(String[] cvsData){
        List<Password> passwords = new ArrayList<>();
        for (String s : cvsData){
            String[] data = s.split(",");
            if (data.length < 4){
                continue;
            }
            passwords.add(new Password(data[0], data[1], data[2], data[3]));
        }
        return passwords;
    }
}

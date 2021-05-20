package utilities;

import models.Password;
import models.User;
//import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.text.RandomStringGenerator;
import securities.AESEncryption;
import securities.StringHashing;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.List;

public class Mixer {
    public static void register(String nickname, String password) throws Exception {
        if (nickname == null || nickname.trim().equals("")){
            throw new Exception("Nickname cannot be empty");
        }
        if (password == null || password.trim().equals("")){
            throw new Exception("Password cannot be empty");
        }

        String[] data = FileManager.getLinesByName("users");
        List<User> users = UserStringConverter.getUsers(data);
        for (User u : users) {
            if (u.getNickname().equals(nickname)){
                throw new Exception("This user already exists");
            }
        }

        String salt = StringHashing.generateSalt();
        String hash = StringHashing.generateHash(password, salt);
        User user = new User(nickname, salt, hash);
        if(StringHashing.validateHash(password, salt, hash)){
            System.out.println("true");
        }
        FileManager.appendFile("users", UserStringConverter.getAccountCvsData(user));
        AESEncryption.encryptFile("", FileManager.getAesPath(nickname));
    }

    public static User login(String nickname, String password) throws Exception {
        String[] data = FileManager.getLinesByName("users");
        List<User> users = UserStringConverter.getUsers(data);
        User user = null;
        for (User u : users) {
            System.out.println(u.toString());
            if (u.getNickname().equals(nickname)){
                user = u;
            }
        }

        if (user == null){
            throw new Exception("This nickname does not exist");
        }
        if (!StringHashing.validateHash(password, user.getSalt(), user.getHash())){
            throw new Exception("Password does not match");
        }

        String[] cvsData = FileDecryptionCombo(nickname);
        List<Password> passwords = UserStringConverter.getPasswords(cvsData);
        user.setPasswords(passwords);
        return user;
    }


    public static void FileEncryptCombo(String name, String[] cvsData) throws InvalidKeyException, IOException {
        AESEncryption.encryptFile(combineArray(cvsData), FileManager.getAesPath(name));
        File file = new File(FileManager.getCsvPath(name));
        file.delete();
    }

    public static String[] FileDecryptionCombo(String name) throws IOException {
        String[] data = AESEncryption.decryptFile(FileManager.getAesPath(name));
        File file = new File(FileManager.getAesPath(name));
        file.delete();
        FileManager.writeFile(name ,combineArray(data));
        return data;
    }

    public static String combineArray(String[] cvsData){
        String data = "";
        for(String s : cvsData){
            data += s + '\n';
        }
        return data;
    }
/*
    public static String combineArray(String[] cvsData){
        String data = "";
        for(String s : cvsData){
            data += s + '\n';
        }
        return data;
    }*/

    public static String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(46, 127)
                .build();
        return pwdGenerator.generate(length);
    }
}

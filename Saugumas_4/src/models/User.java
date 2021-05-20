package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String nickname;
    private String salt;
    private String hash;
    private List<Password> passwords;

    public User(String nickname, String salt, String hash) throws Exception {
        if (nickname == null || nickname.trim().isEmpty())
            throw new Exception("Nickname cannot be empty");
        if (salt == null)
            throw new Exception("Salt cannot be empty");
        if (hash == null)
            throw new Exception("Hash cannot be empty");

        this.nickname = nickname;
        this.salt = salt;
        this.hash = hash;

        passwords = new ArrayList();
    }

    public Password getPassword(String title){
        for (Password p : passwords) {
            if (p.getTitle().equals(title)){
                return p;
            }
        }
        return null;
    }

    public int getPasswordIndex(String title){
        for (int i = 0 ; i < passwords.size(); i++){
            if (passwords.get(0).getTitle().equals(title)){
                return i;
            }
        }
        return -1;
    }

    public void removePassword(String title){
        Password password = getPassword(title);
        passwords.remove(password);
    }

    public void addPassword(Password received) throws Exception {
        if (getPassword(received.getTitle()) != null){
            throw new Exception("Password with such title already exists");
        }
        passwords.add(received);
    }

    public void updatePassword(Password received, String title){
        passwords.set(getPasswordIndex(title), received);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public List<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", salt=" + salt +
                ", hash=" + hash +
                ", passwords=" + passwords +
                '}';
    }
}

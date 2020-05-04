package Models;

import java.io.Serializable;

public class User implements Serializable {
    private int userID;
    private String username;
    private String password;
    private String email;
    private String contactno;

    public User() {
    }

    public User(int userID, String username, String password, String email, String contactno) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.contactno = contactno;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    

}

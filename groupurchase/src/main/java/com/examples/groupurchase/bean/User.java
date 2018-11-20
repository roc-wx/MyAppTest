package com.examples.groupurchase.bean;

public class User {
    private int _id;
    private String username;
    private String password;
    private String user_issue;
    private String password_security;

    public User() {
    }

    public User(int _id, String username, String password, String user_issue, String password_security) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.user_issue = user_issue;
        this.password_security = password_security;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public String getUser_issue() {
        return user_issue;
    }

    public void setUser_issue(String user_issue) {
        this.user_issue = user_issue;
    }

    public String getPassword_security() {
        return password_security;
    }

    public void setPassword_security(String password_security) {
        this.password_security = password_security;
    }
}

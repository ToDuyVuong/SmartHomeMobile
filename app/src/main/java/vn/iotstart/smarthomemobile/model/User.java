package vn.iotstart.smarthomemobile.model;

import java.io.Serializable;


public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String avatar;
    private boolean gender;
    private String email;
    private String phoneNumber;
    private String address;

    public User(String id, String username, String password,
                 String avatar, boolean gender, String email,
                String phoneNumber, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + username + '\'' +
//                ", email='" + email + '\'' +
//                ", gender='" + gender + '\'' +
//                ", images='" + avatar + '\'' +
//                '}';
//    }
}

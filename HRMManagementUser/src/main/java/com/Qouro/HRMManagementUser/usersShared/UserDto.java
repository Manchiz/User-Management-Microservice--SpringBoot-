package com.Qouro.HRMManagementUser.usersShared;

import com.Qouro.HRMManagementUser.UIModel.AlbumResponseModel;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String EncryptedPassword;
    private List<AlbumResponseModel> albums;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return EncryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        EncryptedPassword = encryptedPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AlbumResponseModel> getAlbum() {
        return albums;
    }

    public void setAlbum(List<AlbumResponseModel> albums) {
        this.albums = albums;
    }
}

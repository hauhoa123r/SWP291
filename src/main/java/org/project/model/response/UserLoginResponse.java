package org.project.model.response;

public class UserLoginResponse {

    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;

    public UserLoginResponse() {
    }

    public UserLoginResponse(Long id, String email, String fullName, String password, String username) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

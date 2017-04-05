package in.quantumtech.qthelpcare.ui.model;

/**
 * Created by quantum on 31/3/17.
 */

public class LoginModel {
    private String UserName,Password,Type;

    public LoginModel(String userName, String password, String type) {
        UserName = userName;
        Password = password;
        Type = type;
    }

    public LoginModel() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}

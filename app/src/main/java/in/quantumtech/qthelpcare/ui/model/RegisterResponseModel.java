package in.quantumtech.qthelpcare.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by quantum on 29/3/17.
 */

public class RegisterResponseModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RegisterResponseModel{" +
                "message='" + message + '\'' +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }
}

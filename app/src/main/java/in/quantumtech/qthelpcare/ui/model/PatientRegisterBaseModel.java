package in.quantumtech.qthelpcare.ui.model;

/**
 * Created by quantum on 30/3/17.
 */

public class PatientRegisterBaseModel {
    private String UserName;
    private String EmailId;
    private String MobileNo;
    private String Address;

    public PatientRegisterBaseModel() {
    }

    public PatientRegisterBaseModel(String userName, String emailId, String mobileNo, String address) {
        UserName = userName;
        EmailId = emailId;
        MobileNo = mobileNo;
        Address = address;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}

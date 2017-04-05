package in.quantumtech.qthelpcare.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by quantum on 31/3/17.
 */

public class LoginResponseModel {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("CentreName")
    @Expose
    private String centreName;
    @SerializedName("MobileNo")
    @Expose
    private Integer mobileNo;
    @SerializedName("LandLineNo")
    @Expose
    private Integer landLineNo;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("ProfDetailID")
    @Expose
    private Integer profDetailID;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Specilization")
    @Expose
    private Object specilization;
    @SerializedName("Service")
    @Expose
    private Object service;
    @SerializedName("Timing")
    @Expose
    private List<Timing> timing = null;
    @SerializedName("PaymentId")
    @Expose
    private Integer paymentId;
    @SerializedName("PaymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("AmountPaid")
    @Expose
    private String amountPaid;
    @SerializedName("PatientId")
    @Expose
    private Integer patientId;
    @SerializedName("UserName")
    @Expose
    private Object userName;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public Integer getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Integer mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getLandLineNo() {
        return landLineNo;
    }

    public void setLandLineNo(Integer landLineNo) {
        this.landLineNo = landLineNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getProfDetailID() {
        return profDetailID;
    }

    public void setProfDetailID(Integer profDetailID) {
        this.profDetailID = profDetailID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getSpecilization() {
        return specilization;
    }

    public void setSpecilization(Object specilization) {
        this.specilization = specilization;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public List<Timing> getTiming() {
        return timing;
    }

    public void setTiming(List<Timing> timing) {
        this.timing = timing;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }
}

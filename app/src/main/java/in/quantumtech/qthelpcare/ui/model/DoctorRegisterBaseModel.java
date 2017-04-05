package in.quantumtech.qthelpcare.ui.model;

import java.util.ArrayList;

/**
 * Created by quantum on 30/3/17.
 */

public class DoctorRegisterBaseModel {

    private String Name;
    private int Type;
    private String CentreName;
    private String MobileNo;
    private String LandLineNo;
    private String EmailId;
    private String Address;
    private String Gender;
    private ArrayList<String> Specilization;
    private ArrayList<String> Service;
    private ArrayList<String> DayTo;
    private ArrayList<String> DayFrom;
    private ArrayList<String> TimefromMorning;
    private ArrayList<String> TimeToMorning;
    private ArrayList<String> TimeFromEvening;
    private ArrayList<String> TimeToEvening;
    private String AppointmentFee;
    private String PaymentMethod;
    private String AmountPaid;

    public DoctorRegisterBaseModel() {
    }

    public DoctorRegisterBaseModel(String name, int type, String centreName, String mobileNo, String landLineNo, String emailId, String address, String gender, ArrayList<String> specilization, ArrayList<String> service, ArrayList<String> dayTo, ArrayList<String> dayFrom, ArrayList<String> timefromMorning, ArrayList<String> timeToMorning, ArrayList<String> timeFromEvening, ArrayList<String> timeToEvening, String appointmentFee, String paymentMethod, String amountPaid) {
        Name = name;
        Type = type;
        CentreName = centreName;
        MobileNo = mobileNo;
        LandLineNo = landLineNo;
        EmailId = emailId;
        Address = address;
        Gender = gender;
        Specilization = specilization;
        Service = service;
        DayTo = dayTo;
        DayFrom = dayFrom;
        TimefromMorning = timefromMorning;
        TimeToMorning = timeToMorning;
        TimeFromEvening = timeFromEvening;
        TimeToEvening = timeToEvening;
        AppointmentFee = appointmentFee;
        PaymentMethod = paymentMethod;
        AmountPaid = amountPaid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getCentreName() {
        return CentreName;
    }

    public void setCentreName(String centreName) {
        CentreName = centreName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getLandLineNo() {
        return LandLineNo;
    }

    public void setLandLineNo(String landLineNo) {
        LandLineNo = landLineNo;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public ArrayList<String> getSpecilization() {
        return Specilization;
    }

    public void setSpecilization(ArrayList<String> specilization) {
        Specilization = specilization;
    }

    public ArrayList<String> getService() {
        return Service;
    }

    public void setService(ArrayList<String> service) {
        Service = service;
    }

    public ArrayList<String> getDayTo() {
        return DayTo;
    }

    public void setDayTo(ArrayList<String> dayTo) {
        DayTo = dayTo;
    }

    public ArrayList<String> getDayFrom() {
        return DayFrom;
    }

    public void setDayFrom(ArrayList<String> dayFrom) {
        DayFrom = dayFrom;
    }

    public ArrayList<String> getTimefromMorning() {
        return TimefromMorning;
    }

    public void setTimefromMorning(ArrayList<String> timefromMorning) {
        TimefromMorning = timefromMorning;
    }

    public ArrayList<String> getTimeToMorning() {
        return TimeToMorning;
    }

    public void setTimeToMorning(ArrayList<String> timeToMorning) {
        TimeToMorning = timeToMorning;
    }

    public ArrayList<String> getTimeFromEvening() {
        return TimeFromEvening;
    }

    public void setTimeFromEvening(ArrayList<String> timeFromEvening) {
        TimeFromEvening = timeFromEvening;
    }

    public ArrayList<String> getTimeToEvening() {
        return TimeToEvening;
    }

    public void setTimeToEvening(ArrayList<String> timeToEvening) {
        TimeToEvening = timeToEvening;
    }

    public String getAppointmentFee() {
        return AppointmentFee;
    }

    public void setAppointmentFee(String appointmentFee) {
        AppointmentFee = appointmentFee;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        AmountPaid = amountPaid;
    }
}

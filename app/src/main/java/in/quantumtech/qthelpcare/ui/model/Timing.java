
package in.quantumtech.qthelpcare.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timing {

    @SerializedName("TimeId")
    @Expose
    private Integer timeId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DayTo")
    @Expose
    private String dayTo;
    @SerializedName("DayFrom")
    @Expose
    private String dayFrom;
    @SerializedName("TimeToMorning")
    @Expose
    private String timeToMorning;
    @SerializedName("TimefromMorning")
    @Expose
    private String timefromMorning;
    @SerializedName("TimeToEvening")
    @Expose
    private String timeToEvening;
    @SerializedName("TimeFromEvening")
    @Expose
    private String timeFromEvening;
    @SerializedName("AppointmentFee")
    @Expose
    private Object appointmentFee;

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDayTo() {
        return dayTo;
    }

    public void setDayTo(String dayTo) {
        this.dayTo = dayTo;
    }

    public String getDayFrom() {
        return dayFrom;
    }

    public void setDayFrom(String dayFrom) {
        this.dayFrom = dayFrom;
    }

    public String getTimeToMorning() {
        return timeToMorning;
    }

    public void setTimeToMorning(String timeToMorning) {
        this.timeToMorning = timeToMorning;
    }

    public String getTimefromMorning() {
        return timefromMorning;
    }

    public void setTimefromMorning(String timefromMorning) {
        this.timefromMorning = timefromMorning;
    }

    public String getTimeToEvening() {
        return timeToEvening;
    }

    public void setTimeToEvening(String timeToEvening) {
        this.timeToEvening = timeToEvening;
    }

    public String getTimeFromEvening() {
        return timeFromEvening;
    }

    public void setTimeFromEvening(String timeFromEvening) {
        this.timeFromEvening = timeFromEvening;
    }

    public Object getAppointmentFee() {
        return appointmentFee;
    }

    public void setAppointmentFee(Object appointmentFee) {
        this.appointmentFee = appointmentFee;
    }

}

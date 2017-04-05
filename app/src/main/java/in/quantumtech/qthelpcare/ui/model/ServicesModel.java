package in.quantumtech.qthelpcare.ui.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quantum on 5/4/17.
 */

public class ServicesModel {
    @SerializedName("ID")
    private String id;

    @SerializedName("Name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

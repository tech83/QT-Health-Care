package in.quantumtech.qthelpcare.ui.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by quantum on 29/3/17.
 */

public class ClinicTypeModel {
    @SerializedName("TypeId")
    private Integer typeId;
    @SerializedName("TypeName")
    private String typeName;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

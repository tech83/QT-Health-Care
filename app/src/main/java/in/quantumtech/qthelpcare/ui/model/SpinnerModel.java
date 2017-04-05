package in.quantumtech.qthelpcare.ui.model;

/**
 * Created by quantum on 4/4/17.
 */

public class SpinnerModel {
    private int id;
    private String name;

    public SpinnerModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

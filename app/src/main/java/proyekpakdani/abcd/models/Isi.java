package proyekpakdani.abcd.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SLD on 5/21/2017.
 */

public class Isi {


    private int id;
    private String name;
    private String desc;
    private Boolean status;
    private Boolean isDeleted;
    private String created;
    private String updated;
    public ArrayList<String> surveys;


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public ArrayList<String> getSurveys() {
        return surveys;
    }

    public void setSurveys(ArrayList<String> surveys) {
        this.surveys = surveys;
    }
}


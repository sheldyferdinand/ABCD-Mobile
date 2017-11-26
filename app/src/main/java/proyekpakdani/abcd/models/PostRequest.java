package proyekpakdani.abcd.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SLD on 25-Nov-17.
 */

public class PostRequest {
    @SerializedName("name")
    @Expose
    private String username;
    @SerializedName("pass")
    @Expose
    private String password;

    public PostRequest(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

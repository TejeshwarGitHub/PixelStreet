package helper;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by admin on 26-04-2016.
 */
public class UserModel extends RealmObject {
    //    {"_id":"571ea6ea71b6acf21921902f","username":"Harshit Agarwal","email":"harsu.ag@gmail.com","__v":0,"updated_at":"2016-04-25T23:23:22.361Z","logs":[]}
    @Required
    String server_id, name, email, image_url;

    @PrimaryKey
    String profile_id;

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

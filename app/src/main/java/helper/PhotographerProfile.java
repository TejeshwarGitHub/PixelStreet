package helper;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 12-06-2016.
 */
public class PhotographerProfile implements Parcelable {
    public static final Creator<PhotographerProfile> CREATOR = new Creator<PhotographerProfile>() {
        @Override
        public PhotographerProfile createFromParcel(Parcel in) {
            try {
                return new PhotographerProfile(new JSONObject(in.readString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public PhotographerProfile[] newArray(int size) {
            return new PhotographerProfile[size];
        }
    };
    private int id;
    private String name, description, profile_pic, email;
    private int budget;
    private String[] cities;
    private Double rating;
    private JSONObject completeData;

    public PhotographerProfile(JSONObject data) throws JSONException {
        this.completeData = data;
        id = data.getInt("_id");
        name = data.getString("name");
        description = data.getString("description");
        profile_pic = data.getString("profile_pic");
        email = data.getString("email");
        budget = data.getInt("budget");
        JSONArray cities = data.getJSONArray("cities");
        this.cities = new String[cities.length()];
        for (int i = 0; i < cities.length(); i++) {
            this.cities[i] = cities.getString(i);
        }
        rating = data.getDouble("rating");

    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(completeData.toString());

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getEmail() {
        return email;
    }

    public int getBudget() {
        return budget;
    }

    public String[] getCities() {
        return cities;
    }

    public Double getRating() {
        return rating;
    }

    public JSONObject getCompleteData() {
        return completeData;
    }
}

package fr.istic.mmm.scinov.activities.Journey.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Journey implements Parcelable {

    private String key;
    private String name;
    private List<String> events = new ArrayList<>();
    private Boolean isPublished = false;
    private String author;
    private List<String> subcribers = new ArrayList<>();

    public Journey() {
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("events")
    public List<String> getEvents() {
        return events;
    }

    @PropertyName("events")
    public void setEvents(List<String> events) {
        this.events = events;
    }

    @PropertyName("isPublished")
    public Boolean getPublished() {
        return isPublished;
    }

    @PropertyName("isPublished")
    public void setPublished(Boolean published) {
        isPublished = published;
    }

    @PropertyName("author")
    public String getAuthor() {
        return author;
    }

    @PropertyName("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @PropertyName("subscribers")
    public List<String> getSubcribers() {
        return subcribers;
    }

    @PropertyName("subscribers")
    public void setSubcribers(List<String> subcribers) {
        this.subcribers = subcribers;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "name='" + name + '\'' +
                ", events=" + events +
                ", isPublished=" + isPublished +
                ", author='" + author + '\'' +
                ", subcribers=" + subcribers +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Journey(Parcel in) {
        name = in.readString();
        author = in.readString();
        isPublished = in.readByte() == 0 ? false : true;
        in.readList(events,null);
        in.readList(subcribers,null);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeByte((byte) (this.isPublished ? 1 : 0));
        dest.writeList(this.events);
        dest.writeList(this.subcribers);

    }

    public static final Creator<Journey> CREATOR = new Creator<Journey>() {
        @Override
        public Journey createFromParcel(Parcel in) {
            return new Journey(in);
        }

        @Override
        public Journey[] newArray(int size) {
            return new Journey[size];
        }
    };

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}

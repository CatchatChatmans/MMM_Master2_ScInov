package fr.istic.mmm.scinov.model;


import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Journey {

    private String name;
    private List<Integer> events = new ArrayList<>();
    private Boolean isPublished;
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
    public List<Integer> getEvents() {
        return events;
    }

    @PropertyName("events")
    public void setEvents(List<Integer> events) {
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
}

package fr.istic.mmm.scinov.model;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Journey {

    private String id;
    private String name;
    private Boolean shared;
    private String author;
    private List<String> observers;

    public Journey() {
    }
}

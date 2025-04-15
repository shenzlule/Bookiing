package org.project.booking.ui.models;

import java.util.List;

public class Hostel {
    private  int id;
    private String name;
    private String contact;
    private String location;
    private List<String> features;
    private int rating;
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getRating() {
        return String.valueOf(rating);
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getFeats() {
       return features.get(0)+" , "+features.get(1);

    }
}

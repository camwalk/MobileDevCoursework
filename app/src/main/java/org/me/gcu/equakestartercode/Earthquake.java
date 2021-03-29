package org.me.gcu.equakestartercode;

import java.io.Serializable;
import java.util.Date;

//Student ID: S1920624
public class Earthquake implements Serializable {
    private String Title;
    private String Description;
    private String Link;
    private String Date;
    private String Category;
    private String Latitude;
    private String Longitude;

    public String getDepth() {
        return Depth;
    }

    public void setDepth(String depth) {
        Depth = depth;
    }

    private String Depth;

    public String getMagntitude() {
        return Magntitude;
    }

    public void setMagntitude(String magntitude) {
        Magntitude = magntitude;
    }

    private String Magntitude;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}

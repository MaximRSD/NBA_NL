package com.example.nba_nl.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

public class Team {

    private String Name;
    private String WikipediaLogoUrl;
    private String Key;

    public Team(JSONObject json) {
        this.Name = (String) json.get("Name");
        this.WikipediaLogoUrl = json.get("logoURL").toString();
        this.Key = json.get("Key").toString();
    }

    public Team() {

    }

    public ImageView getLogoImage() {
        Image image = new Image(this.getWikipediaLogoUrl());
        return new ImageView(image);
    }

    public String getKey(){return Key;}

    public void setKey(String Key) {
        this.Key = Key;}

    public String getName(){return Name;}

    public void setName(String Name) {this.Name = Name;}

    public String getWikipediaLogoUrl(){return WikipediaLogoUrl;}

    public void setWikipediaLogoUrl(String WikipediaLogoUrl) {this.WikipediaLogoUrl = WikipediaLogoUrl;}

}

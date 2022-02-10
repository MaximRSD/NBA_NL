package com.example.nba_nl.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

public class Standings {
    private String Key;
    private Long Wins;
    private Long Losses;
    private Double Percentage;
    private String Conference;
    private Long TeamID;
    private Long DivisionRank;
    private String urlEasternTeam;
    private String urlWesternTeam;

    public Standings(String Key, Long Wins, Long Losses, Double Percentage, String Conference, Long TeamID, Long DivisionRank,String urlEasternTeam, String urlWesternTeam) {
        this.Key = Key;
        this.Wins = Wins;
        this.Losses = Losses;
        this.Percentage = Percentage;
        this.Conference = Conference;
        this.TeamID = TeamID;
        this.DivisionRank = DivisionRank;
        this.urlEasternTeam = urlEasternTeam;
        this.urlWesternTeam = urlWesternTeam;
    }

    public Long getTeamID() {
        return TeamID;
    }

    public void setTeamID(Long teamID) {
        TeamID = teamID;
    }

    public Long getDivisionRank() {
        return DivisionRank;
    }

    public void setDivisionRank(Long divisionRank) {
        DivisionRank = divisionRank;
    }

    public String getConference() {
        return Conference;
    }

    public void setConference(String conference) {
        Conference = conference;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public Long getWins() {
        return Wins;
    }

    public void setWins(Long wins) {
        Wins = wins;
    }

    public Long getLosses() {
        return Losses;
    }

    public void setLosses(Long losses) {
        Losses = losses;
    }

    public Double getPercentage() {
        return Percentage;
    }

    public void setPercentage(Double percentage) {
        Percentage = percentage;
    }

    public ImageView getUrlEasternTeam() {
        Image image = new Image(this.urlEasternTeam);
        return new ImageView(image);
    }

    public void setUrlEasternTeam(String urlEasternTeam) {this.urlEasternTeam = urlEasternTeam;}

    public ImageView getUrlWesternTeam() {
        Image image = new Image(this.urlWesternTeam);
        return new ImageView(image);
    }

    public void setUrlWesternTeam(String urlWesternTeam) {
        this.urlWesternTeam = urlWesternTeam;
    }

}

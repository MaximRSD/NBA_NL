package com.example.nba_nl.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;


public class Agenda {

    private String AwayTeam;
    private String HomeTeam;
    private String DateTime;
    private String DateTimeUTC;
    private String Status;
    private Long GameID;
    private String urlLogoAwayTeam;
    private String urlLogoHomeTeam;

    public Agenda(String AwayTeam, String HomeTeam, String DateTime, String DateTimeUTC, String Status, Long GameID, String urlLogoAwayTeam, String urlLogoHomeTeam) {
        this.AwayTeam = AwayTeam;
        this.HomeTeam = HomeTeam;
        this.DateTime = DateTime;
        this.DateTimeUTC = DateTimeUTC;
        this.Status = Status;
        this.GameID = GameID;
        this.urlLogoAwayTeam = urlLogoAwayTeam;
        this.urlLogoHomeTeam = urlLogoHomeTeam;
    }

    public Long getGameID(){return GameID;}

    public void setGameID(Long GameID) {this.GameID = GameID;}

    public String getAwayTeam(){return AwayTeam;}

    public void setAwayTeam(String AwayTeam) {this.AwayTeam = AwayTeam;}

    public String getHomeTeam(){return HomeTeam;}

    public void setHomeTeam(String HomeTeam) {this.HomeTeam = HomeTeam;}

    public String getDateTime(){return DateTime;}

    public void setDateTime(String DateTime) {this.DateTime = DateTime;}

    public String getDateTimeUTC(){return DateTimeUTC;}

    public void setDateTimeUTC(String DateTimeUTC) {this.DateTimeUTC = DateTimeUTC;}

    public String getStatus(){return Status;}

    public void setStatus(String Status) {this.Status = Status;}

    public ImageView getLogoAwayTeam() {
        Image image = new Image(this.urlLogoAwayTeam);
        return new ImageView(image);
    }

    public void setLogoAwayTeam(String urlLogoAwayTeam) {
        this.urlLogoAwayTeam = urlLogoAwayTeam;
    }

    public ImageView getLogoHomeTeam() {
        Image image = new Image(this.urlLogoHomeTeam);
        return new ImageView(image);
    }

    public void setUrlLogoHomeTeam(String urlLogoHomeTeam) {
        this.urlLogoHomeTeam = urlLogoHomeTeam;
    }
}

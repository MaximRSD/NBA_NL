package com.example.nba_nl.screens;

import com.example.nba_nl.Applicatie;
import com.example.nba_nl.models.Team;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.scene.image.ImageView;

import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kordamp.bootstrapfx.BootstrapFX;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Teams {

    private Scene teamScene;
    private GridPane gridpane;
    private GridPane teamGrid = new GridPane();
    private int currentGridRows = 0, currentGridColumns = 0;
    private ScrollPane scrollTeam = new ScrollPane();
    public Teams() {

        int barSize[] = {1200, 100};
        int bottomBarCenter = (Applicatie.applicationSize[0] - barSize[0]) / 2;
        int barSize2[] = {1210, 50};
        int topBarCenter = (Applicatie.applicationSize[0] - barSize2[0]) / 2;

        Pane container = new Pane();
        container.setStyle("-fx-background-color: WHITE");

        Text titletop = new Text("NBA - Teams");


        Button title = new Button("Home");
        Button agenda = new Button("Agenda");
        Button teams = new Button("Teams");
        Button klassement = new Button("Klassement");

        titletop.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: BLACK;");

        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: WHITE; -fx-cursor: hand;");
        agenda.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: WHITE; -fx-cursor: hand;");
        teams.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: WHITE; -fx-cursor: hand;");
        klassement.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: WHITE; -fx-cursor: hand;");

        titletop.relocate(550, (barSize2[1] / 2) - titletop.getScaleY());

        title.relocate(210, (barSize[1] / 4) - title.getScaleY());
        agenda.relocate(420, (barSize[1] / 4) - agenda.getScaleY());
        teams.relocate(620, (barSize[1] / 4) - teams.getScaleY());
        klassement.relocate(820, (barSize[1] / 4) - klassement.getScaleY());

        title.setOnAction(e -> Applicatie.setScene(0));
        agenda.setOnAction(e -> Applicatie.setScene(1));
        teams.setOnAction(e -> Applicatie.setScene(2));
        klassement.setOnAction(e -> Applicatie.setScene(3));

        Pane bottomBar = new Pane();
        bottomBar.setPrefSize(barSize[0], barSize[1]);
        bottomBar.relocate(bottomBarCenter, 550);
        bottomBar.setStyle("-fx-background-color: #455294");
        bottomBar.getChildren().addAll(title, agenda, teams, klassement);
        container.getChildren().addAll(bottomBar);

        Pane topBar = new Pane();
        topBar.setPrefSize(barSize2[0], barSize2[1]);
        topBar.relocate(topBarCenter, -5);
        topBar.setStyle("-fx-background-color: WHITE; -fx-border-color: BLACK");
        topBar.getChildren().add(titletop);
        container.getChildren().addAll(topBar);

        teamGrid.setStyle("-fx-background-color: WHITE; -fx-view-order: 1;");
        teamGrid.setPrefWidth(Applicatie.applicationSize[0] - 100);
        teamGrid.relocate(Applicatie.applicationCenter[0] - (teamGrid.getPrefWidth() / 2), 75);
        teamGrid.setHgap(30);
        teamGrid.setVgap(35);
        teamGrid.setMaxHeight(100);
        teamGrid.setMaxWidth(1200);
        teamGrid.setPadding(new Insets(10, 200, 10, 200));

        scrollTeam.setContent(teamGrid);
        scrollTeam.setFitToWidth(true);
        scrollTeam.setFitToHeight(true);
        scrollTeam.setPrefWidth(Applicatie.applicationSize[0] - 100);
        //scrollTeam.relocate(Applicatie.applicationCenter[0] - (scrollTeam.getPrefWidth() / 2), 75);
        scrollTeam.relocate(0, 45);
        scrollTeam.setPrefSize(1200, 505);
       // scrollTeam.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTeam.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTeam.onScrollProperty();
        scrollTeam.setStyle("-fx-background-color:transparent; -fx-view-order:1;");


        gridpane = new GridPane();
        gridpane.setPrefSize(1200, 650);
        gridpane.setStyle("-fx-background-color: WHITE; -fx-view-order: 1");
        container.getChildren().addAll(gridpane, scrollTeam);

        teamScene = new Scene(container, Applicatie.applicationSize[0], Applicatie.applicationSize[1]);
        teamScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        try {
            getTeams();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
        private void getTeams() throws IOException, ParseException {

            JSONParser parser = new JSONParser();
            URL url = new URL("https://api.npoint.io/e80602c5aeab5a6af1a8");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            JSONArray arr = (JSONArray) parser.parse(in);


            for (int i = 1; i < 30; i++) {
                JSONObject objTeam = (JSONObject) arr.get(i);
                addTeamItemToGrid(getTeamItemPane(new Team(objTeam)));
                //System.out.println("Wiki: " + objTeam.get("WikipediaLogoUrl") + " Name: " + objTeam.get("Name");
                System.out.println("PNG: " + objTeam.get("logoURL") + " Naam: " + objTeam.get("Name"));
            }

    }
    private FlowPane getTeamItemPane(Team teams) {
        final WebView browser = new WebView();
        FlowPane teamItem = new FlowPane();
        teamItem.setAlignment(Pos.CENTER);

        teamItem.setStyle("-fx-border-color: grey; -fx-border-radius: 25%; -fx-view-order: 2; -fx-border-width: 1px");


       // Text NBATitle = new Text(teams.getName());
       // teamItem.getChildren().add(NBATitle);

        // NBA logo fotos
        ImageView posterImage = teams.getLogoImage();
        teamItem.getChildren().add(posterImage);
        posterImage.setStyle("-fx-view-order: -1;");
       // posterImage.setStyle("-fx-fill-height: 200");
        posterImage.setFitHeight(150);
        posterImage.setFitWidth(140);

       // scrollTeam.set
        return teamItem;
    }
    private void addTeamItemToGrid(FlowPane teamItem) {
        teamGrid.add(teamItem, currentGridColumns, currentGridRows);

        if(currentGridColumns == 2) {
            currentGridRows++;
            currentGridColumns = 0;
        } else {
            currentGridColumns++;
        }
    }
    public Scene getTeamScene() {
        return teamScene;
    }
}



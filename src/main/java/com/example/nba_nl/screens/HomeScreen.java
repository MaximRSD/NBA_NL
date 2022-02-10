package com.example.nba_nl.screens;

import com.example.nba_nl.Applicatie;
import com.example.nba_nl.models.Agenda;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.image.Image ;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HomeScreen {

    private Scene homeScene;

    private GridPane gridpane;
    private GridPane LatestGrid = new GridPane();
    private int currentGridRows = 0, currentGridColumns = 0;

    public HomeScreen() {

        int barSize[] = {1200, 100};
        int bottomBarCenter = (Applicatie.applicationSize[0] - barSize[0]) / 2;

        Pane container = new Pane();
        container.setStyle("-fx-background-color: white");




        Button title = new Button("Home");
        Button agenda = new Button("Agenda");
        Button teams = new Button("Teams");
        Button klassement = new Button("Klassement");
        // Text klassement = new Text("Klassement");
        // title.setFill(Color.WHITE);
        // agenda.setFill(Color.WHITE);
        //  teams.setFill(Color.WHITE);
        // klassement.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: WHITE; -fx-cursor: hand;");
        agenda.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: WHITE; -fx-cursor: hand;");
        teams.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: WHITE; -fx-cursor: hand;");
        klassement.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: WHITE; -fx-cursor: hand;");

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
        container.getChildren().addAll(bottomBar, getImage());

        LatestGrid.setStyle("-fx-view-order: 1;");
        LatestGrid.relocate(Applicatie.applicationCenter[0] - (LatestGrid.getPrefWidth() / 2), 250);
        LatestGrid.setPadding(new Insets(10, 150, 150, 150));


        homeScene = new Scene(container, Applicatie.applicationSize[0], Applicatie.applicationSize[1]);
        homeScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        gridpane = new GridPane();
        gridpane.setPrefSize(1200, 650);
        gridpane.setStyle("-fx-background-color: transparent; -fx-view-order: 1");
        container.getChildren().addAll(gridpane, LatestGrid);

        try {
            getTeams();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
    public Scene getHomeScene() {
        return homeScene;
    }

    //Achtergrond foto Homescreen
    public ImageView getImage() {
        Image image = new Image("C:\\Users\\mxm-r\\IdeaProjects\\NBA_NL\\src\\main\\java\\com\\example\\nba_nl\\img\\gsw.png");
        ImageView iview = new ImageView(image);
        iview.relocate(-1, -100);
        iview.setFitHeight(650);
        iview.setFitWidth(1201);
        iview.setStyle("-fx-view-order: 1");
        return iview;
    }

    private void getTeams() throws IOException, ParseException {
            //lokale tijd voor gamesbydate json
            LocalDateTime localtijd = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String stringEnd = formatter.format(localtijd);

            JSONParser parser = new JSONParser();
            URL url = new URL("https://api.sportsdata.io/v3/nba/scores/json/GamesByDate/"+stringEnd+"?key=" + Applicatie.APIKEY);
            URL urlImage = new URL("https://api.npoint.io/e80602c5aeab5a6af1a8");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            BufferedReader inImage = new BufferedReader(new InputStreamReader(urlImage.openConnection().getInputStream()));
            JSONArray arr = (JSONArray) parser.parse(in);
            JSONArray arrImage = (JSONArray) parser.parse(inImage);

            JSONObject objAgenda = (JSONObject) arr.get(0);
            String urlLogoAwayTeam = "";
            String urlLogoHomeTeam = "";

            for (int a = 0; a < 30; a++) {
                JSONObject objTeam = (JSONObject) arrImage.get(a);

                if(objTeam.get("TeamID") == objAgenda.get("AwayTeamID")) {
                    urlLogoAwayTeam = (String) objTeam.get("logoURL");
                }

                if(objTeam.get("TeamID") == objAgenda.get("HomeTeamID")) {
                    urlLogoHomeTeam = (String) objTeam.get("logoURL");
                }
            }
                addTeamItemToGrid(getTeamItemPane(new Agenda((String) objAgenda.get("AwayTeam"), (String) objAgenda.get("HomeTeam"),(String) objAgenda.get("DateTime"), (String) objAgenda.get("DateTimeUTC"), (String) objAgenda.get("Status"), (Long) objAgenda.get("GameID"), urlLogoAwayTeam, urlLogoHomeTeam)));
                System.out.println("Home: " + objAgenda.get("HomeTeamID") + " Datum: " + objAgenda.get("DateTime") + stringEnd + " Away: " + objAgenda.get("AwayTeamID") + " Daytime " + objAgenda.get("DateTimeUTC") + " Foto " + urlLogoHomeTeam + " " + urlLogoAwayTeam);
        }

        private FlowPane getTeamItemPane(Agenda agenda) {
            FlowPane AgendaItem = new FlowPane();
            AgendaItem.setPrefSize(LatestGrid.getPrefWidth() / 2, 450);


            AgendaItem.setPrefWidth(200);
            AgendaItem.setPrefHeight(90);
            //   AgendaItem.setStyle("-fx-background-color: #7bbe33;");
            AgendaItem.setAlignment(Pos.CENTER);

            //Naar nl vertalen
            if(Objects.equals(agenda.getStatus(), "Scheduled")){
                Text StatusTitle = new Text("                  Gepland");
                AgendaItem.getChildren().add(StatusTitle);
                StatusTitle.setFont(Font.font("Myanmar Text" , FontWeight.BOLD, 15));
            }else if(Objects.equals(agenda.getStatus(), "InProgress")){
                Text StatusTitle = new Text("                  Bezig");
                AgendaItem.getChildren().add(StatusTitle);
                StatusTitle.setFont(Font.font("Myanmar Text" , FontWeight.BOLD, 15));
            }
            else {
                Text StatusTitle = new Text(agenda.getStatus());
                AgendaItem.getChildren().add(StatusTitle);
                StatusTitle.setFont(Font.font("Myanmar Text" , FontWeight.BOLD, 15));
            }

            Text tussen1Text = new Text("\t               ");
            AgendaItem.getChildren().add(tussen1Text);

            ImageView posterImageAwayTeam = agenda.getLogoAwayTeam();
            AgendaItem.getChildren().add(posterImageAwayTeam);
            posterImageAwayTeam.setStyle("-fx-view-order: -1;");
            posterImageAwayTeam.setStyle("-fx-fill-height: 200");
            posterImageAwayTeam.setFitHeight(70);
            posterImageAwayTeam.setFitWidth(70);

            Text tussenText4 = new Text("  ");
            AgendaItem.getChildren().add(tussenText4);

            Text AwayTitle = new Text(agenda.getAwayTeam());
            AgendaItem.getChildren().add(AwayTitle);

            Text tussenText = new Text("    -    ");
            AgendaItem.getChildren().add(tussenText);

            Text HomeTitle = new Text(agenda.getHomeTeam());
            AgendaItem.getChildren().add(HomeTitle);

            Text tussenText3 = new Text("  ");
            AgendaItem.getChildren().add(tussenText3);

            ImageView posterImageHomeTeam = agenda.getLogoHomeTeam();
            AgendaItem.getChildren().add(posterImageHomeTeam);
            posterImageHomeTeam.setStyle("-fx-view-order: -1;");
            posterImageHomeTeam.setStyle("-fx-fill-height: 200");
            posterImageHomeTeam.setFitHeight(70);
            posterImageHomeTeam.setFitWidth(70);

            Text tussen2Text = new Text("\t                                                                      ");
            AgendaItem.getChildren().add(tussen2Text);

//            Text DayTimeTitle = new Text(agenda.getDateTimeUTC());
//            AgendaItem.getChildren().add(DayTimeTitle);

            Button toAgenda = new Button(agenda.getDateTimeUTC());
            toAgenda.setOnAction(e -> Applicatie.setScene(1));
            AgendaItem.getChildren().add(toAgenda);
            toAgenda.setStyle("-fx-background-color:transparent; -fx-font-family: 'Myanmar Text'; -fx-font-weight: 300; -fx-font-size: 10; -fx-cursor: hand");

            HomeTitle.setFont(Font.font("Myanmar Text" , FontWeight.BOLD, 15));
            AwayTitle.setFont(Font.font("Myanmar Text" , FontWeight.BOLD, 15));
            toAgenda.setFont(Font.font("Myanmar Text" , FontWeight.LIGHT, 10));

            AgendaItem.setStyle("-fx-border-color: WHITE; -fx-border-radius: 20%; -fx-view-order: 2; -fx-background-color: WHITE; -fx-background-radius: 40px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 10, 10);");

            return AgendaItem;
        }
        private void addTeamItemToGrid(FlowPane agendaItem) {
            LatestGrid.add(agendaItem, currentGridColumns, currentGridRows);

            if(currentGridColumns == 1) {
                currentGridRows++;
                currentGridColumns = 0;
            } else {
                currentGridColumns++;
            }
        }
}

package com.example.nba_nl.screens;

import com.example.nba_nl.Applicatie;
import com.example.nba_nl.models.Agenda;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class AgendaScreen {
    private Scene AgendaScene;

    private GridPane gridpane;
    private GridPane AgendaGrid = new GridPane();
    private int currentGridRows = 0, currentGridColumns = 0;
    private ScrollPane scrollPane;
    private ScrollPane scrollTeam = new ScrollPane();

    public AgendaScreen() {
        int barSize[] = {1200, 100};
        int bottomBarCenter = (Applicatie.applicationSize[0] - barSize[0]) / 2;
        int barSize2[] = {1210, 50};
        int topBarCenter = (Applicatie.applicationSize[0] - barSize2[0]) /2 ;

        Pane container = new Pane();
        container.setStyle("-fx-background-color: white");

        Text titletop = new Text("NBA - Agenda");

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

        AgendaGrid.setStyle("-fx-background-color: WHITE; -fx-view-order: 1;");
        AgendaGrid.setPrefWidth(Applicatie.applicationSize[0] - 100);
        AgendaGrid.relocate(Applicatie.applicationCenter[0] - (AgendaGrid.getPrefWidth() / 2), 75);
        AgendaGrid.setHgap(40);
        AgendaGrid.setVgap(35);
        AgendaGrid.setMaxHeight(100);
        AgendaGrid.setMaxWidth(1200);
        AgendaGrid.setPadding(new Insets(10, 150, 385, 200));

        scrollTeam.setContent(AgendaGrid);
        scrollTeam.setFitToWidth(true);
        scrollTeam.setFitToHeight(true);
        scrollTeam.setPrefWidth(Applicatie.applicationSize[0] - 100);
       // scrollTeam.relocate(Applicatie.applicationCenter[0] - (scrollTeam.getPrefWidth() / 2), 48);
       // scrollTeam.relocate(Pos.CENTER, 45);
        scrollTeam.relocate(0, 45);
        scrollTeam.setPrefSize(1200, 650);
        // scrollTeam.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTeam.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollTeam.setStyle("-fx-background-color:Transparent; -fx-view-order:1;");

        gridpane = new GridPane();
        gridpane.setPrefSize(1200, 650);
        gridpane.setStyle("-fx-background-color: WHITE; -fx-view-order: 1");
        container.getChildren().addAll(gridpane, scrollTeam);

        AgendaScene = new Scene(container, Applicatie.applicationSize[0], Applicatie.applicationSize[1]);
        AgendaScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        try {
            getAgenda();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    private void getAgenda() throws IOException, ParseException {
        // lokaale tijd voor gamesbydate json
        LocalDateTime localtijd = LocalDateTime.now(ZoneId.of("Europe/Amsterdam"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringEnd = formatter.format(localtijd);

        JSONParser parser = new JSONParser();
        URL url = new URL("https://api.sportsdata.io/v3/nba/scores/json/GamesByDate/"+stringEnd+"?key=" + Applicatie.APIKEY);
        URL urlImage = new URL("https://api.npoint.io/e80602c5aeab5a6af1a8");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        BufferedReader inImage = new BufferedReader(new InputStreamReader(urlImage.openConnection().getInputStream()));
        JSONArray arr = (JSONArray) parser.parse(in);
        JSONArray arrImage = (JSONArray) parser.parse(inImage);

        arr.forEach(agenda -> {
            String urlLogoAwayTeam = "";
            String urlLogoHomeTeam = "";

            JSONObject objAgenda = (JSONObject) agenda;
            for (int a = 0; a < 30; a++) {
                JSONObject objTeam = (JSONObject) arrImage.get(a);

//vergelijken of de teams van beide json files overeenkomen
                if(objTeam.get("TeamID") == objAgenda.get("AwayTeamID")) {
               //    System.out.println("Logo Away van: " + objAgenda.get("AwayTeam") + " = " + objTeam.get("logoURL"));
                    urlLogoAwayTeam = (String) objTeam.get("logoURL");
                }

                if(objTeam.get("TeamID") == objAgenda.get("HomeTeamID")) {
                //    System.out.println("Logo Home van: " + objAgenda.get("HomeTeam") + " = " + objTeam.get("logoURL"));
                    urlLogoHomeTeam = (String) objTeam.get("logoURL");
                }
            }
            addTeamItemToGrid(getTeamItemPane(new Agenda((String) objAgenda.get("AwayTeam"), (String) objAgenda.get("HomeTeam"), (String) objAgenda.get("DateTime"), (String) objAgenda.get("DateTimeUTC"), (String) objAgenda.get("Status"), (Long) objAgenda.get("GameID"), urlLogoAwayTeam, urlLogoHomeTeam)));

        });
    }

    private FlowPane getTeamItemPane(Agenda agenda) {
        FlowPane AgendaItem = new FlowPane();
     //   AgendaItem.setPrefSize(AgendaGrid.getPrefWidth() / 2, 250);
     //   AgendaItem.setStyle("-fx-background-color: #7bbe33;");
        AgendaItem.setAlignment(Pos.CENTER);
        AgendaItem.setPrefHeight(90);
//        AgendaItem.setPrefWidth(200);



        //naar nl vertalen
        if(Objects.equals(agenda.getStatus(), "Scheduled")){
            Text StatusTitle = new Text("Gepland");
            AgendaItem.getChildren().add(StatusTitle);
        }else if(Objects.equals(agenda.getStatus(), "InProgress")) {
            Text StatusTitle = new Text("Bezig");
            AgendaItem.getChildren().add(StatusTitle);
            StatusTitle.setFont(Font.font("Myanmar Text", FontWeight.BOLD, 15));
        }else {
            Text StatusTitle = new Text(agenda.getStatus());
            AgendaItem.getChildren().add(StatusTitle);
        }

        Text tussen1Text = new Text("\t                                                  ");
        AgendaItem.getChildren().add(tussen1Text);

        ImageView posterImageAwayTeam = agenda.getLogoAwayTeam();
        AgendaItem.getChildren().add(posterImageAwayTeam);
        posterImageAwayTeam.setStyle("-fx-view-order: -1;");
        posterImageAwayTeam.setStyle("-fx-fill-height: 200");
        posterImageAwayTeam.setFitHeight(65);
        posterImageAwayTeam.setFitWidth(65);

        Text tussenText4 = new Text("  ");
        AgendaItem.getChildren().add(tussenText4);

        Text AwayTitle = new Text(agenda.getAwayTeam());
        AgendaItem.getChildren().add(AwayTitle);

        Text tussenText = new Text("  -  ");
        AgendaItem.getChildren().add(tussenText);

        Text HomeTitle = new Text(agenda.getHomeTeam());
        AgendaItem.getChildren().add(HomeTitle);

        Text tussenText3 = new Text("  ");
        AgendaItem.getChildren().add(tussenText3);

        ImageView posterImageHomeTeam = agenda.getLogoHomeTeam();
        AgendaItem.getChildren().add(posterImageHomeTeam);
        posterImageHomeTeam.setStyle("-fx-view-order: -1;");
        posterImageHomeTeam.setStyle("-fx-fill-height: 200");
        posterImageHomeTeam.setFitHeight(65);
        posterImageHomeTeam.setFitWidth(65);

        Text tussen2Text = new Text("\t                                           ");
        AgendaItem.getChildren().add(tussen2Text);

        Text DayTimeTitle = new Text(agenda.getDateTimeUTC());
        AgendaItem.getChildren().add(DayTimeTitle);

        AgendaItem.setStyle("-fx-border-color: grey; -fx-border-radius: 10%; -fx-view-order: 2; -fx-border-width: 1px; -fx-max-height: 100px; -fx-max-width:220px; -fx-font-family: 'Myanmar Text'; -fx-font-weight: 700; -fx-font-size: 15");
    //    DayTimeTitle.setFont(Font.font("Myanmar Text" , FontWeight.LIGHT, 10));
        DayTimeTitle.setStyle("-fx-font-family: 'Myanmar Text'; -fx-font-weight: 300; -fx-font-size: 10");
        return AgendaItem;
    }
    private void addTeamItemToGrid(FlowPane agendaItem) {
        AgendaGrid.add(agendaItem, currentGridColumns, currentGridRows);

        if(currentGridColumns == 2) {
            currentGridRows++;
            currentGridColumns = 0;
        } else {
            currentGridColumns++;
        }
    }

    public Scene getAgendaScene() {
        return AgendaScene;
    }
}



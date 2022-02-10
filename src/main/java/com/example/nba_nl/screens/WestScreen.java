package com.example.nba_nl.screens;

import com.example.nba_nl.Applicatie;
import com.example.nba_nl.models.Standings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

public class WestScreen {

    private Scene WestScreenScene;

    private GridPane gridpane;
    private GridPane StandGrid = new GridPane();
    private int currentGridRows = 0, currentGridColumns = 0;
    private ScrollPane scrollPane;
    private ScrollPane scrollTeam = new ScrollPane();

    public WestScreen() {
        int barSize[] = {1200, 100};
        int bottomBarCenter = (Applicatie.applicationSize[0] - barSize[0]) / 2;
        int barSize2[] = {1210, 50};
        int topBarCenter = (Applicatie.applicationSize[0] - barSize2[0]) / 2;

        Pane container = new Pane();
        container.setStyle("-fx-background-color: white");

        Text titletop = new Text("West - Klassement");

        Button Western = new Button("Oost Klassement");
        Western.relocate(1000, 45);
        Western.setStyle("-fx-background-color: #455294; -fx-view-order: 2; -fx-text-fill: WHITE; -fx-font-family: 'Myanmar Text'; -fx-font-weight: 700; -fx-font-size: 15");

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

        Button Westernbtn = new Button("Oost Klassement");
        Westernbtn.setStyle("-fx-background-color: #455294; -fx-view-order: 2; -fx-text-fill: WHITE; -fx-font-family: 'Myanmar Text'; -fx-font-weight: 700; -fx-font-size: 15; -fx-cursor:hand");

        Westernbtn.setOnAction(e -> Applicatie.setScene(3));

        Pane topBarWest = new Pane();
        topBarWest.setPrefSize(barSize2[0], barSize2[1]);
        topBarWest.relocate(1000, 50);
        topBarWest.setStyle("-fx-background-color: transparent;");
        topBarWest.getChildren().add(Westernbtn);
        container.getChildren().addAll(topBarWest);

        Pane West = new Pane();
        West.setPrefSize(200, 100);
        West.relocate(0, 15);
        West.getChildren().add(Western);

        StandGrid.setStyle("-fx-background-color: WHITE; -fx-view-order: 1;");
        StandGrid.setPrefWidth(Applicatie.applicationSize[0] - 100);
        StandGrid.relocate(0, 45);
        StandGrid.setHgap(-100);
        StandGrid.setVgap(1);
        StandGrid.setPrefSize(1200, 505);
        StandGrid.setMaxWidth(1200);
        StandGrid.setPadding(new Insets(68, 200, 50, 364));

        scrollTeam.setContent(StandGrid);
        scrollTeam.setFitToWidth(true);
        scrollTeam.setFitToHeight(true);
        scrollTeam.setPrefWidth(Applicatie.applicationSize[0] - 100);
        // scrollTeam.relocate(Applicatie.applicationCenter[0] - (scrollTeam.getPrefWidth() / 2), 48);
        scrollTeam.setPrefSize(1200, 550);
        // scrollTeam.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTeam.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollTeam.setStyle("-fx-background-color:Transparent; -fx-view-order:1;");


        gridpane = new GridPane();
        gridpane.setPrefSize(1200, 650);
        gridpane.setStyle("-fx-background-color: WHITE; -fx-view-order: 1");
        container.getChildren().addAll(gridpane, scrollTeam);

        WestScreenScene = new Scene(container, Applicatie.applicationSize[0], Applicatie.applicationSize[1]);
        WestScreenScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        try {
            getStandings();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }


    public Scene getWestScreenScene() {
        return WestScreenScene;
    }


    private void getStandings() throws IOException, ParseException {
        LocalDateTime localtijd = LocalDateTime.now(ZoneId.of("Europe/Amsterdam"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String stringEnd = formatter.format(localtijd);
        System.out.println(stringEnd);
        JSONParser parser = new JSONParser();
        URL url = new URL("https://api.sportsdata.io/v3/nba/scores/json/Standings/%7B2022%7D?key=1a94e447c4c54eafa08c91e4e857ed99");
        URL urlImage = new URL("https://api.npoint.io/e80602c5aeab5a6af1a8");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        BufferedReader inImage = new BufferedReader(new InputStreamReader(urlImage.openConnection().getInputStream()));
        JSONArray arr = (JSONArray) parser.parse(in);
        JSONArray arrImage = (JSONArray) parser.parse(inImage);

        //Zorgen dat foto en data samen worden gezocht.
        arr.forEach(standing -> {
            String urlEasternTeam = "";
            String urlWesternTeam = "";

            JSONObject jsonStanding = (JSONObject) standing;

            for (int i = 0; i < 30; i++) {
                JSONObject objTeam = (JSONObject) arrImage.get(i);

                if (objTeam.get("TeamID") == jsonStanding.get("TeamID") && Objects.equals(jsonStanding.get("Conference"), "Eastern")) {
                    //    System.out.println("Rank: "+ jsonStanding.get("DivisionRank")  + " ID:  " + objTeam.get("TeamID") + "Key: " + jsonStanding.get("Key") + " Conference: " + jsonStanding.get("Conference") + " Wins: " + jsonStanding.get("Wins") + " Losses: " + jsonStanding.get("Losses") + " %: " + jsonStanding.get("Percentage") + " Logo: " + objTeam.get("logoURL"));
                    urlEasternTeam = (String) objTeam.get("logoURL");
                }

                if(objTeam.get("TeamID") == jsonStanding.get("TeamID") && Objects.equals(jsonStanding.get("Conference"), "Western")){
                    //    System.out.println(" ID: " + objTeam.get("TeamID") + "Key: " + jsonStanding.get("Key") + " Conference: " + jsonStanding.get("Conference") + " Wins: " + jsonStanding.get("Wins") + " Losses: " + jsonStanding.get("Losses") + " %: " + jsonStanding.get("Percentage")+ " Logo: " + objTeam.get("logoURL"));
                    urlEasternTeam = (String) objTeam.get("logoURL");
                }
            }
            //Toevoegen aan grid
            addTeamItemToGrid(getTeamItemPane(new Standings((String) jsonStanding.get("Key"), (Long) jsonStanding.get("Wins"), (Long) jsonStanding.get("Losses"), (Double) jsonStanding.get("Percentage"), (String) jsonStanding.get("Conference"), (Long) jsonStanding.get("TeamID"), (Long) jsonStanding.get("DivisionRank"),  urlEasternTeam, urlWesternTeam)));
        });
    }

    private FlowPane getTeamItemPane(Standings standing) {
        FlowPane standingItem = new FlowPane();
        standingItem.relocate(200, 45);
        if (Objects.equals(standing.getConference(), "Western")) {

//            Label rank = new Label(standing.getDivisionRank().toString());
//            standingItem.getChildren().add(rank);
// items toevoegen aan grid
            ImageView posterImageHomeTeam = standing.getUrlEasternTeam();
            standingItem.getChildren().add(posterImageHomeTeam);
            posterImageHomeTeam.setStyle("-fx-view-order: -1;");
            posterImageHomeTeam.setStyle("-fx-fill-height: 200");
            posterImageHomeTeam.setFitHeight(50);
            posterImageHomeTeam.setFitWidth(50);

            Text Key = new Text(standing.getKey());
            standingItem.getChildren().add(Key);

            Label Tussen2 = new Label(" ");
            standingItem.getChildren().add(Tussen2);

            Label Wins = new Label(standing.getWins().toString());
            standingItem.getChildren().add(Wins);

            Label Tussen1 = new Label(" ");
            standingItem.getChildren().add(Tussen1);

            Label Losses = new Label(standing.getLosses().toString());
            standingItem.getChildren().add(Losses);

            Label Tussen = new Label(" ");
            standingItem.getChildren().add(Tussen);

            Label Perc = new Label(standing.getPercentage().toString());
            standingItem.getChildren().add(Perc);

            standingItem.setStyle("-fx-border-color: grey; -fx-border-radius: 10%; -fx-view-order: 2; -fx-border-width: 1px; -fx-max-height: 100px; -fx-max-width:220px; -fx-font-family: 'Myanmar Text'; -fx-font-weight: 700; -fx-font-size: 15");
        }
        return standingItem;
    }



    private void addTeamItemToGrid(FlowPane standingItem) {
        StandGrid.add(standingItem, currentGridColumns, currentGridRows);

        if(currentGridColumns == 1) {
            currentGridRows++;
            currentGridColumns = 0;
        } else {
            currentGridColumns++;
        }
    }
}





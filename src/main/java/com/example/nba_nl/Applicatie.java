package com.example.nba_nl;

import com.example.nba_nl.screens.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;



public class Applicatie extends Application {

    private static Stage mainStage;
    private static ArrayList<Scene> scenes = new ArrayList<Scene>();

    public static int[] applicationSize = {1200, 650};
    public static int[] applicationCenter = {applicationSize[0] / 2, applicationSize[1] /2};
    public static String APIKEY = "1a94e447c4c54eafa08c91e4e857ed99";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//Scenes toevoegen
        scenes.add(new HomeScreen().getHomeScene());
        scenes.add(new AgendaScreen().getAgendaScene());
        scenes.add(new Teams().getTeamScene());
        scenes.add(new KlassementScreen().getKlassementScene());
        scenes.add(new WestScreen().getWestScreenScene());

        mainStage = primaryStage;
        mainStage.setResizable(false);
        mainStage.setTitle("NBA nl");
        mainStage.show();

        setScene(0);
        // HomeScreen homeScreen = new HomeScreen();
        // primaryStage.setScene(homeScreen.getHomeScene());
        // primaryStage.show();
    }
    public static void setScene(int scene) {mainStage.setScene(scenes.get(scene));}
}

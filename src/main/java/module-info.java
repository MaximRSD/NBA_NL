module com.example.nba_nl {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.web;


    opens com.example.nba_nl to javafx.fxml;
    exports com.example.nba_nl;
}
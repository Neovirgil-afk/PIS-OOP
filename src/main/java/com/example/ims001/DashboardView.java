package com.example.ims001;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView {

    private MainApp mainApp;
    private VBox root;

    public DashboardView(MainApp mainApp, String username) {
        this.mainApp = mainApp;
        createView(username);
    }

    private void createView(String username) {
        root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> mainApp.showLoginView());

        root.getChildren().addAll(welcomeLabel, btnLogout);
    }

    public Parent getView() {
        return root;
    }
}

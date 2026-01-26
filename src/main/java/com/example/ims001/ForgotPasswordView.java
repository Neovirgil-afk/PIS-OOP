package com.example.ims001;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ForgotPasswordView {

    private MainApp mainApp;
    private StackPane root;

    private TextField txtUsername, txtResetCode, txtNewPasswordText;
    private PasswordField txtNewPassword;
    private CheckBox chkShowPassword;
    private Label lblMessage;

    public ForgotPasswordView(MainApp mainApp) {
        this.mainApp = mainApp;

        root = new StackPane();
        root.getStyleClass().add("login-root");

        //background
        Image bgGif = new Image(getClass().getResource("/images/spacebg_5.gif").toExternalForm());
        ImageView bgView = new ImageView(bgGif);
        bgView.setFitWidth(1920);
        bgView.setFitHeight(1080);
        bgView.setPreserveRatio(false);
        bgView.setOpacity(0.5);

        HBox mainBox = new HBox();
        mainBox.setAlignment(Pos.CENTER);

        VBox leftCard = createLeftCard();
        VBox rightCard = createForgotCard();

        mainBox.getChildren().addAll(leftCard, rightCard);
        root.getChildren().addAll(bgView, mainBox);
    }

    private VBox createLeftCard() {
        VBox leftCard = new VBox();
        leftCard.setPrefWidth(500);
        leftCard.setAlignment(Pos.CENTER);
        leftCard.getStyleClass().addAll("left-card", "frosted-glass");

        //Inner black box
        StackPane innerBox = new StackPane();
        innerBox.getStyleClass().addAll("inner-black-box");

        //innerBox responsive to leftCard size
        innerBox.prefWidthProperty().bind(leftCard.widthProperty().multiply(0.9));
        innerBox.prefHeightProperty().bind(leftCard.heightProperty().multiply(0.8));
        innerBox.setAlignment(Pos.BOTTOM_CENTER);

        //Text container inside the black box
        VBox textContainer = new VBox(10);
        textContainer.setAlignment(Pos.BOTTOM_CENTER);
        textContainer.setStyle("-fx-padding: 20;");

        //Logo placeholder
        Label logo = new Label(" *"); // placeholder logo
        logo.getStyleClass().add("inner-title");
        logo.setStyle("-fx-text-fill: #03DE82; -fx-font-size: 100px; -fx-font-weight: bold;");
        StackPane.setAlignment(logo, Pos.TOP_LEFT);
        StackPane.setMargin(logo, new javafx.geometry.Insets(15, 0, 0, 15)); // top-left margin

        //Title
        Label title = new Label("Prestige Inventory Suites");
        title.getStyleClass().add("inner-title");
        title.styleProperty().bind(
                innerBox.heightProperty().multiply(0.04).asString(
                        "-fx-text-fill: #03DE82; -fx-font-weight: bold; -fx-font-family: Inter; -fx-font-size: %.0fpx;"
                )
        );
        title.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(title, new javafx.geometry.Insets(0,0,0,10));
        title.setAlignment(Pos.CENTER_LEFT);

        //Description
        Label description = new Label(
                "This is a powerful inventory management app. " +
                        "Keep track of your stock effortlessly. " +
                        "Designed to make your business operations smooth."
        );
        description.getStyleClass().add("inner-description");
        description.setWrapText(true);
        description.styleProperty().bind(
                innerBox.heightProperty().multiply(0.03).asString(
                        "-fx-text-fill: #03DE82; -fx-font-family: Inter; -fx-font-size: %.0fpx;"
                )
        );
        description.maxWidthProperty().bind(innerBox.widthProperty().multiply(0.85));
        description.setAlignment(Pos.CENTER);

        // added title and description to text container
        textContainer.getChildren().addAll(title, description);

        // added logo and text container to inner box
        innerBox.getChildren().addAll(logo, textContainer);

        // added inner box ngek
        leftCard.getChildren().add(innerBox);
        VBox.setVgrow(innerBox, javafx.scene.layout.Priority.ALWAYS);

        return leftCard;
    }

    private VBox createForgotCard() {
        VBox rightCard = new VBox(12);
        rightCard.setPrefWidth(700);
        rightCard.setPrefHeight(520);   // match Login/Register
        rightCard.setAlignment(Pos.TOP_LEFT);
        rightCard.getStyleClass().add("right-card");

        Label title = new Label("Forgot Password");
        title.getStyleClass().add("title");

        Label subtitle = new Label("Reset your password using your permanent reset code.");
        subtitle.getStyleClass().add("title_2");
        subtitle.setWrapText(true);

        Label userLbl = new Label("Username");
        userLbl.getStyleClass().add("title_3");

        txtUsername = new TextField();
        txtUsername.setPromptText("Enter your Username");
        txtUsername.setMaxWidth(480);
        txtUsername.getStyleClass().add("input-field");

        Label codeLbl = new Label("Reset Code");
        codeLbl.getStyleClass().add("title_3");

        txtResetCode = new TextField();
        txtResetCode.setPromptText("Enter your Reset Code");
        txtResetCode.setMaxWidth(480);
        txtResetCode.getStyleClass().add("input-field");

        Label passLbl = new Label("New Password");
        passLbl.getStyleClass().add("title_3");

        txtNewPassword = new PasswordField();
        txtNewPassword.setPromptText("Enter New Password");
        txtNewPassword.setMaxWidth(480);
        txtNewPassword.getStyleClass().add("input-field");

        txtNewPasswordText = new TextField();
        txtNewPasswordText.setPromptText("Enter New Password");
        txtNewPasswordText.setMaxWidth(480);
        txtNewPasswordText.getStyleClass().add("input-field");
        txtNewPasswordText.setVisible(false);
        txtNewPasswordText.setManaged(false);

        txtNewPassword.textProperty().bindBidirectional(txtNewPasswordText.textProperty());

        chkShowPassword = new CheckBox("Show Password");
        chkShowPassword.getStyleClass().add("pass-button");
        chkShowPassword.setOnAction(e -> {
            boolean show = chkShowPassword.isSelected();
            txtNewPassword.setVisible(!show);
            txtNewPassword.setManaged(!show);
            txtNewPasswordText.setVisible(show);
            txtNewPasswordText.setManaged(show);
        });

        Button btnReset = new Button("Reset Password");
        btnReset.getStyleClass().add("primary-button");
        btnReset.setOnAction(e -> handleReset());

        Button btnBack = new Button("Back to Login");
        btnBack.getStyleClass().add("secondary-button");
        btnBack.setOnAction(e -> mainApp.showLoginView());

        lblMessage = new Label();
        lblMessage.getStyleClass().add("message-label");

        rightCard.getChildren().addAll(
                title,
                subtitle,
                userLbl,
                txtUsername,
                codeLbl,
                txtResetCode,
                passLbl,
                txtNewPassword,
                txtNewPasswordText,
                chkShowPassword,
                btnReset,
                btnBack,
                lblMessage
        );

        return rightCard;
    }

    //LOGIC

    private void handleReset() {
        String username = txtUsername.getText();
        String resetCode = txtResetCode.getText();
        String newPassword = chkShowPassword.isSelected()
                ? txtNewPasswordText.getText()
                : txtNewPassword.getText();

        if (username.isEmpty() || resetCode.isEmpty() || newPassword.isEmpty()) {
            lblMessage.setText("All fields are required.");
            lblMessage.getStyleClass().setAll("message-error");
            return;
        }

        if (!isStrongPassword(newPassword)) {
            lblMessage.setText("Password must be at least 8 chars, include upper, lower, number & symbol.");
            lblMessage.getStyleClass().setAll("message-error");
            return;
        }

        if (!UserDAO.checkResetCode(username, resetCode)) {
            lblMessage.setText("Invalid username or reset code.");
            lblMessage.getStyleClass().setAll("message-error");
            return;
        }

        if (UserDAO.updatePassword(username, newPassword)) {
            lblMessage.setText("Password reset successful! Go back to login.");
            lblMessage.getStyleClass().setAll("message-success");
        } else {
            lblMessage.setText("Failed to reset password. Try again later.");
            lblMessage.getStyleClass().setAll("message-error");
        }
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    public Parent getView() {
        return root;
    }
}

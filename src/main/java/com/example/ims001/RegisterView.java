package com.example.ims001;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.security.SecureRandom;

public class RegisterView {

    private MainApp mainApp;
    private VBox root;
    private TextField txtUsername, txtPasswordText, txtConfirmText;
    private PasswordField txtPassword, txtConfirm;
    private Label lblMessage;
    private CheckBox showPassword;

    public RegisterView(MainApp mainApp) {
        this.mainApp = mainApp;
        createView();
    }

    private void createView() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Register New Account");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setMaxWidth(250);

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.setMaxWidth(250);

        txtPasswordText = new TextField();
        txtPasswordText.setManaged(false);
        txtPasswordText.setVisible(false);
        txtPasswordText.setMaxWidth(250);
        txtPassword.textProperty().bindBidirectional(txtPasswordText.textProperty());

        txtConfirm = new PasswordField();
        txtConfirm.setPromptText("Confirm Password");
        txtConfirm.setMaxWidth(250);

        txtConfirmText = new TextField();
        txtConfirmText.setManaged(false);
        txtConfirmText.setVisible(false);
        txtConfirmText.setMaxWidth(250);
        txtConfirm.textProperty().bindBidirectional(txtConfirmText.textProperty());

        showPassword = new CheckBox("Show Password");
        showPassword.setOnAction(e -> {
            boolean show = showPassword.isSelected();
            txtPassword.setVisible(!show);
            txtPassword.setManaged(!show);
            txtPasswordText.setVisible(show);
            txtPasswordText.setManaged(show);
            txtConfirm.setVisible(!show);
            txtConfirm.setManaged(!show);
            txtConfirmText.setVisible(show);
            txtConfirmText.setManaged(show);
        });

        lblMessage = new Label();

        Button btnSuggest = new Button("Suggest Password");
        btnSuggest.setOnAction(e -> txtPassword.setText(generatePassword(10)));

        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(e -> handleRegister());

        Button btnBack = new Button("Back to Login");
        btnBack.setOnAction(e -> mainApp.showLoginView());

        root.getChildren().addAll(title, txtUsername, txtPassword, txtPasswordText,
                txtConfirm, txtConfirmText, showPassword, btnSuggest, btnRegister, btnBack, lblMessage);
    }

    private void handleRegister() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirm = txtConfirm.getText();
        String resetCode = generateResetCode();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirm)) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Passwords do not match.");
            return;
        }

        if (!isStrongPassword(password)) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Password must be 8+ chars with number & symbol.");
            return;
        }

        boolean success = UserDAO.register(username, password, resetCode);
        if (success) {
            lblMessage.setStyle("-fx-text-fill: green;");
            lblMessage.setText("Registered! Reset code: " + resetCode);
        } else {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Registration failed. Username may exist.");
        }
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()].*");
    }

    private String generateResetCode() {
        SecureRandom rand = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String generatePassword(int length) {
        SecureRandom rand = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        return sb.toString();
    }

    public Parent getView() {
        return root;
    }
}

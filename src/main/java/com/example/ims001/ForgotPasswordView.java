package com.example.ims001;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ForgotPasswordView {

    private MainApp mainApp;
    private VBox root;

    private TextField txtUsername, txtResetCode, txtNewPasswordText;
    private PasswordField txtNewPassword;
    private CheckBox chkShowPassword;
    private Label lblMessage;

    public ForgotPasswordView(MainApp mainApp) {
        this.mainApp = mainApp;
        createView();
    }

    private void createView() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Forgot Password?");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setMaxWidth(250);

        txtResetCode = new TextField();
        txtResetCode.setPromptText("Forever Reset Code");
        txtResetCode.setMaxWidth(250);

        txtNewPassword = new PasswordField();
        txtNewPassword.setPromptText("New Password");
        txtNewPassword.setMaxWidth(250);

        txtNewPasswordText = new TextField();
        txtNewPasswordText.setManaged(false);
        txtNewPasswordText.setVisible(false);
        txtNewPasswordText.setMaxWidth(250);
        txtNewPassword.textProperty().bindBidirectional(txtNewPasswordText.textProperty());

        chkShowPassword = new CheckBox("Show Password");
        chkShowPassword.setOnAction(e -> {
            boolean show = chkShowPassword.isSelected();
            txtNewPassword.setVisible(!show);
            txtNewPassword.setManaged(!show);
            txtNewPasswordText.setVisible(show);
            txtNewPasswordText.setManaged(show);
        });

        lblMessage = new Label();

        Button btnReset = new Button("Reset Password");
        btnReset.setOnAction(e -> handleReset());

        Button btnBack = new Button("Back to Login");
        btnBack.setOnAction(e -> mainApp.showLoginView());

        root.getChildren().addAll(title, txtUsername, txtResetCode, txtNewPassword, txtNewPasswordText,
                chkShowPassword, btnReset, btnBack, lblMessage);
    }

    private void handleReset() {
        String username = txtUsername.getText();
        String resetCode = txtResetCode.getText();
        String newPassword = chkShowPassword.isSelected() ? txtNewPasswordText.getText() : txtNewPassword.getText();

        if (username.isEmpty() || resetCode.isEmpty() || newPassword.isEmpty()) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("All fields are required.");
            return;
        }

        if (!isStrongPassword(newPassword)) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Password must be at least 8 chars, include upper, lower, number & symbol.");
            return;
        }

        if (!UserDAO.checkResetCode(username, resetCode)) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Invalid username or reset code.");
            return;
        }

        if (UserDAO.updatePassword(username, newPassword)) {
            lblMessage.setStyle("-fx-text-fill: green;");
            lblMessage.setText("Password reset successful! Go back to login.");
        } else {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Failed to reset password. Try again later.");
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

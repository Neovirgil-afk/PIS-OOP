package com.example.ims001;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {

    private MainApp mainApp;
    private VBox root;
    private TextField txtUsername, txtPasswordText;
    private PasswordField txtPassword;
    private Label lblMessage;
    private CheckBox showPassword;

    public LoginView(MainApp mainApp) {
        this.mainApp = mainApp;
        createView();
    }

    private void createView() {
        // Root container
        root = new VBox(20); // spacing between elements
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("login-root");

        // Title
        Label title = new Label("Login form");
        title.getStyleClass().add("title");

        // Title_quote
        Label title_quote = new Label ("“Know what you have, sell with confidence, and never run out unexpectedly.”");
        title_quote.getStyleClass().add("title_2");

        // user
        Label input_user = new Label ("Username");
        title_quote.getStyleClass().add("input-field-user");

        // Username field
        txtUsername = new TextField();
        txtUsername.setPromptText("Enter your Username");
        txtUsername.setMaxWidth(719); // matches CSS
        txtUsername.getStyleClass().add("input-field");

        // pass
        Label input_pass = new Label ("Password");
        title_quote.getStyleClass().add("input-field-password");

        // Password fields
        txtPassword = new PasswordField();
        txtPassword.setPromptText("Enter your Password");
        txtPassword.setMaxWidth(719);
        txtPassword.getStyleClass().add("input-field");

        txtPasswordText = new TextField();
        txtPasswordText.setManaged(false);
        txtPasswordText.setVisible(false);
        txtPasswordText.setMaxWidth(719);
        txtPasswordText.getStyleClass().add("input-field");

        // Bind password fields
        txtPassword.textProperty().bindBidirectional(txtPasswordText.textProperty());

        // Show password checkbox
        showPassword = new CheckBox("Show Password");
        showPassword.getStyleClass().add("checkbox");
        showPassword.setOnAction(e -> {
            boolean show = showPassword.isSelected();
            txtPassword.setVisible(!show);
            txtPassword.setManaged(!show);
            txtPasswordText.setVisible(show);
            txtPasswordText.setManaged(show);
        });

        // Message label
        lblMessage = new Label();
        lblMessage.getStyleClass().add("message-label");

        // Buttons
        Button btnForgot = new Button("Forgot Password?");
        btnForgot.getStyleClass().add("link-button");
        btnForgot.setOnAction(e -> mainApp.showForgotPasswordView());

        Button btnLogin = new Button("Get Started");
        btnLogin.getStyleClass().add("primary-button");
        btnLogin.setOnAction(e -> handleLogin());

        Button btnRegister = new Button("Sign up");
        btnRegister.getStyleClass().add("secondary-button");
        btnRegister.setOnAction(e -> mainApp.showRegisterView());

        // Assemble layout
        root.getChildren().addAll(
                title_quote,
                txtUsername,
                txtPassword,
                txtPasswordText,
                showPassword,
                btnLogin,
                btnRegister,
                btnForgot,
                lblMessage
        );
    }

    private void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Username and password cannot be empty.");
            lblMessage.getStyleClass().setAll("message-error");
            return;
        }

        if (UserDAO.login(username, password)) {
            lblMessage.setText("Login successful!");
            lblMessage.getStyleClass().setAll("message-success");
            mainApp.showDashboardView(username);
        } else {
            lblMessage.setText("Invalid username or password.");
            lblMessage.getStyleClass().setAll("message-error");
        }
    }

    public Parent getView() {
        return root;
    }
}

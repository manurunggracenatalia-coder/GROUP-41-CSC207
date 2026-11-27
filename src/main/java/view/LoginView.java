
package view;

import interface_adapter.Login.*;
import interface_adapter.Signup.SignupViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel implements LoginViewModelListener {

    private final LoginViewModel loginViewModel;
    private LoginController loginController;

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel errorLabel = new JLabel(" ");

    public LoginView(LoginViewModel loginViewModel,
                     ViewManagerModel viewManagerModel) {

        this.loginViewModel = loginViewModel;
        loginViewModel.addListener(this);

        //set background
        setBackground(new Color(240,249,250));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //title
        JLabel title = new JLabel("Welcome To Flicksy!");
        title.setFont(new Font("Verdana",Font.BOLD, 70));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //input fields
        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameField);

        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordField);

        usernameInfo.setOpaque(false);
        passwordInfo.setOpaque(false);

        usernameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameInfo.setMaximumSize(usernameInfo.getPreferredSize());
        passwordInfo.setMaximumSize(passwordInfo.getPreferredSize());

        //buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttons.setOpaque(false);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");

        buttons.add(loginButton);
        buttons.add(signupButton);
        buttons.setMaximumSize(buttons.getPreferredSize());

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //layout
        add(Box.createVerticalGlue());
        add(title);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(usernameInfo);
        add(passwordInfo);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(errorLabel);
        add(buttons);
        add(Box.createVerticalGlue());

        //login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            loginController.execute(username, password);
        });

        //signup button
        signupButton.addActionListener(e -> {
            viewManagerModel.setState(SignupViewModel.VIEW_NAME);
            viewManagerModel.notifyViewChanged();
        });
    }

    @Override
    public void stateChanged() {
        LoginState state = loginViewModel.getState();

        usernameField.setText(state.getUsername());

        String error = state.getLoginError();
        errorLabel.setText(error == null ? " " : error);
    }
    public void setLoginController(LoginController controller) {
        this.loginController = controller;
    }
}

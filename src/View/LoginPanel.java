package View;

import View.Listeners.LoginListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginPanel extends JPanel {

    public LoginPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3,10,3,10);
        JLabel textUsername = new JLabel("Username");
        JLabel textPassword = new JLabel("Password");
        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JButton login = new JButton("Login");
        login.setFocusPainted(false);
        login.setActionCommand(LoginListener.LOGIN_BTN);
        LoginListener loginListener = new LoginListener(username, password);
        login.addActionListener(loginListener);
        gbc.gridx=0;
        gbc.gridy=0;
        this.add(textUsername,gbc);
        gbc.gridy=1;
        this.add(username,gbc);
        gbc.gridy=2;
        this.add(textPassword,gbc);
        gbc.gridy=3;
        this.add(password,gbc);
        gbc.gridy=4;
        this.add(login,gbc);
    }
}

package View;

import View.Listeners.LoginListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginPanel extends JPanel {

    public LoginPanel(MainPage frame) {
        this.setLayout(new GridBagLayout());
        Insets insets = new Insets(3,10,3,10);

        JLabel textUsername = new JLabel("Username");
        JLabel textPassword = new JLabel("Password");
        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JButton login = new JButton("Login");

        login.setFocusPainted(false);
        login.setActionCommand(LoginListener.LOGIN_BTN);
        LoginListener loginListener = new LoginListener(username, password);
        loginListener.setFrame(frame);
        login.addActionListener(loginListener);
        this.add(textUsername,new GridBagCostraintsHorizontal(0,0,1,1,insets));
        this.add(username,new GridBagCostraintsHorizontal(0,1,1,1,insets));
        this.add(textPassword,new GridBagCostraintsHorizontal(0,2,1,1,insets));
        this.add(password,new GridBagCostraintsHorizontal(0,3,1,1,insets));
        this.add(login,new GridBagCostraintsHorizontal(0,4,1,1,insets));

        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setFont(font);
        for ( Component child :  this.getComponents()){
            child.setFont(font);
        }

    }
}

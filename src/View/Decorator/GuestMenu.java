package View.Decorator;


import View.MainPage;
import View.Listeners.*;

import javax.swing.*;

public class GuestMenu extends Menu {

    private MainPage frame;

    public GuestMenu(MainPage page) {
        this.frame = page;

        JButton sfogliaCatBtn = new JButton("Sfoglia Catalogo");
        sfogliaCatBtn.setActionCommand(LoginListener.CATALOGO_BTN);
        JButton loginBtn = new JButton("Accedi");
        loginBtn.setActionCommand(LoginListener.TO_LOGIN_BTN);
        JButton registerBtn = new JButton("Registrati");
        registerBtn.setActionCommand(LoginListener.TO_REGISTER_BTN);

        LoginListener loginListener = new LoginListener(frame);
        sfogliaCatBtn.addActionListener(loginListener);
        loginBtn.addActionListener(loginListener);
        registerBtn.addActionListener(loginListener);

        pulsanti.add(sfogliaCatBtn);
        pulsanti.add(loginBtn);
        pulsanti.add(registerBtn);

    }
}

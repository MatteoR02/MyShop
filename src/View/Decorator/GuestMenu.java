package View.Decorator;


import View.MainPage;
import View.Listeners.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestMenu extends Menu {

    private MainPage frame;

    public GuestMenu(MainPage page) {
        this.frame = page;

        JButton sfogliaCatBtn = new JButton("Sfoglia Catalogo");
        sfogliaCatBtn.setActionCommand(LoginListener.CATALOGO_BTN);
        JButton loginBtn = new JButton("Accedi");
        loginBtn.setActionCommand(LoginListener.ENTER_LOGIN_BTN);
        JButton registerBtn = new JButton("Registrati");
        registerBtn.setActionCommand(LoginListener.REGISTER_BTN);

        JButton exitBtn = new JButton("Chiudi");
        exitBtn.setActionCommand(LoginListener.EXIT_BTN);
        JButton backBtn = new JButton("Indietro");
        backBtn.setActionCommand(LoginListener.BACK_GUEST_BTN);

        LoginListener loginListener = new LoginListener(frame);
        sfogliaCatBtn.addActionListener(loginListener);
        loginBtn.addActionListener(loginListener);
        registerBtn.addActionListener(loginListener);


        backBtn.addActionListener(loginListener);
        exitBtn.addActionListener(loginListener);

        pulsantiCentro.add(sfogliaCatBtn);
        pulsantiCentro.add(loginBtn);
        pulsantiCentro.add(registerBtn);

        pulsantiSud.add(backBtn);
        pulsantiSud.add(exitBtn);

    }
}

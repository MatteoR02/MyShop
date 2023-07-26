package View.Decorator;

import View.Listeners.LoginListener;
import View.MainPage;

import javax.swing.*;

public class BottomMenu extends Menu{

    private MainPage frame;

    public BottomMenu(MainPage frame){
        this.frame = frame;

        JButton exitBtn = new JButton("Chiudi");
        exitBtn.setActionCommand(LoginListener.EXIT_BTN);
        JButton backBtn = new JButton("Indietro");
        backBtn.setActionCommand(LoginListener.BACK_BTN);

        LoginListener loginListener = new LoginListener(frame);

        backBtn.addActionListener(loginListener);
        exitBtn.addActionListener(loginListener);

        pulsanti.add(backBtn);
        pulsanti.add(exitBtn);
    }
}


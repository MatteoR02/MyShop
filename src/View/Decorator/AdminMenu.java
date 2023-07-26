package View.Decorator;


import View.Listeners.LoginListener;
import View.MainPage;

import javax.swing.*;

public class AdminMenu extends Menu {

    private MainPage frame;

    public AdminMenu(MainPage page) {
        this.frame = page;

        JButton sfogliaCatBtn = new JButton("Sfoglia Catalogo");
        sfogliaCatBtn.setActionCommand(LoginListener.CATALOGO_BTN);
        JButton profileBtn = new JButton("Profilo");
        profileBtn.setActionCommand(LoginListener.TO_PROFILE_BTN);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setActionCommand(LoginListener.LOGOUT_BTN);



        LoginListener loginListener = new LoginListener(frame);
        sfogliaCatBtn.addActionListener(loginListener);
        profileBtn.addActionListener(loginListener);
        logoutBtn.addActionListener(loginListener);




        pulsanti.add(sfogliaCatBtn);
        pulsanti.add(profileBtn);
        pulsanti.add(logoutBtn);



    }
}

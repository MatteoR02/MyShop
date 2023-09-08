package View.Decorator;


import View.Listeners.AdminListener;
import View.Listeners.LoginListener;
import View.MainPage;

import javax.swing.*;

public class AdminMenu extends Menu {

    private MainPage frame;

    public AdminMenu(MainPage page) {
        this.frame = page;

        JButton sfogliaCatBtn = new JButton("Gestisci Catalogo");
        sfogliaCatBtn.setActionCommand(LoginListener.CATALOGO_BTN);
        JButton creaArticoloBtn = new JButton("Crea Articolo");
        creaArticoloBtn.setActionCommand(AdminListener.TO_CREA_ARTICOLO);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setActionCommand(LoginListener.LOGOUT_BTN);

        LoginListener loginListener = new LoginListener(frame);
        AdminListener adminListener = new AdminListener(frame);

        sfogliaCatBtn.addActionListener(loginListener);
        creaArticoloBtn.addActionListener(adminListener);
        logoutBtn.addActionListener(loginListener);


        pulsanti.add(sfogliaCatBtn);
        pulsanti.add(creaArticoloBtn);
        pulsanti.add(logoutBtn);



    }
}

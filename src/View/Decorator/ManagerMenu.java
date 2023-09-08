package View.Decorator;


import Model.Manager;
import View.Listeners.LoginListener;
import View.Listeners.ManagerListener;
import View.MainPage;

import javax.swing.*;

public class ManagerMenu extends Menu {

    private MainPage frame;

    public ManagerMenu(MainPage page) {
        this.frame = page;

        JButton gestisciArticoliBtn = new JButton("Gestisci articoli");
        gestisciArticoliBtn.setActionCommand(ManagerListener.GESTISCI_ART_BTN);
        JButton gestisciClientiBtn = new JButton("Gestisci clienti");
        gestisciClientiBtn.setActionCommand(ManagerListener.GESTISCI_CLIENTI_BTN);
        JButton prenotazioniBtn = new JButton("Prenotazioni articoli");
        prenotazioniBtn.setActionCommand(ManagerListener.TO_PRENOTAZIONI);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setActionCommand(LoginListener.LOGOUT_BTN);


        LoginListener loginListener = new LoginListener(frame);
        ManagerListener managerListener = new ManagerListener(frame);


        gestisciArticoliBtn.addActionListener(managerListener);
        gestisciClientiBtn.addActionListener(managerListener);
        prenotazioniBtn.addActionListener(managerListener);
        logoutBtn.addActionListener(loginListener);


        pulsanti.add(gestisciArticoliBtn);
        pulsanti.add(gestisciClientiBtn);
        pulsanti.add(prenotazioniBtn);
        pulsanti.add(logoutBtn);



    }
}

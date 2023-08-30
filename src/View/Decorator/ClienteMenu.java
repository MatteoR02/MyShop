package View.Decorator;


import View.Listeners.ClienteListener;
import View.Listeners.LoginListener;
import View.MainPage;

import javax.swing.*;

public class ClienteMenu extends Menu {

    private MainPage frame;

    public ClienteMenu(MainPage page) {
        this.frame = page;

        JButton sfogliaCatBtn = new JButton("Sfoglia Catalogo");
        sfogliaCatBtn.setActionCommand(LoginListener.CATALOGO_BTN);
        JButton profileBtn = new JButton("Profilo");
        profileBtn.setActionCommand(LoginListener.TO_PROFILE_BTN);
        JButton toListeBtn = new JButton("Liste d'acquisto");
        toListeBtn.setActionCommand(ClienteListener.TO_LISTE_BTN);
        JButton toPrenotazioniBtn = new JButton("Prenotazioni");
        toPrenotazioniBtn.setActionCommand(ClienteListener.TO_PRENOTAZIONI);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setActionCommand(LoginListener.LOGOUT_BTN);

        LoginListener loginListener = new LoginListener(frame);
        ClienteListener clienteListener = new ClienteListener(frame);
        sfogliaCatBtn.addActionListener(loginListener);
        profileBtn.addActionListener(loginListener);
        toListeBtn.addActionListener(clienteListener);
        toPrenotazioniBtn.addActionListener(clienteListener);
        logoutBtn.addActionListener(loginListener);

        pulsanti.add(sfogliaCatBtn);
        pulsanti.add(toListeBtn);
        pulsanti.add(toPrenotazioniBtn);
        pulsanti.add(profileBtn);
        pulsanti.add(logoutBtn);

    }
}

package View.Decorator;

import View.Listeners.CatalogoListener;
import View.Listeners.LoginListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;

public class GuestCompCatalogoMenu extends Menu{

    private MainPage frame;

    public GuestCompCatalogoMenu(MainPage page, ComponenteCatalogo comp) {
        this.frame = page;

        JButton viewArtBtn = new JButton("Visualizza");
        viewArtBtn.setActionCommand(CatalogoListener.VIEW_ART_BTN);

        CatalogoListener catalogoListener = new CatalogoListener(frame, comp);
        viewArtBtn.addActionListener(catalogoListener);

        pulsanti.add(viewArtBtn);
    }
}

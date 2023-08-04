package View.Decorator;

import View.Listeners.CatalogoListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;

public class ClienteCompCatalogoMenu extends Menu{

    private MainPage frame;
    private ComponenteCatalogo comp;

    public ClienteCompCatalogoMenu(MainPage page, ComponenteCatalogo comp) {
        this.frame = page;
        this.comp = comp;

        JButton viewArtBtn = new JButton("Visualizza");
        viewArtBtn.setActionCommand(CatalogoListener.VIEW_ART_BTN);
        JButton addBtn = new JButton("Aggiungi alla lista");
        addBtn.setActionCommand(CatalogoListener.TO_ADD_BTN);

        CatalogoListener catalogoListener = new CatalogoListener(frame, comp);
        viewArtBtn.addActionListener(catalogoListener);
        addBtn.addActionListener(catalogoListener);

        pulsanti.add(viewArtBtn);
        pulsanti.add(addBtn);
    }
}

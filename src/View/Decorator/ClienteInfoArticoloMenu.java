package View.Decorator;

import Model.Recensione;
import View.Listeners.CatalogoListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;

public class ClienteInfoArticoloMenu extends Menu{

    private MainPage frame;
    private ComponenteCatalogo comp;

    public ClienteInfoArticoloMenu(MainPage page, ComponenteCatalogo comp) {
        this.frame = page;
        this.comp = comp;

        JButton addToListaBtn = new JButton("Aggiungi alla lista");
        addToListaBtn.setActionCommand(CatalogoListener.TO_ADD_BTN);
        JButton recensisciBtn = new JButton("Lascia una recensione");
        recensisciBtn.setActionCommand(CatalogoListener.TO_ADD_RECENSIONE);

        CatalogoListener catalogoListener = new CatalogoListener(frame, comp);
        recensisciBtn.addActionListener(catalogoListener);
        addToListaBtn.addActionListener(catalogoListener);

        pulsanti.add(recensisciBtn);
        pulsanti.add(addToListaBtn);
    }
}

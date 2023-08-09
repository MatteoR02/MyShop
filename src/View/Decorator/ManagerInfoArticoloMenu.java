package View.Decorator;

import View.Listeners.CatalogoListener;
import View.Listeners.ManagerListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;

public class ManagerInfoArticoloMenu extends Menu{

    private MainPage frame;
    private ComponenteCatalogo comp;

    public ManagerInfoArticoloMenu(MainPage page, ComponenteCatalogo comp) {
        this.frame = page;
        this.comp = comp;

        JButton modificaArticoloBtn = new JButton("Modifica Articolo");
        modificaArticoloBtn.setActionCommand(ManagerListener.GESTISCI_QUANTITA);
        JButton gestisciBtn = new JButton("Gestisci quantità");
        gestisciBtn.setActionCommand(ManagerListener.GESTISCI_QUANTITA);

        CatalogoListener catalogoListener = new CatalogoListener(frame, comp);
        modificaArticoloBtn.addActionListener(catalogoListener);
        gestisciBtn.addActionListener(catalogoListener);

        pulsanti.add(modificaArticoloBtn);
        pulsanti.add(gestisciBtn);
    }
}

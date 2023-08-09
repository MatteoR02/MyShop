package View.Decorator;

import Model.Recensione;
import View.Listeners.CatalogoListener;
import View.Listeners.ManagerListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;

public class ManagerRecensioneMenu extends Menu{

    private MainPage frame;
    private ComponenteCatalogo comp;
    private Recensione recensione;

    public ManagerRecensioneMenu(MainPage page, ComponenteCatalogo comp, Recensione recensione) {
        this.frame = page;
        this.comp = comp;
        this.recensione = recensione;

        JButton rispondiBtn = new JButton("Rispondi alla recensione");
        rispondiBtn.setActionCommand(ManagerListener.RISPONDI_RECENSIONE);

        JButton removeBtn = new JButton("Elimina recensione");
        removeBtn.setActionCommand(ManagerListener.REMOVE_RECENSIONE);

        CatalogoListener catalogoListener = new CatalogoListener(frame, comp, recensione);
        rispondiBtn.addActionListener(catalogoListener);
        removeBtn.addActionListener(catalogoListener);

        pulsanti.add(rispondiBtn);
        pulsanti.add(removeBtn);
    }
}

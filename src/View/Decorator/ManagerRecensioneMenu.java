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
        rispondiBtn.setActionCommand(ManagerListener.TO_RISPONDI_RECENSIONE);

        JButton removeBtn = new JButton("Elimina recensione");
        removeBtn.setActionCommand(ManagerListener.REMOVE_RECENSIONE);

        ManagerListener managerListener = new ManagerListener(frame, comp, recensione);
        rispondiBtn.addActionListener(managerListener);
        removeBtn.addActionListener(managerListener);

        pulsanti.add(rispondiBtn);
        pulsanti.add(removeBtn);
    }
}

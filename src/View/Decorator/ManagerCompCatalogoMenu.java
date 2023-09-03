package View.Decorator;

import View.Listeners.CatalogoListener;
import View.Listeners.ManagerListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ManagerCompCatalogoMenu extends Menu{

    private MainPage frame;
    private ComponenteCatalogo comp;

    public ManagerCompCatalogoMenu(MainPage page, ComponenteCatalogo comp) {
        this.frame = page;
        this.comp = comp;

        JButton viewArtBtn = new JButton("Visualizza");
        viewArtBtn.setActionCommand(CatalogoListener.VIEW_ART_BTN);
        JButton gestisciQuantitaBtn = new JButton("Gestisci quantità");
        gestisciQuantitaBtn.setActionCommand(ManagerListener.GESTISCI_QUANTITA);

        CatalogoListener catalogoListener = new CatalogoListener(frame, comp);
        ManagerListener managerListener = new ManagerListener(frame, comp);
        viewArtBtn.addActionListener(catalogoListener);
        gestisciQuantitaBtn.addActionListener(managerListener);

        gestisciQuantitaBtn.setForeground(Color.BLUE);


        pulsanti.add(viewArtBtn);
        pulsanti.add(gestisciQuantitaBtn);
    }
}

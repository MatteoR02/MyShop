package View.Decorator;

import View.Listeners.AdminListener;
import View.Listeners.CatalogoListener;
import View.Listeners.ManagerListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.awt.*;

public class AdminCompCatalogoMenu extends Menu{

    private MainPage frame;
    private ComponenteCatalogo comp;

    public AdminCompCatalogoMenu(MainPage page, ComponenteCatalogo comp) {
        this.frame = page;
        this.comp = comp;

        JButton viewArtBtn = new JButton("Visualizza");
        viewArtBtn.setActionCommand(CatalogoListener.VIEW_ART_BTN);
        JButton modificaArticoloBtn = new JButton("Modifica articolo");
        modificaArticoloBtn.setActionCommand(AdminListener.MODIFICA_ARTICOLO);
        JButton eliminaArticoloBtn = new JButton("Elimina articolo");
        eliminaArticoloBtn.setActionCommand(AdminListener.ELIMINA_ARTICOLO);

        CatalogoListener catalogoListener = new CatalogoListener(frame, comp);
        AdminListener adminListener = new AdminListener(frame, comp);

        viewArtBtn.addActionListener(catalogoListener);
        modificaArticoloBtn.addActionListener(adminListener);
        eliminaArticoloBtn.addActionListener(adminListener);

        eliminaArticoloBtn.setForeground(Color.RED);


        pulsanti.add(viewArtBtn);
        pulsanti.add(modificaArticoloBtn);
        pulsanti.add(eliminaArticoloBtn);
    }
}

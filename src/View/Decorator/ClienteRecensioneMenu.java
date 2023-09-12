package View.Decorator;

import Model.Recensione;
import View.Listeners.Builders.CatalogoListenerBuilder;
import View.Listeners.CatalogoListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;

public class ClienteRecensioneMenu extends Menu{

    private MainPage frame;
    private ComponenteCatalogo comp;
    private Recensione recensione;

    public ClienteRecensioneMenu(MainPage page, ComponenteCatalogo comp, Recensione recensione) {
        this.frame = page;
        this.comp = comp;
        this.recensione = recensione;

        JButton modificaBtn = new JButton("Modifica recensione");
        modificaBtn.setActionCommand(CatalogoListener.MODIFY_RECENSIONE);

        JButton eliminaBtn = new JButton("Elimina recensione");
        eliminaBtn.setActionCommand(CatalogoListener.DELETE_RECENSIONE);

        CatalogoListener catalogoListener = CatalogoListenerBuilder.newBuilder(frame).compCatalogo(comp).recensione(recensione).build();
        eliminaBtn.addActionListener(catalogoListener);
        modificaBtn.addActionListener(catalogoListener);

        pulsanti.add(eliminaBtn);
        pulsanti.add(modificaBtn);
    }
}

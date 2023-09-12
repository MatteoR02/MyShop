package View.Decorator;


import View.Listeners.AdminListener;
import View.Listeners.Builders.AdminListenerBuilder;
import View.MainPage;

import javax.swing.*;

public class CreaArticoloMenu extends Menu {

    private MainPage frame;

    public CreaArticoloMenu(MainPage page, JComboBox selPuntoVenditaBox) {
        this.frame = page;

        JButton creaProdottoBtn = new JButton("Crea Prodotto");
        creaProdottoBtn.setActionCommand(AdminListener.TO_CREA_PRODOTTO);
        JButton creaServizioBtn = new JButton("Crea Servizio");
        creaServizioBtn.setActionCommand(AdminListener.TO_CREA_SERVIZIO);
        JButton creaProdottoCompositoBtn = new JButton("Crea Prodotto Composito");
        creaProdottoCompositoBtn.setActionCommand(AdminListener.TO_CREA_PRODOTTO_COMPOSITO);
        JButton creaCategoriaBtn = new JButton("Crea Categoria");
        creaCategoriaBtn.setActionCommand(AdminListener.TO_CREA_CATEGORIA);
        JButton creaErogatoreBtn = new JButton("Crea Erogatore");
        creaErogatoreBtn.setActionCommand(AdminListener.TO_CREA_EROGATORE);


        AdminListener adminListener = AdminListenerBuilder.newBuilder(frame).comboBoxPV(selPuntoVenditaBox).build();
        creaProdottoBtn.addActionListener(adminListener);
        creaServizioBtn.addActionListener(adminListener);
        creaProdottoCompositoBtn.addActionListener(adminListener);
        creaCategoriaBtn.addActionListener(adminListener);
        creaErogatoreBtn.addActionListener(adminListener);

        pulsanti.add(creaProdottoBtn);
        pulsanti.add(creaServizioBtn);
        pulsanti.add(creaProdottoCompositoBtn);
        pulsanti.add(creaCategoriaBtn);
        pulsanti.add(creaErogatoreBtn);

    }
}

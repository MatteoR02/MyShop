package View.ViewModel;


import Business.ArticoloBusiness;
import Business.FotoBusiness;
import Business.SessionManager;
import Model.Cliente;
import Model.Utente;
import View.Decorator.ClienteCompCatalogoMenu;
import View.Decorator.GuestCompCatalogoMenu;
import View.Decorator.Menu;
import View.GridBagCostraintsHorizontal;
import View.Listeners.CatalogoListener;
import View.MainPage;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.*;

import static Business.SessionManager.LOGGED_USER;

public class ArticoloList extends JPanel {

    public ArticoloList(ComponenteCatalogo componente, MainPage frame){
        //this.setLayout(new GridBagLayout());
        this.setLayout(new MigLayout("align 50% 50%", "[]push []", "[]8 []8 []8 []8 []8 []8 []8"));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        Font categoriaFont = new Font("Arial", Font.ITALIC, 18);
        Font labelFont = new Font("Arial", Font.PLAIN, 20);

        String prezzoFormattato = String.format("%.2f", componente.getPrezzo());
        String mediaFormattato = String.format("%.2f", componente.getMediaRecensioni());

        Insets insets = new Insets(5,10,5,10);
        JLabel nome = new JLabel(componente.getNomeArticolo());
        JLabel prezzo = new JLabel(prezzoFormattato +"€",SwingConstants.RIGHT);
        JLabel categoria = new JLabel(componente.getNomeCategoria());
        JLabel mediaRec = new JLabel(mediaFormattato);
        categoria.setFont(categoriaFont);
        nome.setFont(labelFont);
        prezzo.setFont(labelFont);
        mediaRec.setFont(labelFont);

        JButton visualizzaSPBtn = new JButton("Visualizza sottoprodotti");
        visualizzaSPBtn.setFocusPainted(false);

        CatalogoListener catalogoListenerSP = new CatalogoListener(frame, componente);
        visualizzaSPBtn.setActionCommand(CatalogoListener.VIEW_SOTTOPRODOTTI);
        visualizzaSPBtn.addActionListener(catalogoListenerSP);

        Border titledBorder = BorderFactory.createTitledBorder(componente.getTipoArticolo().toString());

        this.setBorder(titledBorder);
        ((javax.swing.border.TitledBorder) this.getBorder()).setTitleFont(labelFont);

        Menu compCatalogoMenu;

        Utente u = (Utente) SessionManager.getSession().get(LOGGED_USER);
        if (u instanceof Cliente){
            compCatalogoMenu = new ClienteCompCatalogoMenu(frame, componente);
        } else {
            compCatalogoMenu = new GuestCompCatalogoMenu(frame, componente);
        }

        ImageIcon imageIcon = componente.getImmagini().get(0);

        imageIcon = FotoBusiness.scaleImageIcon(imageIcon, 220, 220);

        JLabel immagine = new JLabel(imageIcon);

        ImageIcon stella = new ImageIcon("resources/fullStar.png");

        stella = FotoBusiness.scaleImageIcon(stella, 20, 20);

        JLabel labelStella = new JLabel(stella);

        this.add(immagine, "align 50% 50%, cell 0 0 2 2, wrap");
        this.add(nome, "cell 0 2");
        this.add(prezzo, "cell 1 2, wrap");
        this.add(categoria, "cell 0 3, wrap");
        this.add(labelStella, "cell 0 4, split 2");
        this.add(mediaRec, "cell 0 4, wrap");

        if (componente.getTipoArticolo()== ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            this.add(visualizzaSPBtn, "cell 0 5, span, growx, wrap");
        }

        int i=0;
        for ( JButton btn : compCatalogoMenu.getPulsanti() ) {
            btn.setFocusPainted(false);
            this.add(btn, "cell 0 " + (6 + i) + ", span, growx, wrap");
            i++;
        }
/*
        this.add(immagine,new GridBagCostraintsHorizontal(0,0,2,2,insets));
        this.add(nome,new GridBagCostraintsHorizontal(0,2,1,1,insets));
        this.add(prezzo,new GridBagCostraintsHorizontal(1,2,1,1,insets));
        this.add(categoria,new GridBagCostraintsHorizontal(0,3,1,1,insets));
        //this.add(labelStella,new GridBagCostraintsHorizontal(1,4,1,1,insets));
        //this.add(mediaRec,new GridBagCostraintsHorizontal(0,4,1,1,insets));

        if (componente.getTipoArticolo()== ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            this.add(visualizzaSPBtn, new GridBagCostraintsHorizontal(0,5,2,1,new Insets(5,10,5,10)));
        }

        int i=0;
        for ( JButton btn : compCatalogoMenu.getPulsanti() ) {
            btn.setFocusPainted(false);
            this.add(btn,new GridBagCostraintsHorizontal(0,6+i,2,1,new Insets(5,10,5,10)));
            i++;
        }*/

    }
}

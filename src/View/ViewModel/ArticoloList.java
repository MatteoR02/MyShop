package View.ViewModel;


import Business.ArticoloBusiness;
import Business.FotoBusiness;
import Business.SessionManager;
import Model.Cliente;
import Model.Manager;
import Model.Utente;
import View.Decorator.ClienteCompCatalogoMenu;
import View.Decorator.GuestCompCatalogoMenu;
import View.Decorator.ManagerCompCatalogoMenu;
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
        String mediaFormattato = String.format(" %.1f", componente.getMediaRecensioni());

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

        Border titledBorder = BorderFactory.createTitledBorder(ArticoloBusiness.tipoArticoloToString(componente.getTipoArticolo()));

        this.setBorder(titledBorder);
        ((javax.swing.border.TitledBorder) this.getBorder()).setTitleFont(labelFont);

        Menu compCatalogoMenu;

        Utente u = (Utente) SessionManager.getSession().get(LOGGED_USER);
        if (u instanceof Cliente){
            compCatalogoMenu = new ClienteCompCatalogoMenu(frame, componente);
        } else if (u instanceof Manager && componente.getTipoArticolo() == ArticoloBusiness.TipoArticolo.PRODOTTO){
            compCatalogoMenu = new ManagerCompCatalogoMenu(frame, componente);
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
    }
}

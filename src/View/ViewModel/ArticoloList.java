package View.ViewModel;


import Business.FotoBusiness;
import Business.SessionManager;
import Model.Cliente;
import Model.Utente;
import View.Decorator.ClienteCompCatalogoMenu;
import View.Decorator.GuestCompCatalogoMenu;
import View.Decorator.Menu;
import View.GridBagCostraintsHorizontal;
import View.MainPage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.*;

import static Business.SessionManager.LOGGED_USER;

public class ArticoloList extends JPanel {

    public ArticoloList(ComponenteCatalogo componente, MainPage frame){
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        Font categoriaFont = new Font("Arial", Font.ITALIC, 18);
        Font labelFont = new Font("Arial", Font.PLAIN, 20);

        String prezzoFormattato = String.format("%.2f", componente.getPrezzo());

        Insets insets = new Insets(5,10,5,10);
        JLabel nome = new JLabel(componente.getNomeArticolo());
        JLabel prezzo = new JLabel(prezzoFormattato +"€",SwingConstants.RIGHT);
        JLabel categoria = new JLabel(componente.getNomeCategoria());
        categoria.setFont(categoriaFont);
        nome.setFont(labelFont);
        prezzo.setFont(labelFont);

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

        imageIcon = FotoBusiness.scaleImageIcon(imageIcon, 200, 200);

        JLabel immagine = new JLabel(imageIcon);

        this.add(immagine,new GridBagCostraintsHorizontal(0,0,2,2,insets));
        this.add(nome,new GridBagCostraintsHorizontal(0,2,1,1,insets));
        this.add(prezzo,new GridBagCostraintsHorizontal(1,2,1,1,insets));
        this.add(categoria,new GridBagCostraintsHorizontal(0,3,1,1,insets));

        int i=0;
        for ( JButton btn : compCatalogoMenu.getPulsanti() ) {
            btn.setFocusPainted(false);
            this.add(btn,new GridBagCostraintsHorizontal(0,4+i,2,1,new Insets(5,10,5,10)));
            i++;
        }

    }
}

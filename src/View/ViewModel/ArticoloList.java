package View.ViewModel;


import Business.SessionManager;
import Model.Cliente;
import Model.Utente;
import View.Decorator.ClienteCompCatalogoMenu;
import View.Decorator.GuestCompCatalogoMenu;
import View.Decorator.Menu;
import View.GridBagCostraintsHorizontal;
import View.MainPage;

import javax.swing.*;

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


        Menu compCatalogoMenu;

        Utente u = (Utente) SessionManager.getSession().get(LOGGED_USER);
        if (u instanceof Cliente){
            compCatalogoMenu = new ClienteCompCatalogoMenu(frame, componente);
        } else {
            compCatalogoMenu = new GuestCompCatalogoMenu(frame, componente);
        }

       // JButton visualizza = new JButton("Visualizza");

       // visualizza.setFocusPainted(false);
        ImageIcon imageIcon = componente.getImmagini().get(0);

        Image image = imageIcon.getImage(); // La trasformo in un Image
        Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // La dimensiono in modo smooth
        imageIcon = new ImageIcon(newimg);
        JLabel immagine = new JLabel(imageIcon);

        //TODO inserire try catch per caricare l'immagine
        /*ImageIcon imageIcon = new ImageIcon("resources/sedia.png"); // Carico l'immagine in un ImageIcon
        Image image = imageIcon.getImage(); // La trasformo in un Image
        Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // La dimensiono in modo smooth
        imageIcon = new ImageIcon(newimg);
        JLabel immagine = new JLabel(imageIcon);*/

        this.add(immagine,new GridBagCostraintsHorizontal(0,0,2,2,insets));
        this.add(nome,new GridBagCostraintsHorizontal(0,2,1,1,insets));
        this.add(prezzo,new GridBagCostraintsHorizontal(1,2,1,1,insets));
        this.add(categoria,new GridBagCostraintsHorizontal(0,3,1,1,insets));
        //this.add(visualizza,new GridBagCostraintsHorizontal(0,3,2,1,insets));
        int i=0;
        for ( JButton btn : compCatalogoMenu.getPulsanti() ) {
            btn.setFocusPainted(false);
            this.add(btn,new GridBagCostraintsHorizontal(0,4+i,2,1,new Insets(5,10,5,10)));
            i++;
        }

    }
}

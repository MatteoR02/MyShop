package View;

import Business.SessionManager;
import Model.Cliente;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ProfiloPanel extends JPanel {

    public ProfiloPanel(MainPage frame){
        this.setLayout(new MigLayout("insets 80, fillx, align 50% 50%", "[]push []push []", "[]10 []10 []10 []10 []10 []10 []10 []"));

        Cliente c = (Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER);

        JLabel labelNome = new JLabel("Nome: ");
        JLabel labelCognome = new JLabel("Cognome: ");
        JLabel labelIndirizzo = new JLabel("Indirizzo: ");
        JLabel labelEmail = new JLabel("Email: ");
        JLabel labelUsername = new JLabel("Username: ");
        JLabel labelProfessione = new JLabel("Professione: ");
        JLabel labelCanalePreferito = new JLabel("Canale Preferito: ");
        JLabel labelStato = new JLabel("Stato: ");

        JLabel nome = new JLabel(c.getPersona().getNome(), SwingConstants.RIGHT);
        JLabel cognome = new JLabel(c.getPersona().getCognome(), SwingConstants.RIGHT);
        JLabel indirizzo = new JLabel(c.getIndirizzo().toString(), SwingConstants.RIGHT);
        JLabel email = new JLabel(c.getPersona().getEmail(), SwingConstants.RIGHT);
        JLabel username = new JLabel(c.getUsername(), SwingConstants.RIGHT);
        JLabel professione = new JLabel(c.getProfessione().name(), SwingConstants.RIGHT);
        JLabel canalePreferito = new JLabel(c.getCanalePreferito().name(), SwingConstants.RIGHT);
        JLabel stato = new JLabel(c.getStato().name(), SwingConstants.RIGHT);

        this.add(labelNome, "cell 0 0");
        this.add(nome, "cell 2 0");
        this.add(labelCognome, "cell 0 1");
        this.add(cognome, "cell 2 1");
        this.add(labelIndirizzo, "cell 0 2");
        this.add(indirizzo, "cell 2 2");
        this.add(labelEmail, "cell 0 3");
        this.add(email, "cell 2 3");
        this.add(labelUsername, "cell 0 4");
        this.add(username, "cell 2 4");
        this.add(labelProfessione, "cell 0 5");
        this.add(professione, "cell 2 5");
        this.add(labelCanalePreferito, "cell 0 6");
        this.add(canalePreferito, "cell 2 6");
        this.add(labelStato, "cell 0 7");
        this.add(stato, "cell 2 7");

        Font font = new Font("Arial", Font.PLAIN, 20);
        this.setFont(font);
        for ( Component child :  this.getComponents()){
            child.setFont(font);
        }
    }
}

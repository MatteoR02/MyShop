package View.CreaArticolo;

import Business.ArticoloBusiness;
import Business.PuntoVenditaBusiness;
import Model.Categoria;
import Model.Erogatore;
import Model.Magazzino;
import Model.PuntoVendita;
import View.Listeners.AdminListener;
import View.Listeners.ManagerListener;
import View.MainPage;
import View.ViewModel.RigaCliente;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CreaProdottoPanel extends JPanel {

    public CreaProdottoPanel(JFrame frame, PuntoVendita pv) {

        this.setLayout(new MigLayout("insets 80, fillx, align 50% 50%", "[]push []push []push []", "[]30 [] []15 [] [] []15 [] []15 []30 []"));

        JLabel labelCreazione = new JLabel("Creazione Prodotto");
        JLabel labelNome = new JLabel("Nome");
        JLabel labelDescrizione = new JLabel("Descrizione");
        JLabel labelCorsia = new JLabel("Corsia ");
        JLabel labelScaffale = new JLabel("Scaffale");
        JLabel labelMagazzino = new JLabel("Magazzino");
        JLabel labelPrezzo = new JLabel("Prezzo €");
        JLabel labelCategoria = new JLabel("Categoria");
        JLabel labelErogatore = new JLabel("Erogatore");
        JLabel labelFoto = new JLabel("Foto");

        JTextField fieldNome = new JTextField(20);
        JTextArea fieldDescrizione = new JTextArea("", 15, 50);
        JTextField fieldCorsia = new JTextField(4);
        JTextField fieldScaffale = new JTextField(4);
        JTextField fieldPrezzo = new JTextField(5);

        fieldDescrizione.setFont(fieldDescrizione.getFont().deriveFont(17f));

        ArrayList<Magazzino> magazzini = PuntoVenditaBusiness.getAllMagazziniOfPV(pv.getId()).getObject();

        Magazzino[] selMagazzino = magazzini.toArray(new Magazzino[0]);
        JComboBox selMagazzinoBox = new JComboBox(selMagazzino);

        ArrayList<Categoria> categorie = ArticoloBusiness.getAllCategorie(Categoria.TipoCategoria.PRODOTTO).getObject();

        Categoria[] selCategoria = categorie.toArray(new Categoria[0]);
        JComboBox selCategoriaBox = new JComboBox(selCategoria);

        ArrayList<Erogatore> erogatori = ArticoloBusiness.getAllErogatori().getObject();

        Erogatore[] selErogatore = erogatori.toArray(new Erogatore[0]);
        JComboBox selErogatoriBox = new JComboBox(selErogatore);

        JButton selezionaImmaginiBtn = new JButton("Seleziona immagini");

        JLabel labelAllegato = new JLabel("", SwingConstants.RIGHT);

        fieldDescrizione.setLineWrap(true);
        fieldDescrizione.setWrapStyleWord(true);

        JScrollPane scrollPaneCorpo = new JScrollPane(fieldDescrizione, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton creaProdottoBtn = new JButton("Crea Prodotto");

        AdminListener adminListener = new AdminListener((MainPage) frame, fieldNome, fieldDescrizione, fieldPrezzo, fieldCorsia, fieldScaffale, selMagazzinoBox, selCategoriaBox, selErogatoriBox, labelAllegato, pv);
        selezionaImmaginiBtn.addActionListener(adminListener);
        selezionaImmaginiBtn.setActionCommand(AdminListener.SELEZIONA_IMMAGINI);

        creaProdottoBtn.addActionListener(adminListener);
        creaProdottoBtn.setActionCommand(AdminListener.CREA_PRODOTTO);


        this.add(labelCreazione, "cell 0 0, wrap");
        this.add(labelNome, "cell 0 1");
        this.add(fieldNome, "cell 0 2");
        this.add(labelCorsia, "cell 1 1, split 2");
        this.add(labelScaffale, "cell 1 1");
        this.add(fieldCorsia, "cell 1 2, split 2");
        this.add(fieldScaffale, "cell 1 2");
        this.add(labelMagazzino, "cell 2 1");
        this.add(selMagazzinoBox, "cell 2 2");
        this.add(labelPrezzo, "cell 3 1");
        this.add(fieldPrezzo, "cell 3 2");
        this.add(labelDescrizione, "cell 0 3");
        this.add(scrollPaneCorpo, "cell 0 4 4 2, growx");
        this.add(labelCategoria, "cell 0 6");
        this.add(selCategoriaBox, "cell 0 7");
        this.add(labelErogatore, "cell 3 6");
        this.add(selErogatoriBox, "cell 3 7");
        this.add(selezionaImmaginiBtn, "cell 0 8, grow");
        this.add(labelAllegato, "cell 2 8");
        this.add(creaProdottoBtn, "cell 0 9, span, grow");


        fieldPrezzo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if (!Character.isDigit(c) && !(c=='.')){
                    ke.consume();
                }
            }
        });

        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setFont(font);
        for ( Component child :  this.getComponents()){
            child.setFont(font);
        }
    }

}


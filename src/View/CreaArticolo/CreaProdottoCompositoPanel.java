package View.CreaArticolo;

import Business.ArticoloBusiness;
import Model.*;
import View.Listeners.AdminListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;
import View.ViewModel.CreazionePCTableModel;
import View.ViewModel.RigaProdottoCreazione;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreaProdottoCompositoPanel extends JPanel {

    public CreaProdottoCompositoPanel(JFrame frame, PuntoVendita pv, boolean modifica, ComponenteCatalogo comp) {

        this.setLayout(new MigLayout("insets 80, fill, align 50% 50%", "[]push [] []", "[]30 [] []15 [] [] []30 [] []15 []30 [] [] [] [] [] [] []"));

        JLabel labelCreazione = new JLabel("Creazione Prodotto Composito");
        JLabel labelNome = new JLabel("Nome");
        JLabel labelDescrizione = new JLabel("Descrizione");
        JLabel labelCategoria = new JLabel("Categoria");
        JLabel labelSottoProdotti = new JLabel("Aggiungi sottoprodotti");

        ArrayList<RigaProdottoCreazione> righe = new ArrayList<>();
        for (Articolo prod : ArticoloBusiness.getAllProdotti().getObject()) {
            RigaProdottoCreazione riga = new RigaProdottoCreazione();
            riga.setIdProdotto(prod.getId());
            riga.setNome(prod.getNome());
            riga.setPrezzo(prod.getPrezzo());
            riga.setCategoria(prod.getCategoria().getNome());
            riga.setErogatore(prod.getErogatore().getNome());
            riga.setIsProdottoComposito(false);
            if (prod instanceof ProdottoComposito){
                riga.setIsProdottoComposito(true);
            }
            righe.add(riga);
        }

        CreazionePCTableModel creazionePCTableModel = new CreazionePCTableModel(righe);
        JTable table = new JTable(creazionePCTableModel);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setRowHeight(30);

        JScrollPane scrollPaneTable = new JScrollPane(table);

        JTextField fieldNome = new JTextField(20);
        JTextArea fieldDescrizione = new JTextArea("", 15, 50);
        fieldDescrizione.setFont(fieldDescrizione.getFont().deriveFont(17f));

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

        JButton creaProdottoBtn = new JButton("Crea Prodotto Composito");

        AdminListener adminListener = new AdminListener((MainPage) frame, fieldNome, fieldDescrizione, null, selCategoriaBox, selErogatoriBox, labelAllegato, table);
        selezionaImmaginiBtn.addActionListener(adminListener);
        selezionaImmaginiBtn.setActionCommand(AdminListener.SELEZIONA_IMMAGINI);

        creaProdottoBtn.addActionListener(adminListener);
        creaProdottoBtn.setActionCommand(AdminListener.CREA_PRODOTTO_COMPOSITO);

        if (modifica){
            fieldNome.setText(comp.getNomeArticolo());
            fieldDescrizione.setText(comp.getDescrizioneArticolo());
            creaProdottoBtn.setText("Modifica Prodotto Composito");
            creaProdottoBtn.setActionCommand(AdminListener.MODIFICA_PRODOTTO);
        }


        this.add(labelCreazione, "cell 0 0, wrap");
        this.add(labelNome, "cell 0 1");
        this.add(fieldNome, "cell 0 2");
        this.add(labelDescrizione, "cell 0 3");
        this.add(scrollPaneCorpo, "cell 0 4 3 2, grow");
        this.add(labelSottoProdotti, "cell 0 6");
        this.add(scrollPaneTable, "cell 0 7 3 5, grow");


        this.add(labelCategoria, "cell 0 12");
        this.add(selCategoriaBox, "cell 0 13");
        this.add(selErogatoriBox, "cell 2 13");
        this.add(selezionaImmaginiBtn, "cell 0 14, grow");
        this.add(labelAllegato, "cell 2 14");
        this.add(creaProdottoBtn, "cell 0 15, span, grow");


        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setFont(font);
        for ( Component child :  this.getComponents()){
            child.setFont(font);
        }
    }

}


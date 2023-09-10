package View;


import Business.*;
import Business.Strategy.OrdinamentoRecensioni.OrdinamentoRecensioni;
import Model.Cliente;
import Model.Manager;
import Model.Recensione;
import Model.Utente;
import View.Decorator.*;
import View.Decorator.Menu;
import View.Listeners.CatalogoListener;
import View.ViewModel.ComponenteCatalogo;
import View.ViewModel.RecensioneList;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InfoArticoloPanel extends JPanel {

    public InfoArticoloPanel(MainPage frame, ComponenteCatalogo comp, boolean ordinato, ArrayList<Recensione> listaRecensioni){
        this.setLayout(new GridLayout(2,1,5,5));
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(10,5,10,5);


        JPanel panelArticolo = new JPanel(new BorderLayout());
        JPanel panelInfo = new JPanel(new MigLayout("insets 15","[]28 [] [] []20 []","[]10 []10 []10 []5 []"));
        JPanel panelRecensioni = new JPanel(new BorderLayout());


        ImageIcon imageIcon = comp.getImmagini().get(0);

        imageIcon = FotoBusiness.scaleImageIcon(imageIcon, 250, 250);

        JLabel immagine = new JLabel(imageIcon);
        immagine.setName("0");

        JLabel nomeArticolo = new JLabel(comp.getNomeArticolo());
        JLabel prezzo = new JLabel(comp.getPrezzo() +"€");
        JLabel nomeCategoria = new JLabel("Categoria: " + comp.getNomeCategoria());
        JLabel nomeProduttore = new JLabel("Erogatore: " + comp.getNomeErogatore());

        JLabel descrizioneArticolo = new JLabel("<html>Maggiori informazioni sull'articolo: <br> " + comp.getDescrizioneArticolo() + " </html>");

        JLabel labelIndexFoto = new JLabel("1/" + comp.getImmagini().toArray().length, SwingConstants.CENTER);

        JButton nextImageBtn = new JButton("Foto ->");
        JButton backImageBtn = new JButton("<- Foto");
        nextImageBtn.setFocusPainted(false);
        backImageBtn.setFocusPainted(false);

        CatalogoListener catalogoListenerFoto = new CatalogoListener(frame, comp, immagine, labelIndexFoto);
        nextImageBtn.setActionCommand(CatalogoListener.NEXT_FOTO);
        backImageBtn.setActionCommand(CatalogoListener.BACK_FOTO);

        nextImageBtn.addActionListener(catalogoListenerFoto);
        backImageBtn.addActionListener(catalogoListenerFoto);

        panelInfo.add(immagine, "cell 0 0 4 5");
        panelInfo.add(nomeArticolo, "cell 4 0, wrap");
        panelInfo.add(prezzo, " cell 4 1, wrap");
        panelInfo.add(nomeCategoria, "cell 4 2, wrap");
        panelInfo.add(nomeProduttore, "cell 4 3, wrap");
        panelInfo.add(descrizioneArticolo, "cell 4 4, wrap");
        panelInfo.add(backImageBtn, "cell 0 5");
        panelInfo.add(labelIndexFoto, "cell 2 5");
        panelInfo.add(nextImageBtn, "cell 3 5, gapleft push");

        Font font = new Font("Arial", Font.PLAIN, 22);
        this.setFont(font);
        for ( Component child :  panelInfo.getComponents()){
            if(!(child instanceof JButton)){
                child.setFont(font);
            }
        }

        panelArticolo.add(panelInfo, BorderLayout.CENTER);

        JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottoni.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        Utente u = (Utente) (SessionManager.getSession().get(SessionManager.LOGGED_USER));
        if (u instanceof Cliente || u == null) {

            Menu clienteInfoArticoloMenu = new ClienteInfoArticoloMenu(frame, comp);

            for (JButton button : clienteInfoArticoloMenu.getPulsanti()   ) {
                button.setFocusPainted(false);
                panelBottoni.add(button);
            }
        } if (u instanceof Manager && comp.getTipoArticolo() == ArticoloBusiness.TipoArticolo.PRODOTTO){
            Manager m = (Manager) u;
            Menu managerInfoArticoloMenu = new ManagerInfoArticoloMenu(frame,comp);
            for (JButton button : managerInfoArticoloMenu.getPulsanti()   ) {
                button.setFocusPainted(false);
                panelBottoni.add(button);
            }
        }

        if (comp.getTipoArticolo() == ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            JButton visualizzaSPBtn = new JButton("Visualizza sottoprodotti");
            visualizzaSPBtn.setFocusPainted(false);

            CatalogoListener catalogoListenerSP = new CatalogoListener(frame, comp);
            visualizzaSPBtn.setActionCommand(CatalogoListener.VIEW_SOTTOPRODOTTI);
            visualizzaSPBtn.addActionListener(catalogoListenerSP);
            panelBottoni.add(visualizzaSPBtn);
        }


        panelArticolo.add(panelBottoni, BorderLayout.SOUTH);

        this.add(panelArticolo);

        JPanel recensioniScrollPanel = new JPanel(new GridLayout(0,1,10,10));

        ExecuteResult<Recensione> resultRec = RecensioneBusiness.getRecensioniOfArticolo(comp.getIdArticolo());
        if (!ordinato){
            ArrayList<Recensione> recensioni = resultRec.getObject();
            for (Recensione rec: recensioni) {
                RecensioneList recensioneList = new RecensioneList(rec, comp, frame, false);
                ExecuteResult<Recensione> resultRisposta = RecensioneBusiness.getRisposta(rec.getId());
                if (resultRisposta.getResult()== ExecuteResult.ResultStatement.OK){
                    Recensione risposta = RecensioneBusiness.getRisposta(rec.getId()).getSingleObject();
                    RecensioneList rispostaList = new RecensioneList(risposta, comp, frame, true);
                    recensioniScrollPanel.add(recensioneList);
                    recensioniScrollPanel.add(rispostaList);
                } else {
                    recensioniScrollPanel.add(recensioneList);
                }
            }
        } else {
            for (Recensione rec : listaRecensioni) {
                RecensioneList recensioneList = new RecensioneList(rec, comp, frame, false);
                ExecuteResult<Recensione> resultRisposta = RecensioneBusiness.getRisposta(rec.getId());
                if (resultRisposta.getResult()== ExecuteResult.ResultStatement.OK){
                    Recensione risposta = RecensioneBusiness.getRisposta(rec.getId()).getSingleObject();
                    RecensioneList rispostaList = new RecensioneList(risposta, comp, frame, true);
                }
            }
        }



        for (int i = 0; i<4; i++) {
            recensioniScrollPanel.add(new JPanel());
        }

        JScrollPane scrollPane = new JScrollPane(recensioniScrollPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelRecensioni.add(scrollPane,BorderLayout.CENTER);

        JPanel panelNordRecensioni = new JPanel(new MigLayout("", "[]push [] []", "[]"));

        JLabel textRecensioni = new JLabel("Recensioni");
        textRecensioni.setFont(new Font("Arial", Font.BOLD, 30));

        OrdinamentoRecensioni.Ordinamento[] ordinamentoStrategies = {OrdinamentoRecensioni.Ordinamento.RECENTI, OrdinamentoRecensioni.Ordinamento.MIGLIORI};
        JComboBox ordinamento = new JComboBox(ordinamentoStrategies);

        CatalogoListener catalogoListenerSort = new CatalogoListener(resultRec.getObject(), ordinamento, comp);
        catalogoListenerSort.setFrame(frame);

        JButton ordinaBtn = new JButton("Ordina recensioni");
        ordinaBtn.setActionCommand(CatalogoListener.SORT_RECENSIONI);
        ordinaBtn.addActionListener(catalogoListenerSort);

        ordinaBtn.setFocusPainted(false);

        panelNordRecensioni.add(textRecensioni,"cell 0 0");
        panelNordRecensioni.add(ordinamento, "cell 1 0");
        panelNordRecensioni.add(ordinaBtn, "cell 2 0");

        panelRecensioni.add(panelNordRecensioni, BorderLayout.NORTH);

        this.add(panelRecensioni);

    }
}

package View;

import Business.ArticoloBusiness;
import Business.Strategy.OrdinamentoArticoli.OrdinamentoArticoli;
import Model.*;
import View.Listeners.CatalogoListener;
import View.ViewModel.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CatalogoPanel extends JPanel {

    private List<Articolo> articoli;
    private MainPage frame;

    public CatalogoPanel(ArrayList<Articolo> articoli, MainPage frame, boolean isProdCom) {

        this.articoli=articoli;
        ArrayList<ComponenteCatalogo> componenti = new ArrayList<>();

        this.setLayout(new BorderLayout());
        JPanel centro = new JPanel(new GridLayout(0,4,10,10));

      /*  SessionManager.getSession().putIfAbsent(SessionManager.CATALOGO_VIEW, centro);

        if ( ((JPanel) SessionManager.getSession().get(SessionManager.CATALOGO_VIEW)).getComponents().length==0  ){

            for (Articolo articolo : articoli ) {
                if(!ArticoloBusiness.isUndefined(articolo.getNome())){
                    ComponenteCatalogo componenteCatalogo = new ComponenteCatalogo();
                    componenteCatalogo.setId(articolo.getId());
                    componenteCatalogo.setNomeArticolo(articolo.getNome());
                    componenteCatalogo.setPrezzo(articolo.getPrezzo());
                    componenteCatalogo.setNomeCategoria(articolo.getCategoria().getNome());
                    componenteCatalogo.setDefaultImmagine(ArticoloBusiness.blobToImage(articolo.getImmagini().get(0).getImmagine()));
                    componenteCatalogo.setNomeProduttore("MyShop");
                    componenteCatalogo.setRecensioni(articolo.getRecensioni());
                    if (articolo instanceof Prodotto){
                        componenteCatalogo.setTipoArticolo(ArticoloBusiness.TipoArticolo.PRODOTTO);
                        componenteCatalogo.setNomeProduttore(((Prodotto) articolo).getProduttore().getNome());
                    } else if (articolo instanceof ProdottoComposito){
                        componenteCatalogo.setTipoArticolo(ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO);
                    } else if (articolo instanceof Servizio){
                        componenteCatalogo.setTipoArticolo(ArticoloBusiness.TipoArticolo.SERVIZIO);
                    }

                    componenti.add(componenteCatalogo);
                }
            }
            System.out.println("PRIMA VOLTA");
            for (ComponenteCatalogo componenteCatalogo: componenti  ) {
                ArticoloList articoloList = new ArticoloList(componenteCatalogo,frame);
                centro.add(articoloList);
                SessionManager.getSession().put(SessionManager.CATALOGO_VIEW, centro);
            }
        } else {
            centro = ((JPanel) SessionManager.getSession().get(SessionManager.CATALOGO_VIEW));
            System.out.println("NON LA PRIMA VOLTA");
        }*/

        for (Articolo articolo : articoli ) {
            if(!ArticoloBusiness.isUndefined(articolo.getNome())){
                ComponenteCatalogo componenteCatalogo = new ComponenteCatalogo();
                componenteCatalogo.setIdArticolo(articolo.getId());
                componenteCatalogo.setNomeArticolo(articolo.getNome());
                componenteCatalogo.setDescrizioneArticolo(articolo.getDescrizione());
                componenteCatalogo.setPrezzo(articolo.getPrezzo());
                componenteCatalogo.setNomeCategoria(articolo.getCategoria().getNome());
                componenteCatalogo.setDefaultImmagine(ArticoloBusiness.blobToImage(articolo.getImmagini().get(0).getImmagine()));
                componenteCatalogo.setNomeErogatore(articolo.getErogatore().getNome());
                componenteCatalogo.setRecensioni(articolo.getRecensioni());
                componenteCatalogo.setMediaRecensioni(articolo.getMediaRecensioni());

                ArrayList<ImageIcon> imageIcons = new ArrayList<>();

                for (Foto foto: articolo.getImmagini()  ) {
                    imageIcons.add(ArticoloBusiness.blobToImage(foto.getImmagine()));
                }
                componenteCatalogo.setImmagini(imageIcons);

                if (articolo instanceof Prodotto){
                    componenteCatalogo.setTipoArticolo(ArticoloBusiness.TipoArticolo.PRODOTTO);
                } else if (articolo instanceof ProdottoComposito){
                    componenteCatalogo.setTipoArticolo(ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO);
                } else if (articolo instanceof Servizio){
                    componenteCatalogo.setTipoArticolo(ArticoloBusiness.TipoArticolo.SERVIZIO);
                }

                componenti.add(componenteCatalogo);
            }
        }
        for (ComponenteCatalogo componenteCatalogo: componenti  ) {
            ArticoloList articoloList = new ArticoloList(componenteCatalogo,frame);
            centro.add(articoloList);
        }

        for (int i = 0; i<3; i++){
            centro.add(new JPanel());
        }


        JScrollPane scrollPane = new JScrollPane(centro);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane,BorderLayout.CENTER);

        JPanel panelSud = new JPanel(new MigLayout("", "[]push [] []", "[]"));

        OrdinamentoArticoli.Ordinamento[] ordinamentoStrategies = {OrdinamentoArticoli.Ordinamento.PREZZO_PIU_ALTO, OrdinamentoArticoli.Ordinamento.PREZZO_PIU_BASSO, OrdinamentoArticoli.Ordinamento.PIU_VOTATI, OrdinamentoArticoli.Ordinamento.ORDINE_ALFABETICO, OrdinamentoArticoli.Ordinamento.TIPOLOGIA};
        JComboBox ordinamento = new JComboBox(ordinamentoStrategies);

        JButton ordinaBtn = new JButton("Ordina articoli");
        ordinaBtn.setFocusPainted(false);

        CatalogoListener catalogoListenerSort = new CatalogoListener(ordinamento);
        catalogoListenerSort.setFrame(frame);
        ordinaBtn.setActionCommand(CatalogoListener.SORT_CATALOGO);
        ordinaBtn.addActionListener(catalogoListenerSort);

        panelSud.add(ordinamento, "cell 1 0");
        panelSud.add(ordinaBtn, "cell 2 0");

        this.add(panelSud, BorderLayout.NORTH);


    }
}

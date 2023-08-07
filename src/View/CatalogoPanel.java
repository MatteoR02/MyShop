package View;

import Business.ArticoloBusiness;
import Business.SessionManager;
import Model.Articolo;
import Model.Prodotto;
import Model.ProdottoComposito;
import Model.Servizio;
import View.ViewModel.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CatalogoPanel extends JPanel {

    private List<Articolo> articoli;
    private MainPage frame;

    public CatalogoPanel(ArrayList<Articolo> articoli, MainPage frame) {

        this.articoli=articoli;
        ArrayList<ComponenteCatalogo> componenti = new ArrayList<>();

        this.setLayout(new BorderLayout());
        JPanel centro = new JPanel();
        centro.setLayout(new GridLayout(0,4,10,10));

        SessionManager.getSession().putIfAbsent(SessionManager.CATALOGO_VIEW, centro);

        if ( ((JPanel) SessionManager.getSession().get(SessionManager.CATALOGO_VIEW)).getComponents().length==0   ){

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
        }

       /* for (Articolo articolo : articoli ) {
            if(!NotWorking_ArticoloBusiness.isUndefined(articolo.getNome())){
                ComponenteCatalogo componenteCatalogo = new ComponenteCatalogo();
                componenteCatalogo.setId(articolo.getId());
                componenteCatalogo.setNomeArticolo(articolo.getNome());
                componenteCatalogo.setPrezzo(articolo.getPrezzo());
                componenteCatalogo.setNomeCategoria(articolo.getCategoria().getNome());
                componenteCatalogo.setDefaultImmagine(NotWorking_ArticoloBusiness.blobToImage(articolo.getImmagini().get(0).getImmagine()));
                componenteCatalogo.setNomeProduttore("MyShop");
                componenteCatalogo.setRecensioni(articolo.getRecensioni());
                if (articolo instanceof Prodotto){
                    componenteCatalogo.setTipoArticolo(NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO);
                    componenteCatalogo.setNomeProduttore(((Prodotto) articolo).getProduttore().getNome());
                } else if (articolo instanceof ProdottoComposito){
                    componenteCatalogo.setTipoArticolo(NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO);
                } else if (articolo instanceof Servizio){
                    componenteCatalogo.setTipoArticolo(NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO);
                }

                componenti.add(componenteCatalogo);
            }
        }
        for (ComponenteCatalogo componenteCatalogo: componenti  ) {
            ArticoloList articoloList = new ArticoloList(componenteCatalogo,frame);
            centro.add(articoloList);
        }
*/

        this.add(centro, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(centro);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane,BorderLayout.CENTER);

    }
}

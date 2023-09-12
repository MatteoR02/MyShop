package Business;

import DAO.*;
import Model.*;

import java.io.File;
import java.util.ArrayList;

public class ArticoloBusiness {

    private static final IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();
    private static final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private static final IUtenteDAO utenteDAO = UtenteDAO.getInstance();
    private static final IFotoDAO fotoDAO = FotoDAO.getInstance();
    private static final IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
    private static final ICategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
    private static final IErogatoreDAO erogatoreDAO = ErogatoreDAO.getInstance();
    public enum TipoArticolo{PRODOTTO, PRODOTTO_COMPOSITO, SERVIZIO, NOT_ARTICLE}

    private static final Foto defaultFoto = fotoDAO.loadDefaultFoto();


    /**
     * Prende tutti gli articoli e li mette in sessione
     * @return un result contenente tutti gl iarticoli
     */
    public static ExecuteResult<Articolo> getAllArticoli(){
        ExecuteResult<Articolo> result = new ExecuteResult<>();
        ArrayList<Articolo> articoliUncheck = articoloDAO.loadAllProdotti(); //carica prodotti e prodotti compositi
        ArrayList<Servizio> serviziUncheck = articoloDAO.loadAllServizi();

        ArrayList<Servizio> servizi = new ArrayList<>();
        ArrayList<Articolo> articoli = new ArrayList<>();

        for (Servizio serv : serviziUncheck   ) {
            if (hasNoFoto(serv)){
                servizi.add((Servizio) setDefaultFoto(serv));
            } else {
                servizi.add(serv);
            }
        }
        for (Articolo art : articoliUncheck   ) {
            if (hasNoFoto(art)){
                articoli.add(setDefaultFoto(art));
            } else {
                articoli.add(art);
            }
        }

        articoli.addAll(servizi);
        result.setObject(articoli);
        result.setMessage(ExecuteResult.ResultStatement.OK.toString());
        if(isUndefined(result.getObjectFromArray(0).getNome())){
            result.removeFromArray(0);
        }
        result.execute(SessionManager.ALL_ARTICOLI);

        return null;
    }

    /**
     * Fornisce tutti i prodotti e prodotti compositi
     * @return un result contenente tutti i prodotti e prodotti compositi
     */
    public static ExecuteResult<Articolo> getAllProdotti(){
        ExecuteResult<Articolo> result = new ExecuteResult<>();
        ArrayList<Articolo> articoliUncheck = articoloDAO.loadAllProdotti(); //carica prodotti e prodotti compositi

        ArrayList<Articolo> articoli = new ArrayList<>();

        for (Articolo art : articoliUncheck   ) {
            if (hasNoFoto(art)){
                articoli.add(setDefaultFoto(art));
            } else {
                articoli.add(art);
            }
        }

        result.setObject(articoli);
        if(isUndefined(result.getObjectFromArray(0).getNome())){
            result.removeFromArray(0);
        }

        result.setResult(ExecuteResult.ResultStatement.OK);
        result.setMessage(ExecuteResult.ResultStatement.OK.toString());
        return result;
    }

    /**
     * Fornisce tutti gli articoli di un punto vendita
     * @param id del punto vendita
     * @return un result di tutti gli articoli
     */
    public static ExecuteResult<Articolo> getAllArticoliFromPV(int id){
        ExecuteResult<Articolo> result = new ExecuteResult<>();
        if(puntoVenditaDAO.isPuntoVendita(id)){

            ArrayList<Articolo> articoliUncheck = articoloDAO.loadAllArticoliFromPuntoVendita(id);
            ArrayList<Articolo> articoli = new ArrayList<>();

            for (Articolo art : articoliUncheck   ) {
                if (hasNoFoto(art)){
                    articoli.add(setDefaultFoto(art));
                } else {
                    articoli.add(art);
                }
            }

            result.setObject(articoli);
            if(isUndefined(result.getObjectFromArray(0).getNome())){
                result.removeFromArray(0);
            }
            result.setMessage("L'id inserito corrisponde ad un punto vendita.");
            result.setResult(ExecuteResult.ResultStatement.OK);
        } else{
            result.setResult(ExecuteResult.ResultStatement.WRONG_ACCESS);
            result.setMessage("L'id inserito non corrisponde ad un punto vendita");
            return result;
        }
        result.execute(SessionManager.ALL_ARTICOLI_PV);
        return result;
    }

    /**
     * Fornisce uno specifico articolo
     * @param id dell'articolo desiderato
     * @return un result contenente l'articolo specifico
     */
    public static ExecuteResult<Articolo> getArticolo(int id){
        ExecuteResult<Articolo> result = new ExecuteResult<>();
        if(articoloCheckType(id) == TipoArticolo.SERVIZIO){
            result.setSingleObject(articoloDAO.loadServizio(id));
            result.setMessage("Servizio caricato!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        }else if(articoloCheckType(id) == TipoArticolo.PRODOTTO){
            result.setSingleObject(articoloDAO.loadProdotto(id));
            result.setMessage("Prodotto!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        }
        if(articoloCheckType(id) == TipoArticolo.PRODOTTO_COMPOSITO){
            result.reset();
            result.setSingleObject(articoloDAO.loadProdottoComposito(id));
            result.setMessage("Prodotto composito caricato!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        }
        if(articoloCheckType(id) != TipoArticolo.SERVIZIO && articoloCheckType(id) != TipoArticolo.PRODOTTO && articoloCheckType(id) != TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Non è stato passato l'id di un articolo!");
        }
        if(isUndefined(result.getSingleObject().getNome())){
            result.reset();
            result.setMessage("Articolo selezionato: undefined!");
            result.setResult(ExecuteResult.ResultStatement.UNDEFINED);
        }
        return result;
    }

    /**
     * Fornisce uno specifico prodotto o prodotto composito
     * @param id del prodotto
     * @return un result contenente il prodotto specifico
     */
    public static ExecuteResult<IProdotto> getIProdotto(int id){
        ExecuteResult<IProdotto> result = new ExecuteResult<>();
        if(articoloCheckType(id) == TipoArticolo.SERVIZIO){
            result.setMessage("L'id fornito è un servizio");
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
        }else if(articoloCheckType(id) == TipoArticolo.PRODOTTO){
            result.setSingleObject(articoloDAO.loadProdotto(id));
            result.setMessage("Prodotto!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        }
        if(articoloCheckType(id) == TipoArticolo.PRODOTTO_COMPOSITO){
            result.reset();
            result.setSingleObject(articoloDAO.loadProdottoComposito(id));
            result.setMessage("Prodotto composito caricato!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        }
        if(articoloCheckType(id) != TipoArticolo.SERVIZIO && articoloCheckType(id) != TipoArticolo.PRODOTTO && articoloCheckType(id) != TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Non è stato passato l'id di un articolo!");
        }
        if(isUndefined(result.getSingleObject().getNome())){
            result.reset();
            result.setMessage("Articolo selezionato: undefined!");
            result.setResult(ExecuteResult.ResultStatement.UNDEFINED);
        }
        return result;
    }

    /**
     * Aggiorna l'articolo
     * @param articolo
     * @return
     */
    public static ExecuteResult<Boolean> updateArticolo(Articolo articolo){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        TipoArticolo check = articoloCheckType(articolo.getId());
        if(check == TipoArticolo.PRODOTTO){
            Prodotto p = (Prodotto) articolo;
            articoloDAO.updateProdotto(p);
            result.setMessage("prodotto aggiornato!");
        }
        if(check == TipoArticolo.SERVIZIO){
            Servizio s = (Servizio) articolo;
            articoloDAO.updateServizio(s);
            result.setMessage("Servizio aggiornato!");
        }
        if(check == TipoArticolo.PRODOTTO_COMPOSITO){
            ProdottoComposito p = (ProdottoComposito) articolo;
            articoloDAO.updateProdotto(p);
            result.setMessage("Servizio aggiornato!");
        }
        if(check != TipoArticolo.PRODOTTO && check != TipoArticolo.SERVIZIO && check != TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage(articolo.toString());
        }
        getAllArticoli();
        return result;
    }

    /**
     * Aggiorna l'articolo aggiungendoci foto
     * @param articolo
     * @param imgFiles file forniti da aggiungere all'articolo
     * @return
     */
    public static ExecuteResult<Boolean> updateArticolo(Articolo articolo, ArrayList<File> imgFiles){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        TipoArticolo check = articoloCheckType(articolo.getId());
        if(check == TipoArticolo.PRODOTTO){
            Prodotto p = (Prodotto) articolo;
            for (File file :imgFiles) {
                Foto foto = new Foto();
                foto.setImmagine(FotoBusiness.imgToBlob(file));
                FotoDAO.getInstance().addNewFotoToArticolo(foto, p.getId());
            }
            articoloDAO.updateProdotto(p);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("prodotto aggiornato!");
        }
        if(check == TipoArticolo.SERVIZIO){
            Servizio s = (Servizio) articolo;
            for (File file :imgFiles) {
                Foto foto = new Foto();
                foto.setImmagine(FotoBusiness.imgToBlob(file));
                FotoDAO.getInstance().addNewFotoToArticolo(foto, s.getId());
            }
            articoloDAO.updateServizio(s);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Servizio aggiornato!");
        }
        if(check == TipoArticolo.PRODOTTO_COMPOSITO){
            ProdottoComposito pc = (ProdottoComposito) articolo;
            for (File file :imgFiles) {
                Foto foto = new Foto();
                foto.setImmagine(FotoBusiness.imgToBlob(file));
                FotoDAO.getInstance().addNewFotoToArticolo(foto, pc.getId());
            }
            articoloDAO.updateProdotto(pc);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Servizio aggiornato!");
        }
        if(check != TipoArticolo.PRODOTTO && check != TipoArticolo.SERVIZIO && check != TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage(articolo.toString());
        }
        getAllArticoli();
        return result;
    }

    /**
     * Aggiunge un articolo
     * @param articolo
     * @param tipoArticolo
     * @return
     */
    public static ExecuteResult<Boolean> addArticolo(Articolo articolo, TipoArticolo tipoArticolo){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        if(tipoArticolo == TipoArticolo.PRODOTTO){
            Prodotto p = (Prodotto) articolo;
            articolo.setId(articoloDAO.addProdotto(p, true));
            result.setMessage("Prodotto aggiunto!");
        }
        if(tipoArticolo == TipoArticolo.SERVIZIO){
            Servizio s = (Servizio) articolo;
            articoloDAO.addServizio(s, true);
            result.setMessage("Servizio aggiunto!");
        }
        if(tipoArticolo == TipoArticolo.PRODOTTO_COMPOSITO){
            ProdottoComposito pc = (ProdottoComposito) articolo;
            articoloDAO.addProdotto(pc, true);
            result.setMessage("Prodotto composito aggiunto!");
        }
        if(tipoArticolo != TipoArticolo.PRODOTTO && tipoArticolo != TipoArticolo.SERVIZIO && tipoArticolo != TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage(articolo.toString());
        }
        getAllArticoli();
        return result;
    }

    /**
     * Aggiunge un articolo aggiungendoci foto
     * @param articolo
     * @param tipoArticolo
     * @param imgFiles
     * @param idPuntoVendita
     * @return
     */
    public static ExecuteResult<Boolean> addArticolo(Articolo articolo, TipoArticolo tipoArticolo, ArrayList<File> imgFiles, int idPuntoVendita){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        if(tipoArticolo == TipoArticolo.PRODOTTO){
            Prodotto p = (Prodotto) articolo;
            p.setImmagini(new ArrayList<Foto>());
            for (File f: imgFiles) {
                Foto foto = new Foto();
                foto.setImmagine(FotoBusiness.imgToBlob(f));
                p.getImmagini().add(foto);
            }
            int idProd = articoloDAO.addProdotto(p, true);
            puntoVenditaDAO.addArticoloToPuntoVendita(idProd, idPuntoVendita);
            //getAllArticoli();
            result.setMessage("prodotto aggiunto!");

        }
        if(tipoArticolo == TipoArticolo.SERVIZIO){
            Servizio s = (Servizio) articolo;
            s.setImmagini(new ArrayList<Foto>());
            for (File f: imgFiles) {
                Foto foto = new Foto();
                foto.setImmagine(FotoBusiness.imgToBlob(f));
                s.getImmagini().add(foto);
            }
            int idServ = articoloDAO.addServizio(s, true);
            puntoVenditaDAO.addArticoloToPuntoVendita(idServ, idPuntoVendita);
            result.setMessage("Servizio aggiunto!");
        }
        if(tipoArticolo == TipoArticolo.PRODOTTO_COMPOSITO){
            ProdottoComposito pc = (ProdottoComposito) articolo;
            pc.setImmagini(new ArrayList<Foto>());
            for (File f: imgFiles) {
                Foto foto = new Foto();
                foto.setImmagine(FotoBusiness.imgToBlob(f));
                pc.getImmagini().add(foto);
            }
            int idProdComp = articoloDAO.addProdotto(pc, true);
            puntoVenditaDAO.addArticoloToPuntoVendita(idProdComp,idPuntoVendita);
            result.setMessage("Prodotto composito aggiunto!");
        }
        if(tipoArticolo != TipoArticolo.PRODOTTO && tipoArticolo != TipoArticolo.SERVIZIO && tipoArticolo != TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage(articolo.toString());
        }
        getAllArticoli();
        return result;
    }

    /**
     * Elimina un articolo
     * @param idArticolo
     * @return
     */
    public static ExecuteResult<Boolean> removeArticolo(int idArticolo){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        TipoArticolo check = articoloCheckType(idArticolo);
        if(check == TipoArticolo.PRODOTTO || check == TipoArticolo.PRODOTTO_COMPOSITO || check == TipoArticolo.SERVIZIO){
            if(check == TipoArticolo.PRODOTTO || check == TipoArticolo.PRODOTTO_COMPOSITO){
                articoloDAO.removeProdotto(idArticolo);
                result.setMessage("prodotto rimosso dal catalogo!");
            }
            if(check == TipoArticolo.SERVIZIO){
                articoloDAO.removeServizio(idArticolo);
                result.setMessage("Servizio rimosso dal catalogo!");
            }

            //rimuoviamo l'articolo anche dalle liste d'acquisto NON ANCORA PAGATE
            ExecuteResult <Integer> result1 = ListaAcquistoBusiness.removeArticoloFromAllListe(idArticolo);
            result.setMessage(result.getMessage() + "\n" + result1.getMessage());
        }


        if(check != TipoArticolo.PRODOTTO && check != TipoArticolo.SERVIZIO && check != TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage("Id inserito non corrispondente ad un articolo!");
        }
        getAllArticoli();
        return result;
    }

    /**
     * Verifica se un articolo è stato acquistato da un cliente
     * @param idArticolo
     * @param idCliente
     * @return
     */
    public static ExecuteResult<Boolean> isArticoloBoughtFrom(int idArticolo, int idCliente){
        ExecuteResult<Boolean> result = new ExecuteResult<>();

        Cliente c = utenteDAO.loadCliente(idCliente);
        Articolo a = new Articolo();

        if (articoloCheckType(idArticolo) == TipoArticolo.PRODOTTO){
            a = articoloDAO.loadProdotto(idArticolo);
        } else if (articoloCheckType(idArticolo) == TipoArticolo.PRODOTTO_COMPOSITO){
            a = articoloDAO.loadProdottoComposito(idArticolo);
        } else if (articoloCheckType(idArticolo) == TipoArticolo.SERVIZIO){
            a = articoloDAO.loadServizio(idArticolo);
        } else {
            result.setSingleObject(false);
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("C'è stato un problema nel trovare l'articolo");
            return result;
        }

        ArrayList<ListaAcquisto> listeCliente = (ArrayList<ListaAcquisto>) c.getListeAcquisto();

        for (ListaAcquisto lista : listeCliente) {
            if (lista.getArticoli().containsKey(a) && lista.getStatoPagamento() == ListaAcquisto.StatoPagamentoType.PAGATO){
                result.setSingleObject(true);
                result.setResult(ExecuteResult.ResultStatement.OK);
                result.setMessage("L'articolo "+ a.getNome()+ " è stato acquistato dal cliente " + c.getUsername());
                return result;
            }
        }
        result.setSingleObject(false);
        result.setResult(ExecuteResult.ResultStatement.OK_WITH_WARNINGS);
        result.setMessage("L'articolo "+ a.getNome()+ " NON è stato acquistato dal cliente " + c.getUsername());
        return result;
    }

    /**
     * Prende un prodotto composito e fornisce i suoi sottoprodotti come articoli
     * @param prodottoComposito
     * @return i sottoprodotti come un arraylist di articoli
     */
    public static ArrayList<Articolo> sottoProdottiToArticoli(ProdottoComposito prodottoComposito){
        ArrayList<Articolo> articoliUncheck = new ArrayList<>();
        ArrayList<Articolo> articoli = new ArrayList<>();
        for (IProdotto iProd : prodottoComposito.getSottoProdotti()) {
            if (iProd instanceof Prodotto){
                articoliUncheck.add((Prodotto) iProd);
            } else if (iProd instanceof ProdottoComposito){
                articoliUncheck.add((ProdottoComposito) iProd);
            }
        }
        for (Articolo art : articoliUncheck   ) {
            if (hasNoFoto(art)) {
                articoli.add(setDefaultFoto(art));
            } else {
                articoli.add(art);
            }
        }
        return articoli;
    }

    /**
     * Fornisce tutti gli articoli di una particolare categoria
     * @param articoli
     * @param categoria
     * @return
     */
    public static ArrayList<Articolo> getArticoliOfCategoria(ArrayList<Articolo> articoli, Categoria categoria){
        ArrayList<Articolo> listArticoli = new ArrayList<>();
        for (Articolo art : articoli) {
            if (art.getCategoria().getId() == categoria.getId()){
                listArticoli.add(art);
            }
        }
        if (categoria.getId() == -1){
            return articoli;
        }
        return listArticoli;
    }

    /**
     * Fornisce tutte le categorie
     * @return
     */
    public static ExecuteResult<Categoria> getAllCategorie(){
        ExecuteResult<Categoria> result = new ExecuteResult<>();
        ArrayList<Categoria> categorie = (ArrayList<Categoria>) categoriaDAO.loadAllCategorie();
        categorie.removeIf(cat -> isUndefined(cat.getNome()));
        result.setObject(categorie);
        return result;
    }

    /**
     * Fornisce tutte le categorie di una particolare tipologia di categoria
     * @param tipologia
     * @return
     */
    public static ExecuteResult<Categoria> getAllCategorie(Categoria.TipoCategoria tipologia){
        ExecuteResult<Categoria> result = new ExecuteResult<>();
        ArrayList<Categoria> categorie = new ArrayList<>();
        switch (tipologia){
            case PRODOTTO -> categorie = (ArrayList<Categoria>) categoriaDAO.loadAllCategorieProdotto();
            case SERVIZIO -> categorie = (ArrayList<Categoria>) categoriaDAO.loadAllCategorieServizio();
        }
        categorie.removeIf(cat -> isUndefined(cat.getNome()));
        result.setObject(categorie);
        return result;
    }

    /**
     * Fornisce una categoria fittizia "Tutto" con id=-1
     * @return
     */
    public static Categoria createTuttoCategoria(){
        Categoria tutto = new Categoria();
        tutto.setId(-1);
        tutto.setNome("Tutte le categoria");
        return tutto;
    }

    /**
     * Crea una nuova categoria
     * @param categoria
     * @return
     */
    public static ExecuteResult<Boolean> creaCategoria(Categoria categoria){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        int rows = categoriaDAO.addCategoria(categoria);
        if (rows>0){
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Categoria creata con successo");
            result.setSingleObject(true);
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Errore nella creazione della categoria");
            result.setSingleObject(false);
        }
        return result;
    }

    /**
     * Crea un nuovo erogatore
     * @param erogatore
     * @return
     */
    public static ExecuteResult<Boolean> creaErogatore(Erogatore erogatore){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        int rows = erogatoreDAO.addErogatore(erogatore);
        if (rows>0){
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Erogatore creato con successo");
            result.setSingleObject(true);
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Errore nella creazione dell'erogatore");
            result.setSingleObject(false);
        }
        return result;
    }

    /**
     * Fornisce tutti gli erogatori
     * @return
     */
    public static ExecuteResult<Erogatore> getAllErogatori(){
        ExecuteResult<Erogatore> result = new ExecuteResult<>();
        ArrayList<Erogatore> erogatori = (ArrayList<Erogatore>) erogatoreDAO.loadAllErogatori();
        erogatori.removeIf(er -> isUndefined(er.getNome()));
        result.setObject(erogatori);
        return result;
    }


    /**
     * Controlla la tipologia di un articolo
     * @param idArticolo
     * @return
     */
    public static TipoArticolo articoloCheckType(int idArticolo){
        if(articoloDAO.isProdottoComposito(idArticolo)) return TipoArticolo.PRODOTTO_COMPOSITO;
        if(articoloDAO.isProdotto(idArticolo) || articoloDAO.isSottoProdotto(idArticolo)) return TipoArticolo.PRODOTTO;
        if(articoloDAO.isServizio(idArticolo)) return TipoArticolo.SERVIZIO;
        else return TipoArticolo.NOT_ARTICLE;
    }

    /**
     * Verifica se la stringa data è undefined
     * @param string
     * @return true se è undefined, false altrimenti
     */
    public static boolean isUndefined(String string){
        return string.equals("undefined");
    }

    /**
     * Fornisce il nome di una tipologia di articolo
     * @param tipoArticolo
     * @return
     */
    public static String tipoArticoloToString(TipoArticolo tipoArticolo){
        String tipo = "";
        switch (tipoArticolo){
            case PRODOTTO -> tipo = "Prodotto";
            case PRODOTTO_COMPOSITO -> tipo = "Prodotto composito";
            case SERVIZIO -> tipo = "Servizio";
        }
        return tipo;
    }

    /**
     * Controlla se un articolo ha foto
     * @param articolo
     * @return
     */
    public static boolean hasNoFoto(Articolo articolo){
        return articolo.getImmagini().isEmpty();
    }

    /**
     * Aggiunge una foto di default ad un articolo
     * @param articolo
     * @return
     */
    public static Articolo setDefaultFoto(Articolo articolo){

        articolo.getImmagini().add(defaultFoto);

        return articolo;
    }


}

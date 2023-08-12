package Business;

import DAO.*;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static Business.ArticoloBusiness.hasNoFoto;
import static Business.ArticoloBusiness.setDefaultFoto;

public class ListaAcquistoBusiness {

    private static final IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();
    private static final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private static final IUtenteDAO utenteDAO = UtenteDAO.getInstance();

    public static ExecuteResult<ListaAcquisto> getAllListe(){
        ExecuteResult<ListaAcquisto> result = new ExecuteResult<>();
        ArrayList<ListaAcquisto> liste = listaAcquistoDAO.loadAllListaAcquisto(); //carica prodotti e prodotti compositi
        articoloDAO.loadAllProdotti();
        articoloDAO.loadAllServizi();



        //result.setObject(liste);
        result.setMessage(ExecuteResult.ResultStatement.OK.toString());
        result.execute(SessionManager.ALL_ARTICOLI);

        return null;
    }

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
        result.execute(SessionManager.ALL_ARTICOLI);

        return null;
    }

    public static ExecuteResult<Integer> removeArticoloFromAllListe(int idElementToRemove){
        ArrayList<Cliente> clienti = utenteDAO.loadAllClienti();
        ExecuteResult<Integer> result = new ExecuteResult<>();
        result.setSingleObject(1);

        //scorro tutti i clienti
        for(Cliente cliente : clienti){
            //scorro tutte le liste d'acquisto
            for(ListaAcquisto lista : cliente.getListeAcquisto()){
                //cerco le liste d'acquisto non pagate (quelle pagate avranno come id del prodotto il prodotto "undefined")
                Iterator<Articolo> iterator = lista.getArticoli().keySet().iterator();
                //scorro tra gli articoli
                while(iterator.hasNext()){
                    Articolo temp = iterator.next();
                    if(temp.getId() == idElementToRemove){
                        if(!lista.isPagato()){
                            iterator.remove();
                            result.setSingleObject(result.getSingleObject() + 1);
                            result.setResult(ExecuteResult.ResultStatement.OK);
                        }else{
                            //se il prodotto è prresente nella lista già acquistata (al momento) verrà comunque rimosso
                            //lasciamo però aperto lo spazio ad una implementazione futura che disporrà altre soluzioni
                            iterator.remove();
                            result.setSingleObject(result.getSingleObject() + 1);
                            result.setResult(ExecuteResult.ResultStatement.OK);
                        }

                    }
                }
                updateLista(lista);
            }
        }
        result.setMessage("Articolo rimosso da " + result.getSingleObject() + " liste d'acquisto non pagate! ");
        return result;
    }

    public static ExecuteResult<Boolean> updateLista(ListaAcquisto listaAcquisto){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        if(listaAcquistoDAO.isListaAcquisto(listaAcquisto.getId())){
            //aggiorna la struttura della lista (nome, campo pagato)
            listaAcquistoDAO.updateListaAcquisto(listaAcquisto);

            result.setSingleObject(true);
            result.setMessage("Lista aggiornata correttamente!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        } else {
            result.setSingleObject(false);
            result.setMessage("Lista inserita non valida!");
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
        }
        return result;
    }

    public static ExecuteResult<Boolean> removeLista(int idLista){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        if(listaAcquistoDAO.isListaAcquisto(idLista)){
            listaAcquistoDAO.removeListaAcquisto(idLista);
            result.setMessage("Lista eliminata correttamente!");
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setSingleObject(true);
        }else {
            result.setMessage("Errore nella ricerca della lista!");
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
        }
        return result;
    }


    public static ExecuteResult<Boolean> addLista(String nomeLista, Cliente cliente){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        listaAcquistoDAO.addListaAcquisto(nomeLista,cliente.getId());
        result.setMessage("Lista aggiunta correttamente!");
        result.setResult(ExecuteResult.ResultStatement.OK);
        result.setSingleObject(true);

        return result;
    }

    public static ExecuteResult<Boolean> addOrRemoveToLista(Articolo articolo, ListaAcquisto listaAcquisto, int quantita, Cliente cliente){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
       // ListaAcquisto newList = listaAcquisto;
        ExecuteResult<ListaAcquisto> newLista = new ExecuteResult<>();
        newLista.setSingleObject(listaAcquisto);

        if(listaAcquisto.getId()>0){
            if (listaAcquistoDAO.isListaAcquisto(listaAcquisto.getId())){
                if(!listaAcquisto.isPagato()){
                    ArrayList<Integer> idArticoli = new ArrayList<>();
                    for (Articolo art : listaAcquisto.getArticoli().keySet()  ) {
                        idArticoli.add(art.getId());
                    }
                        if (!listaAcquisto.getArticoli().containsKey(articolo)) {
                            if(ArticoloBusiness.articoloCheckType(articolo.getId()) == ArticoloBusiness.TipoArticolo.SERVIZIO) quantita=1;
                            listaAcquisto.getArticoli().put(articolo,quantita);
                            result.setMessage("Articolo '" + articolo.getNome() + "' aggiunto alla lista '" + listaAcquisto.getNome() +"'");
                            result.setResult(ExecuteResult.ResultStatement.OK);
                            listaAcquistoDAO.insertArticoloInLista(listaAcquisto.getId(),articolo.getId(),quantita);
                        } else if (ArticoloBusiness.articoloCheckType(articolo.getId())== ArticoloBusiness.TipoArticolo.SERVIZIO){
                                if (quantita < 0 ){
                                    listaAcquistoDAO.removeArticoloInLista(listaAcquisto.getId(),articolo.getId());
                                    listaAcquisto.getArticoli().remove(articolo);
                                    result.setMessage("Il servizio è stato rimosso");
                                    result.setResult(ExecuteResult.ResultStatement.DATA_DELETED);
                                    result.setSingleObject(false);
                                } else {
                                    result.setMessage("La quantità del servizio è rimasta invariata");
                                    result.setResult(ExecuteResult.ResultStatement.OK_WITH_WARNINGS);
                                    result.setSingleObject(false);
                                }
                            } else {
                                if (quantita > 0 ){
                                    if (listaAcquisto.getArticoli().get(articolo)!=quantita) {
                                        listaAcquisto.getArticoli().replace(articolo, quantita);
                                        listaAcquistoDAO.updateArticoloInLista(listaAcquisto.getId(), articolo.getId(), quantita);
                                        result.setMessage("La quantità dell'articolo " + articolo.getNome() + " è stata aggiornata a: " + quantita + "");
                                        result.setResult(ExecuteResult.ResultStatement.OK);
                                        result.setSingleObject(false);
                                    } else {
                                        result.setMessage("La quantità dell'articolo è rimasta invariata");
                                        result.setResult(ExecuteResult.ResultStatement.OK_WITH_WARNINGS);
                                        result.setSingleObject(false);
                                    }
                                } else {
                                    listaAcquisto.getArticoli().remove(articolo);
                                    listaAcquistoDAO.removeArticoloInLista(listaAcquisto.getId(),articolo.getId());
                                    result.setResult(ExecuteResult.ResultStatement.DATA_DELETED);
                                    result.setMessage("L'articolo '"+articolo.getNome()+"' è stato rimosso dalla lista '"+listaAcquisto.getNome()+"'");
                                    result.setSingleObject(false);
                                }
                            }
                        newLista.setSingleObject(listaAcquisto);
                        newLista.execute(SessionManager.LISTE_CLIENTE);
                        updateLista(listaAcquisto);

                }  else {
                    result.setSingleObject(false);
                    result.setMessage("La lista d'acquisto essendo pagata, non può essere modificata nelle quantità!");
                    result.setResult(ExecuteResult.ResultStatement.WRONG_ACCESS);
                }

            } else {
                //se l'id non corrisponde a nessuna lista e non serve temporaneamente alla view, allora idLista contiene un valore sbagliato
                result.setSingleObject(false);
                result.setMessage("Lista d'acquisto non trovata!");
                result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            }
        }
        else{ //creo una lista temporanea
            HashMap<Articolo, Integer> newHash = new HashMap<>();
            newHash.put(articolo,quantita);
            //creo una nuova lista
            newLista.setSingleObject(new ListaAcquisto("Nuova Lista", ListaAcquisto.StatoPagamentoType.DA_PAGARE, null, newHash));
            addLista(newLista.getSingleObject().getNome(),cliente);
            newLista.execute(SessionManager.NEW_TEMP_LIST);
        }

        //result.setSingleObject(true);
        return result;

    }

    public static ExecuteResult<Boolean> checkDisponibilita(Prodotto prodotto, ListaAcquisto listaAcquisto){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        for (Articolo art:listaAcquisto.getArticoli().keySet()  ) {
            if(art instanceof Prodotto){
                if (prodotto.getId() == art.getId()){
                    result.setSingleObject(listaAcquisto.getArticoli().get(art) < prodotto.getCollocazione().getQuantita());
                    result.setResult(ExecuteResult.ResultStatement.OK);
                    result.setMessage("Prodotto disponibile");
                }
            }
        }
        return result;
    }

}

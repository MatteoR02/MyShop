package Business;

import DAO.*;
import Model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrenotazioneBusiness {

    private static final IPrenotazioneDAO prenotazioneDAO = PrenotazioneDAO.getInstance();
    private static final IUtenteDAO utenteDAO = UtenteDAO.getInstance();
    private static final IPuntoVenditaDAO puntovenditaDAO = PuntoVenditaDAO.getInstance();

    /**
     * Verifica se un articolo è stato già prenotato da un cliente
     * @param prod
     * @param idCliente
     * @return
     */
    public static Boolean isArticoloPrenotato(IProdotto prod, int idCliente){
        ArrayList<Prenotazione> prenotazioni = prenotazioneDAO.loadPrenotazioniOfCliente(idCliente);
        for (Prenotazione prenotazione : prenotazioni  ) {
            if (prenotazione.getStatoPrenotazione()== Prenotazione.StatoPrenotazione.IN_CORSO){
                if (prenotazione.getProdottiPrenotati().containsKey(prod)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Prenota dei prodotti
     * TODO sistemare data di arrivo
     * @param articoli
     * @return
     */
    public static ExecuteResult<Boolean> prenotaProdotti(Map<IProdotto, Integer> articoli){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        Cliente c = (Cliente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        PuntoVendita pv = (PuntoVendita) SessionManager.getSession().get(SessionManager.PUNTO_VENDITA);

        for (IProdotto prod : articoli.keySet() ) {
            if (isArticoloPrenotato(prod, c.getId())){
                result.setSingleObject(false);
                result.setResult(ExecuteResult.ResultStatement.OK_WITH_WARNINGS);
                result.setMessage("Il prodotto è stato già prenotato dal cliente");
                return result;
            }
        }

        Prenotazione prenotazione = new Prenotazione(articoli, c.getId(), pv.getId(), Date.valueOf(LocalDate.now()) ,Date.valueOf((LocalDate.now()).plusDays(7)) , Prenotazione.StatoPrenotazione.IN_CORSO  );

        int rows = prenotazioneDAO.addPrenotazione(prenotazione);

        if (rows > 0){
            result.setSingleObject(true);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Prenotazione effettuata con successo");
        } else {
            result.setSingleObject(false);
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("C'è stato un errore con la prenotazione");
        }
        return result;
    }

    /**
     * Fornisce tutte le prenotazioni di un utente
     * @param idUtente
     * @return prenotazioni del cliente se l'id fornito appartiene ad un cliente, prenotazioni del punto vendita assegnato se l'id fornito appartiene ad un manager
     */
    public static ExecuteResult<Prenotazione> getAllPrenotazioni(int idUtente){
        ExecuteResult<Prenotazione> result = new ExecuteResult<>();
        String username = UtenteBusiness.getUsernameByID(idUtente).getSingleObject();
        UtenteBusiness.TipoUtente tipoUtente = UtenteBusiness.checkRole(username);
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();

        if (tipoUtente == UtenteBusiness.TipoUtente.CLIENTE){
             prenotazioni = prenotazioneDAO.loadPrenotazioniOfCliente(idUtente);
             result.setResult(ExecuteResult.ResultStatement.OK);
             result.setObject(prenotazioni);
        } else if (tipoUtente == UtenteBusiness.TipoUtente.MANAGER){
            Manager m = utenteDAO.loadManager(username);
            prenotazioni = prenotazioneDAO.loadPrenotazioniOfPV(m.getIdPuntoVendita());
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setObject(prenotazioni);
        }
        return result;
    }

    /**
     * Elimina una prenotazione
     * @param idPren
     * @return
     */
    public static ExecuteResult<Boolean> removePrenotazione(int idPren){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        int rows = prenotazioneDAO.removePrenotazione(idPren);
        if (rows>0){
            result.setMessage("Prenotazione eliminata correttamente!");
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setSingleObject(true);
        }else {
            result.setMessage("Errore nella eliminazione della prenotazione!");
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
        }
        return result;
    }

    /**
     * Cambia lo stato di una prenotazione
     * @param idPren
     * @param stato
     * @return
     */
    public static ExecuteResult<Boolean> changePrenotazioneStatus(int idPren, Prenotazione.StatoPrenotazione stato){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        Prenotazione prenotazione = prenotazioneDAO.loadPrenotazione(idPren);
            if (!(prenotazione.getStatoPrenotazione() == stato)){
                prenotazioneDAO.changePrenotazioneStatus(idPren, stato);
                result.setSingleObject(true);
                result.setResult(ExecuteResult.ResultStatement.OK);
                } else{
                result.setSingleObject(false);
                result.setResult(ExecuteResult.ResultStatement.OK_WITH_WARNINGS);
            }
        return result;
    }

    /**
     * Aggiorna una prenotazione
     * @param prenotazione
     * @return
     */
    public static ExecuteResult<Boolean> updatePrenotazione(Prenotazione prenotazione){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        int rows = prenotazioneDAO.updatePrenotazione(prenotazione);
        if (rows>0){
            result.setSingleObject(true);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Prenotazione aggiornata correttamente");
        } else {
            result.setSingleObject(false);
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
        }
        return result;
    }

    /**
     * Aggiorna la quantità di un prodotto all'interno di una prenotazione
     * @param prodotto
     * @param prenotazione
     * @param quantita
     * @param cliente
     * @return
     */
    public static ExecuteResult<Boolean> addOrRemoveToPrenotazione(Articolo prodotto, Prenotazione prenotazione, int quantita, Cliente cliente){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        // ListaAcquisto newList = listaAcquisto;
        ExecuteResult<Prenotazione> newPrenotazione = new ExecuteResult<>();
        newPrenotazione.setSingleObject(prenotazione);

        if(prenotazione.getId()>0) {
            if (prenotazioneDAO.isPrenotazione(prenotazione.getId())) {
                if (prenotazione.getStatoPrenotazione() == Prenotazione.StatoPrenotazione.IN_CORSO) {
                    ArrayList<Integer> idArticoli = new ArrayList<>();
                    for (IProdotto prod : prenotazione.getProdottiPrenotati().keySet()) {
                        idArticoli.add(prod.getId());
                    }
                    if (!prenotazione.getProdottiPrenotati().containsKey(prodotto)) {
                        prenotazione.getProdottiPrenotati().put((IProdotto) prodotto, quantita);
                        result.setMessage("Prodotto '" + prodotto.getNome() + "' aggiunto alla prenotazione '" + prodotto.getNome() + "'");
                        result.setResult(ExecuteResult.ResultStatement.OK);
                        prenotazioneDAO.updatePrenotazione(prenotazione);
                    } else {
                        if (quantita > 0) {
                            if (prenotazione.getProdottiPrenotati().get(prodotto) != quantita) {
                                prenotazione.getProdottiPrenotati().replace((IProdotto) prodotto, quantita);
                                prenotazioneDAO.updatePrenotazione(prenotazione);
                                result.setMessage("La quantità del prodotto " + prodotto.getNome() + " è stata aggiornata a: " + quantita + "");
                                result.setResult(ExecuteResult.ResultStatement.OK);
                                result.setSingleObject(false);
                            } else {
                                result.setMessage("La quantità dell prodotto è rimasta invariata");
                                result.setResult(ExecuteResult.ResultStatement.OK_WITH_WARNINGS);
                                result.setSingleObject(false);
                            }
                        } else {
                            prenotazione.getProdottiPrenotati().remove(prodotto);
                            prenotazioneDAO.removeProdottoFromPrenotazione(prenotazione.getId(), prodotto.getId());
                            result.setResult(ExecuteResult.ResultStatement.DATA_DELETED);
                            result.setMessage("Il prodotto '" + prodotto.getNome() + "' è stato rimosso dalla prenotazione");
                            result.setSingleObject(false);
                        }
                    }
                    newPrenotazione.setSingleObject(prenotazione);
                    newPrenotazione.execute(SessionManager.LISTE_CLIENTE);
                    updatePrenotazione(prenotazione);

                } else {
                    result.setSingleObject(false);
                    result.setMessage("La prenotazione essendo conclusa, non può essere modificata nelle quantità!");
                    result.setResult(ExecuteResult.ResultStatement.WRONG_ACCESS);
                }
            } else {
                //se l'id non corrisponde a nessuna prenotazione e non serve temporaneamente alla view, allora idPrenotazione contiene un valore sbagliato
                result.setSingleObject(false);
                result.setMessage("Prenotazione non trovata!");
                result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            }
        }
        return result;

    }
}

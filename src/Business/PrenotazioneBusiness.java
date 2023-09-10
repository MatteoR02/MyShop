package Business;

import DAO.*;
import Model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class PrenotazioneBusiness {

    private static final IPrenotazioneDAO prenotazioneDAO = PrenotazioneDAO.getInstance();
    private static final IUtenteDAO utenteDAO = UtenteDAO.getInstance();
    private static final IPuntoVenditaDAO puntovenditaDAO = PuntoVenditaDAO.getInstance();

    public static Boolean isArticoloPrenotato(IProdotto prod, int idCliente){
        ArrayList<Prenotazione> prenotazioni = prenotazioneDAO.loadPrenotazioniOfCliente(idCliente);
        for (Prenotazione prenotazione : prenotazioni  ) {
            if (prenotazione.getProdottiPrenotati().containsKey(prod)){
                return true;
            }
        }
        return false;
    }

    public static ExecuteResult<Boolean> prenotaArticoli(Map<IProdotto, Integer> articoli){
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
        //TODO sistemare data di arrivo

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
}

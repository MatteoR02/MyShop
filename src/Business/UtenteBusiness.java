package Business;

import DAO.*;
import Model.*;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Map;

public class UtenteBusiness {
    private static UtenteBusiness instance;
    private static final IUtenteDAO utenteDAO = UtenteDAO.getInstance();
    private static final IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();
    private static final IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
    private static final IPrenotazioneDAO prenotazioneDAO = PrenotazioneDAO.getInstance();
    private static final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();

    public enum TipoUtente{ADMIN, MANAGER, CLIENTE}

    public static synchronized UtenteBusiness getInstance(){
        if (instance == null) {
            instance = new UtenteBusiness();
        }
        return instance;
    }

    public LoginResult login(String username, String password){

        LoginResult result = new LoginResult();

        // 1. controllare se l'utente esiste
        boolean userExists = utenteDAO.userExists(username);
        if(!userExists) {
            result.setResult(LoginResult.Result.USER_NOT_FOUND);
            result.setMessage("Username inserito non esiste");
            return result;
        }

        // 2. controllare se username e password sono ok
        boolean credentialsOk = utenteDAO.checkCredentials(username, password);
        if(!credentialsOk){
            result.setResult(LoginResult.Result.WRONG_PASSWORD);
            result.setMessage("Password inserita non è corretta");
            return result;
        }

        // 3. che tipo di utente e' (cliente/manager/amministratore)
        TipoUtente tipoUtente = checkRole(username);

        boolean isBlocked = utenteDAO.isBlocked(username);
        if (isBlocked){
            result.setResult(LoginResult.Result.USER_BLOCKED);
            result.setMessage("Questo utente è bloccato");
            return result;
        }


        // 4. caricare oggetto utente (a seconda della tipologia)
        if(tipoUtente==TipoUtente.CLIENTE){
            Cliente c = utenteDAO.loadCliente(username);
            SessionManager.getSession().put(SessionManager.LOGGED_USER, c);
            result.setMessage("Benvenuto " + c.getUsername()+ "!");

        } else if (tipoUtente==TipoUtente.MANAGER) {
            Manager m = utenteDAO.loadManager(username);
            SessionManager.getSession().put(SessionManager.LOGGED_USER,m);
            result.setMessage("Benvenuto " + m.getUsername()+ "!");

        } else if (tipoUtente==TipoUtente.ADMIN) {
            Admin a = utenteDAO.loadAdmin(username);
            SessionManager.getSession().put(SessionManager.LOGGED_USER,a);
            result.setMessage("Benvenuto " + a.getUsername()+ "!");
        }

        result.setResult(LoginResult.Result.LOGIN_OK);

        if(!((SessionManager.getSession().get(SessionManager.CATALOGO_VIEW))==null)){
            SessionManager.getSession().remove(SessionManager.CATALOGO_VIEW);
        }


        return result;
    }

    public boolean bloccaUtente(String username){
        boolean flag = false;
        if (checkRole(username)==TipoUtente.CLIENTE){
            if (utenteDAO.loadCliente(username).getStato()== Cliente.StatoUtenteType.ABILITATO){
                utenteDAO.blockCliente(username);
                flag = true;
            }
        } return flag;
    }


    public static boolean uploadFoto(File img, int idArticolo){
        try {
            Foto foto = new Foto();
            foto.setImmagine(ArticoloBusiness.imgToBlob(img));
            FotoDAO.getInstance().addFotoToArticolo(foto,idArticolo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ExecuteResult<ListaAcquisto> getLista(int idLista){
        ExecuteResult<ListaAcquisto> result = new ExecuteResult<>();
        if (listaAcquistoDAO.isListaAcquisto(idLista)){
            result.setSingleObject(listaAcquistoDAO.loadListaAcquisto(idLista));
            result.setMessage("Lista d'acquisto caricata con successo");
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.execute(SessionManager.LISTE_CLIENTE);
        } else {
            result.setMessage("L'id passato non corrisponde ad una lista d'acquisto");
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
        }
       return result;
    }

    public static ExecuteResult<ListaAcquisto> getListeOfCliente(Cliente cliente){
        ExecuteResult<ListaAcquisto> result = new ExecuteResult<>();

        if (checkRole(cliente.getUsername())==TipoUtente.CLIENTE) {
            result.setObject(listaAcquistoDAO.getListeOfCliente(cliente.getId()));
            result.setMessage("Liste caricate correttamente!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        } else{
            result.setMessage("Utente inserito non valido!");
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
        }
        result.execute(SessionManager.LISTE_CLIENTE);
        return result;

    }
    public static ExecuteResult<Cliente> getAllClientiOfPV(int id){
        ExecuteResult<Cliente> result = new ExecuteResult<>();
        if(puntoVenditaDAO.isPuntoVendita(id)){

            ArrayList<Cliente> clienti = utenteDAO.loadAllClientiOfPV(id);

            result.setObject(clienti);
            if(ArticoloBusiness.isUndefined(result.getObjectFromArray(0).getUsername())){
                result.removeFromArray(0);
            }
            result.setMessage("L'id inserito corrisponde ad un punto vendita.");
            result.setResult(ExecuteResult.ResultStatement.OK);
        } else{
            result.setResult(ExecuteResult.ResultStatement.WRONG_ACCESS);
            result.setMessage("L'id inserito non corrisponde ad un punto vendita");
            return result;
        }

        //ArrayList<Articolo> articoli = result.getObject();
       /* for (int i = 0; i < articoli.size(); i++) {
            if(articoloCheckType(articoli.get(i).getId()) != TipoArticolo.PRODOTTO_COMPOSITO && articoloCheckType(articoli.get(i).getId()) != TipoArticolo.SERVIZIO){
                ExecuteResult<Foto> res = FotoBusiness.loadSingleFoto(articoli.get(i).getId(), FotoDAO.ID_ARTICOLO);
                ArrayList<Foto> photos = res.getObject();
                ((Prodotto)articoli.get(i)).setImmagini(photos);
                result.setMessage(result.getMessage() + res.getMessage());
            }
        }*/

        //result.setObject(articoli);

        result.execute(SessionManager.ALL_CLIENTI_PV);
        return result;
    }

    public static boolean bloccaUtente(int idCliente){
        boolean flag = false;
        Cliente cliente = utenteDAO.loadCliente(utenteDAO.findUsernameByID(idCliente));
        if(checkRole(cliente.getUsername()) == TipoUtente.CLIENTE){
            if (cliente.getStato() == Cliente.StatoUtenteType.ABILITATO){
                if(!ArticoloBusiness.isUndefined(cliente.getUsername())){
                    utenteDAO.blockCliente(cliente.getUsername());
                    flag = true;
                }
            } else{
                //utente già bloccato
                flag = false;
            }
        }

        return flag;
    }

    public static boolean changeClienteStatus(int idCliente, Cliente.StatoUtenteType statoCliente){
        boolean flag = false;
        Cliente cliente = utenteDAO.loadCliente(utenteDAO.findUsernameByID(idCliente));
        if(checkRole(cliente.getUsername()) == TipoUtente.CLIENTE){
            if (!(cliente.getStato() == statoCliente)){
                if(!ArticoloBusiness.isUndefined(cliente.getUsername())){
                    utenteDAO.changeClienteStatus(cliente.getUsername(), statoCliente);
                    flag = true;
                }
            } else{
                //utente già bloccato
                flag = false;
            }
        }

        return flag;
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

        Prenotazione prenotazione = new Prenotazione(articoli, c.getId(), 2, Date.valueOf(LocalDate.now()) ,Date.valueOf((LocalDate.now()).plusDays(7)) , Prenotazione.StatoPrenotazione.IN_CORSO  );
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

    public static ExecuteResult<String> getClienteByID(int idCliente){
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        String username = utenteDAO.findUsernameByID(idCliente);
        if (checkRole(username) == TipoUtente.CLIENTE){
            executeResult.setSingleObject(username);
            executeResult.setResult(ExecuteResult.ResultStatement.OK);
        } else {
            executeResult.setResult(ExecuteResult.ResultStatement.NOT_OK);
        }
        return executeResult;
    }

    public static Boolean isArticoloPrenotato(IProdotto prod, int idCliente){
        ArrayList<Prenotazione> prenotazioni = prenotazioneDAO.loadPrenotazioniOfCliente(idCliente);
        for (Prenotazione prenotazione : prenotazioni  ) {
            if (prenotazione.getProdottiPrenotati().containsKey(prod)){
                return true;
            }
        }
        return false;
    }


    public static TipoUtente checkRole(String username){
        if (utenteDAO.isAdmin(username)) {
            return TipoUtente.ADMIN;
        }
        else if(utenteDAO.isManager(username)) {
            return TipoUtente.MANAGER;
        }
        else if(utenteDAO.isCliente(username)) {
            return TipoUtente.CLIENTE;
        }
        return null;
    }

    public static boolean closeSession(){
        SessionManager.getSession().remove(SessionManager.LOGGED_USER);
        SessionManager.getSession().remove(SessionManager.LISTE_CLIENTE);
        SessionManager.getSession().remove(SessionManager.CATALOGO_VIEW);
        return SessionManager.getSession().isEmpty();
    }

}

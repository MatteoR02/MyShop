package Business;

import Business.Factory.Notifica;
import Business.Factory.NotificationFactory;
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

    /**
     * Effettua il login avendo fornito le credenziali e mette in sessione l'utente loggato
     * @param username
     * @param password
     * @return
     */
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
            SessionManager.getSession().put(SessionManager.PUNTO_VENDITA, PuntoVenditaBusiness.getPV(c.getIdPuntoVendita()).getSingleObject());
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

    /**
     * Registra un cliente fornendo tutti i dati e lo mette in sessione
     * @param cliente
     * @return
     */
    public static RegisterResult registerCliente(Cliente cliente){
        RegisterResult result = new RegisterResult();

        boolean userExists = utenteDAO.userExists(cliente.getUsername());
        if(userExists) {
            result.setResult(RegisterResult.Result.USERNAME_TAKEN);
            result.setMessage("Username già preso");
            return result;
        }

        boolean userEmailExists = utenteDAO.userEmailExists(cliente.getUsername());
        if(userEmailExists) {
            result.setResult(RegisterResult.Result.EMAIL_TAKEN);
            result.setMessage("Email già registrata");
            return result;
        }

        int rows = utenteDAO.addCliente(cliente);
        if (rows>0){
            result.setResult(RegisterResult.Result.OK);
            result.setMessage("Cliente registrato con successo");
            SessionManager.getSession().put(SessionManager.LOGGED_USER,cliente);
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
            foto.setImmagine(FotoBusiness.imgToBlob(img));
            FotoDAO.getInstance().addFotoToArticolo(foto,idArticolo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Fornisce tutte le liste d'acquisto di un cliente e le mette in sessione
     * @param cliente
     * @return
     */
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

    /**
     * Fornisce tutti i clienti registrati ad uno specifico punto vendita
     * @param id
     * @return
     */
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

    /**
     * Cambia lo stato di un cliente e manda una notifica
     * @param idCliente
     * @param statoCliente
     * @return
     */
    public static boolean changeClienteStatus(int idCliente, Cliente.StatoUtenteType statoCliente){
        boolean flag = false;
        Cliente cliente = utenteDAO.loadCliente(utenteDAO.findUsernameByID(idCliente));
        NotificationFactory factory = new NotificationFactory();
        Notifica n = factory.getCanaleNotifica(cliente.getCanalePreferito());

        if(checkRole(cliente.getUsername()) == TipoUtente.CLIENTE){
            if (!(cliente.getStato() == statoCliente)){
                if(!ArticoloBusiness.isUndefined(cliente.getUsername())){
                    utenteDAO.changeClienteStatus(cliente.getUsername(), statoCliente);
                    flag = true;
                    if (statoCliente== Cliente.StatoUtenteType.BLOCCATO){
                        n.setCliente(cliente);
                        n.setTitolo("Cliente bloccato su myshop");
                        n.setTesto("Gentile cliente,<br> la informiamo che il suo account è stato bloccato sulla piattaforma MyShop. <br> Contattare l'assistenza per ulteriori informazioni");
                        n.inviaNotifica();
                    } else if (statoCliente == Cliente.StatoUtenteType.ABILITATO){
                        n.setTitolo("Cliente abilitato su myshop");
                        n.setTesto("Gentile cliente,<br> la informiamo che il suo account è stato abilitato sulla piattaforma MyShop.");
                        n.inviaNotifica();
                    }
                }
            } else{
                //utente già bloccato
                flag = false;
            }
        }

        return flag;
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

    /**
     * Fornisce l'username di un utente passando il suo id
     * @param idUtente
     * @return username utente
     */
    public static ExecuteResult<String> getUsernameByID(int idUtente){
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        String username = utenteDAO.findUsernameByID(idUtente);
        executeResult.setSingleObject(username);
        executeResult.setResult(ExecuteResult.ResultStatement.OK);
        return executeResult;
    }

    /**
     * Controlla il ruolo di un utente
     * @param username
     * @return tipo dell'utente
     */
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

    /**
     * Chiude la sessione per effettuare il logout
     * @return true se la sessione è vuota
     */
    public static boolean closeSession(){
        SessionManager.getSession().remove(SessionManager.LOGGED_USER);
        SessionManager.getSession().remove(SessionManager.LISTE_CLIENTE);
        SessionManager.getSession().remove(SessionManager.CATALOGO_VIEW);
        SessionManager.getSession().remove(SessionManager.PUNTO_VENDITA);
        SessionManager.getSession().remove(SessionManager.ALL_CLIENTI_PV);
        return SessionManager.getSession().isEmpty();
    }

}

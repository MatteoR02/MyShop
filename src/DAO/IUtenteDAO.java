package DAO;

import Model.*;

import java.util.ArrayList;

public interface IUtenteDAO {
    Utente loadUtente(String username);
    ArrayList<Utente> loadAllUtenti();
    int addUtente(Utente utente);
    int removeUtente(String username);
    int updateUtente(Utente utente);
    boolean userExists(String username);

    boolean isCliente(String username);
    boolean isManager(String username);
    boolean isAdmin(String username);

    Cliente loadCliente(String username);
    Cliente loadCliente(int idCliente);
    ArrayList<Cliente> loadAllClienti();
    ArrayList<Cliente> loadAllClientiOfPV(int idPV);
    int addCliente(Cliente cliente);
    int removeCliente(String username);
    int updateCliente(Cliente cliente);
    boolean checkCredentials(String username, String password);
    int blockCliente(String username);
    int unlockCliente(String username);
    boolean isBlocked(String username);
    int changeClienteStatus(String username, Cliente.StatoUtenteType stato);

    Manager loadManager(String username);
    Manager loadManager(int idManager);
    ArrayList<Manager> loadAllManager();
    int addManager(Manager manager);
    int removeManager(String username);
    int updateManager(Manager manager);
    int assignManager(int idManager, int idPuntoVendita);

    Admin loadAdmin(String username);
    Admin loadAdmin(int idAdmin);
    ArrayList<Admin> loadAllAdmin();
    int addAdmin(Admin admin);
    int removeAdmin(String username);
    int updateAdmin(Admin admin);

    int findIdByUsername(String username);
    String findUsernameByID(int id);

}

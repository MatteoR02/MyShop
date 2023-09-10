package Business;

import java.util.HashMap;

public class SessionManager {

    public static final String LOGGED_USER = "logged_user";
    public static final String PUNTO_VENDITA = "punto_vendita";
    public static final String ALL_ARTICOLI = "all_articoli";
    public static final String ALL_ARTICOLI_PV = "all_articoli_pv";
    public static final String LISTE_CLIENTE = "liste_cliente";
    public static final String NEW_TEMP_LIST = "new_temp_list";
    public static final String ALL_CLIENTI_PV = "all_clienti";
    public static final String CATALOGO_VIEW = "catalogo_view";

    private static HashMap<String, Object> session = new HashMap<>();

    public static HashMap getSession(){
        return session;
    }
}

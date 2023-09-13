package View;

import Business.*;
import Model.*;
import View.AdminCreazione.*;
import View.Catalogo.CatalogoPanel;
import View.Catalogo.InfoArticoloPanel;
import View.Decorator.*;
import View.Decorator.Menu;
import View.ListaClienti.ClientiTablePanel;
import View.ListeAcquisto.ListeAcquistoPanel;
import View.Main.AdminMainPanel;
import View.Main.ClienteMainPanel;
import View.Main.GuestMainPanel;
import View.Main.ManagerMainPanel;
import View.Prenotazioni.PrenotazioniClientePanel;
import View.Prenotazioni.PrenotazioniManagerPanel;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Business.SessionManager.LOGGED_USER;

public class MainPage extends JFrame {

    private JPanel nord = new JPanel();
    private JPanel centro = new JPanel();
    private JPanel est = new JPanel();
    private JPanel ovest = new JPanel();
    private JPanel sud = new JPanel();
    private JPanel main = new JPanel();

    public enum PaginaCorrente{MAIN, LOGIN, REGISTER, CATALOGO, SOTTOPRODOTTI, LISTE, ARTICOLO, MENU_CREAZIONE, PROFILO}

    private PaginaCorrente paginaCorrente;

    public MainPage() {
        super("MyShop");
        this.setSize(1000,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);



        ImageIcon icon = new ImageIcon("resources/appIcon.png");
        Image iconImage = icon.getImage();
        this.setIconImage(iconImage);

        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());

        Color sfondo = Color.WHITE;
        nord.setBackground(sfondo);
        centro.setBackground(sfondo);

        Menu bottomMenu = new BottomMenu(this);

        mostraMain();

        sud.setLayout(new FlowLayout(FlowLayout.RIGHT));
        for (JButton btn : bottomMenu.getPulsanti()) {
            btn.setFocusPainted(false);
            sud.add(btn);
        }


        JLabel titolo = new JLabel("MyShop");
        titolo.setFont(new Font("Calibri", Font.BOLD, 50));
        nord.add(titolo);

        c.add(nord, BorderLayout.NORTH);
        c.add(centro, BorderLayout.CENTER);
        c.add(est, BorderLayout.EAST);
        c.add(ovest, BorderLayout.WEST);
        c.add(sud, BorderLayout.SOUTH);

        JPanel main = centro;


        this.setVisible(true);
    }

    public void mostraCatalogo(ArrayList<Articolo> articoli, boolean flag){
        centro.removeAll();
        centro.setLayout(new BorderLayout());

        Utente u = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        ArrayList<Articolo> articoliPV = new ArrayList<>();

        if((SessionManager.getSession().get(SessionManager.ALL_ARTICOLI))==null){
            ArticoloBusiness.getAllArticoli();
        }

        paginaCorrente = PaginaCorrente.CATALOGO;

        if (flag){
            CatalogoPanel catalogoSelectedCatPanel = new CatalogoPanel(articoli, this, false);
            centro.add(catalogoSelectedCatPanel);
        } else {
            CatalogoPanel catalogoPanel = new CatalogoPanel((ArrayList<Articolo>) SessionManager.getSession().get(SessionManager.ALL_ARTICOLI),this, false);
            centro.add(catalogoPanel);
        }
        repaint();
        validate();
    }

    public void mostraSottoProdotti(ProdottoComposito prodottoComposito){
        centro.removeAll();
        centro.setLayout(new BorderLayout());

        ArrayList<Articolo> sottoProdotti = ArticoloBusiness.sottoProdottiToArticoli(prodottoComposito);

        paginaCorrente = PaginaCorrente.SOTTOPRODOTTI;
        CatalogoPanel catalogoPanel = new CatalogoPanel(sottoProdotti,this, true);
        centro.add(catalogoPanel);
        repaint();
        validate();
    }


    public void mostraLogin(){
        centro.removeAll();
        centro.setLayout(new GridBagLayout());
        centro.add(new LoginPanel(this));
        repaint();
        validate();
    }

    public void mostraRegister(){
        centro.removeAll();
        centro.setLayout(new GridBagLayout());
        centro.add(new RegisterPanel(this));
        repaint();
        validate();
    }

    public void mostraMain(){
        centro.removeAll();
        centro.setLayout(new GridBagLayout());

        paginaCorrente = PaginaCorrente.MAIN;

        Utente u = (Utente) SessionManager.getSession().get(LOGGED_USER);
        if (u instanceof Cliente){
            centro.add(new ClienteMainPanel(this));
        } else if(u instanceof Manager){
            centro.add(new ManagerMainPanel(this));
        } else if (u instanceof Admin){
            centro.add(new AdminMainPanel(this));
        } else {
            centro.add(new GuestMainPanel(this));
        }
        repaint();
        validate();
    }

    public void mostraProfilo(){
        paginaCorrente = PaginaCorrente.PROFILO;
        centro.removeAll();
        centro.add(new ProfiloPanel(this));
        repaint();
        validate();
    }

    public void mostraClientiTable(){
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        Manager m = (Manager) SessionManager.getSession().get(LOGGED_USER);
        UtenteBusiness.getAllClientiOfPV(m.getIdPuntoVendita());
        centro.add(new ClientiTablePanel((ArrayList<Cliente>) SessionManager.getSession().get(SessionManager.ALL_CLIENTI_PV), this));
        repaint();
        validate();
    }

    public void mostraListe(){
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        Cliente c = (Cliente) SessionManager.getSession().get(LOGGED_USER);
        UtenteBusiness.getListeOfCliente(c);
        centro.add(new ListeAcquistoPanel(this, (ArrayList<ListaAcquisto>) SessionManager.getSession().get(SessionManager.LISTE_CLIENTE)), BorderLayout.CENTER);
        repaint();
        validate();
    }

    public void mostraArticolo(ComponenteCatalogo comp, boolean ordinato, ArrayList<Recensione> recensioni){
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new InfoArticoloPanel(this, comp, ordinato, recensioni));
        paginaCorrente = PaginaCorrente.ARTICOLO;
        repaint();
        validate();
    }

    public void mostraPrenotazioni(){
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        Utente u = (Utente) SessionManager.getSession().get(LOGGED_USER);
        if (u instanceof Cliente c){
            centro.add(new PrenotazioniClientePanel(this, PrenotazioneBusiness.getAllPrenotazioni(c.getId()).getObject()));
        } else if (u instanceof Manager m){
            centro.add(new PrenotazioniManagerPanel(this, PrenotazioneBusiness.getAllPrenotazioni(m.getId()).getObject()));
        }
        repaint();
        validate();
    }

    public void mostraCreaArticolo(){
        paginaCorrente=PaginaCorrente.CATALOGO;
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new CreaArticoloPanel(this));
        repaint();
        validate();
    }

    public void mostraCreaProdotto(PuntoVendita pv, boolean modifica, ComponenteCatalogo comp){
        paginaCorrente = PaginaCorrente.MENU_CREAZIONE;
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        if (modifica){
            paginaCorrente = PaginaCorrente.CATALOGO;
            centro.add(new CreaProdottoPanel(this, pv, true, comp));
        } else {
            centro.add(new CreaProdottoPanel(this, pv, false, null));
        }

        repaint();
        validate();
    }

    public void mostraCreaProdottoComposito(PuntoVendita pv, boolean modifica, ComponenteCatalogo comp){
        paginaCorrente = PaginaCorrente.MENU_CREAZIONE;
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new CreaProdottoCompositoPanel(this, pv, modifica, comp));
        repaint();
        validate();
    }

    public void mostraCreaServizio(PuntoVendita pv, boolean modifica, ComponenteCatalogo comp){
        paginaCorrente = PaginaCorrente.MENU_CREAZIONE;
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new CreaServizioPanel(this,pv,modifica, comp));
        repaint();
        validate();
    }

    public void mostraCreaCategoria(){
        paginaCorrente = PaginaCorrente.MENU_CREAZIONE;
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new CreaCategoriaPanel(this));
        repaint();
        validate();
    }

    public void mostraCreaErogatore(){
        paginaCorrente = PaginaCorrente.MENU_CREAZIONE;
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new CreaErogatorePanel(this));
        repaint();
        validate();
    }

    public void mostraCreaPV(){
        paginaCorrente = PaginaCorrente.CATALOGO;
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new CreaPuntoVenditaPanel(this));
        repaint();
        validate();
    }

    public void mostraCreaManager(){
        paginaCorrente = PaginaCorrente.CATALOGO;
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new CreaManagerPanel(this));
        repaint();
        validate();
    }

    public PaginaCorrente getPaginaCorrente(){
        return paginaCorrente;
    }
}

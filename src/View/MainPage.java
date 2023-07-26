package View;

import Business.*;
import Model.*;
import View.Decorator.*;
import View.Decorator.Menu;
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

    public enum PaginaCorrente{MAIN, LOGIN, REGISTER, CATALOGO, LISTE, ARTICOLO}

    private PaginaCorrente paginaCorrente;

    public MainPage() {
        super("MyShop");
        this.setSize(1000,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ArticoloBusiness.getAllArticoli();

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

    public void mostraCatalogo(){
        centro.removeAll();
        centro.setLayout(new BorderLayout());

        paginaCorrente = PaginaCorrente.CATALOGO;
        CatalogoPanel catalogoPanel = new CatalogoPanel((ArrayList<Articolo>) SessionManager.getSession().get(SessionManager.ALL_ARTICOLI),this);
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
        centro.add(new RegisterPanel());
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
        } else {
            centro.add(new GuestMainPanel(this));
        }
        repaint();
        validate();
    }

    public void mostraClientiTable(){
        centro.removeAll();
        centro.setLayout(new BorderLayout());

        Manager m = (Manager) SessionManager.getSession().get(LOGGED_USER);
        UtenteBusiness.getAllClientiOfPV(m.getIdPuntoVendita());
        //System.out.println((ArrayList<Cliente>) SessionManager.getSession().get(SessionManager.ALL_CLIENTI_PV));
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

    public void mostraArticolo(ComponenteCatalogo comp){
        centro.removeAll();
        centro.setLayout(new BorderLayout());
        centro.add(new InfoArticoloPanel(this, comp));
        paginaCorrente = PaginaCorrente.ARTICOLO;
        repaint();
        validate();
    }

    public PaginaCorrente getPaginaCorrente(){
        return paginaCorrente;
    }
}

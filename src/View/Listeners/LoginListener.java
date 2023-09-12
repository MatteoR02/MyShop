package View.Listeners;

import Business.ExecuteResult;
import Business.LoginResult;
import Business.RegisterResult;
import Business.UtenteBusiness;
import Model.Cliente;
import Model.Indirizzo;
import Model.Persona;
import Model.PuntoVendita;
import View.MainPage;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

public class LoginListener implements ActionListener {

    public final static String LOGIN_BTN = "login_btn";
    public final static String LOGOUT_BTN = "logout_btn";
    public final static String TO_REGISTER_BTN = "to_register_btn";
    public final static String REGISTER_BTN = "register_btn";
    public final static String EXIT_BTN = "exit_btn";
    public final static String CATALOGO_BTN = "catalogo_btn";
    public final static String RECOVER_BTN = "recover_btn";
    public final static String TO_LOGIN_BTN = "to_login";
    public final static String BACK_BTN = "back_btn";
    public final static String BACK_GUEST_BTN = "back_guest_btn";
    public final static String TO_PROFILE_BTN = "to_profile_btn";

    private MainPage frame;

    private JTextField fieldNome, fieldCognome, fieldEmail, fieldTelefono, fieldUsername;
    private JPasswordField fieldPassword;
    private JDateChooser dataNascita;
    private JTextField fieldNazione, fieldCitta, fieldCap, fieldVia, fieldCivico;
    private JComboBox selPuntoVenditaBox, selProfessioneBox, selCanaleBox;


    public LoginListener(JTextField fieldUsername, JPasswordField fieldPassword) {
        this.fieldUsername = fieldUsername;
        this.fieldPassword = fieldPassword;
    }

    public LoginListener(MainPage frame, JTextField fieldNome, JTextField fieldCognome, JTextField fieldEmail, JTextField fieldTelefono, JTextField fieldUsername, JPasswordField fieldPassword, JDateChooser dataNascita, JTextField fieldNazione, JTextField fieldCitta, JTextField fieldCap, JTextField fieldVia, JTextField fieldCivico, JComboBox selPuntoVenditaBox, JComboBox selProfessioneBox, JComboBox selCanaleBox) {
        this.frame = frame;
        this.fieldNome = fieldNome;
        this.fieldCognome = fieldCognome;
        this.fieldEmail = fieldEmail;
        this.fieldTelefono = fieldTelefono;
        this.fieldUsername = fieldUsername;
        this.fieldPassword = fieldPassword;
        this.dataNascita = dataNascita;
        this.fieldNazione = fieldNazione;
        this.fieldCitta = fieldCitta;
        this.fieldCap = fieldCap;
        this.fieldVia = fieldVia;
        this.fieldCivico = fieldCivico;
        this.selPuntoVenditaBox = selPuntoVenditaBox;
        this.selProfessioneBox = selProfessioneBox;
        this.selCanaleBox = selCanaleBox;
    }

    public LoginListener(MainPage frame) {
        this.frame = frame;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (TO_LOGIN_BTN.equals(action)) {
            frame.mostraLogin();
        } else if(LOGIN_BTN.equals(action)){
            String username = fieldUsername.getText();
            String password = new String(fieldPassword.getPassword());
            LoginResult result = UtenteBusiness.getInstance().login(username,password);
            if (LoginResult.Result.LOGIN_OK == result.getResult()){
                frame.mostraMain();
            } else {
                JOptionPane.showMessageDialog(frame,result.getMessage(), "Errore Login", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (EXIT_BTN.equals(action)) {
            System.exit(0);
        } else if (TO_REGISTER_BTN.equals(action)) {
            frame.mostraRegister();
        } else if (REGISTER_BTN.equals(action)){
            String nome = fieldNome.getText();
            String cognome = fieldCognome.getText();
            String email = fieldEmail.getText();
            String telefono = fieldTelefono.getText();
            String username = fieldUsername.getText();
            String password = new String(fieldPassword.getPassword());
            java.util.Date data = dataNascita.getDate();
            Date dataNasc = new Date(data.getTime());
            String nazione = fieldNazione.getText();
            String citta = fieldCitta.getText();
            String cap = fieldCap.getText();
            String via = fieldVia.getText();
            String civico = fieldCivico.getText();
            int idPV = ((PuntoVendita) selPuntoVenditaBox.getSelectedItem()).getId();
            Cliente.ProfessioneType professione = (Cliente.ProfessioneType) selProfessioneBox.getSelectedItem();
            Cliente.CanalePreferitoType canalePreferito = (Cliente.CanalePreferitoType) selCanaleBox.getSelectedItem();

            Cliente clienteNuovo = new Cliente(new Persona(nome, cognome, email, telefono, dataNasc), username, password, new Indirizzo(nazione, citta, cap, via, civico), professione,canalePreferito, Date.valueOf(LocalDate.now()), idPV, Cliente.StatoUtenteType.ABILITATO);

            RegisterResult result = UtenteBusiness.registerCliente(clienteNuovo);
            if (result.getResult()== RegisterResult.Result.OK){
                JOptionPane.showMessageDialog(frame,"Registrazione effettuata con successo", "Registrazione effettuata", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraMain();
            } else {
                JOptionPane.showMessageDialog(frame,result.getMessage(), "Errore Login", JOptionPane.ERROR_MESSAGE);
            }

        } else if (CATALOGO_BTN.equals(action)) {
            frame.mostraCatalogo(null, false);
        } else if (BACK_BTN.equals(action)){
            MainPage.PaginaCorrente paginaCorrente = frame.getPaginaCorrente();
            if (paginaCorrente == MainPage.PaginaCorrente.ARTICOLO){
                frame.mostraCatalogo(null, false);
            } else if(paginaCorrente == MainPage.PaginaCorrente.CATALOGO){
               frame.mostraMain();
            } else if (paginaCorrente == MainPage.PaginaCorrente.SOTTOPRODOTTI){
                frame.mostraCatalogo(null, false);
            } else if (paginaCorrente == MainPage.PaginaCorrente.MENU_CREAZIONE) {
                frame.mostraCreaArticolo();
            }else{
                frame.mostraMain();
            }
        } else if (LOGOUT_BTN.equals(action)){
            UtenteBusiness.closeSession();
            frame.mostraMain();
        } else if (TO_PROFILE_BTN.equals(action)){
            frame.mostraProfilo();
        }
    }
}

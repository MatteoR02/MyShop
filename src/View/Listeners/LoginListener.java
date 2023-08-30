package View.Listeners;

import Business.LoginResult;
import Business.UtenteBusiness;
import View.MainPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginListener implements ActionListener {

    public final static String LOGIN_BTN = "login_btn";
    public final static String LOGOUT_BTN = "logout_btn";
    public final static String REGISTER_BTN = "register_btn";
    public final static String EXIT_BTN = "exit_btn";
    public final static String CATALOGO_BTN = "catalogo_btn";
    public final static String RECOVER_BTN = "recover_btn";
    public final static String TO_LOGIN_BTN = "to_login";
    public final static String BACK_BTN = "back_btn";
    public final static String BACK_GUEST_BTN = "back_guest_btn";
    public final static String TO_PROFILE_BTN = "to_profile_btn";



    private JTextField usernameField;
    private JPasswordField passwordField;

    private MainPage frame;

    public LoginListener(JTextField usernameField, JPasswordField passwordField) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
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
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            LoginResult result = UtenteBusiness.getInstance().login(username,password);
            if (LoginResult.Result.LOGIN_OK == result.getResult()){
                frame.mostraMain();
            } else {
                JOptionPane.showMessageDialog(frame,result.getMessage(), "Errore Login", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (EXIT_BTN.equals(action)) {
            System.exit(0); //Chiude l'applicazione
        } else if (REGISTER_BTN.equals(action)) {
            //Apre view della registrazione
            //TODO Registrazione
            frame.mostraRegister();
        } else if (CATALOGO_BTN.equals(action)) {
            //Visualizza catalogo Guest
            frame.mostraCatalogo(null, false);
        } else if (BACK_BTN.equals(action)){
            MainPage.PaginaCorrente paginaCorrente = frame.getPaginaCorrente();
            if (paginaCorrente == MainPage.PaginaCorrente.ARTICOLO){
                frame.mostraCatalogo(null, false);
            } else if(paginaCorrente == MainPage.PaginaCorrente.CATALOGO){
               frame.mostraMain();
            } else if (paginaCorrente == MainPage.PaginaCorrente.SOTTOPRODOTTI){
                frame.mostraCatalogo(null, false);
            } else{
                frame.mostraMain();
            }
        } else if (LOGOUT_BTN.equals(action)){
            UtenteBusiness.closeSession();
            frame.mostraMain();
        } else if (TO_PROFILE_BTN.equals(action)){

        }
    }
}

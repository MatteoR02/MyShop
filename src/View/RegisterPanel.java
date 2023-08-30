package View;

import View.Listeners.LoginListener;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegisterPanel extends JPanel {

    public RegisterPanel() {
        this.setLayout(new GridBagLayout());
        Insets insets = new Insets(3,10,3,10);
        JLabel textNome = new JLabel("Nome");
        JLabel textCognome = new JLabel("Cognome");
        JLabel textEmail = new JLabel("Email");
        JLabel textUsername = new JLabel("Username");
        JLabel textPassword = new JLabel("Password");
        JLabel textTelefono = new JLabel("Telefono");
        JLabel textDataNascita = new JLabel("Data di nascita");
        JLabel textNazione  = new JLabel("Nazione");
        JLabel textCitta = new JLabel("Citta");
        JLabel textCap = new JLabel("Cap");
        JLabel textVia = new JLabel("Via");
        JLabel textCivico = new JLabel("Civico");
        JLabel textPuntiVendita = new JLabel("Scegli il punto vendita a cui ti vuoi registrare");
        JTextField nome = new JTextField(20);
        JTextField cognome = new JTextField(20);
        JTextField email = new JTextField(20);
        JTextField telefono = new JTextField(20);
        JDateChooser dataNascita = new JDateChooser();
        JTextField nazione = new JTextField(20);
        JTextField citta = new JTextField(20);
        JTextField cap = new JTextField(20);
        JTextField via = new JTextField(20);
        JTextField civico = new JTextField(20);

        //prendere puntiVendita da database
        String[] puntiVendita = { "Punto Vendita Lecce", "Punto Vendita Milano", "Punto Vendita Roma", "Punto Vendita Pisa", "Punto Vendita Torino" };

        JComboBox puntiVenditaList = new JComboBox(puntiVendita);
        puntiVenditaList.setSelectedIndex(0);

        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JButton register = new JButton("Registrati");
        //register.setPreferredSize(new Dimension(200,50));
        register.setFocusPainted(false);
        register.setActionCommand(LoginListener.REGISTER_BTN);
        LoginListener loginListener = new LoginListener(username, password);
        register.addActionListener(loginListener);
        JDateChooser compleanno = new JDateChooser();
        this.add(textNome,new GridBagCostraintsHorizontal(0,0,1,1,insets));

        this.add(new JLabel(""),new GridBagCostraintsHorizontal(1,0,1,1,insets));

        this.add(textCognome,new GridBagCostraintsHorizontal(2,0,1,1,insets));

        this.add(nome,new GridBagCostraintsHorizontal(0,1,1,1,insets));

        this.add(cognome,new GridBagCostraintsHorizontal(2,1,1,1,insets));

        this.add(textEmail,new GridBagCostraintsHorizontal(0,2,1,1,insets));

        this.add(email,new GridBagCostraintsHorizontal(0,3,3,1,insets));

        this.add(textTelefono,new GridBagCostraintsHorizontal(0,4,2,1,insets));

        this.add(textDataNascita,new GridBagCostraintsHorizontal(2,4,1,1,insets));

        this.add(telefono,new GridBagCostraintsHorizontal(0,5,2,1,insets));

        this.add(dataNascita,new GridBagCostraintsHorizontal(2,5,1,1,insets));

        this.add(textNazione,new GridBagCostraintsHorizontal(0,6,1,1,insets));

        this.add(textCitta,new GridBagCostraintsHorizontal(1,6,1,1,insets));

        this.add(textCap,new GridBagCostraintsHorizontal(2,6,1,1,insets));

        this.add(nazione,new GridBagCostraintsHorizontal(0,7,1,1,insets));

        this.add(citta,new GridBagCostraintsHorizontal(1,7,1,1,insets));

        this.add(cap,new GridBagCostraintsHorizontal(2,7,1,1,insets));

        this.add(textVia,new GridBagCostraintsHorizontal(0,8,1,1,insets));

        this.add(textCivico,new GridBagCostraintsHorizontal(2,8,1,1,insets));

        this.add(via,new GridBagCostraintsHorizontal(0,9,2,1,insets));

        this.add(civico,new GridBagCostraintsHorizontal(2,9,1,1,insets));

        this.add(textUsername,new GridBagCostraintsHorizontal(0,10,3,1,insets));

        this.add(username,new GridBagCostraintsHorizontal(0,11,3,1,insets));

        this.add(textPassword,new GridBagCostraintsHorizontal(0,12,3,1,insets));

        this.add(password,new GridBagCostraintsHorizontal(0,13,3,1,insets));

        this.add(textPuntiVendita,new GridBagCostraintsHorizontal(0,14,3,1,insets) );

        this.add(puntiVenditaList, new GridBagCostraintsHorizontal(0,15,1,1,insets));

        this.add(register,new GridBagCostraintsHorizontal(1,15,2,1,insets));

        telefono.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if (!Character.isDigit(c)){
                    ke.consume();
                }
            }
        });

        Font font = new Font("Arial", Font.PLAIN, 18);
        this.setFont(font);
        for ( Component child :  this.getComponents()){
            child.setFont(font);
        }
    }
}

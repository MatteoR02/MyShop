package View;

import Model.Cliente;
import Model.Utente;
import View.Decorator.*;
import View.Decorator.Menu;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {

    private JPanel nord = new JPanel();
    private JPanel centro = new JPanel();
    private JPanel est = new JPanel();
    private JPanel ovest = new JPanel();
    private JPanel sud = new JPanel();
    private JPanel main = new JPanel();

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

        Menu guestMenu = new GuestMenu(this);

        mostraMain();

        sud.setLayout(new FlowLayout(FlowLayout.RIGHT));
        for (JButton btn : guestMenu.getPulsantiSud()) {
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
        centro.add(new CatalogoPanel());
        repaint();
        validate();
    }

    public void mostraLogin(){
        centro.removeAll();
        centro.setLayout(new GridBagLayout());
        centro.add(new LoginPanel());
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
        centro.add(new GuestMainPanel(this));
        repaint();
        validate();
    }
}

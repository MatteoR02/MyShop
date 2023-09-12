package View.ListeAcquisto;

import View.GridBagCostraintsHorizontal;
import View.Listeners.Builders.ClienteListenerBuilder;
import View.Listeners.ClienteListener;
import View.MainPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddListaPanel extends JPanel {

    public AddListaPanel(MainPage frame, JDialog dialog){

        this.setLayout(new GridBagLayout());
        Color sfondo = Color.WHITE;
        //this.setBackground(sfondo);
        Dimension buttonDimension = new Dimension(200, 70);
        Insets insets = new Insets(5,10,5,10);
        Insets btnInsets = new Insets(40,10,5,10);
        setBorder(new EmptyBorder(2,10,2,10));

        JLabel textNuovaLista = new JLabel("Inserisci un nome per la nuova lista");

        JButton annullaBtn = new JButton("Annulla");
        JTextField nuovaListaField = new JTextField(30);
        JButton creaNuovaListaBtn = new JButton("Crea una nuova lista");

        ClienteListener clienteListener = ClienteListenerBuilder.newBuilder(frame).fieldNewLista(nuovaListaField).dialog(dialog).build();
        clienteListener.setFrame(frame);

        creaNuovaListaBtn.setActionCommand(ClienteListener.CREATE_NEW_LISTA);
        creaNuovaListaBtn.addActionListener(clienteListener);

        annullaBtn.addActionListener(e -> {
            dialog.dispose();
        });

        this.add(textNuovaLista, new GridBagCostraintsHorizontal(0,0,3,1,insets));

        this.add(nuovaListaField, new GridBagCostraintsHorizontal(0,1,3,1,insets));

        this.add(creaNuovaListaBtn, new GridBagCostraintsHorizontal(3,1,1,1,insets));

        this.add(annullaBtn, new GridBagCostraintsHorizontal(0,2,4,1,btnInsets));

    }

}

package View.ListaClienti;

import View.Listeners.Builders.ManagerListenerBuilder;
import View.Listeners.CatalogoListener;
import View.Listeners.ManagerListener;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;
import View.ViewModel.RigaCliente;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class InviaMessaggioPanel extends JPanel {

    public InviaMessaggioPanel(JFrame frame, JDialog dialog, RigaCliente rigaCliente) {

        this.setLayout(new MigLayout("insets 20, fillx", "[]push [] []", "10[]15 []5 []8 []5 [] []10 []30 []"));

        JLabel labelCliente = new JLabel(rigaCliente.getEmail());
        JLabel labelOggetto = new JLabel("Oggetto");
        JLabel labelCorpo = new JLabel("Corpo");

        JTextField fieldOggetto = new JTextField(15);
        JTextArea fieldCorpo = new JTextArea("", 3, 100);

        JLabel labelAllegato = new JLabel("", SwingConstants.RIGHT);

        fieldCorpo.setLineWrap(true);
        fieldCorpo.setWrapStyleWord(true);

        JScrollPane scrollPaneCorpo = new JScrollPane(fieldCorpo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        ManagerListener managerListenerInvia = ManagerListenerBuilder.newBuilder((MainPage) frame).dialog(dialog).fieldOggetto(fieldOggetto).
                fieldCorpo(fieldCorpo).rigaCliente(rigaCliente).labelAllegato(labelAllegato).build();

        JButton allegaFileBtn = new JButton("Allega file");
        allegaFileBtn.setActionCommand(ManagerListener.ALLEGA_FILE);
        allegaFileBtn.addActionListener(managerListenerInvia);

        allegaFileBtn.setFocusPainted(false);

        JButton annullaBtn = new JButton("Annulla");
        JButton inviaBtn = new JButton("Invia messaggio");

        managerListenerInvia.setFrame((MainPage) frame);
        inviaBtn.addActionListener(managerListenerInvia);
        inviaBtn.setActionCommand(ManagerListener.INVIA_MESSAGGIO);


        annullaBtn.setFocusPainted(false);
        inviaBtn.setFocusPainted(false);


        annullaBtn.addActionListener(e -> {
            dialog.dispose();
        });


        this.add(labelCliente, "cell 0 0, wrap");
        this.add(labelOggetto, "cell 0 1");
        this.add(fieldOggetto, "cell 0 2");
        this.add(labelCorpo, "cell 0 3");
        this.add(scrollPaneCorpo, "cell 0 4 3 2");
        this.add(allegaFileBtn, "cell 0 6, grow");
        this.add(labelAllegato, "cell 2 6");
        this.add(annullaBtn, "cell 0 7, grow");
        this.add(inviaBtn, "cell 2 7, grow");

    }
}


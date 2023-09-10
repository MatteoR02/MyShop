package View;

import Model.ListaAcquisto;
import Model.Prenotazione;
import View.Listeners.ClienteListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class PrenotazioniClientePanel extends JPanel {

    public PrenotazioniClientePanel(MainPage frame, ArrayList<Prenotazione> prenotazioni){
        this.setLayout(new BorderLayout());
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);

        JList<Prenotazione> list = new JList<>();
        DefaultListModel<Prenotazione> model = new DefaultListModel<>();

        JPanel listaPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());

        JPanel pulsantiPanel = new JPanel(new GridLayout(0,1, 5, 5));

        JSplitPane splitPane = new JSplitPane();

        list.setModel(model);

        for (Prenotazione prenotazione: prenotazioni) {
                model.addElement(prenotazione);
        }


            list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()) {
                        Prenotazione pr = list.getSelectedValue();
                        listaPanel.removeAll();
                        listaPanel.add(new PrenotazioniClienteTablePanel((pr), frame));
                        frame.repaint();
                        frame.validate();
                    }
                }
            });

        JScrollPane scrollPane = new JScrollPane(list);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        leftPanel.add(pulsantiPanel, BorderLayout.SOUTH);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(listaPanel);
        splitPane.setDividerLocation(0.1);
        splitPane.setResizeWeight(0.1);

        this.add(splitPane);
        this.setVisible(true);
        }

}

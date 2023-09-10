package View;

import Model.ListaAcquisto;
import View.Listeners.Builders.ClienteListenerBuilder;
import View.Listeners.ClienteListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class ListeAcquistoPanel extends JPanel {

    public ListeAcquistoPanel(MainPage frame, ArrayList<ListaAcquisto> liste){
        this.setLayout(new BorderLayout());
        Color sfondo = Color.WHITE;
        this.setBackground(sfondo);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonDimension = new Dimension(200, 70);

        JList<ListaAcquisto> list = new JList<>();
        DefaultListModel<ListaAcquisto> model = new DefaultListModel<>();

        JPanel listaPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());

        JPanel pulsantiPanel = new JPanel(new GridLayout(0,1, 5, 5));

        ClienteListener clienteListener = ClienteListenerBuilder.newBuilder(frame).build();

        JButton creaListaBtn = new JButton("Crea Lista");
        creaListaBtn.setFocusPainted(false);
        creaListaBtn.setActionCommand(ClienteListener.TO_CREATE_NEW_LISTA);
        creaListaBtn.addActionListener(clienteListener);

        JButton mostraListePagateBtn = new JButton("Mostra liste pagate");


        ClienteListener clienteListenerListe = ClienteListenerBuilder.newBuilder(frame).listModel(model).arrayListeAcq(liste).buttonMostraNascondi(mostraListePagateBtn).build();

        mostraListePagateBtn.setFocusPainted(false);
        mostraListePagateBtn.setActionCommand(ClienteListener.MOSTRA_LISTE_PAGATE);
        mostraListePagateBtn.addActionListener(clienteListenerListe);

        pulsantiPanel.add(creaListaBtn);
        pulsantiPanel.add(mostraListePagateBtn);

        JSplitPane splitPane = new JSplitPane();

        list.setModel(model);

        for (ListaAcquisto lista: liste) {
            if (lista.getStatoPagamento()!= ListaAcquisto.StatoPagamentoType.PAGATO){
                model.addElement(lista);
            }
        }


            list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()) {
                        ListaAcquisto ls = list.getSelectedValue();
                        listaPanel.removeAll();
                        listaPanel.add(new ListaTablePanel((ls), frame));
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
        splitPane.setDividerLocation(0.2);
        splitPane.setResizeWeight(0.2);

        this.add(splitPane);
        this.setVisible(true);
        }

}

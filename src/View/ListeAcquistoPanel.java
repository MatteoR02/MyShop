package View;

import Model.ListaAcquisto;
import Model.Prodotto;
import View.Decorator.ClienteMenu;
import View.Decorator.Menu;
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

        JPanel pulsantiPanel = new JPanel(new GridLayout());

        ClienteListener clienteListener = new ClienteListener(frame);

        JButton creaListaBtn = new JButton("Crea Lista");
        creaListaBtn.setActionCommand(ClienteListener.TO_CREATE_NEW_LISTA);
        creaListaBtn.addActionListener(clienteListener);

        pulsantiPanel.add(creaListaBtn);

        JSplitPane splitPane = new JSplitPane();

        list.setModel(model);

        for (ListaAcquisto lista: liste) {
            model.addElement(lista);
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

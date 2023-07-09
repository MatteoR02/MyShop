package View.ViewModel;


import javax.swing.*;

import java.awt.*;

public class ArticoloList extends JPanel {

    public ArticoloList(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        //this.setBorder(BorderFactory.createRaisedBevelBorder());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);
        JLabel nome = new JLabel("Sedia");
        JLabel prezzo = new JLabel("49,99€",SwingConstants.RIGHT);
        JButton visualizza = new JButton("Visualizza");
        visualizza.setFocusPainted(false);

        //TODO inserire try catch per caricare l'immagine
        ImageIcon imageIcon = new ImageIcon("resources/sedia.png"); // Carico l'immagine in un ImageIcon
        Image image = imageIcon.getImage(); // La trasformo in un Image
        Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // La dimensiono in modo smooth
        imageIcon = new ImageIcon(newimg);
        JLabel immagine = new JLabel(imageIcon);

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.gridheight=2;
        this.add(immagine,gbc);
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        this.add(nome,gbc);
        gbc.gridx=1;
        this.add(prezzo,gbc);
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=2;
        this.add(visualizza,gbc);

    }
}

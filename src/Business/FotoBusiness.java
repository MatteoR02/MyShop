package Business;

import DAO.FotoDAO;
import DAO.IFotoDAO;

import javax.swing.*;
import java.awt.*;

public class FotoBusiness {

    private final static IFotoDAO fotoDAO = FotoDAO.getInstance();



    public static ImageIcon scaleImageIcon(ImageIcon imageIcon, int width, int height){
        Image image = imageIcon.getImage(); // La trasformo in un Image
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // La dimensiono in modo smooth
        return new ImageIcon(newimg);
    }

}

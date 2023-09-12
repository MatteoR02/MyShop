package Business;

import DAO.FotoDAO;
import DAO.IFotoDAO;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class FotoBusiness {

    private final static IFotoDAO fotoDAO = FotoDAO.getInstance();

    /**
     * Trasforma le dimensioni di un ImageIcon
     * @param imageIcon immagine da trasformare
     * @param width larghezza
     * @param height altezza
     * @return immagine trasformata
     */
    public static ImageIcon scaleImageIcon(ImageIcon imageIcon, int width, int height){
        Image image = imageIcon.getImage(); // La trasformo in un Image
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // La dimensiono in modo smooth
        return new ImageIcon(newimg);
    }

    /**
     * Converte un blob a immagine
     * @param imageBlob
     * @return immagine convertita in ImageIcon
     */
    public static ImageIcon blobToImage(Blob imageBlob){
        try {
            InputStream in = imageBlob.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            return new ImageIcon(image);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converte un file immagine in un blob
     * @param image
     * @return blob del file convertito
     */
    public static Blob imgToBlob(File image){
        try {
            String imageFormat = "";
            if(image.getName().endsWith(".png")){
                imageFormat = "png";
            } else if (image.getName().endsWith(".jpg")){
                imageFormat = "jpg";
            } else if (image.getName().endsWith(".jpeg")){
                imageFormat = "jpeg";
            }else if(image.getName().endsWith(".bmp")){
                imageFormat = "bmp";
            } else {
                imageFormat = "png"; //Di default metto png
            }

            //Leggo l'immagine estrapolandone i dati
            BufferedImage bufferedImage = ImageIO.read(image);
            //Uno stream che scrive l'input in un array di bytes
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //Scrive l'immagine nello Stream che converte i dati e li mette nell'array di bytes
            ImageIO.write(bufferedImage, imageFormat, byteArrayOutputStream);

            //Contiene l'immagine convertita in Bytes
            byte[] bytes = byteArrayOutputStream.toByteArray();

            //Ritorna il Blob contenente i bytes dell'array
            return new SerialBlob(bytes);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

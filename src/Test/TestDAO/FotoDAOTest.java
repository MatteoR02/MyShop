package Test.TestDAO;

import Business.FotoBusiness;
import Business.UtenteBusiness;
import DAO.FotoDAO;
import DAO.IFotoDAO;
import Model.Foto;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class FotoDAOTest {

    private IFotoDAO fotoDAO = FotoDAO.getInstance();


    @Test
    public void loadFoto() throws SQLException, IOException {
        Foto foto = fotoDAO.loadFoto(3);
        Assert.assertNotNull(foto);

        JFileChooser fileSave = new JFileChooser();
        fileSave.showSaveDialog(null);
        File fileSaved = fileSave.getSelectedFile();

        //Per salvare il file devo riconvertire il blob della foto in byte[] è scriverlo nel file del path
            //Converto il Blob in array di byte
        byte[] bytes = foto.getImmagine().getBytes(1, (int) foto.getImmagine().length());

            //Creo l'immagine dall'array di byte e la salvo
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage img = ImageIO.read(inputStream);
        ImageIO.write(img, "png", fileSaved);
    }


    @Test
    public void getFoto(){
        Foto foto = fotoDAO.loadFoto(3);
        Assert.assertNotNull(foto);
    }


    @Test
    public void loadAllFoto(){
        IFotoDAO fotoDAO = FotoDAO.getInstance();
        ArrayList<Foto> immagini = (ArrayList<Foto>) fotoDAO.loadAllFoto();
        Assert.assertNotNull(immagini);
    }

    @Test
    public void updateFoto(){
        JFileChooser chooser = new JFileChooser();
        chooser.setDragEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Upload File");
        chooser.setMultiSelectionEnabled(false);
        chooser.showOpenDialog(null);

        Foto foto = fotoDAO.loadFoto(3);
        foto.setImmagine(FotoBusiness.imgToBlob(chooser.getSelectedFile()));

        Assert.assertEquals(1,fotoDAO.updateFoto(foto));
    }

    @Test
    public void removeFoto(){
        Assert.assertEquals(1, fotoDAO.removeFoto(50));
    }

}

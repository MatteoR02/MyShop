package Business;

import DAO.*;
import Model.*;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticoloBusiness {

    private static final IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();
    private static final IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private static final IUtenteDAO utenteDAO = UtenteDAO.getInstance();
    private static final IFotoDAO fotoDAO = FotoDAO.getInstance();
    private static final IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();

    public static ExecuteResult<Articolo> getAllArticoli(){
        ExecuteResult<Articolo> result = new ExecuteResult<>();
        ArrayList<Articolo> articoliUncheck = articoloDAO.loadAllProdotti(); //carica prodotti e prodotti compositi
        ArrayList<Servizio> serviziUncheck = articoloDAO.loadAllServizi();

        ArrayList<Servizio> servizi = new ArrayList<>();
        ArrayList<Articolo> articoli = new ArrayList<>();

        for (Servizio serv : serviziUncheck   ) {
            if (hasNoFoto(serv)){
                servizi.add((Servizio) setDefaultFoto(serv));
            } else {
                servizi.add(serv);
            }
        }
        for (Articolo art : articoliUncheck   ) {
            if (hasNoFoto(art)){
                articoli.add(setDefaultFoto(art));
            } else {
                articoli.add(art);
            }
        }

        articoli.addAll(servizi);
        result.setObject(articoli);
        result.setMessage(ExecuteResult.ResultStatement.OK.toString());
        result.execute(SessionManager.ALL_ARTICOLI);

        return null;
    }

    public static ExecuteResult<Articolo> getAllProdottifromPV(int id){
        ExecuteResult<Articolo> result = new ExecuteResult<>();
        if(puntoVenditaDAO.isPuntoVendita(id)){

            ArrayList<Articolo> articoliUncheck = articoloDAO.loadAllArticoliFromPuntoVendita(id);
            ArrayList<Articolo> articoli = new ArrayList<>();

            for (Articolo art : articoliUncheck   ) {
                if (hasNoFoto(art)){
                    articoli.add(setDefaultFoto(art));
                } else {
                    articoli.add(art);
                }
            }

            result.setObject(articoli);
            if(isUndefined(result.getObjectFromArray(0).getNome())){
                result.removeFromArray(0);
            }
            result.setMessage("L'id inserito corrisponde ad un punto vendita.");
            result.setResult(ExecuteResult.ResultStatement.OK);
        } else{
            result.setResult(ExecuteResult.ResultStatement.WRONG_ACCESS);
            result.setMessage("L'id inserito non corrisponde ad un punto vendita");
            return result;
        }

        //ArrayList<Articolo> articoli = result.getObject();
       /* for (int i = 0; i < articoli.size(); i++) {
            if(articoloCheckType(articoli.get(i).getId()) != TipoArticolo.PRODOTTO_COMPOSITO && articoloCheckType(articoli.get(i).getId()) != TipoArticolo.SERVIZIO){
                ExecuteResult<Foto> res = FotoBusiness.loadSingleFoto(articoli.get(i).getId(), FotoDAO.ID_ARTICOLO);
                ArrayList<Foto> photos = res.getObject();
                ((Prodotto)articoli.get(i)).setImmagini(photos);
                result.setMessage(result.getMessage() + res.getMessage());
            }
        }*/

        //result.setObject(articoli);

        result.execute(SessionManager.ALL_ARTICOLI_PV);
        return result;
    }



    public static ExecuteResult<Articolo> getArticolo(int id){
        ExecuteResult<Articolo> result = new ExecuteResult<>();
        if(articoloCheckType(id) == NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO){
            result.setSingleObject(articoloDAO.loadServizio(id));
            result.setMessage("Servizio caricato!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        }else if(articoloCheckType(id) == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO){
            result.setSingleObject(articoloDAO.loadProdotto(id));
            result.setMessage("Prodotto!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        }
        if(articoloCheckType(id) == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            result.reset();
            result.setSingleObject(articoloDAO.loadProdottoComposito(id));
            result.setMessage("Prodotto composito caricato!");
            result.setResult(ExecuteResult.ResultStatement.OK);
        }
        if(articoloCheckType(id) != NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO && articoloCheckType(id) != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO && articoloCheckType(id) != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Non è stato passato l'id di un articolo!");
        }
        if(isUndefined(result.getSingleObject().getNome())){
            result.reset();
            result.setMessage("Articolo selezionato: undefined!");
            result.setResult(ExecuteResult.ResultStatement.UNDEFINED);
        }
        return result;
    }

    public static ExecuteResult<Boolean> updateArticolo(Articolo articolo){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        NotWorking_ArticoloBusiness.TipoArticolo check = articoloCheckType(articolo.getId());
        if(check == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO){
            Prodotto p = (Prodotto) articolo;
            articoloDAO.updateProdotto(p);
            result.setMessage("prodotto aggiornato!");
        }
        if(check == NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO){
            Servizio s = (Servizio) articolo;
            articoloDAO.updateServizio(s);
            result.setMessage("Servizio aggiornato!");
        }
        if(check == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            ProdottoComposito p = (ProdottoComposito) articolo;
            articoloDAO.updateProdotto(p);
            result.setMessage("Servizio aggiornato!");
        }
        if(check != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO && check != NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO && check != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage(articolo.toString());
        }
        return result;
    }

    public static ExecuteResult<Boolean> updateArticolo(Articolo articolo, ArrayList<File> imgFiles){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        NotWorking_ArticoloBusiness.TipoArticolo check = articoloCheckType(articolo.getId());
        if(check == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO){
            Prodotto p = (Prodotto) articolo;
            for (File file :imgFiles) {
                Foto foto = new Foto();
                foto.setImmagine(imgToBlob(file));
                FotoDAO.getInstance().addFotoToArticolo(foto, p.getId());
            }
            articoloDAO.updateProdotto(p);
            result.setMessage("prodotto aggiornato!");
        }
        if(check == NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO){
            Servizio s = (Servizio) articolo;
            articoloDAO.updateServizio(s);
            result.setMessage("Servizio aggiornato!");
        }
        if(check == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            ProdottoComposito p = (ProdottoComposito) articolo;
            articoloDAO.updateProdotto(p);
            result.setMessage("Servizio aggiornato!");
        }
        if(check != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO && check != NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO && check != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage(articolo.toString());
        }
        return result;
    }

    public static ExecuteResult<Boolean> addArticolo(Articolo articolo, NotWorking_ArticoloBusiness.TipoArticolo tipoArticolo){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        if(tipoArticolo == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO){
            Prodotto p = (Prodotto) articolo;
            articolo.setId(articoloDAO.addProdotto(p));
            result.setMessage("prodotto aggiunto!");
        }
        if(tipoArticolo == NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO){
            Servizio s = (Servizio) articolo;
            articoloDAO.addServizio(s);
            result.setMessage("Servizio aggiunto!");
        }
        if(tipoArticolo == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            ProdottoComposito pc = (ProdottoComposito) articolo;
            articolo.setId(articoloDAO.addProdotto(pc));
            result.setMessage("Servizio aggiunto!");
        }
        if(tipoArticolo != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO && tipoArticolo != NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO && tipoArticolo != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage(articolo.toString());
        }
       /* if(result.getSingleObject()){
            ExecuteResult<Articolo> artResult = new ExecuteResult<>();
            artResult.execute(SessionManager.NEW_TEMP_ARTICOLI);
        }*/
        return result;
    }

    public static ExecuteResult<Boolean> addArticolo(Articolo articolo, NotWorking_ArticoloBusiness.TipoArticolo tipoArticolo, ArrayList<File> imgFiles){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        if(tipoArticolo == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO){
            Prodotto p = articoloDAO.loadProdotto(articoloDAO.addProdotto(articolo));
            for (File i:imgFiles) {
                Foto foto = new Foto();
                foto.setImmagine(imgToBlob(i));
                FotoDAO.getInstance().addFotoToArticolo(foto,p.getId());
            }
            //Refresho gli articoli nella session
            //getAllProdottiDisponibili();
            getAllArticoli();
            result.setMessage("prodotto aggiunto!");

        }
        if(tipoArticolo == NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO){
            Servizio s = (Servizio) articolo;
            articoloDAO.addServizio(s);
            result.setMessage("Servizio aggiunto!");
        }
        if(tipoArticolo == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            ProdottoComposito pc = (ProdottoComposito) articolo;
            articoloDAO.addProdotto(pc);
            result.setMessage("Servizio aggiunto!");
        }
        if(tipoArticolo != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO && tipoArticolo != NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO && tipoArticolo != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage(articolo.toString());
        }
        /*if(result.getSingleObject()){
            ExecuteResult<Articolo> artResult = new ExecuteResult<>();
            artResult.execute(SessionManager.NEW_TEMP_ARTICOLI);
        }*/
        return result;
    }

    public static ExecuteResult<Boolean> removeArticolo(int idArticolo){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        result.setSingleObject(true);
        result.setResult(ExecuteResult.ResultStatement.OK);

        NotWorking_ArticoloBusiness.TipoArticolo check = articoloCheckType(idArticolo);
        if(check == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO || check == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO || check == NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO){
            if(check == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO || check == NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
                articoloDAO.removeProdotto(idArticolo);
                result.setMessage("prodotto rimosso dal catalogo!");
            }
            if(check == NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO){
                articoloDAO.removeServizio(idArticolo);
                result.setMessage("Servizio rimosso dal catalogo!");
            }

            //rimuoviamo l'articolo anche dalle liste d'acquisto NON ANCORA PAGATE
            ExecuteResult <Integer> result1 = ListaAcquistoBusiness.removeArticoloFromAllListe(idArticolo);
            result.setMessage(result.getMessage() + "\n" + result1.getMessage());
        }


        if(check != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO && check != NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO && check != NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO){
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage("Id inserito non corrispondente ad un articolo!");
        }
        return result;
    }


    public static NotWorking_ArticoloBusiness.TipoArticolo articoloCheckType(int idArticolo){
        if(articoloDAO.isProdottoComposito(idArticolo)) return NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO_COMPOSITO;
        if(articoloDAO.isProdotto(idArticolo) || articoloDAO.isSottoProdotto(idArticolo)) return NotWorking_ArticoloBusiness.TipoArticolo.PRODOTTO;
        if(articoloDAO.isServizio(idArticolo)) return NotWorking_ArticoloBusiness.TipoArticolo.SERVIZIO;
        else return NotWorking_ArticoloBusiness.TipoArticolo.NOT_ARTICLE;
    }

    public static boolean isUndefined(String string){
        return string.equals("undefined");
    }

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

    public static boolean hasNoFoto(Articolo articolo){
        return articolo.getImmagini().isEmpty();
    }

    private static final Foto defaultFoto = fotoDAO.loadDefaultFoto();

    public static Articolo setDefaultFoto(Articolo articolo){

        articolo.getImmagini().add(defaultFoto);

        return articolo;
    }


}

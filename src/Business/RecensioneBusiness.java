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

public class RecensioneBusiness {

    private static final IRecensioneDAO recensioneDAO = RecensioneDAO.getInstance();

    public static ExecuteResult<Recensione> getRecensioniOfArticolo(int idArticolo){
        ExecuteResult<Recensione> result = new ExecuteResult<>();
        if (ArticoloBusiness.articoloCheckType(idArticolo) != ArticoloBusiness.TipoArticolo.NOT_ARTICLE){
            ArrayList<Recensione> recensioni = (ArrayList<Recensione>) recensioneDAO.loadRecensioniOfArticolo(idArticolo);
            result.setObject(recensioni);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Le recensioni dell'articolo sono state caricate correttamente");
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Articolo non trovato");
        }
        return result;
    }

    public static ExecuteResult<Recensione> getRecensione(int idRecensione){
        ExecuteResult<Recensione> result = new ExecuteResult<>();
        Recensione recensione = recensioneDAO.loadRecensione(idRecensione);
        result.setSingleObject(recensione);
        result.setResult(ExecuteResult.ResultStatement.OK);
        result.setMessage("Recensione caricata correttamente");
        return result;
    }

    public static ExecuteResult<Recensione> getRisposta(int idRecensione){
        ExecuteResult<Recensione> result = new ExecuteResult<>();
        Recensione recensione = recensioneDAO.loadRisposta(idRecensione);
        if (recensione!=null){
            result.setSingleObject(recensione);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Risposta caricata correttamente");
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("La recensione non ha una risposta");
        }
        return result;
    }

    public static ExecuteResult<Boolean> addRecensione(Recensione recensione, int idArticolo){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        if (ArticoloBusiness.articoloCheckType(idArticolo) != ArticoloBusiness.TipoArticolo.NOT_ARTICLE){
            if (ArticoloBusiness.isArticoloBoughtFrom(idArticolo, recensione.getIdCliente()).getSingleObject()){
                if (recensioneDAO.isRecensioneDone(idArticolo,recensione.getIdCliente())){
                    result.setResult(ExecuteResult.ResultStatement.OK_WITH_WARNINGS);
                    result.setSingleObject(false);
                    result.setMessage("Articolo già recensito dal cliente");

                } else {
                    recensioneDAO.addRecensione(recensione, idArticolo);
                    result.setResult(ExecuteResult.ResultStatement.OK);
                    result.setSingleObject(true);
                    result.setMessage("Recensione aggiunta");
                }
            } else {
                result.setResult(ExecuteResult.ResultStatement.NOT_OK);
                result.setSingleObject(false);
                result.setMessage("Articolo non ancora acquistato dal cliente");
            }
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setSingleObject(false);
            result.setMessage("Articolo non trovato");
        }

        return result;
    }

    public static ExecuteResult<Boolean> addRisposta(Recensione recensione, int idRecensione){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        Manager m = (Manager) SessionManager.getSession().get(SessionManager.LOGGED_USER);
        int rows = recensioneDAO.addRisposta(recensione, idRecensione);
        if (recensioneDAO.isRispostaDone(m.getId(), idRecensione)) {
            result.setResult(ExecuteResult.ResultStatement.OK_WITH_WARNINGS);
            result.setSingleObject(false);
            result.setMessage("Risposta già data alla recensione");
        } else {
            if(rows>0){
                result.setResult(ExecuteResult.ResultStatement.OK);
                result.setSingleObject(true);
                result.setMessage("Risposta aggiunta");
            } else {
                result.setResult(ExecuteResult.ResultStatement.NOT_OK);
                result.setSingleObject(false);
                result.setMessage("Errore aggiunta risposta");
            }
        }
        return result;
    }

    public static ExecuteResult<Boolean> removeRecensione (int idRecensione){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        if (recensioneDAO.isRecensione(idRecensione)){
            recensioneDAO.removeRecensione(idRecensione);
            result.setSingleObject(true);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Recensione eliminata");
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Recensione non trovata");
        }
        return result;
    }

    public static Recensione.Punteggio integerToPunteggio(int valutazione){
        Recensione.Punteggio voto = Recensione.Punteggio.ORRIBILE;
        switch (valutazione){
            case 1 : voto = Recensione.Punteggio.ORRIBILE; break;
            case 2 : voto = Recensione.Punteggio.SCARSO; break;
            case 3 : voto = Recensione.Punteggio.MEDIOCRE; break;
            case 4 : voto = Recensione.Punteggio.BUONO; break;
            case 5 : voto = Recensione.Punteggio.ECCELLENTE; break;
        }
        return voto;
    }



}

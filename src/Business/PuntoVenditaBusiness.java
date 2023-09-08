package Business;

import DAO.*;
import Model.*;

import java.util.ArrayList;

public class PuntoVenditaBusiness {

    private static IMagazzinoDAO magazzinoDAO = MagazzinoDAO.getInstance();
    private static IPuntoVenditaDAO puntoVenditaDAO = PuntoVenditaDAO.getInstance();
    private static IArticoloDAO articoloDAO = ArticoloDAO.getInstance();
    private static IListaAcquistoDAO listaAcquistoDAO = ListaAcquistoDAO.getInstance();
    private static IUtenteDAO utenteDAO = UtenteDAO.getInstance();

    public static ExecuteResult<Boolean> aggiornaQuantitaInMagazzino(Prodotto prodotto, int quantitaAggiuntaORimossa){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        int disponibilita = prodotto.getCollocazione().getQuantita();
        int nuovaQuantita = disponibilita + quantitaAggiuntaORimossa;
        prodotto.getCollocazione().setQuantita(nuovaQuantita);
        int rows = articoloDAO.updateProdotto(prodotto);

        //mail ai clienti
        if (rows!=0){
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Quantità in magazzino aggiornata");
            result.setSingleObject(true);
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Quantità in magazzino non aggiornata correttamente");
            result.setSingleObject(false);
        }
        return result;
    }

    public static ExecuteResult<Boolean> creaManager(Manager manager){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        if (!utenteDAO.userExists(manager.getUsername())){
            utenteDAO.addManager(manager);
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Manager creato con successo");
            result.setSingleObject(true);
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Manager non creato perché è già presente un utente con questo username");
            result.setSingleObject(false);
        }
        return result;
    }

    public ExecuteResult<Boolean> assegnaManager (int idManager, int idPuntoVendita){
        ExecuteResult<Boolean> result = new ExecuteResult<>();
        int rows = utenteDAO.assignManager(idManager,idPuntoVendita);
        if (rows!=0){
            result.setResult(ExecuteResult.ResultStatement.OK);
            result.setMessage("Manager assegnato con successo");
            result.setSingleObject(true);
        } else {
            result.setResult(ExecuteResult.ResultStatement.NOT_OK);
            result.setMessage("Manager già assegnato o inesistente");
            result.setSingleObject(false);
        }
        return result;
    }

    public static ExecuteResult<PuntoVendita> getAllPV(){
        ExecuteResult<PuntoVendita> result = new ExecuteResult<>();
        ArrayList<PuntoVendita> puntiVendita = (ArrayList<PuntoVendita>) puntoVenditaDAO.loadAllPuntiVendita();
        puntiVendita.removeIf(puntoVendita -> isUndefined(puntoVendita.getNome()));
        result.setObject(puntiVendita);
        result.setResult(ExecuteResult.ResultStatement.OK);
        result.setMessage("Punti vendita caricati con successo");
        return result;
    }

    public static ExecuteResult<Magazzino> getAllMagazziniOfPV(int idPV){
        ExecuteResult<Magazzino> result = new ExecuteResult<>();
        ArrayList<Magazzino> magazzini = (ArrayList<Magazzino>) magazzinoDAO.loadMagazziniOfPuntoVendita(idPV);
        magazzini.removeIf(magazzino -> isUndefined(magazzino.getIndirizzo().getNazione()));
        result.setObject(magazzini);
        result.setResult(ExecuteResult.ResultStatement.OK);
        result.setMessage("Magazzini caricati con successo");
        return result;
    }

    public static boolean isUndefined(String string){
        return string.equals("undefined");
    }

}

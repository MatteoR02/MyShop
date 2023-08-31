package Business;

import DAO.*;
import Model.Manager;
import Model.Prodotto;
import Model.PuntoVendita;
import Model.Utente;

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


}

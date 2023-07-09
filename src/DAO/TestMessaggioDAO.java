package DAO;

import Model.Messaggio;

public class TestMessaggioDAO {
    public static void main(String[] args) {
        Messaggio testMessaggio = new Messaggio();
        testMessaggio.setTitolo("Aiuto");
        testMessaggio.setTesto("Ho un problema con la lista");
        testMessaggio.setStatoMessaggio(Messaggio.StatoMessaggioType.INVIATO);

        MessaggioDAO messaggioDAO = MessaggioDAO.getInstance();

        //messaggioDAO.addMessaggio(testMessaggio);

        messaggioDAO.sendMessaggioToManager(4,20,21);



    }
}

package DAO;

import Model.Messaggio;

import java.util.List;

public interface IMessaggioDAO {

    Messaggio loadMessaggio(int idMessaggio);
    List<Messaggio> loadMessaggi();
    List<Messaggio> loadMessaggiOfCliente(int idCliente);
    List<Messaggio> loadMessaggiOfManager(int idManager);

    int addMessaggio(Messaggio messaggio);
    int updateMessaggio(Messaggio messaggio);
    int removeMessaggio(int idMessaggio);

    int sendMessaggioToManager(int idMessaggio, int idCliente, int idManager);
    int sendMessaggioToCliente(int idMessaggio, int idCliente, int idManager);
}

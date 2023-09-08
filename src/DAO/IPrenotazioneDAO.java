package DAO;

import Model.IProdotto;
import Model.Prenotazione;


import java.util.ArrayList;
import java.util.Map;

public interface IPrenotazioneDAO {

    boolean isPrenotazione(int idPrenotazione);

    Prenotazione loadPrenotazione(int idPrenotazione);
    ArrayList<Prenotazione> loadAllPrenotazioni();

    Map<IProdotto, Integer> loadProdottiFromPrenotazione(int idPrenotazione);

    ArrayList<Prenotazione> loadPrenotazioniOfCliente(int idCliente);
    ArrayList<Prenotazione> loadPrenotazioniOfPV(int idPV);

    int removeProdottiFromPrenotazione(int idPrenotazione);
    int removeProdottoFromPrenotazione(int idPrenotazione, int idProdotto);
    int removeProdottoFromAllPrenotazioni(int idProdotto);

    int addPrenotazione(Prenotazione prenotazione);
    int updatePrenotazione(Prenotazione prenotazione);
    int removePrenotazione(int idPrenotazione);

    boolean isArrivata(int idPrenotazione);

    int setFKProdottoToDefault(int idProdotto);


}

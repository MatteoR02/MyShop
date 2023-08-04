package View.ViewModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaTableModel extends AbstractTableModel {

    private List<RigaArticoloLista> righe = new ArrayList<RigaArticoloLista>();
    private Map<RigaArticoloLista, Float> rigaPrezzo = new HashMap<>();

    public ListaTableModel(List<RigaArticoloLista> righe) {
        this.righe = righe;
    }

    @Override
    public int getRowCount() {
        return righe.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    public List<RigaArticoloLista> getRighe() {
        return righe;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        RigaArticoloLista riga = righe.get(rowIndex);

        String prezzoFormattato = String.format("%.2f", riga.getPrezzo());

        switch (columnIndex) {
            case 0: return riga.getIdArticolo();
            case 1: return riga.getNome();
            case 2: return riga.getQuantita();
            case 3: return prezzoFormattato + " €";
            case 4: return riga.getCategoria();
            case 5: return riga.getProduttore();
            case 6: return riga.getSelezionato();
        }

        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){

        RigaArticoloLista riga = righe.get(rowIndex);
        float prezzoBase;
        float prezzoAggiornato=0;

        if (columnIndex == 2){
            if (!rigaPrezzo.containsKey(riga)) {
                prezzoBase = riga.getPrezzo() / riga.getQuantita();
                rigaPrezzo.put(riga, prezzoBase);
            }
            if(Integer.parseInt(value.toString())!=0) {
                prezzoAggiornato = rigaPrezzo.get(riga) * Integer.parseInt(value.toString());
            } else {
                prezzoAggiornato =0;
            }
        }

        switch (columnIndex) {
            case 2: riga.setQuantita(Integer.parseInt(value.toString()));
            case 3: riga.setPrezzo(prezzoAggiornato);
            case 6: riga.setSelezionato(Boolean.parseBoolean(value.toString()));
        }


    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2 || columnIndex ==6;
    }

    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex) {
            case 0: return "ID Prodotto";
            case 1: return "Nome";
            case 2: return "Quantità";
            case 3: return "Prezzo";
            case 4: return "Categoria";
            case 5: return "Produttore";
        }
        return null;
    }

    @Override
    public Class getColumnClass(int columnIndex){
        if(columnIndex == 6){
            return Boolean.class;
        }
        return Object.class;
    }
}

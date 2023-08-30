package View.ViewModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrenotazioniClienteTableModel extends AbstractTableModel {

    private List<RigaPrenotazioneLista> righe = new ArrayList<RigaPrenotazioneLista>();

    public PrenotazioniClienteTableModel(List<RigaPrenotazioneLista> righe) {
        this.righe = righe;
    }

    @Override
    public int getRowCount() {
        return righe.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    public List<RigaPrenotazioneLista> getRighe() {
        return righe;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        RigaPrenotazioneLista riga = righe.get(rowIndex);

        switch (columnIndex) {
            case 0: return riga.getIdArticolo();
            case 1: return riga.getNome();
            case 2: return riga.getQuantita();
            case 3: return riga.getSelezionato();
        }

        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){

        RigaPrenotazioneLista riga = righe.get(rowIndex);

        switch (columnIndex) {
            case 2: riga.setQuantita(Integer.parseInt(value.toString()));
            case 3: riga.setSelezionato(Boolean.parseBoolean(value.toString()));
        }


    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2 || columnIndex ==3;
    }

    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex) {
            case 0: return "ID Prodotto";
            case 1: return "Nome";
            case 2: return "Quantità";
            case 3: return "Selezionato";
        }
        return null;
    }

    @Override
    public Class getColumnClass(int columnIndex){
        if(columnIndex == 3){
            return Boolean.class;
        }
        return Object.class;
    }
}

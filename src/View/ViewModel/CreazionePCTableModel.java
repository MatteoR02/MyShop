package View.ViewModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CreazionePCTableModel extends AbstractTableModel {

    private List<RigaProdottoCreazione> righe = new ArrayList<RigaProdottoCreazione>();


    public CreazionePCTableModel(List<RigaProdottoCreazione> righe) {
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

    public List<RigaProdottoCreazione> getRighe() {
        return righe;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        RigaProdottoCreazione riga = righe.get(rowIndex);

        String prezzoFormattato = String.format("%.2f", riga.getPrezzo());
        String isPCFormattato = "";

        if (riga.isProdottoComposito()){
            isPCFormattato = "Si";
        } else {
            isPCFormattato = "No";
        }

        switch (columnIndex) {
            case 0: return riga.getIdProdotto();
            case 1: return riga.getNome();
            case 2: return prezzoFormattato + " €";
            case 3: return riga.getCategoria();
            case 4: return riga.getErogatore();
            case 5: return isPCFormattato;
            case 6: return riga.getSelezionato();
        }

        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){

        RigaProdottoCreazione riga = righe.get(rowIndex);

        if (columnIndex == 6) {
            riga.setSelezionato(Boolean.parseBoolean(value.toString()));
        }


    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6;
    }

    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex) {
            case 0: return "ID Articolo";
            case 1: return "Nome";
            case 2: return "Prezzo";
            case 3: return "Categoria";
            case 4: return "Erogatore";
            case 5: return "Prodotto Composito";
            case 6: return "Selezionato";
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

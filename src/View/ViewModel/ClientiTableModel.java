package View.ViewModel;

import Model.Cliente;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClientiTableModel extends AbstractTableModel {

    private List<RigaCliente> righe = new ArrayList<RigaCliente>();

    public ClientiTableModel(List<RigaCliente> righe) {
        this.righe = righe;
    }

    @Override
    public int getRowCount() {
        return righe.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    public List<RigaCliente> getRighe() {
        return righe;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        RigaCliente riga = righe.get(rowIndex);



        switch (columnIndex) {
            case 0: return riga.getIdCliente();
            case 1: return riga.getNome();
            case 2: return riga.getCognome();
            case 3: return riga.getUsername();
            case 4: return riga.getEmail();
            case 5: return riga.getProfessione();
            case 6: return riga.getCanalePreferito();
            case 7: return riga.getStato();
            case 8: return riga.getSelezionato();
        }

        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){

        RigaCliente riga = righe.get(rowIndex);

        switch (columnIndex) {
            case 0: riga.setIdCliente(Integer.parseInt(value.toString()));
            case 1: riga.setNome(value.toString());
            case 2: riga.setCognome(value.toString());
            case 3: riga.setUsername(value.toString());
            case 4: riga.getEmail();
            case 5: riga.setProfessione(Cliente.ProfessioneType.valueOf(value.toString()));
            case 6: riga.setCanalePreferito(Cliente.CanalePreferitoType.valueOf(value.toString()));
            case 7: riga.setStato(Cliente.StatoUtenteType.valueOf(value.toString()));
            case 8: riga.setSelezionato(Boolean.parseBoolean(value.toString()));
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 8;
    }

    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex) {
            case 0: return "ID cliente";
            case 1: return "Nome";
            case 2: return "Cognome";
            case 3: return "Username";
            case 4: return "Email";
            case 5: return "Professione";
            case 6: return "Canale Preferito";
            case 7: return "Stato";
        }
        return null;
    }

    @Override
    public Class getColumnClass(int columnIndex){
        if(columnIndex == 8){
            return Boolean.class;
        }
        return Object.class;
    }
}

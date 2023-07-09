package View.ViewModel;

import View.ViewModel.RigaCatalogo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CatalogoTableModel extends AbstractTableModel {

    private List<RigaCatalogo> righe = new ArrayList<RigaCatalogo>();

    public CatalogoTableModel(List<RigaCatalogo> righe) {
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

    public List<RigaCatalogo> getRighe() {
        return righe;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        RigaCatalogo riga = righe.get(rowIndex);



        switch (columnIndex) {
            case 0: return riga.getIdProdotto();
            case 1: return riga.getNomeProdotto();
            case 2: return riga.getNomeProduttore();
            case 3: return riga.getNomeCategoria();
            case 4: return riga.getPrezzo();
            case 5: return riga.getSelezionato();
        }

        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){

        RigaCatalogo riga = righe.get(rowIndex);

        switch (columnIndex) {
            case 0: riga.setIdProdotto(Integer.parseInt(value.toString()));
            case 1: riga.setNomeProdotto(value.toString());
            case 2: riga.setNomeProduttore(value.toString());
            case 3: riga.setNomeCategoria(value.toString());
            case 4: riga.setPrezzo(Float.parseFloat(value.toString()));
            case 5: riga.setSelezionato(Boolean.parseBoolean(value.toString()));
        }
        System.out.println("Cambiato");
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 4 && columnIndex <=5;
    }

    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex) {
            case 0: return "ID prodotto";
            case 1: return "Nome";
            case 2: return "Produttore";
            case 3: return "Categoria";
            case 4: return "Prezzo ()";
            case 5: return "Seleziona";
            case 6: return "Immagine";
        }
        return null;
    }

    @Override
    public Class getColumnClass(int columnIndex){
        if(columnIndex == 5){
            return Boolean.class;
        }
        if(columnIndex == 6) return ImageIcon.class;
        return Object.class;
    }
}

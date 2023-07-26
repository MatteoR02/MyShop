package View.ViewModel;

import javax.swing.*;
import java.awt.*;

public class SpinnerColoumnModel extends DefaultCellEditor {
    JSpinner sp;
    JSpinner.DefaultEditor defaultEditor;
    JTextField text;
    // Initialize the spinner
    public SpinnerColoumnModel() {
        super(new JTextField());
        sp = new JSpinner();
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, null, 1);
        sp.setModel(spinnerModel);
        defaultEditor = ((JSpinner.DefaultEditor)sp.getEditor());
        text = defaultEditor.getTextField();
    }
    // Prepare the spinner component and return it
    public Component getTableCellEditorComponent(JTable table, Object
            value, boolean isSelected, int row, int column)
    {
        sp.setValue(value);
        return sp;
    }
    // Returns the current value of the spinners
    public Object getCellEditorValue() {
        return sp.getValue();
    }
}
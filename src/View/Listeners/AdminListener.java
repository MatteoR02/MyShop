package View.Listeners;

import Business.ArticoloBusiness;
import Business.ExecuteResult;
import Business.RecensioneBusiness;
import View.MainPage;
import View.ViewModel.ComponenteCatalogo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminListener implements ActionListener {

    public final static String ASSIGN_MANAGER = "assign_manager";
    public final static String CREA_MANAGER = "crea_manager";
    public final static String CREA_ARTICOLO = "crea_articolo";
    public final static String MODIFICA_ARTICOLO = "modifica_articolo";
    public final static String ELIMINA_ARTICOLO = "elimina_articolo";

    private MainPage frame;
    private ComponenteCatalogo componenteCatalogo;

    public AdminListener(MainPage frame) {
        this.frame = frame;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    public AdminListener(MainPage frame, ComponenteCatalogo componenteCatalogo) {
        this.frame = frame;
        this.componenteCatalogo = componenteCatalogo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (ASSIGN_MANAGER.equals(action)) {

        } else if (MODIFICA_ARTICOLO.equals(action)){

        } else if (ELIMINA_ARTICOLO.equals(action)){
            int input = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare l'articolo?", "Eliminare articolo?",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input==0){
                ExecuteResult<Boolean> result = ArticoloBusiness.removeArticolo(componenteCatalogo.getIdArticolo());
                JOptionPane.showMessageDialog(frame,"Articolo eliminato", "Articolo eliminato", JOptionPane.INFORMATION_MESSAGE);
                frame.mostraCatalogo(null, false);
            }
        }
    }
}

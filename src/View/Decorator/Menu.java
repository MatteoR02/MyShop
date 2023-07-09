package View.Decorator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    protected List<JButton> pulsantiCentro = new ArrayList<>();
    protected List<JButton> pulsantiSud = new ArrayList<>();

    public List<JButton> getPulsantiCentro() {
        return pulsantiCentro;
    }
    public List<JButton> getPulsantiSud() {return pulsantiSud;}
}

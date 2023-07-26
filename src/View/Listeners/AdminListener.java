package View.Listeners;

import Business.LoginResult;
import Business.UtenteBusiness;
import View.MainPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminListener implements ActionListener {

    public final static String ASSIGN_MANAGER = "assign_manager";

    private MainPage frame;

    public AdminListener(MainPage frame) {
        this.frame = frame;
    }

    public void setFrame(MainPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (ASSIGN_MANAGER.equals(action)) {

        }
    }
}

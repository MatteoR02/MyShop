package Business.Email;

import Model.Manager;

import java.io.File;

public class MessaggioManagerEmail extends Email{

    private Manager manager;

    public MessaggioManagerEmail(String destinatario, String oggetto, String corpo, File allegato, Manager manager) {
        super(destinatario, oggetto, corpo, allegato);
        this.manager = manager;
        this.corpo = corpo + "<br><br><br> <i>Messaggio inviato da manager: " + manager.getUsername()+"</i>";
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void inviaEmail(){
        EmailSender.sendEmail(destinatario, oggetto, corpo, allegato);
    }


}

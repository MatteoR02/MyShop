package Business.Email;

import java.io.File;

public abstract class Email {

    protected String destinatario;
    protected String oggetto;
    protected String corpo;
    protected File allegato;

    public Email(String destinatario, String oggetto, String corpo, File allegato) {
        this.destinatario = destinatario;
        this.oggetto = oggetto;
        this.corpo = corpo;
        this.allegato = allegato;
    }

    public Email(){
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public File getAllegato() {
        return allegato;
    }

    public void setAllegato(File allegato) {
        this.allegato = allegato;
    }

    public abstract void inviaEmail();
}

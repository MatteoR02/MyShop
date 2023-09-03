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

    public Email() {
    }

    public abstract void inviaEmail();
}

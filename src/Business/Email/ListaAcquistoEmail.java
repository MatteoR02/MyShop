package Business.Email;

import Business.Bridge.DocumentoListaAcquisto;
import Business.Bridge.PdfBoxAPI;
import Model.ListaAcquisto;

public class ListaAcquistoEmail extends Email{

    private ListaAcquisto listaAcquisto;

    public ListaAcquistoEmail(String destinatario, ListaAcquisto listaAcquisto) {
        this.destinatario = destinatario;
        this.listaAcquisto = listaAcquisto;
        this.oggetto = "MyShop lista d'acquisto: " + listaAcquisto.getNome();
        this.corpo = "<h2>Lista d'acquisto da stampare e portare al punto vendita</h2>";
        DocumentoListaAcquisto documentoListaAcquisto = new DocumentoListaAcquisto(listaAcquisto, new PdfBoxAPI());
        this.allegato = documentoListaAcquisto.getFile();
    }

    public void setListaAcquisto(ListaAcquisto listaAcquisto) {
        this.listaAcquisto = listaAcquisto;
    }

    @Override
    public void inviaEmail(){
        EmailSender.sendEmail(this);
    }

}

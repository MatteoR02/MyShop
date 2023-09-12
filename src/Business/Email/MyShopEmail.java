package Business.Email;

import Business.Bridge.DocumentoListaAcquisto;
import Business.Bridge.PdfBoxAPI;
import Model.ListaAcquisto;

import java.io.File;

public class MyShopEmail extends Email{

    public MyShopEmail(String destinatario, String oggetto, String corpo, File allegato) {
        super(destinatario, oggetto, corpo, allegato);
    }

    @Override
    public void inviaEmail(){
        EmailSender.sendEmail(this);
    }

}

package Business.Bridge;

import java.io.File;

public abstract class Documento {

    protected PdfAPI pdfAPI;

    public Documento(PdfAPI pdfAPI) {
        this.pdfAPI = pdfAPI;
    }

    public abstract File getFile();

}

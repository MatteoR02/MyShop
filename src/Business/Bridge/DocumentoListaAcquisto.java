package Business.Bridge;

import Model.Articolo;
import Model.ListaAcquisto;


import java.io.File;
import java.util.*;

public class DocumentoListaAcquisto extends Documento {

    private ListaAcquisto lista;

    public DocumentoListaAcquisto(ListaAcquisto lista, PdfAPI pdfAPI) {
        super(pdfAPI);
        this.lista = lista;
    }

    @Override
    public File getFile() {

        Map<Articolo, Integer> articoli = lista.getArticoli();
        String text = "";
        Iterator<Articolo> i = articoli.keySet().iterator();
        ArrayList<String> lines = new ArrayList<>();
        lines.add(lista.getNome().toUpperCase(Locale.ROOT));
        float totale = 0;
        while(i.hasNext()) {
            Articolo a = i.next();
            text = "ID: " + a.getId() + " " + a.getClass().getSimpleName().toUpperCase(Locale.ROOT) + " - " + articoli.get(a) + "x " + a.getNome()+" " + a.getPrezzo() * articoli.get(a) + "€ ";
            totale += a.getPrezzo() * articoli.get(a);
            lines.add(text);
        }
        lines.add("Totale da pagare: " + totale + "€");
        lines.add("");
        String codice = Integer.toHexString(lista.getId() * 16102022);
        lines.add("Codice identificativo: " + codice);

        try {
            File tempFile = new File("C:\\Users\\matte\\Desktop\\Progetti IntelliJ\\MyShop\\PDFOutput\\MyShop_lista " + lista.getNome() +".pdf");
            //File tempFile = File.createTempFile("MyShop_lista " + lista.getNome(), ".pdf");
            System.out.println(tempFile);
            pdfAPI.creaPdf(lines, tempFile.getAbsolutePath());
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

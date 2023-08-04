package Business.Bridge;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.util.ArrayList;

public class PdfBoxAPI implements PdfAPI {

    @Override
    public void creaPdf(ArrayList<String> lines, String outfile) {

        try (PDDocument doc = new PDDocument())
        {
            PDPage page = new PDPage();
            doc.addPage(page);

            //PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page))
            {
                contents.beginText();
                contents.setFont(PDType1Font.HELVETICA, 12);
                contents.newLineAtOffset(100, 700);
                contents.setLeading(25.0f);
                for (String text: lines ) {
                    contents.showText(text);
                    contents.newLine();
                }
                contents.endText();
            }

            doc.save(outfile);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

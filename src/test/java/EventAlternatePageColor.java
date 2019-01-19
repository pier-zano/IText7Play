

import java.io.IOException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

public class EventAlternatePageColor implements IEventHandler {
	private Color even;
	private Color odd;
	private PdfFont helvetica;
	
	public EventAlternatePageColor(Color oddColor, Color evenColor) throws IOException {
		even = evenColor;
		odd = oddColor;
		helvetica = PdfFontFactory.createFont(StandardFonts.HELVETICA);
	}
	
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        int pageNumber = pdfDoc.getPageNumber(page);
        Rectangle pageSize = page.getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(
            page.newContentStreamBefore(), page.getResources(), pdfDoc);

        //Set background
        pdfCanvas.saveState()
                .setFillColor(pageNumber % 2 == 1 ? odd : even)
                .rectangle(pageSize.getLeft(), pageSize.getBottom(),
                    pageSize.getWidth(), pageSize.getHeight())
                .fill().restoreState();
        //Add header and footer
        pdfCanvas.beginText()
                .setFontAndSize(helvetica, 9)
                .moveText(pageSize.getWidth() / 2 - 60, pageSize.getTop() - 20)
                .showText("THE TRUTH IS OUT THERE")
                .moveText(60, -pageSize.getTop() + 30)
                .showText(String.valueOf(pageNumber))
                .endText();
        //Add watermark
        Canvas canvas = new Canvas(pdfCanvas, pdfDoc, page.getPageSize());
        canvas.setFontColor(new DeviceRgb(255, 255, 255));
        canvas.setProperty(Property.FONT_SIZE, UnitValue.createPointValue(70));
        canvas.setProperty(Property.FONT, helvetica);
        canvas.showTextAligned(new Paragraph("CONFIDENTIAL"),
            298, 421, pdfDoc.getPageNumber(page),
            TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);

        pdfCanvas.release();
    }
}
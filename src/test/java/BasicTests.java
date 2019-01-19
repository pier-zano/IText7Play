

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

public class BasicTests extends BaseTestReports {

	Color green = new DeviceRgb(10, 220, 8);
	Color ltgray = new DeviceRgb(210, 210, 210);
	Color yellow = new DeviceRgb(255, 190, 40);
	Color lime = new DeviceCmyk(0.208f, 0, 0.584f, 0);
	Color blue = new DeviceCmyk(0.445f, 0.0546f, 0, 0.0667f);

	@Test
	public void helloWorld() {
		File fOut = getFileOutput("helloworld.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			document.add(new Paragraph("Hello World!"));
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void helloWorld2() {
		File fOut = getFileOutput("helloworld2.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			Paragraph p = new Paragraph("Hello World con bordi arrotondati e padding!");
			// p.setBackgroundColor(new
			// com.itextpdf.kernel.colors.Color(PdfColorSpace.makeColorSpace(pdfObject) 210,
			// 187, 76));
			Border b = new SolidBorder(1f);
			BorderRadius br = new BorderRadius(2f);
			p.setBorder(b);
			p.setBorderRadius(br);
			p.setPaddings(5, 10, 5, 10);

			document.add(p);
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void helloWorld3() {
		File fOut = getFileOutput("helloworld3.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			Paragraph p = new Paragraph("Hello World con sfondo verde e altezza fissa");

			Color green = new DeviceRgb(10, 220, 8);
			p.setBackgroundColor(green);
			p.setHeight(200);

			document.add(p);
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void helloWorldFonts() {
		File fOut = getFileOutput("helloworldFonts.pdf");
		final String REGULAR = "fonts/constan.ttf";
		final String BOLD = "fonts/constanb.ttf";
		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			
			PdfFont font_reg = PdfFontFactory.createFont(getClass().getResource(REGULAR).toString(), true);
			
			Paragraph p = new Paragraph("Hello World con font constantia");
			p.setFont(font_reg);
			p.setFontSize(15);

			Color green = new DeviceRgb(10, 220, 8);
			p.setBackgroundColor(green);
			p.setHeight(200);

			document.add(p);
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void canvas() {
		File fOut = getFileOutput("canvas.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			PageSize ps = PageSize.A4.rotate();
			PdfPage page = pdf.addNewPage(ps);
			PdfCanvas canvas = new PdfCanvas(page);
			// Replace the origin of the coordinate system to the center of the page
			canvas.concatMatrix(1, 0, 0, 1, ps.getWidth() / 2, ps.getHeight() / 2);
			// Draw the axes
			drawAxes(canvas, ps);
			pdf.close();

			pdf.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void gridLines() {
		File fOut = getFileOutput("gridLines.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			PageSize ps = PageSize.A4.rotate();
			PdfPage page = pdf.addNewPage(ps);
			PdfCanvas canvas = new PdfCanvas(page);
			// Replace the origin of the coordinate system to the center of the page
			canvas.concatMatrix(1, 0, 0, 1, ps.getWidth() / 2, ps.getHeight() / 2);

			Color grayColor = new DeviceCmyk(0.f, 0.f, 0.f, 0.875f);
			Color greenColor = new DeviceCmyk(1.f, 0.f, 1.f, 0.176f);
			Color blueColor = new DeviceCmyk(1.f, 0.156f, 0.f, 0.118f);

			canvas.setLineWidth(0.5f).setStrokeColor(blueColor);

			// Draw horizontal grid lines
			for (int i = -((int) ps.getHeight() / 2 - 57); i < ((int) ps.getHeight() / 2 - 56); i += 40) {
				canvas.moveTo(-(ps.getWidth() / 2 - 15), i).lineTo(ps.getWidth() / 2 - 15, i);
			}
			// Draw vertical grid lines
			for (int j = -((int) ps.getWidth() / 2 - 61); j < ((int) ps.getWidth() / 2 - 60); j += 40) {
				canvas.moveTo(j, -(ps.getHeight() / 2 - 15)).lineTo(j, ps.getHeight() / 2 - 15);
			}
			canvas.stroke();

			// Draw axes
			canvas.setLineWidth(3).setStrokeColor(grayColor);
			drawAxes(canvas, ps);

			// Draw plot
			canvas.setLineWidth(2).setStrokeColor(greenColor).setLineDash(10, 10, 8)
					.moveTo(-(ps.getWidth() / 2 - 15), -(ps.getHeight() / 2 - 15))
					.lineTo(ps.getWidth() / 2 - 15, ps.getHeight() / 2 - 15).stroke();

			pdf.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	private void drawAxes(PdfCanvas canvas, PageSize ps) {
		// Draw X axis
		canvas.moveTo(-(ps.getWidth() / 2 - 15), 0).lineTo(ps.getWidth() / 2 - 15, 0).stroke();

		// Draw X axis arrow
		canvas.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND).moveTo(ps.getWidth() / 2 - 25, -10)
				.lineTo(ps.getWidth() / 2 - 15, 0).lineTo(ps.getWidth() / 2 - 25, 10).stroke()
				.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.MITER);

		// Draw Y axis
		canvas.moveTo(0, -(ps.getHeight() / 2 - 15)).lineTo(0, ps.getHeight() / 2 - 15).stroke();

		// Draw Y axis arrow
		canvas.saveState().setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND).moveTo(-10, ps.getHeight() / 2 - 25)
				.lineTo(0, ps.getHeight() / 2 - 15).lineTo(10, ps.getHeight() / 2 - 25).stroke().restoreState();

		// Draw X serif
		for (int i = -((int) ps.getWidth() / 2 - 61); i < ((int) ps.getWidth() / 2 - 60); i += 40) {
			canvas.moveTo(i, 5).lineTo(i, -5);
		}
		// Draw Y serif
		for (int j = -((int) ps.getHeight() / 2 - 57); j < ((int) ps.getHeight() / 2 - 56); j += 40) {
			canvas.moveTo(5, j).lineTo(-5, j);
		}
		canvas.stroke();
	}

	@Test
	public void list() {
		File fOut = getFileOutput("list.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);

			// Create a PdfFont
			PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
			// Add a Paragraph
			document.add(new Paragraph("iText is:").setFont(font));
			// Create a List
			List list = new List().setSymbolIndent(12).setListSymbol("\u2022").setFont(font);
			// Add ListItem objects
			list.add(new ListItem("Never gonna give you up")).add(new ListItem("Never gonna let you down"))
					.add(new ListItem("Never gonna run around and desert you"))
					.add(new ListItem("Never gonna make you cry")).add(new ListItem("Never gonna say goodbye"))
					.add(new ListItem("Never gonna tell a lie and hurt you"));
			// Add the list
			document.add(list);

			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void image() {
		File fOut = getFileOutput("image.pdf");

		final String DOG = "/images/dog.jpg";
		final String FOX = "/images/fox.jpg";

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);

			Image fox = new Image(ImageDataFactory.create(getClass().getResource(FOX)));
			fox.setWidth(80);
			Image dog = new Image(ImageDataFactory.create(getClass().getResource(DOG)));
			dog.setWidth(80);
			Paragraph p = new Paragraph("The quick brown ").add(fox).add(" jumps over the lazy ").add(dog);
			document.add(p);

			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}

	@Test
	public void columns() {
		File fOut = getFileOutput("columns.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			PageSize ps = PageSize.A4;
			Document document = new Document(pdf, ps);

			// Set column parameters
			float offSet = 36;
			float columnWidth = (ps.getWidth() - offSet * 2 + 10) / 3;
			float columnHeight = ps.getHeight() - offSet * 2;

			// Define column areas
			Rectangle[] columns = { new Rectangle(offSet - 5, offSet, columnWidth, columnHeight),
					new Rectangle(offSet + columnWidth, offSet, columnWidth, columnHeight),
					new Rectangle(offSet + columnWidth * 2 + 5, offSet, columnWidth, columnHeight) };
			document.setRenderer(new ColumnDocumentRenderer(document, columns));

			// adding content
			for (int i = 1; i <= 2; i++) {
				String basePath = "/articles/art" + i;
				Image img = new Image(ImageDataFactory.create(getClass().getResource(basePath + "_img.jpg")))
						.setWidth(columnWidth);
				String titolo = new String(
						Files.readAllBytes(Paths.get(getClass().getResource(basePath + "_tit.txt").toURI())),
						StandardCharsets.UTF_8);
				String autore = new String(
						Files.readAllBytes(Paths.get(getClass().getResource(basePath + "_aut.txt").toURI())),
						StandardCharsets.UTF_8);
				String testo = new String(
						Files.readAllBytes(Paths.get(getClass().getResource(basePath + "_text.txt").toURI())),
						StandardCharsets.UTF_8);
				addArticle(document, titolo, autore, img, testo);
			}

			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}

	private void addArticle(Document doc, String title, String author, Image img, String text) throws IOException {
		PdfFont roman = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

		Style titolo = new Style();
		titolo.setFont(roman).setBold().setBackgroundColor(new DeviceRgb(200, 200, 200)).setFontSize(14)
				.setTextAlignment(TextAlignment.CENTER).setPadding(3).setBorderRadius(new BorderRadius(2f));

		Style autore = new Style();
		autore.setFont(roman).setItalic().setFontColor(new DeviceRgb(150, 150, 150)).setFontSize(10)
				.setTextAlignment(TextAlignment.RIGHT);

		Style testo = new Style();
		testo.setFont(roman).setFontColor(new DeviceRgb(30, 30, 30)).setFontSize(12);

		Paragraph p1 = new Paragraph(title).addStyle(titolo);
		doc.add(p1);
		doc.add(img);
		Paragraph p2 = new Paragraph(author).addStyle(autore);
		doc.add(p2);

		Paragraph p3 = new Paragraph(text).addStyle(testo);
		doc.add(p3);
	}

	@Test
	public void basicTable() {
		File fOut = getFileOutput("basicTable.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			PageSize ps = PageSize.A4;
			Document document = new Document(pdf, ps);

			Table table = new Table(new float[] { 20f, 25, 10 });
			table.setWidth(UnitValue.createPercentValue(80)).setTextAlignment(TextAlignment.CENTER)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);

			Cell cell = new Cell().add(new Paragraph("H1"));
			cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
			cell.setPadding(5).setBorder(null);
			table.addHeaderCell(cell);

			cell = new Cell().add(new Paragraph("H2"));
			cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
			cell.setPadding(5).setBorder(null);
			table.addHeaderCell(cell);

			cell = new Cell().add(new Paragraph("H3"));
			cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
			cell.setPadding(5).setBorder(null);
			table.addHeaderCell(cell);

			cell = new Cell().add(new Paragraph("C11"));
			cell.setPadding(2);
			table.addCell(cell);
			cell = new Cell(1, 2).add(new Paragraph("C12"));
			cell.setPadding(2);
			table.addCell(cell);

			cell = new Cell(1, 3).add(new Paragraph("C21"));
			cell.setPadding(2).setBackgroundColor(yellow);
			table.addCell(cell);

			Paragraph p = new Paragraph("P1").add(new Paragraph("P2").add(new Paragraph("P3")));

			cell = new Cell(1, 3).add(new Paragraph("C31"));
			cell.add(p);
			cell.setPadding(2).setBackgroundColor(ltgray);
			table.addCell(cell);

			document.add(table);

			document.add(p);
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}

	@Test
	public void basicParagraphs() {
		File fOut = getFileOutput("basicParagraphs.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			PageSize ps = PageSize.A4;
			Document document = new Document(pdf, ps);

			Border b = new DashedBorder(blue, 2f, 1f);
			
			//un paragrafo aggiunto ad un altro non mantiene il block nè l'allineamento
			Paragraph p2 = new Paragraph("P2").setTextAlignment(TextAlignment.CENTER);
			Paragraph p1 = new Paragraph("P1").setBorder(b);
			p1.add(p2);

			Paragraph p3 = new Paragraph("P3_").add("\n").add(p2).setBorder(b);

			document.add(p1);
			document.add(p2);
			document.add(p3);
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void basicParagraphsDiv() {
		File fOut = getFileOutput("basicParagraphsDiv.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			PageSize ps = PageSize.A4;
			Document document = new Document(pdf, ps);

			Border b = new DashedBorder(blue, 2f, 1f);
			
			//un paragrafo aggiunto ad un altro non mantiene il block nè l'allineamento
			Paragraph p2 = new Paragraph("P2").setTextAlignment(TextAlignment.CENTER);
			Div p1 = new Div().setBorder(b);
			p1.add(new Paragraph("p1"));
			p1.add(p2);

			Paragraph p3 = new Paragraph("P3_").add("\n").add(p2).setBorder(b);

			document.add(p1);
			document.add(p2);
			document.add(p3);
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}
	
	@Test
	public void basicParagraphs2() {
		File fOut = getFileOutput("basicParagraphs2.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			PageSize ps = PageSize.A4;
			Document document = new Document(pdf, ps);

			Border b = new DashedBorder(blue, 2f, 1f);
			
			//un paragrafo aggiunto ad un altro non mantiene il block nè l'allineamento
			Paragraph p1 = new Paragraph("P1").setBorder(b);
			Paragraph p2 = new Paragraph("P2").setTextAlignment(TextAlignment.CENTER);
			addToParagraph(p1, p2);

			addToParagraph(p1, "pippo");
			
			Paragraph p3 = new Paragraph("P3").setBorder(b);
			
			addToParagraph(p1, p3);
			addToParagraph(p1, "XX");
			addToParagraph(p1, "YY");
			addToParagraph(p1, new Text("hello"));
			
			document.add(p1);
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}	

	@Test
	public void pageEvents() {
		File fOut = getFileOutput("pageEvents.pdf");

		try {
			PdfWriter writer = new PdfWriter(fOut);
			PdfDocument pdf = new PdfDocument(writer);
			
			pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new EventAlternatePageColor(lime, blue));
			
			Document document = new Document(pdf);

			PdfFont helveticaB = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			PdfFont helvetica = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			
			Paragraph p = new Paragraph("List of reported UFO sightings in 20th century")
					.setTextAlignment(TextAlignment.CENTER).setFont(helveticaB).setFontSize(14);
			document.add(p);
			Table table = new Table(new float[] { 3, 5, 7 });
			table.setWidth(UnitValue.createPercentValue(100));
			for (int i = 0; i < 300; i++) {
				Cell c = new Cell();
				p = new Paragraph(RandomStringUtils.random(12, true, true))
						.setTextAlignment(TextAlignment.LEFT).setFont(helvetica).setFontSize(12);				
				c.add(p);
				table.addCell(c);
			}
			document.add(table);
			document.close();
			openOutputWithReader(fOut);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}
	
	private boolean lastChildBlock; 
	
	private void addToParagraph(Paragraph p, Object child) {
		if (p.getChildren().isEmpty()) {
			lastChildBlock = true;
		}
		if (child instanceof String) {
			if (lastChildBlock) {
				p.add("\n");
			}
			p.add(child.toString());
			lastChildBlock = false;
		}
		else if (child instanceof Text) {
			if (lastChildBlock) {
				p.add("\n");
			}
			p.add((Text)child);
			lastChildBlock = false;
		}
		else if (child instanceof Paragraph) {
			boolean first = p.getChildren().isEmpty();
			if (!first) {
				p.add("\n");
			}
			Paragraph childP = (Paragraph)child;
			for (IElement el : childP.getChildren()) {
				addToParagraph(p, el);
			}
			lastChildBlock = true;
		}
	}

}

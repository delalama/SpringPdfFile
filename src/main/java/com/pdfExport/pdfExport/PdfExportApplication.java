package com.pdfExport.pdfExport;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PdfExportApplication  {

    public static void main(String[] args) {
        SpringApplication.run(PdfExportApplication.class, args);
    }

    @Component
    private class pdfGenerator implements ApplicationRunner {

        private void createPdf() throws com.itextpdf.text.DocumentException, FileNotFoundException {

            PdfOwner pdfOwner = new PdfOwner("Jesús", "de la Lama", "youBelieveAndrew@yumuil.com");
            List<PdfField> fieldList = Arrays.asList
                    (new PdfField("Coltrane", "Sax"),
                            new PdfField("Pat Metheny", "Guitar"),
                            new PdfField("Kurt Rosenwinkel", "Guitar"),
                            new PdfField("Lyle Mays", "Piano"),
                            new PdfField("Hank Mobley", "Sax")
                    );

            String userName = pdfOwner.getName();
            String lastName = pdfOwner.getLastName();
            String email = pdfOwner.getEmail();

            //PDF SETTINGS
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document pdf = new com.itextpdf.text.Document();
            PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("src/main/resources/MyCustomPdf"));
            pdf.open();

            //DOCUMENT TOP
            String documentInit = "OFFICIAL PDF DOCUMENT";
            String documentPurpose = "THIS STANDARD PDF IS CREATED FOR LEARNING PURPOSE, ENJOY LEARNING, :) ";

            pdf.add(new Paragraph(documentInit));
            pdf.add(new Paragraph(documentPurpose));
            pdf.add(new Paragraph("                                          "));

            //DIVIDER
            DottedLineSeparator dottedline = new DottedLineSeparator();
            dottedline.setOffset(-2);
            dottedline.setGap(2f);
            pdf.add(dottedline);
            pdf.add(new Paragraph("                                          "));

            //USER DATA
            PdfPTable userDatatable = new PdfPTable(2);
            userDatatable.setWidthPercentage(60);
            userDatatable.setWidths(new int[]{3, 3});

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("USER NAME", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            userDatatable.addCell(hcell);

            hcell = new PdfPCell(new Phrase("EMAIL", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            userDatatable.addCell(hcell);

            hcell = new PdfPCell(new Phrase(userName + " " + lastName));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            userDatatable.addCell(hcell);

            hcell = new PdfPCell(new Phrase(email));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            userDatatable.addCell(hcell);

            pdf.add(userDatatable);
            pdf.add(new Paragraph("                                          "));

//      FieldList
            PdfPTable fieldDatatable = new PdfPTable(2);
            fieldDatatable.setWidthPercentage(95);
            fieldDatatable.setWidths(new int[]{2, 10});

            hcell = new PdfPCell(new Phrase("FIELD NAME", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            fieldDatatable.addCell(hcell);

            hcell = new PdfPCell(new Phrase("FIELD VALUE", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            fieldDatatable.addCell(hcell);

            fieldList.forEach(field -> {
                PdfPCell hcell1;
                hcell1 = new PdfPCell(new Phrase(field.getFieldName()));
                hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                fieldDatatable.addCell(hcell1);

                hcell1 = new PdfPCell(new Phrase(field.getFieldValue()));
                hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                fieldDatatable.addCell(hcell1);
            });

            pdf.add(fieldDatatable);

            pdf.close();

            writer.close();
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            createPdf();
        }
    }

    @AllArgsConstructor
    @Getter
    private class PdfOwner {
        private String name;
        private String lastName;
        private String email;
    }

    @AllArgsConstructor
    @Getter
    private class PdfField {
        private String fieldName;
        private String fieldValue;
    }

}

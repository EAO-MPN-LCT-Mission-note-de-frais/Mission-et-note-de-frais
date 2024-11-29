package com.diginamic.mission_note_de_frais.util;

import com.diginamic.mission_note_de_frais.model.dto.ExpenseDTO;
import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.dto.SimpleExpenseReportDTO;
import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.service.MissionService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe utilitaire pour générer des documents PDF
 */
@Component
public class PdfGenerator {

    @Autowired
    private MissionService missionService;

    /**
     * Génère un document PDF pour une note de frais donnée et la mission associée.
     *
     * @param expenseReportDTO La note de frais à exporter au format PDF.
     * @param missionDTO La DTO de la mission associée à la note de frais.
     * @param expenseDTOs La liste des lignes de frais sous forme de DTO.
     * @return Un tableau de bytes représentant le document PDF généré.
     * @throws Exception S'il y a une erreur lors de la génération du PDF.
     */
    public byte[] generatePdf(SimpleExpenseReportDTO expenseReportDTO, MissionDTO missionDTO, List<ExpenseDTO> expenseDTOs) throws Exception  {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4, false);
        document.setMargins(50, 50, 50, 50);

        // Définir le format de la date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Style pour le titre principal
        Style titleStyle = new Style();
        titleStyle.setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.BLACK)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));

        // Style pour les sous-titres
        Style subtitleStyle = new Style();
        subtitleStyle.setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setMarginTop(15);

        // Style pour les cellules d'en-tête
        Style headerCellStyle = new Style();
        headerCellStyle.setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setPadding(3);

        // Style pour le texte du total
        Style totalStyle = new Style();
        totalStyle.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontColor(ColorConstants.BLACK);

        // Ajouter le titre
        Paragraph title = new Paragraph("Note de frais").addStyle(titleStyle);
        document.add(title);

        // Utiliser le service pour obtenir les transports pour la mission
        Set<TransportDTO> transports = missionService.getTransportsForMission(missionDTO.getId());
        String transportNames = transports.stream()
                .map(TransportDTO::getName)
                .collect(Collectors.joining(", "));

        // Ajouter les détails de la mission
        Paragraph recapMissionSubtitle = new Paragraph("Récapitulatif de la mission").addStyle(subtitleStyle);
        document.add(recapMissionSubtitle);
        document.add(new Paragraph("Mission : " + missionDTO.getStartTown() + " à " + missionDTO.getEndTown()));
        String formattedStartDate = missionDTO.getStartDate().format(dateFormatter);
        String formattedEndDate = missionDTO.getEndDate().format(dateFormatter);
        document.add(new Paragraph("Période : Du " + formattedStartDate + " au " + formattedEndDate));
        document.add(new Paragraph("Transports : " + transportNames));

        // Créer un tableau pour les dépenses
        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 5, 2, 2})).useAllAvailableWidth().setMarginTop(10).setMarginBottom(10);
        table.addHeaderCell(new Cell().add(new Paragraph("Date").addStyle(headerCellStyle)));
        table.addHeaderCell(new Cell().add(new Paragraph("Nature").addStyle(headerCellStyle)));
        table.addHeaderCell(new Cell().add(new Paragraph("Description").addStyle(headerCellStyle)));
        table.addHeaderCell(new Cell().add(new Paragraph("Montant (€)").addStyle(headerCellStyle)));
        table.addHeaderCell(new Cell().add(new Paragraph("TVA (%)").addStyle(headerCellStyle)));

        // Ajouter les détails des dépenses au tableau
        for (ExpenseDTO expenseDTO : expenseDTOs) {
            String formattedDate = expenseDTO.getDate().format(dateFormatter);
            table.addCell(new Cell().add(new Paragraph(formattedDate)).setPadding(3));
            table.addCell(new Cell().add(new Paragraph(expenseDTO.getExpenseType())).setPadding(3));
            table.addCell(new Cell().add(new Paragraph(expenseDTO.getDescription() != null ? expenseDTO.getDescription() : "")).setPadding(3));
            table.addCell(new Cell().add(new Paragraph(expenseDTO.getAmount() + " €")).setPadding(3));
            table.addCell(new Cell().add(new Paragraph(expenseDTO.getTax() > 0 ? expenseDTO.getTax() + " %" : "")).setPadding(3));
        }

        // Ajouter le tableau au document
        Paragraph tableSubtitle = new Paragraph("Tableau des dépenses").addStyle(subtitleStyle);
        document.add(tableSubtitle);
        document.add(table);

        // Ajouter le montant total
        Paragraph totalParagraph = new Paragraph("Total : " + expenseReportDTO.getAmount() + " €").addStyle(totalStyle);
        document.add(totalParagraph);

        // Fermer le document
        document.close();
        return outputStream.toByteArray();
    }
}

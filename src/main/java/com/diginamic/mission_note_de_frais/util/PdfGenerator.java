package com.diginamic.mission_note_de_frais.util;

import com.diginamic.mission_note_de_frais.model.dto.ExpenseDTO;
import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.dto.SimpleExpenseReportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Classe utilitaire pour générer des documents PDF
 */
public class PdfGenerator {

    /**
     * Génère un document PDF pour une note de frais donnée et la mission associée.
     *
     * @param expenseReportDTO La note de frais à exporter au format PDF.
     * @param missionDTO La DTO de la mission associée à la note de frais.
     * @param expenseDTOs La liste des lignes de frais sous forme de DTO.
     * @return Un tableau de bytes représentant le document PDF généré.
     * @throws Exception S'il y a une erreur lors de la génération du PDF.
     */
    public static byte[] generatePdf(SimpleExpenseReportDTO expenseReportDTO, MissionDTO missionDTO, List<ExpenseDTO> expenseDTOs) throws Exception  {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Style pour le titre principal
        Style titleStyle = new Style();
        titleStyle.setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.BLACK);

        // Style pour les sous-titres
        Style subtitleStyle = new Style();
        subtitleStyle.setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontColor(ColorConstants.DARK_GRAY);

        // Ajouter le titre
        Paragraph title = new Paragraph("Note de frais pour la mission").addStyle(titleStyle);
        document.add(title);

        // Ajouter les détails de la mission
        Paragraph recapMissionSubtitle = new Paragraph("Récapitulatif de la mission").addStyle(subtitleStyle);
        document.add(recapMissionSubtitle);
        document.add(new Paragraph("Mission : " + missionDTO.getStartTown() + " à " + missionDTO.getEndTown()));
        document.add(new Paragraph("Période : " + missionDTO.getStartDate() + " au " + missionDTO.getEndDate()));

        // Créer un tableau pour les dépenses
        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 5, 2, 2})).useAllAvailableWidth();
        table.addHeaderCell("Date");
        table.addHeaderCell("Description");
        table.addHeaderCell("Type de dépense");
        table.addHeaderCell("Montant (€)");
        table.addHeaderCell("TVA (%)");

        // Ajouter les détails des dépenses au tableau
        for (ExpenseDTO expenseDTO : expenseDTOs) {
            table.addCell(expenseDTO.getDate().toString());
            table.addCell(expenseDTO.getExpenseType());
            table.addCell(expenseDTO.getDescription() != null ? expenseDTO.getDescription() : "");
            table.addCell(expenseDTO.getAmount() + " €");
            table.addCell(expenseDTO.getTax() > 0 ? expenseDTO.getTax() + " %" : "");

        }

        // Ajouter le tableau au document
        Paragraph tableSubtitle = new Paragraph("Tableau des dépenses").addStyle(subtitleStyle);
        document.add(tableSubtitle);
        document.add(table);

        // Ajouter le montant total
        document.add(new Paragraph("Total : " + expenseReportDTO.getAmount()+ " €"));

        // Fermer le document
        document.close();
        return outputStream.toByteArray();
    }
}

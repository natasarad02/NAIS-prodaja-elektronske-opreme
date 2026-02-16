package ticket_service.com.service;

import ticket_service.com.model.ServiceTicket;
import ticket_service.com.model.ServiceTicketHistory;
import ticket_service.com.model.ServiceTicketState;
import ticket_service.com.repo.ServiceTicketHistoryRepository;
import ticket_service.com.repo.ServiceTicketRepository;
import ticket_service.com.repo.ServiceTicketStateRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.BaseColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfReportService {

    @Autowired
    private ServiceTicketRepository ticketRepository;

    @Autowired
    private ServiceTicketStateRepository stateRepository;

    @Autowired
    private ServiceTicketHistoryRepository historyRepository;

    @Autowired
    private QueryService queryService;

    private static Font createFont(float size, int style, BaseColor color) {
        try {
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, "Cp1250", BaseFont.EMBEDDED);
            return new Font(baseFont, size, style, color);
        } catch (Exception e) {
            // Fallback to default
            return new Font(Font.FontFamily.HELVETICA, size, style, color);
        }
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final Font TITLE_FONT = createFont(18, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font HEADER_FONT = createFont(14, Font.BOLD, BaseColor.BLACK);
    private static final Font SUBHEADER_FONT = createFont(12, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font NORMAL_FONT = createFont(10, Font.NORMAL, BaseColor.BLACK);
    private static final Font SMALL_FONT = createFont(8, Font.NORMAL, BaseColor.DARK_GRAY);

    /**
     * Generates a comprehensive service ticket report in PDF format
     * Includes simple and complex sections as required
     */
    public byte[] generateComprehensiveReport(
            String stateName,
            Integer minPriority,
            Boolean onlyResolved,
            LocalDateTime fromDate,
            LocalDateTime toDate) throws Exception {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            addReportTitle(document);
            addReportMetadata(document, fromDate, toDate);

            // Prosta sekcija 1: All Active States
            addSimpleSection1_ActiveStates(document);

            // Prosta sekcija 2: Filtered Tickets
            addSimpleSection2_FilteredTickets(document, stateName, minPriority, onlyResolved);

            // Prosta sekcija 3: Recent History
            addSimpleSection3_RecentHistory(document);

            // Kompleksna sekcija 1: Bottleneck Analysis
            addComplexSection1_BottleneckAnalysis(document);

            // Kompleksna sekcija 2: Customer Satisfaction Analysis
            addComplexSection2_SatisfactionAnalysis(document, fromDate, toDate);

            // Summary
            addReportSummary(document);

            document.close();
        } catch (Exception e) {
            document.close();
            throw e;
        }

        return out.toByteArray();
    }

    private void addReportTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("IZVEŠTAJ O SERVISNIM NALOZIMA", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        Paragraph subtitle = new Paragraph("Analiza procesa servisiranja i zadovoljstva korisnika", SUBHEADER_FONT);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(20);
        document.add(subtitle);
    }

    private void addReportMetadata(Document document, LocalDateTime fromDate, LocalDateTime toDate) throws DocumentException {
        Paragraph metadata = new Paragraph();
        metadata.add(new Chunk("Datum generisanja: ", NORMAL_FONT));
        metadata.add(new Chunk(LocalDateTime.now().format(DATE_FORMATTER) + "\n", SMALL_FONT));
        
        if (fromDate != null && toDate != null) {
            metadata.add(new Chunk("Period analize: ", NORMAL_FONT));
            metadata.add(new Chunk(fromDate.format(DATE_FORMATTER) + " - " + toDate.format(DATE_FORMATTER) + "\n", SMALL_FONT));
        }
        
        metadata.setSpacingAfter(15);
        document.add(metadata);
        document.add(new Paragraph(" "));
    }

    private void addSimpleSection1_ActiveStates(Document document) throws DocumentException {
        Paragraph sectionTitle = new Paragraph("1. AKTIVNA STANJA NALOG", HEADER_FONT);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        List<ServiceTicketState> activeStates = stateRepository.findByIsActiveTrueOrderByOrderSequenceAsc();

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        // Headers
        addTableHeader(table, "Naziv Stanja");
        addTableHeader(table, "Opis");
        addTableHeader(table, "Kategorija");
        addTableHeader(table, "Redosled");
        addTableHeader(table, "Zahteva Akciju");

        // Data
        for (ServiceTicketState state : activeStates) {
            addTableCell(table, state.getStateName());
            addTableCell(table, state.getStateDescription());
            addTableCell(table, state.getStateCategory());
            addTableCell(table, String.valueOf(state.getOrderSequence()));
            addTableCell(table, state.getRequiresCustomerAction() ? "Da" : "Ne");
        }

        document.add(table);
        
        Paragraph summary = new Paragraph("Ukupno aktivnih stanja: " + activeStates.size(), SMALL_FONT);
        summary.setSpacingAfter(20);
        document.add(summary);
    }

    private void addSimpleSection2_FilteredTickets(Document document, String stateName, 
                                                   Integer minPriority, Boolean onlyResolved) throws DocumentException {
        Paragraph sectionTitle = new Paragraph("2. PREGLED NALOGA", HEADER_FONT);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        // Show applied filters
        if (stateName != null || minPriority != null || onlyResolved != null) {
            Paragraph filters = new Paragraph("Primenjeni filteri: ", SUBHEADER_FONT);
            if (stateName != null) filters.add(new Chunk("Stanje = " + stateName + "; ", SMALL_FONT));
            if (minPriority != null) filters.add(new Chunk("Minimalni prioritet = " + minPriority + "; ", SMALL_FONT));
            if (onlyResolved != null) filters.add(new Chunk("Samo razrešeni = " + (onlyResolved ? "Da" : "Ne") + "; ", SMALL_FONT));
            filters.setSpacingAfter(10);
            document.add(filters);
        }

        // Get filtered tickets
        List<ServiceTicket> tickets = getFilteredTickets(stateName, minPriority, onlyResolved);
        
        // Limit to first 20 for readability
        List<ServiceTicket> displayTickets = tickets.stream().limit(20).collect(Collectors.toList());

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        // Headers
        addTableHeader(table, "ID");
        addTableHeader(table, "Naslov");
        addTableHeader(table, "Stanje");
        addTableHeader(table, "Prioritet");
        addTableHeader(table, "Korisnik");
        addTableHeader(table, "Status");

        // Data
        for (ServiceTicket ticket : displayTickets) {
            addTableCell(table, ticket.getId().substring(0, Math.min(8, ticket.getId().length())));
            addTableCell(table, ticket.getTitle());
            addTableCell(table, ticket.getCurrentStateName());
            addTableCell(table, String.valueOf(ticket.getPriority()));
            addTableCell(table, ticket.getCustomerName());
            addTableCell(table, ticket.getIsResolved() ? "Razrešen" : "Aktivan");
        }

        document.add(table);
        
        Paragraph summary = new Paragraph("Prikazano: " + displayTickets.size() + " od ukupno " + tickets.size() + " naloga", SMALL_FONT);
        summary.setSpacingAfter(20);
        document.add(summary);
    }

    private void addSimpleSection3_RecentHistory(Document document) throws DocumentException {
        Paragraph sectionTitle = new Paragraph("3. SKORAŠNJE PROMENE STANJA", HEADER_FONT);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        List<ServiceTicketHistory> allHistory = new ArrayList<>();
        historyRepository.findAll().forEach(allHistory::add);
        
        // Sort by date and take last 15
        List<ServiceTicketHistory> recentHistory = allHistory.stream()
            .sorted((h1, h2) -> h2.getChangedAt().compareTo(h1.getChangedAt()))
            .limit(15)
            .collect(Collectors.toList());

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        addTableHeader(table, "Nalog ID");
        addTableHeader(table, "Iz Stanja");
        addTableHeader(table, "U Stanje");
        addTableHeader(table, "Trajanje (min)");
        addTableHeader(table, "Datum Promene");

        for (ServiceTicketHistory history : recentHistory) {
            addTableCell(table, history.getTicketId().substring(0, Math.min(8, history.getTicketId().length())));
            addTableCell(table, history.getFromStateName());
            addTableCell(table, history.getToStateName());
            addTableCell(table, String.valueOf(history.getDurationInPreviousState()));
            addTableCell(table, history.getChangedAt().format(DATE_FORMATTER));
        }

        document.add(table);
        
        Paragraph summary = new Paragraph("Prikazano poslednjih " + recentHistory.size() + " promena", SMALL_FONT);
        summary.setSpacingAfter(20);
        document.add(summary);
    }

    private void addComplexSection1_BottleneckAnalysis(Document document) throws DocumentException {
        Paragraph sectionTitle = new Paragraph("4. ANALIZA USKIH GRLA U PROCESU", HEADER_FONT);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        Map<String, Object> bottleneckData = queryService.analyzeBottlenecks(100L, null);
        
        @SuppressWarnings("unchecked")
        List<Map.Entry<String, Map<String, Object>>> bottlenecks = 
            (List<Map.Entry<String, Map<String, Object>>>) bottleneckData.get("bottlenecksByState");

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        addTableHeader(table, "Stanje");
        addTableHeader(table, "Broj Prelaza");
        addTableHeader(table, "Prosečno Trajanje");
        addTableHeader(table, "Min Trajanje");
        addTableHeader(table, "Max Trajanje");

        for (Map.Entry<String, Map<String, Object>> entry : bottlenecks.stream().limit(10).collect(Collectors.toList())) {
            Map<String, Object> stats = entry.getValue();
            addTableCell(table, entry.getKey());
            addTableCell(table, stats.get("count").toString());
            addTableCell(table, String.format("%.1f min", (Double) stats.get("averageDuration")));
            addTableCell(table, stats.get("minDuration").toString() + " min");
            addTableCell(table, stats.get("maxDuration").toString() + " min");
        }

        document.add(table);

        Paragraph analysis = new Paragraph();
        analysis.add(new Chunk("Analiza: ", SUBHEADER_FONT));
        if (!bottlenecks.isEmpty()) {
            Map.Entry<String, Map<String, Object>> topBottleneck = bottlenecks.get(0);
            analysis.add(new Chunk("Najveće usko grlo je u stanju '" + topBottleneck.getKey() + 
                "' sa prosečnim trajanjem od " + String.format("%.1f", (Double) topBottleneck.getValue().get("averageDuration")) + 
                " minuta.\n", NORMAL_FONT));
        }
        analysis.setSpacingAfter(20);
        document.add(analysis);
    }

    private void addComplexSection2_SatisfactionAnalysis(Document document, 
                                                         LocalDateTime fromDate, 
                                                         LocalDateTime toDate) throws DocumentException {
        Paragraph sectionTitle = new Paragraph("5. ANALIZA ZADOVOLJSTVA KORISNIKA", HEADER_FONT);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        Map<String, Object> satisfactionData = queryService.analyzeCustomerSatisfaction(
            3, fromDate, toDate, true
        );

        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> satisfactionByType = 
            (Map<String, Map<String, Object>>) satisfactionData.get("satisfactionByServiceType");

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        addTableHeader(table, "Tip Servisa");
        addTableHeader(table, "Broj Naloga");
        addTableHeader(table, "Prosečna Ocena");
        addTableHeader(table, "Prosečno Trajanje");

        for (Map.Entry<String, Map<String, Object>> entry : satisfactionByType.entrySet()) {
            Map<String, Object> stats = entry.getValue();
            addTableCell(table, entry.getKey());
            addTableCell(table, stats.get("count").toString());
            addTableCell(table, String.format("%.2f/5", (Double) stats.get("averageRating")));
            addTableCell(table, String.format("%.0f min", (Double) stats.get("averageDuration")));
        }

        document.add(table);

        Paragraph overallRating = new Paragraph();
        overallRating.add(new Chunk("Ukupna prosečna ocena: ", SUBHEADER_FONT));
        overallRating.add(new Chunk(String.format("%.2f/5.00", satisfactionData.get("overallAverageRating")), NORMAL_FONT));
        overallRating.setSpacingAfter(20);
        document.add(overallRating);
    }

    private void addReportSummary(Document document) throws DocumentException {
        document.add(new Paragraph(" "));
        Paragraph summary = new Paragraph("ZAKLJUČAK", HEADER_FONT);
        summary.setSpacingBefore(15);
        summary.setSpacingAfter(10);
        document.add(summary);

        Paragraph conclusion = new Paragraph(
            "Ovaj izveštaj pruža sveobuhvatan pregled servisnih naloga, uključujući analizu " +
            "uskih grla u procesu i zadovoljstva korisnika. Podaci omogućavaju identifikaciju " +
            "područja za unapređenje i optimizaciju procesa servisiranja.",
            NORMAL_FONT
        );
        document.add(conclusion);
    }

    private List<ServiceTicket> getFilteredTickets(String stateName, Integer minPriority, Boolean onlyResolved) {
        List<ServiceTicket> allTickets = new ArrayList<>();
        ticketRepository.findAll().forEach(allTickets::add);

        return allTickets.stream()
            .filter(t -> stateName == null || stateName.equals(t.getCurrentStateName()))
            .filter(t -> minPriority == null || t.getPriority() >= minPriority)
            .filter(t -> onlyResolved == null || t.getIsResolved().equals(onlyResolved))
            .collect(Collectors.toList());
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(1);
        header.setPhrase(new Phrase(headerTitle, SUBHEADER_FONT));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setPadding(5);
        table.addCell(header);
    }

    private void addTableCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, SMALL_FONT));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }
}

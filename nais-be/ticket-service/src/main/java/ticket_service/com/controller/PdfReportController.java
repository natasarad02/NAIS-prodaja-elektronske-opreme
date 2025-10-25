package ticket_service.com.controller;

import ticket_service.com.service.PdfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/reports")
public class PdfReportController {

    @Autowired
    private PdfReportService pdfReportService;

    /**
     * Generates a comprehensive PDF report with simple and complex sections
     * 
     * Example usage:
     * GET /api/reports/comprehensive?stateName=OTVORENO&minPriority=3&onlyResolved=false
     * GET /api/reports/comprehensive (no filters - generates full report)
     */
    @GetMapping("/comprehensive")
    public ResponseEntity<byte[]> generateComprehensiveReport(
            @RequestParam(required = false) String stateName,
            @RequestParam(required = false) Integer minPriority,
            @RequestParam(required = false) Boolean onlyResolved,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {

        try {
            byte[] pdfBytes = pdfReportService.generateComprehensiveReport(
                stateName, minPriority, onlyResolved, fromDate, toDate
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            
            String filename = "Izvestaj_Servisni_Nalozi_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

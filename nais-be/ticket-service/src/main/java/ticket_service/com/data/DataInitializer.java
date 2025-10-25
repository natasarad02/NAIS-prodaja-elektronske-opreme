package ticket_service.com.data;

import ticket_service.com.model.ServiceTicket;
import ticket_service.com.model.ServiceTicketHistory;
import ticket_service.com.model.ServiceTicketState;
import ticket_service.com.repo.ServiceTicketHistoryRepository;
import ticket_service.com.repo.ServiceTicketRepository;
import ticket_service.com.repo.ServiceTicketStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ServiceTicketStateRepository stateRepository;

    @Autowired
    private ServiceTicketRepository ticketRepository;

    @Autowired
    private ServiceTicketHistoryRepository historyRepository;

    private final Random random = new Random();
    private List<ServiceTicketState> states;

    @Override
    public void run(String... args) {
        System.out.println("Starting data initialization...");

        if (stateRepository.count() > 0) {
            System.out.println("Data already exists. Skipping initialization.");
            return;
        }

        createStates();

        createTickets(1000);

        createHistory(1200);

        System.out.println("Data initialization completed!");
        System.out.println("States: " + stateRepository.count());
        System.out.println("Tickets: " + ticketRepository.count());
        System.out.println("History: " + historyRepository.count());
    }

    private void createStates() {
        states = new ArrayList<>();

        states.add(createState("OTVORENO", "Nalog je kreiran i čeka dodelu", 1, true, false, "ACTIVE", "#3B82F6", false));
        states.add(createState("DODELJENO", "Nalog je dodeljen tehničaru", 2, false, false, "ACTIVE", "#8B5CF6", false));
        states.add(createState("U_OBRADI", "Tehničar radi na nalogu", 3, false, false, "ACTIVE", "#F59E0B", false));
        states.add(createState("CEKA_DELOVE", "Čekaju se rezervni delovi", 4, false, false, "WAITING", "#EF4444", false));
        states.add(createState("CEKA_ODOBRENJE", "Čeka se odobrenje korisnika za popravku", 5, false, false, "WAITING", "#F97316", true));
        states.add(createState("DIJAGNOSTIKA", "Tehničar analizira problem", 6, false, false, "ACTIVE", "#06B6D4", false));
        states.add(createState("POPRAVKA", "Uređaj se popravlja", 7, false, false, "ACTIVE", "#10B981", false));
        states.add(createState("TESTIRANJE", "Uređaj se testira nakon popravke", 8, false, false, "ACTIVE", "#14B8A6", false));
        states.add(createState("CEKA_PREUZIMANJE", "Uređaj je spreman za preuzimanje", 9, false, false, "WAITING", "#6366F1", true));
        states.add(createState("ZAVRSENO", "Nalog je uspešno završen", 10, false, true, "COMPLETED", "#22C55E", false));
        states.add(createState("OTKAZANO", "Nalog je otkazan", 11, false, true, "CANCELLED", "#EF4444", false));
        states.add(createState("NA_CEKANJU", "Nalog je privremeno pauziran", 12, false, false, "WAITING", "#A855F7", false));

        stateRepository.saveAll(states);
        System.out.println("Created " + states.size() + " states");
    }

    private ServiceTicketState createState(String name, String description, int order, boolean isInitial, 
                                          boolean isFinal, String category, String color, boolean requiresCustomerAction) {
        ServiceTicketState state = new ServiceTicketState();
        state.setStateName(name);
        state.setStateDescription(description);
        state.setOrderSequence(order);
        state.setIsInitialState(isInitial);
        state.setIsFinalState(isFinal);
        state.setStateCategory(category);
        state.setRequiresCustomerAction(requiresCustomerAction);
        state.setIsActive(true);
        return state;
    }

    private void createTickets(int count) {
        List<ServiceTicket> tickets = new ArrayList<>();
        String[] serviceTypes = {"POPRAVKA", "ODRZAVANJE", "INSTALACIJA", "DIJAGNOSTIKA", "ZAMENA_DELOVA"};
        String[] deviceTypes = {"LAPTOP", "DESKTOP", "SMARTPHONE", "TABLET", "MONITOR", "STAMPAC", "ROUTER"};
        String[] deviceModels = {"Dell XPS 15", "HP Pavilion", "MacBook Pro", "iPhone 14", "Samsung Galaxy S23", 
                                "iPad Air", "LG UltraWide", "Canon Pixma", "TP-Link Archer"};
        String[] customers = {"Marko Marković", "Ana Anić", "Petar Petrović", "Jovana Jovanović", "Nikola Nikolić",
                             "Milica Milić", "Stefan Stefanović", "Jelena Jelenić", "Dimitrije Dimitrijević", "Katarina Katić"};
        String[] technicians = {"tech_001", "tech_002", "tech_003", "tech_004", "tech_005"};

        for (int i = 0; i < count; i++) {
            ServiceTicket ticket = new ServiceTicket();
            ticket.setTitle("Servisni nalog #" + (i + 1));
            ticket.setDescription(generateDescription());
            ticket.setServiceType(serviceTypes[random.nextInt(serviceTypes.length)]);
            
            ServiceTicketState currentState = states.get(random.nextInt(states.size()));
            ticket.setCurrentStateId(currentState.getId());
            ticket.setCurrentStateName(currentState.getStateName());
            
            LocalDateTime createdAt = LocalDateTime.now().minusDays(random.nextInt(180));
            ticket.setCreatedAt(createdAt);
            ticket.setUpdatedAt(createdAt.plusHours(random.nextInt(100)));
            
            String customerName = customers[random.nextInt(customers.length)];
            ticket.setCustomerId("customer_" + (random.nextInt(1000) + 1));
            ticket.setCustomerName(customerName);
            ticket.setCustomerEmail(customerName.toLowerCase().replace(" ", ".") + "@example.com");
            
            ticket.setAssignedTo(technicians[random.nextInt(technicians.length)]);
            ticket.setPriority(random.nextInt(5) + 1);
            
            boolean isResolved = currentState.getIsFinalState();
            ticket.setIsResolved(isResolved);
            
            if (isResolved) {
                ticket.setResolvedAt(ticket.getUpdatedAt());
                ticket.setCustomerFeedback(generateFeedback());
                ticket.setCustomerRating(random.nextInt(3) + 3); // 3-5 stars
                ticket.setTotalDuration((long) (random.nextInt(2000) + 100));
            } else {
                ticket.setTotalDuration((long) random.nextInt(500));
            }
            
            ticket.setDeviceType(deviceTypes[random.nextInt(deviceTypes.length)]);
            ticket.setDeviceModel(deviceModels[random.nextInt(deviceModels.length)]);
            ticket.setSerialNumber("SN" + String.format("%010d", random.nextInt(1000000000)));
            ticket.setInternalNotes("Interna napomena za nalog " + (i + 1));
            
            tickets.add(ticket);
        }

        ticketRepository.saveAll(tickets);
        System.out.println("Created " + count + " tickets");
    }

    private void createHistory(int count) {
        List<ServiceTicketHistory> historyEntries = new ArrayList<>();
        List<ServiceTicket> allTickets = new ArrayList<>();
        ticketRepository.findAll().forEach(allTickets::add);
        
        String[] changeReasons = {"PARTS_ARRIVED", "CUSTOMER_APPROVED", "DIAGNOSTIC_COMPLETE", 
                                 "REPAIR_COMPLETE", "CUSTOMER_REQUEST", "AUTOMATIC_TRANSITION", "TECHNICIAN_UPDATE"};
        String[] changedByNames = {"Marko Tehničar", "Ana Servis", "Petar Admin", "Jovana Support", "Sistem"};

        int historyPerTicket = count / allTickets.size();
        
        for (ServiceTicket ticket : allTickets) {
            LocalDateTime currentTime = ticket.getCreatedAt();
            ServiceTicketState previousState = states.get(0);
            
            for (int i = 0; i < historyPerTicket; i++) {
                ServiceTicketHistory history = new ServiceTicketHistory();
                history.setTicketId(ticket.getId());
                
                ServiceTicketState newState = states.get(random.nextInt(states.size()));
                
                history.setFromStateId(previousState.getId());
                history.setFromStateName(previousState.getStateName());
                history.setToStateId(newState.getId());
                history.setToStateName(newState.getStateName());
                
                currentTime = currentTime.plusHours(random.nextInt(48) + 1);
                history.setChangedAt(currentTime);
                
                boolean isAutomated = random.nextBoolean();
                history.setIsAutomatedChange(isAutomated);
                
                if (isAutomated) {
                    history.setChangedBy("SYSTEM");
                } else {
                    history.setChangedBy("user_" + (random.nextInt(10) + 1));
                }
                
                history.setDurationInPreviousState((long) (random.nextInt(1440) + 10));
                history.setChangeReason(changeReasons[random.nextInt(changeReasons.length)]);
                
                historyEntries.add(history);
                previousState = newState;
            }
        }

        historyRepository.saveAll(historyEntries);
        System.out.println("Created " + historyEntries.size() + " history entries");
    }

    private String generateDescription() {
        String[] issues = {
            "Uređaj se ne pali nakon pada",
            "Ekran je pukao i ne reaguje na dodir",
            "Baterija se brzo prazni",
            "Softverski problemi i česte greške",
            "Pregrevanje uređaja tokom rada",
            "Problem sa konekcijom na internet",
            "Zvučnici ne rade",
            "Tastatura ne reaguje na pojedine dugmiće",
            "USB portovi ne prepoznaju uređaje",
            "Čudni zvukovi iz uređaja"
        };
        return issues[random.nextInt(issues.length)];
    }

    private String generateFeedback() {
        String[] feedbacks = {
            "Odličan servis, brzo i efikasno!",
            "Zadovoljan sam popravkom, sve radi besprekorno.",
            "Profesionalna usluga, preporučujem!",
            "Moglo bi biti brže, ali kvalitet popravke je dobar.",
            "Sve je u redu, hvala na pomoći!",
            "Odlična komunikacija i brza popravka.",
            "Uredan servis, sve pohvale tehničaru.",
            "Zadovoljan sam rezultatom."
        };
        return feedbacks[random.nextInt(feedbacks.length)];
    }
}

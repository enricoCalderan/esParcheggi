package Api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import Observer.Observer;
import Query.QuerySoste;
import Observer.ObservableImpl;
import classi.AreaSosta;
import classi.FactorySosta;
import classi.ReportSoste;
import classi.Sosta;
import classi.Veicolo;
import eccezioni.VeicoloGiaParcheggiatoException;
import enums.CategoriaVeicolo;
import enums.StatoSosta;


public class ManagerSosteImpl implements ManagerSoste , Observer{
    
    private final Map<String, Sosta> sosteAttive;
    
    // Lista per lo STORICO: necessaria per non perdere i dati per i rendiconti finanziari
    private final List<Sosta> storicoSoste;

    public ManagerSosteImpl() {
        this.sosteAttive = new HashMap<>();
        this.storicoSoste = new ArrayList<>();
    }


    @Override
    public Collection<Sosta> getStoricoSoste()
    {
        return storicoSoste;
    }

    @Override
    public void avviaSosta(Veicolo veicolo, AreaSosta area, LocalDateTime inizio, LocalDateTime scadenzaPrevista) throws VeicoloGiaParcheggiatoException;
    {
        Objects.requireNonNull(veicolo, "Veicolo obbligatorio");
        String targa = veicolo.getTarga().toUpperCase();

        // Controllo difensivo per impedire doppie soste simultanee
        if (sosteAttive.containsKey(targa)) {
            throw new IllegalStateException("Il veicolo " + targa + " ha già una sosta in corso.");
        }

        // 1. Creazione tramite la Factory
        Sosta nuovaSosta = FactorySosta.creaSosta(veicolo, area, inizio, scadenzaPrevista);
        
        // 2. Pattern Observer: il manager si iscrive per ascoltare gli eventi di questa sosta
        nuovaSosta.addObserver(this);
        
        // 3. Salvataggio in memoria
        sosteAttive.put(targa, nuovaSosta);
        storicoSoste.add(nuovaSosta);

    }
    
    @Override
    public void interrompiSosta(String targa, LocalDateTime oraStop) {
        if (targa == null) {
            throw new IllegalArgumentException("Targa non valida");
        }

        // Ricerca immediata grazie alla Mappa!
        Sosta sostaDaFermare = sosteAttive.get(targa.toUpperCase());
        
        if (sostaDaFermare == null) {
            throw new IllegalArgumentException("Nessuna sosta attiva trovata per la targa: " + targa);
        }

        // Chiamando questo metodo, la sosta ricalcola la tariffa ed esegue notifyObservers()
        sostaDaFermare.terminaAnticipatamente(oraStop);
    }
    
    @Override
    public boolean verificaRegolarita(String targa, AreaSosta area, LocalDateTime momento)
    {
        return storicoSoste.stream()
                .filter(s -> s.getVeicolo().getTarga().equalsIgnoreCase(targa))
                .filter(s -> s.getArea().getId().equals(area.getId()))
                .anyMatch(s -> {
                    LocalDateTime fine = (s.getDataFineEffettiva() != null) ? s.getDataFineEffettiva() : s.getDataFine();
                    return !momento.isBefore(s.getDataInizio()) && !momento.isAfter(fine);
                });
    }

    @Override
    public void update(ObservableImpl o) {
        if (o instanceof Sosta) {
            Sosta sostaAggiornata = (Sosta) o;
            
            // Se la sosta è appena terminata, il Manager la rimuove automaticamente dalla mappa 
            // delle soste attive per liberare lo spazio logico, mantenendola solo nello storico.
            if (sostaAggiornata.getStato() == StatoSosta.IRREGOLARE) {
                String targa = sostaAggiornata.getVeicolo().getTarga().toUpperCase();
                sosteAttive.remove(targa);
                
                System.out.println("[SISTEMA] Sosta terminata per " + targa + 
                                   ". Importo definitivo: €" + sostaAggiornata.getImporto());
            }
        }
    }

    public double calcolaRicaviDal(LocalDateTime dataInizioRendiconto) {
        return storicoSoste.stream()
                .filter(s -> !s.getDataInizio().isBefore(dataInizioRendiconto))
                .mapToDouble(Sosta::getImporto) // Estrae l'importo da ogni sosta
                .sum();                                 // Somma totale incassi
    }

    /**
     * Genera il resoconto finanziario per una specifica giornata.
     * Utilizza le funzioni di aggregazione degli Stream.
     */
    public ReportSoste generaReportGiornaliero(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("La data del report non può essere nulla");
        }

        // 1. Filtra lo storico usando il nostro Pattern Query!
        List<Sosta> sosteDelGiorno = storicoSoste.stream()
                .filter(QuerySoste.concluseInData(data)) // Usa il predicato creato prima
                .toList(); // Equivalente moderno di Collectors.toList()

        // 2. Calcola incassi raggruppati per ID dell'Area di Sosta
        Map<String, Double> incassiArea = sosteDelGiorno.stream()
                .collect(Collectors.groupingBy(
                    sosta -> sosta.getArea().getId(),
                    Collectors.summingDouble(Sosta::getImporto)
                ));

        // 3. Calcola incassi raggruppati per Categoria di Veicolo (es. quanto han pagato le Auto vs Moto)
        Map<CategoriaVeicolo, Double> incassiCategoria = sosteDelGiorno.stream()
                .collect(Collectors.groupingBy(
                    sosta -> sosta.getVeicolo().getCategoria(),
                    Collectors.summingDouble(Sosta::getImporto)
                ));

        // 4. Calcola i totali assoluti
        double totaleIncassi = sosteDelGiorno.stream()
                .mapToDouble(Sosta::getImporto)
                .sum();
                
        int numeroSoste = sosteDelGiorno.size();

        // 5. Ritorna il DTO immutabile
        return new ReportSoste(
            data,
            incassiArea,
            incassiCategoria,
            totaleIncassi,
            numeroSoste
        );
    }
}

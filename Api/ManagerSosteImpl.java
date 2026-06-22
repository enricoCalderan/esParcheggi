package Api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observer;

import Observer.ObservableImpl;
import classi.AreaSosta;
import classi.FactorySosta;
import classi.Sosta;
import classi.Veicolo;
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
    public void avviaSosta(Veicolo veicolo, AreaSosta area, LocalDateTime inizio, LocalDateTime scadenzaPrevista)
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
        if (targa == null || targa.isBlank()) {
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
                    LocalDateTime fine = (s.getDataFineEffettiva() != null) ? s.getDataFineEffettiva() : s.getScadenzaPrevista();
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
}

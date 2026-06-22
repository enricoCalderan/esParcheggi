package classi;
import java.time.*;

import Observer.ObservableImpl;
import enums.StatoSosta;

//mission: rappresenta una sosta 
//stato astratto: una tupla delle variabili d'istanza che rappresentano una sosta
//stato concreto: campi final per memorizzare gli elementi della tupla
//invariante: Campi non nulli e dataInizio precedente a dataFine
public class Sosta extends ObservableImpl{
    //dati immutabili
    private final Veicolo veicolo;
    private final AreaSosta area;
    private final LocalDateTime dataInizio;
    private final LocalDateTime dataFine;
    
    //dati mutabili
    private StatoSosta stato;
    private LocalDateTime dataFineEffettiva;
    private double importo;

    
    Sosta(Veicolo veicolo, AreaSosta area, LocalDateTime dataInizio, LocalDateTime dataFine) {
        this.veicolo = veicolo;
        this.area = area;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        
        this.stato = StatoSosta.REGOLARE;
        this.dataFineEffettiva = null;
        
        // Calcolo preventivo: ipotizziamo che il cliente rimanga fino alla scadenza naturale pagata.
        // Deleghiamo la matematica al Calcolatore per non sporcare questa classe (SRP).
        this.importo = CalcolatoreTariffa.calcolaImporto(veicolo, area, dataInizio, dataFine);
    }

    public void terminaAnticipatamente(LocalDateTime oraStop) {
        if (this.stato == StatoSosta.IRREGOLARE) {
            throw new IllegalStateException("Impossibile interrompere: la sosta è già terminata.");
        }
        if (oraStop == null || oraStop.isBefore(dataInizio)) {
            throw new IllegalArgumentException("L'orario di stop non è valido (precede l'inizio o è nullo).");
        }

        // Se l'utente preme "stop" in ritardo rispetto alla scadenza, paga comunque fino alla scadenza prevista
        // (La gestione delle multe/ritardi non è richiesta, ci fermiamo alla scadenza naturale).
        this.dataFineEffettiva = oraStop.isBefore(dataFine) ? oraStop : dataFine;
        this.stato = StatoSosta.IRREGOLARE;

        // Ricalcolo dell'importo sui minuti effettivi goduti
        this.importo = CalcolatoreTariffa.calcolaImporto(veicolo, area, dataInizio, dataFineEffettiva);

        // PATTERN OBSERVER: Avvisa immediatamente il ParkManager (e chiunque altro sia in ascolto)
        // che la sosta si è conclusa e i dati finanziari sono definitivi.
        notifyObservers();
    }


    public Veicolo getVeicolo() {
        return veicolo;
    }

    public AreaSosta getArea() {
        return area;
    }

    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    public LocalDateTime getDataFine() {
        return dataFine;
    }

    public StatoSosta getStato() {
        return stato;
    }

    public LocalDateTime getDataFineEffettiva() {
        return dataFineEffettiva;
    }

    public double getImporto() {
        return importo;
    }

}

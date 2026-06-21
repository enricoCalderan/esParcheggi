package classi;

import java.time.LocalDateTime;

/**
 * Factory dedicata alla creazione sicura e validata degli eventi di sosta.
 */
public class FactorySosta {

    /**
     * Crea una nuova sosta. 
     * Grazie al polimorfismo, il parametro "area" accetta automaticamente 
     * sia oggetti ZonaStradale sia oggetti StrutturaMultipiano!
     */
    public static Sosta creaSosta(Veicolo veicolo, AreaSosta area, LocalDateTime inizio, LocalDateTime scadenzaPrevista) {
        
        // 1. Programmazione Difensiva (Clean Code: Fail Fast)
        if (veicolo == null) {
            throw new IllegalArgumentException("Il veicolo non può essere nullo");
        }
        if (area == null) {
            throw new IllegalArgumentException("L'area di sosta non può essere nulla");
        }
        if (inizio == null || scadenzaPrevista == null) {
            throw new IllegalArgumentException("Gli orari non possono essere nulli");
        }
        if (scadenzaPrevista.isBefore(inizio) || scadenzaPrevista.isEqual(inizio)) {
            throw new IllegalArgumentException("L'orario di scadenza deve essere successivo all'inizio");
        }

        // 2. Creazione dell'oggetto
        // Passiamo i dati "grezzi" alla sosta, che internamente userà il CalcolatoreTariffa 
        // per stabilire quanto far pagare al cliente.
        return new Sosta(veicolo, area, inizio, scadenzaPrevista);
    }
}
package classi;

import java.time.LocalDateTime;


public class FactorySosta {


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

    return new Sosta(veicolo, area, inizio, scadenzaPrevista);
    }
}
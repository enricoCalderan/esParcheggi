package classi;

import enums.CategoriaVeicolo;
import java.time.LocalDate;
import java.util.Map;

/**
 * Mission: Rappresentare in modo immutabile il resoconto finanziario giornaliero.
 * Stato astratto: Dati aggregati della giornata (date, incassiPerArea, incassiPerCategoria).
 */
public record ReportSoste(
    LocalDate data,
    Map<String, Double> incassiPerArea,
    Map<CategoriaVeicolo, Double> incassiPerCategoria,
    double incassoTotale,
    int totaleSosteConcluse
) {
    /**
     * Costruttore compatto con Programmazione Difensiva (Precondizioni).
     */
    public ReportSoste {
        if (data == null) throw new IllegalArgumentException("La data non può essere nulla");
        if (incassiPerArea == null) throw new IllegalArgumentException("Mappa incassi per area nulla");
        if (incassiPerCategoria == null) throw new IllegalArgumentException("Mappa incassi per categoria nulla");
        if (incassoTotale < 0) throw new IllegalArgumentException("L'incasso totale non può essere negativo");
        if (totaleSosteConcluse < 0) throw new IllegalArgumentException("Il totale delle soste non può essere negativo");

        // Assicura l'immutabilità profonda creando copie non modificabili delle mappe
        incassiPerArea = Map.copyOf(incassiPerArea);
        incassiPerCategoria = Map.copyOf(incassiPerCategoria);
    }
}
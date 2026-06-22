package Query;

import enums.StatoSosta;
import classi.Sosta;
import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Mission: Centralizzare i criteri di ricerca (Specification Pattern) per le Soste.
 * Evita la duplicazione della logica di filtraggio nel Manager.
 */
public class QuerySoste {

    /**
     * @return Un predicato che filtra solo le soste concluse in una specifica data.
     */
    public static Predicate<Sosta> concluseInData(LocalDate data) {
        return sosta -> sosta.getStato() == StatoSosta.IRREGOLARE && 
                        sosta.getDataFineEffettiva() != null &&
                        sosta.getDataFineEffettiva().toLocalDate().equals(data);
    }

    /**
     * @return Un predicato che trova tutte le soste relative a una specifica targa.
     */
    public static Predicate<Sosta> perTarga(String targa) {
        return sosta -> sosta.getVeicolo().getTarga().equalsIgnoreCase(targa.trim());
    }
}
package classi;

public class Calcolatoretariffe {
    package classi;

import enums.CategoriaVeicolo;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;


public class CalcolatoreTariffa {

    // Costanti per la fascia oraria gratuita (20:00 - 07:00 del mattino successivo)
    private static final LocalTime INIZIO_FASCIA_GRATUITA = LocalTime.of(20, 0);
    private static final LocalTime FINE_FASCIA_GRATUITA = LocalTime.of(7, 0);

    /**
     * Calcola l'importo totale dovuto per una sosta.
     */
    public static double calcolaImporto(Veicolo veicolo, AreaSosta area, LocalDateTime inizio, LocalDateTime fine) {
        Objects.requireNonNull(veicolo, "Veicolo obbligatorio per il calcolo");
        Objects.requireNonNull(area, "Area di sosta obbligatoria per il calcolo");
        Objects.requireNonNull(inizio, "Orario di inizio obbligatorio");
        Objects.requireNonNull(fine, "Orario di fine obbligatorio");

        
        double moltiplicatoreVeicolo = decodificaMoltiplicatoreVeicolo(veicolo.getCategoria());
        if (moltiplicatoreVeicolo == 0.0) {
            return 0.0;
        }

        // Tariffe calcolate al minuto
        double tariffaAlMinutoBase = area.getTariffa() / 60.0;
        long minutiFatturabili = 0;

        // Simulazione temporale minuto per minuto
        LocalDateTime cursore = inizio;
        while (cursore.isBefore(fine)) {
            if (!isGratuito(cursore)) {
                minutiFatturabili++;
            }
            cursore = cursore.plusMinutes(1);
        }

        double importoGrezzo = minutiFatturabili * tariffaAlMinutoBase;
        return arrotonda(importoGrezzo * moltiplicatoreVeicolo);
    }


    private static boolean isGratuito(LocalDateTime momento) {

        if (momento.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return true;
        }

        LocalTime oraCorrente = momento.toLocalTime();
        
        if (!oraCorrente.isBefore(INIZIO_FASCIA_GRATUITA) || oraCorrente.isBefore(FINE_FASCIA_GRATUITA)) {
            return true;
        }

        return false;
    }


    private static double decodificaMoltiplicatoreVeicolo(CategoriaVeicolo categoria) {
        switch (categoria) {
            case MOTOCICLO:
            case CICLOMOTORE:
                return 0.50;
            case AUTOMOBILE:
                return 1.00;
            case FURGONE_OLTRE_6M:
                return 1.50;
            case AUTOCARRI:
                return 1.80;
            case FORZE_DELL_ORDINE:
            case AMBULANZA:
            case VIGILI_DEL_FUOCO:
            case ALTRI_MEZZI_PUBBLICI:
                return 0.00;
            default:
                throw new IllegalArgumentException("Categoria veicolo non gestita dal sistema tariffario");
        }
    }

}

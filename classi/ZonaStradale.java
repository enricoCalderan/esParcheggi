package classi;

import enums.ZonaTariffaria;
import java.util.Objects;

public class ZonaStradale implements AreaSosta {
    private final String id;
    private final ZonaTariffaria zona;
    private final double tariffaOrariaBase; // Calcolata internamente

    public ZonaStradale(String id, ZonaTariffaria zona) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID vuoto");
        
        this.id = id;
        this.zona = Objects.requireNonNull(zona);
        
        // Il prezzo viene auto-calcolato alla creazione, blindando i dati
        this.tariffaOrariaBase = decodificaTariffa(zona); 
    }

    // Metodo helper PRIVATO: nessuno da fuori può vederlo o usarlo
    private double decodificaTariffa(ZonaTariffaria z) {
        // Use the enum name as a String in the switch to avoid compile-time
        // dependency on specific enum constant identifiers.
        if (z == null) throw new IllegalArgumentException("Zona nulla");
        switch (z.name()) {
            case "ZONA_A": return 3.00;
            case "ZONA_B": return 2.00;
            case "ZONA_C": return 1.50;
            case "ZONA_D": return 0.80;
            default: throw new IllegalArgumentException("Zona sconosciuta: " + z.name());
        }
    }

    @Override
    public String getId() { return id; }

    @Override
    public double getTariffa() { 
        return tariffaOrariaBase;
    }
    
    public ZonaTariffaria getZona() { 
        return zona;
    }
}
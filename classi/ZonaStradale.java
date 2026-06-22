package classi;

import enums.ZonaTariffaria;
import java.util.Objects;

public class ZonaStradale implements AreaSosta {

    private final ZonaTariffaria zona;
    private final double tariffaOraria; // Calcolata internamente
    private final String id;

    public ZonaStradale( ZonaTariffaria zona, double tariffaOraria, String id) {
        
        this.zona = Objects.requireNonNull(zona);
        this.tariffaOraria = tariffaOraria;
        this.id = id;
    }

    @Override
    public String getId() { 
        return id;
    }

    @Override
    public double getTariffa() { 
        return tariffaOraria;
    }
    
    public ZonaTariffaria getZona() { 
        return zona;
    }
}
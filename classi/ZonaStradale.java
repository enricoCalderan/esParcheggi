package classi;

import enums.ZonaTariffaria;
import java.util.Objects;

public class ZonaStradale implements AreaSosta {

    private final ZonaTariffaria zona;
    private final double tariffaOraria; // Calcolata internamente

    public ZonaStradale( ZonaTariffaria zona, double tariffaOraria) {
        
        this.zona = Objects.requireNonNull(zona);
        this.tariffaOraria = tariffaOraria;
    }

    @Override
    public String getStruttura() { 
        return zona.toString();
    }

    @Override
    public double getTariffa() { 
        return tariffaOraria;
    }
    
    public ZonaTariffaria getZona() { 
        return zona;
    }
}
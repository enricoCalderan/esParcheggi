package classi;

import enums.ZonaTariffaria;


public class FactoryAreaSosta {

    public static ZonaStradale creaZonaStradale(String id, ZonaTariffaria zona) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID della zona non può essere vuoto");
        }
        if (zona == null) {
            throw new IllegalArgumentException("La tipologia di zona non può essere nulla");
        }

        double tariffaReale;
        
        // Assegnazione centralizzata dei prezzi richiesti dal testo dell'esame
        switch (zona) {
            case ZONA_A: 
                tariffaReale = 3.00; 
                break;
            case ZONA_B: 
                tariffaReale = 2.00; 
                break;
            case ZONA_C: 
                tariffaReale = 1.50; 
                break;
            case ZONA_D: 
                tariffaReale = 0.80; 
                break;
            default: 
                throw new IllegalArgumentException("Zona tariffaria non riconosciuta");
        }
        
        return new ZonaStradale(id, zona, tariffaReale);
    }


    public static StrutturaMultipiano creaStrutturaMultipiano(String id, double tariffaOraria) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID della struttura non può essere vuoto");
        }
        if (tariffaOraria <= 0) {
            throw new IllegalArgumentException("La tariffa oraria deve essere maggiore di zero");
        }
        
        return new StrutturaMultipiano(id, tariffaOraria);
    }
}

package eccezioni;

/**
 * Eccezione lanciata quando un veicolo risulta già parcheggiato.
 */
public class VeicoloGiaParcheggiatoException extends Exception {

    public VeicoloGiaParcheggiatoException(String targa) {
        // Passiamo direttamente la stringa formattata al costruttore del genitore
        super("Operazione negata: Il veicolo con targa " + targa + " ha già una sosta in corso.");
    }
}
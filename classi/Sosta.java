package classi;
import java.time.*;

import enums.StatoSosta;

//mission: rappresenta una sosta 
//stato astratto: una tupla delle variabili d'istanza che rappresentano una sosta
//stato concreto: campi final per memorizzare gli elementi della tupla
//invariante: Campi non nulli e dataInizio precedente a dataFine
public class Sosta {
    private Veicolo veicolo;
    private final AreaSosta zonaTariffaria;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private StatoSosta statoSosta;
    
    public Sosta(Veicolo veicolo, AreaSosta area, LocalDateTime dataInizio, LocalDateTime dataFine) {
        this.veicolo = veicolo;
        this.zonaTariffaria = area;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.statoSosta = StatoSosta.ATTIVO;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public AreaSosta getZonaTariffaria() {
        return zonaTariffaria;
    }

    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    public LocalDateTime getDataFine() {
        return dataFine;
    }

    public StatoSosta getStatoSosta() {
        return statoSosta;
    }

}

package Api;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import Observer.ObservableImpl;
import classi.AreaSosta;
import classi.Sosta;
import classi.Veicolo;

public interface ManagerSoste {

    Map<String,Sosta> getStoricoSoste();

    public void avviaSosta(Veicolo veicolo, AreaSosta area, LocalDateTime inizio, LocalDateTime scadenzaPrevista);
    
    public void interrompiSosta(String targa, LocalDateTime oraStop);
    
    public boolean verificaRegolarita(String targa, AreaSosta area, LocalDateTime momento);

    public void update(ObservableImpl o);

}

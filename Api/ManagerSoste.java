package Api;

import java.time.LocalDateTime;
import java.util.Collection;

import Observer.ObservableImpl;
import classi.AreaSosta;
import classi.Sosta;
import classi.Veicolo;
import eccezioni.VeicoloGiaParcheggiatoException;

public interface ManagerSoste {

    Collection<Sosta> getStoricoSoste();

    public void avviaSosta(Veicolo veicolo, AreaSosta area, LocalDateTime inizio, LocalDateTime scadenzaPrevista) throws VeicoloGiaParcheggiatoException;
    
    public void interrompiSosta(String targa, LocalDateTime oraStop);
    
    public boolean verificaRegolarita(String targa, AreaSosta area, LocalDateTime momento);

    public void update(ObservableImpl o);

}

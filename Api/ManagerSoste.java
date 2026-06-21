package Api;

import java.util.Collection;
import java.util.Map;

import classi.Sosta;

public interface ManagerSoste {

    Map<String,Sosta> getStoricoSoste();

    public void avviaSosta();
    
    public void interrompiSosta();
    
    public double calcolaSosta();

    public boolean sostaRegolare();

}

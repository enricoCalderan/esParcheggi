package Api;

import java.util.HashMap;
import java.util.Map;

import classi.Sosta;


public class ManagerSosteImpl implements ManagerSoste{
    
    private final Map<String,Sosta> soste;

    public ManagerSosteImpl()
    {
        this.soste = new HashMap<>();
    }

    public Map<String,Sosta> getSoste()
    {
        return soste;
    }

    @Override
    public void avviaSosta(Sosta sosta)
    {

    }
    
    @Override
    public void interrompiSosta()
    {

    }
    
    @Override
    public double calcolaSosta()
    {

    }

    @Override
    public boolean sostaRegolare()
    {

    }
}

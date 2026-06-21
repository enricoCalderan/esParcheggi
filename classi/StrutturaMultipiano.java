package classi;

public class StrutturaMultipiano implements areaSosta
{
    private final String id;
    private final double tariffaOrariaBase;

    public StrutturaMultipiano(String id, double tariffaOrariaBase) {
        this.id = id;
        this.tariffaOrariaBase = tariffaOrariaBase;
    }

    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public double getTariffa()
    {
        return tariffaOrariaBase;
    }

    public String getID() {
        return id;
    }
    public double getTariffaOrariaBase() {
        return tariffaOrariaBase;
    }
}

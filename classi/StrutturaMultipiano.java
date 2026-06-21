package classi;

public class StrutturaMultipiano implements AreaSosta
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

    public double getTariffaOrariaBase() {
        return tariffaOrariaBase;
    }
}

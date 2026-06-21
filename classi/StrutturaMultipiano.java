package classi;

public class StrutturaMultipiano implements AreaSosta
{
    private final String id;
    private final double tariffaOraria;

    public StrutturaMultipiano(String id, double tariffaOraria) {
        this.id = id;
        this.tariffaOraria = tariffaOraria;
    }

    @Override
    public String getStruttura()
    {
        return id;
    }

    @Override
    public double getTariffa()
    {
        return tariffaOraria;
    }

}

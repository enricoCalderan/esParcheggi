package classi;

import java.util.Objects;

import enums.CategoriaVeicolo;

/**
 * Value Object immutabile che rappresenta un veicolo nel sistema.
 * RI: targa != null && !targa.isBlank() && categoria != null
 */
public class Veicolo {
    private final String targa;
    private final CategoriaVeicolo categoria;

    // [Costruttore con controlli difensivi sulle Precondizioni]
    public Veicolo(String targa, CategoriaVeicolo categoria) {
        this.targa = targa;
        this.categoria = categoria;
    }

    // [Getter per targa e categoria]
    public String getTarga() {
        return targa;
    }

    public CategoriaVeicolo getCategoria() {
        return categoria;
    }


    // Sovrascrittura fondamentale: due veicoli sono uguali se hanno la stessa targa
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Veicolo veicolo = (Veicolo) obj;
        return Objects.equals(targa, veicolo.targa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targa);
    }

    @Override
    public String toString() {
        return String.format("Veicolo[%s, %s]", targa, categoria);
    }

}
package Observer;

/**
 * Contratto per qualsiasi componente che deve reagire ai cambi di stato del sistema.
 */
public interface Observer {
    /**
     * Metodo invocato automaticamente quando l'oggetto osservato chiama notifyObservers().
     * @// param o Il riferimento all'oggetto osservato che ha cambiato stato.
     */
    void update(ObservableImpl o);
}
package Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe astratta di supporto che centralizza la gestione degli osservatori.
 * Evita la duplicazione di codice nelle classi di dominio.
 */
public abstract class ObservableImpl implements Observable{
    
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public final void addObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public final void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Scansiona la lista e notifica tutti gli ascoltatori registrati.
     */
    @Override
    public final void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
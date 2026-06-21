package Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe astratta di supporto che centralizza la gestione degli osservatori.
 * Evita la duplicazione di codice nelle classi di dominio.
 */
public abstract class ObservableImpl {
    private final List<Observer> observers = new ArrayList<>();

    public final void addObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    public final void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Scansiona la lista e notifica tutti gli ascoltatori registrati.
     */
    protected final void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
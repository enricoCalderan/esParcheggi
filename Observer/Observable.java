package Observer;


/**
 * Contratto formale per gli oggetti che vogliono essere "osservati" nel sistema.
 * Definisce i metodi necessari per gestire il ciclo di iscrizione e notifica.
 */
public interface Observable {

    /**
     * Permette a un Observer di iscriversi per ricevere futuri aggiornamenti.
     */
    void addObserver(Observer o);

    /**
     * Rimuove un Observer dalla lista delle notifiche.
     */
    void removeObserver(Observer o);

    /**
     * Invia un segnale a tutti gli Observer registrati per avvisarli 
     * che è avvenuto un cambio di stato rilevante.
     */
    void notifyObservers();
}
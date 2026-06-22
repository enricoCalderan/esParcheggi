package main;

import Api.ManagerSosteImpl;
import classi.AreaSosta;
import classi.FactoryAreaSosta;
import classi.Veicolo;
import enums.CategoriaVeicolo;
import enums.ZonaTariffaria;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== AVVIO SISTEMA PARCHEGGI ===\n");

        // 1. Inizializzazione del Manager (Il cuore del sistema)
        ManagerSosteImpl manager = new ManagerSosteImpl();

        // 2. Creazione della "Città" (Aree di Sosta)
        // ATTENZIONE: Usiamo la FactoryAreaSosta, impedendo al programmatore di inventarsi i prezzi!
        AreaSosta centro = FactoryAreaSosta.creaZonaStradale("Z-01", ZonaTariffaria.ZONA_A);
        AreaSosta multipiano = FactoryAreaSosta.creaStrutturaMultipiano("M-01", 2.50);

        // 3. Creazione dei veicoli che entreranno nel parcheggio
        Veicolo auto = new Veicolo("AB123CD", CategoriaVeicolo.AUTOMOBILE);
        Veicolo moto = new Veicolo("EF456GH", CategoriaVeicolo.MOTOCICLO);
        Veicolo ambulanza = new Veicolo("CRI999", CategoriaVeicolo.AMBULANZA);

        // 4. Simuliamo un orario fisso per facilitare i calcoli a mente (Es. Oggi alle 10:00 del mattino)
        LocalDateTime oraIngresso = LocalDateTime.now().withHour(10).withMinute(0);

        System.out.println("--- INGRESSO VEICOLI ---");
        // Il manager chiama internamente la FactorySosta e fa scattare il pattern Observer
        manager.avviaSosta(auto, centro, oraIngresso, oraIngresso.plusHours(2));
        System.out.println("L'Auto " + auto.getTarga() + " ha parcheggiato in " + centro.getId() + " per 2 ore previste.");

        manager.avviaSosta(moto, multipiano, oraIngresso, oraIngresso.plusHours(3));
        System.out.println("La Moto " + moto.getTarga() + " ha parcheggiato nel multipiano per 3 ore previste.");

        manager.avviaSosta(ambulanza, centro, oraIngresso, oraIngresso.plusHours(5));
        System.out.println("L'Ambulanza " + ambulanza.getTarga() + " ha parcheggiato in emergenza per 5 ore.\n");

        // 5. I vigili passano e fanno un controllo
        System.out.println("--- CONTROLLO VIGILI (Ore 11:00) ---");
        boolean autoRegolare = manager.verificaRegolarita("AB123CD", centro, oraIngresso.plusHours(1));
        System.out.println("L'auto AB123CD è in regola? " + (autoRegolare ? "SI" : "NO") + "\n");

        // 6. Test del Pattern OBSERVER: L'auto decide di andarsene prima del tempo
        System.out.println("--- USCITA ANTICIPATA ---");
        System.out.println("L'utente dell'auto AB123CD decide di uscire 1 ora prima (alle 11:00).");
        // Cliccando "interrompi", la Sosta farà il ricalcolo e urlerà al Manager (tramite notifyObservers)
        manager.interrompiSosta("AB123CD", oraIngresso.plusHours(1));

        // 7. Resoconto finanziario serale
        System.out.println("\n--- RESOCONTO FINANZIARIO ---");
        double incassiGiornata = manager.calcolaRicaviDal(oraIngresso.toLocalDate().atStartOfDay());
        System.out.println("Incassi totali registrati fino ad ora: €" + incassiGiornata);
        
        System.out.println("\nNota: L'ambulanza risulta gratuita, la moto è ancora in sosta, mentre l'auto ha pagato per 1 sola ora effettiva!");
    }
}
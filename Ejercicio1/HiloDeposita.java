package Ejercicio1;
/**
 *
 * @author Javier Fern�ndez Ferrol
 */
public class HiloDeposita extends Thread {
    private final Semaforo smf;
    /**
     *
     * @param smf
     */
    public HiloDeposita(Semaforo smf) {
        this.smf = smf;
    }
    @Override
    public void run() {
    	smf.accesoEscribir();
    }
}

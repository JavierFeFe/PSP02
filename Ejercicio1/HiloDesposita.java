package Ejercicio1;
/**
 *
 * @author Javier Fernández Ferrol
 */
public class HiloDesposita extends Thread {
    private final Semaforo smf;

    /**
     *
     * @param smf
     */
    public HiloDesposita(Semaforo smf) {
        this.smf = smf;
    }
    @Override
    public void run() {
    	smf.accesoEscribir();
    }
}

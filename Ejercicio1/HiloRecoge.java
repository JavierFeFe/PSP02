package Ejercicio1;
/**
 *
 * @author Javier Fern�ndez Ferrol
 */
public class HiloRecoge extends Thread {
    private final Semaforo smf;
    /**
     *
     * @param smf
     */
    public HiloRecoge(Semaforo smf) {
        this.smf = smf;
    }
    @Override
    public void run() {
       smf.accesoLeer();

    }
}

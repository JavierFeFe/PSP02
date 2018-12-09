package Ejercicio2;
/**
 *
 * @author Javier Fern�ndez Ferrol
 */
public class HiloFilosofo extends Thread {
    private final Semaforo smf;
    /**
     *
     * @param smf
     */
    public HiloFilosofo(String filosofo, Semaforo smf) {
        this.setName(filosofo);
    	this.smf = smf;
    }

    @Override
    public void run() {
    	smf.entraCena(getName());
    }
}

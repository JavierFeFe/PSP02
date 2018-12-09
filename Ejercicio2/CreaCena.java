package Ejercicio2;
/**
 *
 * @author Javier Fernández Ferrol
 */
public class CreaCena {
    private final Semaforo smf;
    String[] filosofos;
    /**
     *
     * @param smf
     */
    public CreaCena(String[] filosofos) {
        smf = new Semaforo(filosofos);
        this.filosofos=filosofos;
        
    }
    public void empieza() {
    	for (String f:filosofos) {		
    		new HiloFilosofo (f,smf).start();
    	}
    }

}

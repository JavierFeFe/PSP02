package Ejercicio2;
/**
 *
 * @author Javier Fernández Ferrol
 */
public class Cena {
    private final Mesa smf;
    String[] filosofos;
    /**
     *
     * @param smf
     */
    public Cena(String[] filosofos) {
        smf = new Mesa(filosofos);
        this.filosofos=filosofos;
        
    }
    public void empieza() {
    	System.out.println("*** LLegan los comensales ***");
    	for (String f:filosofos) {		
    		new Filosofo (f,smf).start();
    	}
    }

}

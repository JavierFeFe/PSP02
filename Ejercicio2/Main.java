package Ejercicio2;
/**
 *
 * @author Javier Fernández Ferrol
 */
public class Main {
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
    	System.out.println("Iniciando Cena...");
    	String[] filosofos = new String[] {"Sócrates","Platón", "Pitágoras", "Aristóteles", "Demócrito"};
    	CreaCena cena = new CreaCena(filosofos);
    	cena.empieza();

    }

}

package Ejercicio2;
/**
 *
 * @author Javier Fern�ndez Ferrol
 */
public class Main {
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
    	System.out.println("Iniciando Cena...");
    	String[] filosofos = new String[] {"S�crates","Plat�n", "Pit�goras", "Arist�teles", "Dem�crito"};
    	CreaCena cena = new CreaCena(filosofos);
    	cena.empieza();

    }

}

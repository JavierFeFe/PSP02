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
    	String[] filosofos = new String[] {"S�crates","Plat�n", "Pit�goras", "Arist�teles", "Dem�crito"};//Creamos un array de Fil�sofos
    	Cena cena = new Cena(filosofos);//Creamos una cena para los fil�sofos
    	cena.empieza();//Iniciamos la cena
    }

}

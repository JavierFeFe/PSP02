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
    	String[] filosofos = new String[] {"Sócrates","Platón", "Pitágoras", "Aristóteles", "Demócrito"};//Creamos un array de Filósofos
    	Cena cena = new Cena(filosofos);//Creamos una cena para los filósofos
    	cena.empieza();//Iniciamos la cena
    }

}

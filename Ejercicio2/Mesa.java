package Ejercicio2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
/**
 * @author Javier Fernández Ferrol
 */
public class Mesa extends Semaphore{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5022496273140521607L;
	private List<String> sillas; //Aquí almaceno los filósofos que me servirán para determinar su posición en la mesa
    private List<Integer> palillos = new ArrayList<>(); //Aquí almaceno un listado simple de palillos para controlar cuales están en uso y cuales no
    public  Mesa(String[] filosofos) {
    	super(filosofos.length);
    	this.sillas = Arrays.asList(filosofos);//Agrego los filósofos al array de control
    	for (int i=0; i<filosofos.length; i++) {//Creo un array de cubiertos para el nº de filósofos de la cena
    		palillos.add(i);
    		
    	}
    }
    public List<String> getFilosofos(){//Obtengo el arrayList para trabajar desde el hilo
    	return sillas;
    }
    public List<Integer> getPalillos(){//Obtengo el arrayList para trabajar desde el hilo
    	return palillos;
    }
   
}

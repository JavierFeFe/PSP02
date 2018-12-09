package Ejercicio2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * @author Javier Fernández Ferrol
 */
public class Semaforo {
    private final Random rmd = new Random();
    private List<String> filosofos;
    private int contador = 0;
    private List<Integer> cubiertos = new ArrayList<>();
    
    public  Semaforo(String[] filosofos) {
    	this.filosofos = Arrays.asList(filosofos);
    	for (int i=0; i<filosofos.length; i++) {//Creo un array de cubiertos para el nº de filósofos de la cena
    		cubiertos.add(i);
    		
    	}
    }
    public synchronized void entraCena(String nombre) {
    	if (contador != filosofos.size()) contador++; //Cuento los filósofos hasta q las mesas estáne llenas
    	System.out.println(nombre + " entra en la cena...");
    	while (contador != filosofos.size()) { //El filósofo espera a q ocupen las mesas	
    		try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	estaPensando(nombre);//El filósofo empieza a pensar
    	
    }

    public synchronized void estaPensando(String nombre) {
    	System.out.println(nombre + " está pensando...");
    	try {
			wait(rmd.nextInt(4000)+4000);//El filósofo piensa durante un tiempo entre 4 y 8 segundos
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	estaHambriento(nombre);//El filósofo está hambriento
    }
    public synchronized void estaHambriento(String nombre) {

    	System.out.println(nombre + " hambriento...");
    	int cubiertoDerecha = filosofos.indexOf(nombre);
    	int cubiertoIzquierda = cubiertoDerecha-1;
    	if (cubiertoIzquierda == -1) {
    		cubiertoIzquierda = filosofos.size()-1;
    	}
    	while (!cubiertos.contains(cubiertoIzquierda) || !cubiertos.contains(cubiertoDerecha)) {//El filósofo busca que estén libres los palillos
    		try {
				wait(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	cubiertos.remove((Integer)cubiertoIzquierda);//El filósofo empieza a comer
    	cubiertos.remove((Integer)cubiertoDerecha);

    	System.out.println(nombre + " comiendo... Usa: " + (cubiertoDerecha+1) + ", " + (cubiertoIzquierda+1));
    	try {
			wait(rmd.nextInt(4000)+2000);//El filósofo come en un tiempo entre 2 y 6 segundos
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(nombre + " termina de comer, libres palillos: " + (cubiertoDerecha+1) + ", " + (cubiertoIzquierda+1));
    	cubiertos.add(cubiertoDerecha);//El filósofo termina de comer
    	cubiertos.add(cubiertoIzquierda);
    	estaPensando(nombre); //El filósofo vuelve a pensar
    }
    

}

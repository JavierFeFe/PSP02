package Ejercicio2;
import java.util.Random;

/**
 *
 * @author Javier Fernández Ferrol
 */
public class Filosofo extends Thread {
    private final Mesa smf;//La clase mesa nos servirá como semáforo
    private final Random rmd = new Random();//Para generar número aleatorios
    private int cubiertoDerecha;
    private int cubiertoIzquierda;
    /**
     *
     * @param smf
     */
    public Filosofo(String filosofo, Mesa smf) {
        this.setName(filosofo);//Almacenamos el nombre del filósofo en el hilo
    	this.smf = smf;//Definimos el Semaphore compartido
    }

    @Override
    public void run() {
    	cubiertoDerecha = smf.getFilosofos().indexOf(getName());//Almaceno el valor de la posición del palillo de la derecha
    	cubiertoIzquierda = cubiertoDerecha-1;//Almaceno el valor de la posición del palillo de la izquierda
    	entraCena();
    }
    public void entraCena() {
    	System.out.println(getName() + " toma su asiento...");
    	try {
			smf.acquire();//Ocupamos un hilo del semáforo
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	while (smf.availablePermits() != 0) { //El filósofo espera a q ocupen las mesas	 		
    		try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	estaPensando();//El filósofo empieza a pensar	
    }
    private void estaPensando() {
    	System.out.println(getName() + " está pensando...");
    	try {
			sleep(rmd.nextInt(4000)+4000);//El filósofo piensa durante un tiempo entre 4 y 8 segundos
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	estaHambriento();//El filósofo está hambriento
    }
    private void estaHambriento() {
    	System.out.println(getName() + " está hambriento...");
    	if (cubiertoIzquierda == -1) {
    		cubiertoIzquierda = smf.getFilosofos().size()-1;
    	}
    	while (!smf.getPalillos().contains(cubiertoIzquierda) || !smf.getPalillos().contains(cubiertoDerecha)) {//El filósofo busca que estén libres los palillos
    		try {
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	estaComiendo();//Si salimos del bucle quiere decir que podemos comer  	
    }
    private void estaComiendo() {
    	smf.getPalillos().remove((Integer)cubiertoIzquierda);//El filósofo empieza a comer
    	smf.getPalillos().remove((Integer)cubiertoDerecha);
    	System.out.println(getName() + " comiendo... Usa: " + (cubiertoDerecha+1) + ", " + (cubiertoIzquierda+1));
    	try {
			sleep(rmd.nextInt(4000)+2000);//El filósofo come en un tiempo entre 2 y 6 segundos
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(getName() + " termina de comer, libres palillos: " + (cubiertoDerecha+1) + ", " + (cubiertoIzquierda+1));
    	smf.getPalillos().add(cubiertoDerecha);//El filósofo termina de comer
    	smf.getPalillos().add(cubiertoIzquierda);
    	estaPensando(); //El filósofo vuelve a pensar
    }
}

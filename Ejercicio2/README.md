
## Ejercicio 2:

**Primera parte:** De igual manera a lo visto en el tema, ahora te proponemos que resuelvas el clásico problema denominado "La cena de los filósofos" utilizando la clase Semaphore del paquete java.util.concurrent.

El problema es el siguiente: Cinco filósofos se sientan alrededor de una mesa y pasan su vida comiendo y pensando. Cada filósofo tiene un plato de arroz chino y un palillo a la izquierda de su plato. Cuando un filósofo quiere comer arroz, cogerá los dos palillos de cada lado del plato y comerá. El problema es el siguiente: establecer un ritual (algoritmo) que permita comer a los filósofos. El algoritmo debe satisfacer la exclusión mutua (dos filósofos no pueden emplear el mismo palillo a la vez), además de evitar el interbloqueo y la inanición. 

````Java
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
````
*Creo una clase que hereda de Semaphore donde almacenaremos datos sobre los filósofos y sobre los palillos utilizados*  
````Java
public class Filosofo extends Thread {
    private final Mesa smf;//La clase mesa nos servirá como semáforo
    private final Random rmd = new Random();//Para generar número aleatorios
    private int cubiertoDerecha;
    private int cubiertoIzquierda;
````
*Creo la clase Filosofo con un semáforo asociado y varias variables que utilizaremos más adelante.*  
````Java
    /**
     *
     * @param smf
     */
    public Filosofo(String filosofo, Mesa smf) {
        this.setName(filosofo);//Almacenamos el nombre del filósofo en el hilo
    	this.smf = smf;//Definimos el Semaphore compartido
    	cubiertoDerecha = smf.getFilosofos().indexOf(getName());//Almaceno el valor de la posición del palillo de la derecha
    	cubiertoIzquierda = cubiertoDerecha-1;//Almaceno el valor de la posición del palillo de la izquierda
    }
````
*Creo un constructor del que obtendré el nombre del filósofo y la posición de los cubiertos que utilizará*  
````Java
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
````
*Creo un método entraCena que se encargará de verificar si la "Mesa" está completa para iniciar el hilo*  
````Java
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
````
*Método simple que generará una espera aleatoria para simular que el filósofo está "Pensando"*  
````Java
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
````
*Este método se iniciará despues de que el filósofo termine de pensar y se encargará de determinar si están disponibles los palillos que necesita para comer, en caso contrario se quedará en espera*
````Java
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
````
*Se ejecuta una vez comprobado que tiene los palillos disponibles, generará una espera aleatoria y despues volverá a ejecutar el método estaPensando()*  
````Java
    @Override
    public void run() {
    	entraCena();
    }
````
*Método heredado de la clase Thread necesario para iniciar el hilo*
````Java
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
    	System.out.println("*** LLegan los comensales ");
    	for (String f:filosofos) {		
    		new Filosofo (f,smf).start();
    	}
    }
}
````
*Clase simple para simular el inicio de una cena*
````Java
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
````
*Clase con el método main que hace una llamada a la clase Cena, introduciendo en ella un array de Strings con los nombres de los filósofos (el número de filósofos debrería ser siempre superior a 3)*
![image](https://user-images.githubusercontent.com/44543081/49699937-eeef4a00-fbd7-11e8-8a4e-f7dd70c6d259.png)  
*Muestra del resultado por pantalla*

**Posibles mejoras: Crear una cola de comensales para asegurarse que siempre se dejará comer al comensal que lleva más tiempo esperando**

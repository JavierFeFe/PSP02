## Ejercicio 1:

**Primera parte:** De igual manera a lo visto en el tema, ahora te proponemos un ejercicio del tipo productor-consumidor que mediante un hilo productor almacene datos (15 caracteres) en un búfer compartido, de donde los debe recoger un hilo consumidor (consume 15 caracteres). La capacidad del búfer ahora es de 6 caracteres, de manera que el consumidor podrá estar cogiendo caracteres del búfer siempre que éste no esté vacío. El productor sólo podrá poner caracteres en el búfer, cuando esté vacío o haya espacio.

```Java
public class Semaforo {
    private boolean libre = true; //Estado inicial
    private final Random rmd = new Random();
    private final List<Character> letras = new ArrayList<Character>();//ArrayList donde se almacenarán los carácteres del buffer

```
*Creamos la clase semáforo y definimos sus variables, la variable libre se utilizará para evitar la ejecución de 2 hilos al mismo tiempo, el ArrayList letras será el "buffer" donde se almacenarán los carácteres.*  

```Java
    public synchronized void recogeCaracter() {
        if (libre && letras.size() > 0) { //Si el estado esta libre y el tamaño del buffer es mayor a 0 recogemos un carácter
            libre = false;//Antes de recoger cambiamos el estado a ocupado
            System.out.println("Recogido el carácter " + letras.get(letras.size()-1) + " del buffer (" + (letras.size() -1) + ")");
            letras.remove(letras.size()-1);//Eliminamos el carácter del buffer
        } else {
            while (!libre || letras.isEmpty()) {//Mientras el estado está ocupado o el buffer vacío el hilo se queda en espera
                try {
                    wait(100);//Esperamos la liberación del hilo, si en 0.1 segundo no recibe la liberación comprueba las condiciones del bucle
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            libre=false;//Si salimos del bucle quiere decir que es posible la lectura por lo que cambiamos el estado antes de realizar la operación
            System.out.println("Recogido el carácter " + letras.get(letras.size()-1) + " del buffer (" + (letras.size() -1) + ")");
            letras.remove(letras.size()-1);
        }
        libre=true;//Si llegamos aquí quiere decir que se finalizó el hilo, por lo que podemos liberar el estado y dar aviso al resto de hilos.
        notify();
    }
````
*Creamos el método recogeCaracter que se encargará de leer el primer carácter del ArrayList y eliminarlo de la lista. Durante este proceso la variable libre será igual a "false" por lo que los demás hilos se quedarán en espera.*  
````Java
    public synchronized void depositaCaracter() {
        char  letra = (char) (rmd.nextInt(26) + 65);//Generamos un caracter de letra mayúscula aleatorio
        if (libre && letras.size() < 6) { //Si el estado está libre  y el buffer es menor a 6 podemos depositar un nuevo carácter en el buffer
            libre = false;//Antes de empezar cambiamos el estado a ocupado
            letras.add(letra);
            System.out.println("Depositado carácter " + letra + " en el buffer (" + letras.size() + ")");
        } else {
            while (!libre  || letras.size() >=6) {//Mientras el estado sea ocupado y el buffer sea mayor o igual a 6 (realmente nunca deberí llegar a ser superior) se queda en espera
                try {
                    wait(100);//Esperamos la liberación del hilo, si en 0.1 segundo no recibe la liberación comprueba las condiciones del bucle
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            libre = false;//Si salimos del bucle quiere decir que es posible la escritura por lo que cambiamos el estado antes de realizar la operación
            letras.add(letra);
            System.out.println("Depositado carácter " + letra + " en el buffer (" + letras.size() + ")");
        }
        libre = true;//Si llegamos aquí quiere decir que se finalizó el hilo, por lo que podemos liberar el estado y dar aviso al resto de hilos.
        notify();
    }
````
*El método depositaCaracter funciona de una forma similar al anterior, pero en este caso agregará un nuevo caracter aleatorio al array*  
````Java
public class HiloDeposita extends Thread {
    private final Semaforo smf;
    /**
     *
     * @param smf
     */
    public HiloDeposita(Semaforo smf) {
        this.smf = smf;
    }
    @Override
    public void run() {
    	smf.depositaCaracter();
    }
}
````
*Creamos la clase HiloDeposita que se encargará de crear un hilo de ejecución para depositar carácteres controlado por la clase semáforo*  
````Java
public class HiloRecoge extends Thread {
private final Semaforo smf;
    /**
     *
     * @param smf
     */
    public HiloRecoge(Semaforo smf) {
        this.smf = smf;
    }
    @Override
    public void run() {
       smf.recogeCaracter();
    }
}
````
*Creamos la clase HiloRecoge con un funcionamiento muy similar a la clase anterior*  
````Java
public class Main {
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        Semaforo smf = new Semaforo();
        for (int i = 0; i < 15; i++) {//Creamos 15 hilos para depositar caracteres en el buffer
            new HiloDeposita(smf).start();
        }
        try {
			Thread.sleep(1000);//Espero 1000ms para dar tiempo a llenar los buffer y comprobar que no sobrepasa el límite
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (int i = 0; i < 15; i++) {//Creamos 15 hilos para retirar caracteres del buffer
            new HiloRecoge(smf).start();
        }
    }

}
````
*Creamos la clase con el método main para ejecutar 10 hilos de cada*  

![image](https://user-images.githubusercontent.com/44543081/49691382-f879a300-fb40-11e8-9663-e6b4f61e2141.png)  
*Comprobamos el correcto funcionamiento de la aplicación*  

**Ha sido necesario especificar un valor de espera dentro del wait para evitar bloqueos aleatorios cuando trabajo con muchos hilos (Ej. 150) con esto fuerzo la comprobación de las condiciones del while despues de un tiempo de espera razonable (100 ms)**


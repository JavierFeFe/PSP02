package Ejercicio1;
/**
 *
 * @author Javier Fernández Ferrol
 */
public class Main {
    /**
     *
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String args[]) throws InterruptedException {
        Semaforo smf = new Semaforo();
        for (int i = 0; i < 15; i++) {//Creamos 15 hilos para depositar caracteres en el buffer
        	Thread.sleep(25);
            new HiloDesposita(smf).start();
        }
        for (int i = 0; i < 15; i++) {//Creamos 15 hilos para retirar caracteres del buffer
        	Thread.sleep(10);
            new HiloRecoge(smf).start();
        }
    }

}

package Ejercicio1;
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

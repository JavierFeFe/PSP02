package Ejercicio1;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @author Javier Fern�ndez Ferrol
 */
public class Semaforo {
    private boolean libre = true; //Estado inicial
    private final Random ascii = new Random();
    private final List<Character> letras = new ArrayList<Character>();//ArrayList donde se almacenarán los car�cteres del buffer
    public synchronized void accesoLeer() {
        if (libre && letras.size() > 0) { //Si el estado esta libre y el tamaño del buffer es mayor a 0 recogemos un car�cter
            libre = false;//Antes de recoger cambiamos el estado a ocupado
            System.out.println("Recogido el car�cter " + letras.get(0) + " del buffer (" + (letras.size() -1) + ")");
            letras.remove(0);//Eliminamos el carácter del buffer
        } else {
            while (!libre || letras.isEmpty()) {//Mientras el estado est� ocupado o el buffer vac�o el hilo se queda en espera
                try {
                    wait(100);//Esperamos la liberaci�n del hilo, si en 0.1 segundo no recibe la liberaci�n comprueba las condiciones del bucle
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            libre=false;//Si salimos del bucle quiere decir que es posible la lectura por lo que cambiamos el estado antes de realizar la operaci�n
            System.out.println("Recogido el car�cter " + letras.get(0) + " del buffer (" + (letras.size() -1) + ")");
            letras.remove(0);
        }
        libre=true;//Si llegamos aqu� quiere decir que se finaliz� el hilo, por lo que podemos liberar el estado y dar aviso al resto de hilos.
        notify();
    }
    public synchronized void accesoEscribir() {
        char  letra = (char) (ascii.nextInt(26) + 65);//Generamos un caracter de letra may�scula aleatorio
        if (libre && letras.size() < 6) { //Si el estado est� libre  y el buffer es menor a 6 podemos depositar un nuevo car�cter en el buffer
            libre = false;//Antes de empezar cambiamos el estado a ocupado
            letras.add(letra);
            System.out.println("Depositado car�cter " + letra + " en el buffer (" + letras.size() + ")");
        } else {
            while (!libre  || letras.size() >=6) {//Mientras el estado sea ocupado y el buffer sea mayor o igual a 6 (realmente nunca deber� llegar a ser superior) se queda en espera
                try {
                    wait(100);//Esperamos la liberaci�n del hilo, si en 0.1 segundo no recibe la liberaci�n comprueba las condiciones del bucle
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            libre = false;//Si salimos del bucle quiere decir que es posible la escritura por lo que cambiamos el estado antes de realizar la operaci�n
            letras.add(letra);
            System.out.println("Depositado car�cter " + letra + " en el buffer (" + letras.size() + ")");
        }
        libre = true;//Si llegamos aqu� quiere decir que se finaliz� el hilo, por lo que podemos liberar el estado y dar aviso al resto de hilos.
        notify();
    }

}

package Ejercicio1;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @author Javier Fernández Ferrol
 */
public class Semaforo {
    private boolean libre = true; //Estado inicial
    private final Random rmd = new Random();
    private final List<Character> letras = new ArrayList<Character>();//ArrayList donde se almacenarán los carácteres del buffer
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

}

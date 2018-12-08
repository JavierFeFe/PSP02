## Ejercicio 1:

**Primera parte:** De igual manera a lo visto en el tema, ahora te proponemos un ejercicio del tipo productor-consumidor que mediante un hilo productor almacene datos (15 caracteres) en un búfer compartido, de donde los debe recoger un hilo consumidor (consume 15 caracteres). La capacidad del búfer ahora es de 6 caracteres, de manera que el consumidor podrá estar cogiendo caracteres del búfer siempre que éste no esté vacío. El productor sólo podrá poner caracteres en el búfer, cuando esté vacío o haya espacio.

```Java
public class Semaforo {
    private boolean libre = true; //Estado inicial
    private final Random rmd = new Random();
    private final List<Character> letras = new ArrayList<Character>();//ArrayList donde se almacenarán los carácteres del buffer

```
*Creamos la clase semáforo y definimos sus variables  

```Java
public synchronized void recogeCaracter() {
        if (libre && letras.size() > 0) { //Si el estado esta libre y el tamaño del buffer es mayor a 0 recogemos un carácter
            libre = false;//Antes de recoger cambiamos el estado a ocupado
            System.out.println("Recogido el carácter " + letras.get(0) + " del buffer (" + (letras.size() -1) + ")");
            letras.remove(0);//Eliminamos el carácter del buffer
        } else {
            while (!libre || letras.isEmpty()) {//Mientras el estado está ocupado o el buffer vacío el hilo se queda en espera
                try {
                    wait(100);//Esperamos la liberación del hilo, si en 0.1 segundo no recibe la liberación comprueba las condiciones del bucle
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            libre=false;//Si salimos del bucle quiere decir que es posible la lectura por lo que cambiamos el estado antes de realizar la operación
            System.out.println("Recogido el carácter " + letras.get(0) + " del buffer (" + (letras.size() -1) + ")");
            letras.remove(0);
        }
        libre=true;//Si llegamos aquí quiere decir que se finalizó el hilo, por lo que podemos liberar el estado y dar aviso al resto de hilos.
        notify();
}
````

![image](https://user-images.githubusercontent.com/44543081/49691338-24485900-fb40-11e8-8a39-5ab0d1f2371f.png)  
![image](https://user-images.githubusercontent.com/44543081/49691348-4a6df900-fb40-11e8-94e7-91c46b8e8af5.png)  
![image](https://user-images.githubusercontent.com/44543081/49691357-738e8980-fb40-11e8-9d03-4f30792a4585.png)  
![image](https://user-images.githubusercontent.com/44543081/49691362-91f48500-fb40-11e8-93a7-6c097b14e1af.png)  
![image](https://user-images.githubusercontent.com/44543081/49691365-adf82680-fb40-11e8-8572-7ccd994160e1.png)  
![image](https://user-images.githubusercontent.com/44543081/49691374-d54ef380-fb40-11e8-9c23-49fd848578c2.png)  
![image](https://user-images.githubusercontent.com/44543081/49691382-f879a300-fb40-11e8-9663-e6b4f61e2141.png)  


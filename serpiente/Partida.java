package serpiente;

import java.util.Random;
import java.util.Scanner;

/**
 * Partida
 */
public class Partida {
    private Tablero tablero;
    private Serpiente serpiente;
    private Manzana manzana;

    public Partida(int tamanio) {
        tablero = new Tablero(tamanio);
        serpiente = new Serpiente();
        manzana = null;
    }

    public void jugar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Para mover la serpiente usa W A S D. Pulsa ENTER para comenzar");
        sc.nextLine();
        char cambio;
        boolean estado = true; // Verifica si la partida puede continuar
        tablero.colocarSerpiente(serpiente);
        manzana = generarManzana();
        tablero.colocarManzana(manzana);
        while (estado) {
            tablero.mostrarTablero();
            cambio = sc.next().charAt(0);
            estado = actualizarPartida(cambio);
        }
        System.out.println("La partida ha acabado");
        sc.close();
    }

    private boolean actualizarPartida(char cambio) {
        boolean estado = true;
        int nuevaPosX = serpiente.getPosCabezaX(), nuevaPosY = serpiente.getPosCabezaY();
        switch (Character.toUpperCase(cambio)) {
            case 'W':
                nuevaPosY--;
                break;
            case 'A':
                nuevaPosX--;
                break;
            case 'S':
                nuevaPosY++;
                break;
            case 'D':
                nuevaPosX++;
                break;
            default:
                nuevaPosX = -1; // Si el valor introducido no es valido, pasa
        }

        if (nuevaPosX != -1) {
            // Verifica que la posicion de la serpiente no sobrepase los bordes y tampoco se choque consigo misma
            if (esValido(nuevaPosX, nuevaPosY) && tablero.obtenerCaracter(nuevaPosX, nuevaPosY) != 'S') {
                boolean actualizarCola = true;
                if (manzana.getX() == nuevaPosX && manzana.getY() == nuevaPosY) { // Si la serpiente toca una manzana
                    serpiente.aumentarLongitud();
                    manzana = generarManzana();
                    tablero.colocarManzana(manzana);
                    actualizarCola = false; // Si toca una manzana, se mueve la cabeza y no la cola
                }

                tablero.moverSerpiente(serpiente, nuevaPosX, nuevaPosY, actualizarCola);
                serpiente.actualizarPosSerpiente(nuevaPosX, nuevaPosY, actualizarCola, tablero);
            } else 
                estado = false;
        }
        return estado;
    }

    private boolean esValido(int x, int y) {
        return x >= 0 && x < tablero.getLimite() && y >= 0 && y < tablero.getLimite();
    }

    private Manzana generarManzana() {
        Random rand = new Random();
        int x = -1, y = -1;
        int limite = tablero.getLimite();
        do {
            x = rand.nextInt(limite); 
            y = rand.nextInt(limite);  
        } while (tablero.obtenerCaracter(x, y) == 'S' || tablero.obtenerCaracter(x, y) == 'C'); // Observa si donde va a poner la manzana no hay serpiente
        return new Manzana(x, y);
    }

    public static void main(String[] args) {
        Partida partida = new Partida(5);
        partida.jugar();
    }
}
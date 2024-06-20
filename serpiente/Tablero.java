package serpiente;

import java.util.Arrays;

public class Tablero {
    private char[][] matriz;

    public Tablero(int tamanio) {
        matriz = new char[tamanio][tamanio];
        for (int i = 0; i < matriz.length; i++) {
            Arrays.fill(matriz[i], ' ');
        }
    }

    public void moverSerpiente(Serpiente serpiente, int nuevoX, int nuevoY, boolean actualizarCola) { // Los parametros coinciden con la nueva posicion de la serpiente
        matriz[nuevoY][nuevoX] = 'C';
        matriz[serpiente.getPosCabezaY()][serpiente.getPosCabezaX()] = 'S';
        if (actualizarCola) {
            matriz[serpiente.getPosColaY()][serpiente.getPosColaX()] = ' ';
        }
    }

    public int getLimite() {
        return matriz.length;
    }
    public char[][] getMatriz() {
        return matriz;
    }

    public void colocarSerpiente(Serpiente serpiente) {
        matriz[0][0] = 'C';
    }

    public void colocarManzana(Manzana manzana) {
        matriz[manzana.getY()][manzana.getX()] = 'M';
    }

    public char obtenerCaracter(int x, int y) {
        return matriz[y][x];
    }

    public void mostrarTablero() {
        System.out.println("# ".repeat(matriz.length + 2));
        for (int i = 0; i < matriz.length; i++) {
            System.out.print('#');
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println(" #");
        }
        System.out.println("# ".repeat(matriz.length + 2));
    }
}

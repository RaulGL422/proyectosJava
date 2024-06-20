package serpiente;

public class Serpiente {
    private int longitud;
    private int posCabezaX;
    private int posCabezaY;
    private int posColaX;
    private int posColaY;

    public Serpiente() {
        posCabezaX = 0;
        posCabezaY = 0;
        posColaY = 0;
        posColaX = 0;
        longitud = 1;
    }

    public int getLongitud() {
        return longitud;
    }

    public void aumentarLongitud() {
        longitud++;
    }
    
    public int getPosCabezaX() {
        return posCabezaX;
    }

    public int getPosCabezaY() {
        return posCabezaY;
    }

    public int getPosColaX() {
        return posColaX;
    }

    public int getPosColaY() {
        return posColaY;
    }

    public void actualizarPosSerpiente(int x, int y, boolean actualizarCola, Tablero tablero) {
        this.posCabezaY = y;
        this.posCabezaX = x;
        if (actualizarCola) {
            int[] nuevaCola = buscarNuevaCola(tablero); // Devuelve la siguiente posicion de la cola [x, y]
            this.posColaX = nuevaCola[0];
            this.posColaY = nuevaCola[1];
        }
    }

    private int[] buscarNuevaCola(Tablero tablero) {
        int[] cola = {posColaX, posColaY}; // Por defecto la cola es la actual
        char[][] matriz = tablero.getMatriz();
        if (esValido(posColaX + 1, posColaY, tablero) && matriz[posColaY][posColaX + 1] == 'S') { // Mira si a la derecha hay cola
            cola[0]++;
        } else if (esValido(posColaX - 1, posColaY, tablero) && matriz[posColaY][posColaX - 1] == 'S') { // Mira si a la izquierda hay cola
            cola[0]--;
        } else if (esValido(posColaX, posColaY + 1, tablero) && matriz[posColaY + 1][posColaX] == 'S') { // Mira si abajo hay cola
            cola[1]++;
        } else if (esValido(posColaX, posColaY - 1, tablero) && matriz[posColaY - 1][posColaX] == 'S') { // Mira si arriba hay cola
            cola[1]--;
        }
        return cola;
    }

    private boolean esValido(int x, int y, Tablero tablero) {
        return x >= 0 && x < tablero.getLimite() && y >= 0 && y < tablero.getLimite();
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", posCabezaX, posCabezaY);
    }
}

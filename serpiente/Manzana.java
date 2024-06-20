package serpiente;

public class Manzana {
    private int y;
    private int x;
    
    public Manzana(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}

package libros.model;

public class Libro implements Comparable<Libro>{
    /* Atributos */
    private int id;
    private String titulo;
    private String autor;
    private double price;
    private int cantidad;

    /* Constructor */
    public Libro(int id, String titulo, String autor, double price, int cantidad) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.price = price;
        this.cantidad = cantidad;
    }

    public Libro(String titulo, String autor, double price, int cantidad) {
        this.id = -1;
        this.titulo = titulo;
        this.autor = autor;
        this.price = price;
        this.cantidad = cantidad;
    }

    /* Metodos */
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public double getPrice() {
        return price;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", titulo, autor);
    }

    @Override
    public int compareTo(Libro o) {
        return id - o.id;
    }
    
}
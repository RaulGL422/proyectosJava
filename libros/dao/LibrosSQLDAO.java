package libros.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import libros.model.Libro;

public class LibrosSQLDAO {
    //private final String URL = "jdbc:mysql://10.1.3.205:3306/libros?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private final String URL = "jdbc:mysql://127.0.0.1:3306/libros?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private final String USUARIO = "raul";
    private final String CONTRASENIA = "Hola1234";

    public List<Libro> getAll() {
        List<Libro> listaLibros = new ArrayList<>();

        try (
            Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENIA); 
            Statement stt = connection.createStatement();
        ) {
            ResultSet resultado = stt.executeQuery("SELECT * FROM libros");
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                double precio = resultado.getDouble("precio");
                int cantidad = resultado.getInt("cantidad");

                listaLibros.add(new Libro(id, titulo, autor, precio, cantidad));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } 

        return listaLibros;
    }

    public boolean guardar(Libro libro) {
        boolean completado = false;
        try (
            Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENIA); 
            Statement stt = connection.createStatement();
        ) {
            String query = "INSERT INTO libros (titulo, autor, precio, cantidad) VALUES " + 
            "('" + libro.getTitulo() + "', '" + libro.getAutor() + "', " + libro.getPrice() + ", " + libro.getCantidad() + ");";
            stt.executeUpdate(query);
            completado = true;
        } catch(SQLException ex) {
            ex.printStackTrace();
        } 
        return completado;
    }

    public boolean eliminar(Libro libro) {
        boolean completado = false;
        try (
            Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENIA); 
            Statement stt = connection.createStatement();
        ) {
            String query = "DELETE FROM libros WHERE " + 
            "titulo = '" + libro.getTitulo() + "';";
            stt.executeUpdate(query);
            completado = true;
        } catch(SQLException ex) {
            ex.printStackTrace();
        } 
        return completado;
    }

    public boolean actualizar(Libro libro, String[] parametros) {
        boolean completado = false;
        try (
            Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENIA); 
            Statement stt = connection.createStatement();
        ) {
            String query = "UPDATE libros SET precio=" + Double.parseDouble(parametros[0]) + " WHERE " + 
            "titulo = '" + libro.getTitulo() + "';";
            stt.executeUpdate(query);
            completado = true;
        } catch(SQLException ex) {
            ex.printStackTrace();
        } 
        return completado;
    }
 
    public List<Libro> getLibros(String[] filtro) {
        List<Libro> lista = new ArrayList<>();
        if (filtro[0].equals("") && filtro[1].equals("") && filtro[2].equals(""))
            lista = getAll();
        else {
            try (
                Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENIA); 
                Statement stt = connection.createStatement();
            ) {
                String query = "SELECT * FROM libros WHERE ";
                if (!filtro[0].equals("")) {
                    query += "autor LIKE '%" + filtro[0] + "%' ";
                    if (!filtro[1].equals("") || !filtro[2].equals("")) query += " AND ";
                }
                if (!filtro[1].equals("")) {
                    query += "titulo LIKE '%" + filtro[1] + "%' ";
                    if (!filtro[2].equals("")) query += " AND ";
                }
                if (!filtro[2].equals("")) query += "precio < " + filtro[2];
                query += ";";
                System.out.println(query);
                ResultSet resultado = stt.executeQuery(query);
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String titulo = resultado.getString("titulo");
                    String autor = resultado.getString("autor");
                    double precio = resultado.getDouble("precio");
                    int cantidad = resultado.getInt("cantidad");

                    lista.add(new Libro(id, titulo, autor, precio, cantidad));
                }
            } catch(SQLException ex) {
                ex.printStackTrace();
            } 
        }
        return lista;
    }
}
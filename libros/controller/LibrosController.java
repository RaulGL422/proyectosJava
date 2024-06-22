package libros.controller;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import libros.dao.LibrosSQLDAO;
import libros.model.Libro;

public class LibrosController implements Initializable {

    private LibrosSQLDAO daoSQL;

    @FXML
    private Pane panelFormularioLibro;

    @FXML
    private Label autor;

    @FXML
    private TextField autorFormulario;

    @FXML
    private Button botonAceptarCambioPrecio;

    @FXML
    private Button botonAceptarFormulario;

    @FXML
    private Button botonAgregarLibro;

    @FXML
    private Button botonCancelarFormulario;

    @FXML
    private Button botonCambioPrecio;

    @FXML
    private Button botonEliminar;

    @FXML
    private TextField campoCambioPrecio;

    @FXML
    private Label cantidad;

    @FXML
    private TextField cantidadFormulario;

    @FXML
    private TextField filtroAutor;

    @FXML
    private Label filtroPrecioMaxValor;

    @FXML
    private TextField filtroTitulo;

    @FXML
    private Label id;

    @FXML
    private ListView<Libro> listadoLibros;

    @FXML
    private Pane panelCambioPrecio;

    @FXML
    private Pane panelInformacionLibro;

    @FXML
    private Label mensaje;

    @FXML
    private Label precio;

    @FXML
    private TextField precioFormulario;

    @FXML
    private Slider sliderFiltroMaximo;

    @FXML
    private Label titulo;

    @FXML
    private TextField tituloFormulario;

    private void resetEscena() {
        botonCambioPrecio.setVisible(true);
        botonEliminar.setVisible(true);
        panelFormularioLibro.setVisible(false);
        panelInformacionLibro.setVisible(false);
        panelCambioPrecio.setVisible(false);
        resetFormulario();
    }

    private void resetFormulario() {
        tituloFormulario.clear();
        autorFormulario.clear();
        precioFormulario.clear();
        cantidadFormulario.clear();
    }

    @FXML
    void activarFormularioCambioPrecio(ActionEvent event) {
        botonCambioPrecio.setVisible(false);
        panelCambioPrecio.setVisible(true);
    }

    // Agrega el nuevo libro a la lista y a la base de datos
    @FXML
    void aceptarFormulario(ActionEvent event) {
        try {
            String tituloLibro = tituloFormulario.getText();
            String autorLibro = autorFormulario.getText();
            double precioLibro = Double.parseDouble(precioFormulario.getText());
            int cantidadLibro = Integer.parseInt(cantidadFormulario.getText());
            Libro nuevoLibro = new Libro(tituloLibro, autorLibro, precioLibro, cantidadLibro);
            boolean confirmarTransaccion = daoSQL.guardar(nuevoLibro);
            mostrarMensaje(confirmarTransaccion ? "El libro se ha agregado" : "El libro no se ha podido agregar");
            List<Libro> libros = recargarListadoLibros();
            actualizarValoresFiltro(libros);
            resetEscena();
        } catch (NumberFormatException e) {
            mostrarMensaje("Un dato del formulario no es valido");
            resetFormulario();
        }
    }

    // Se encarga de la transaccion de la eliminacion del libro
    @FXML
    void eliminarLibro(ActionEvent event) {
        Libro libroSeleccionado = listadoLibros.getSelectionModel().getSelectedItem();
        boolean confirmarTransaccion = daoSQL.eliminar(libroSeleccionado);
        mostrarMensaje(confirmarTransaccion ? "Libro eliminado correctamente" : "El libro no ha podido ser eliminado");
        List<Libro> libros = recargarListadoLibros();
        actualizarValoresFiltro(libros);
        resetEscena();
    }

    // Muestra los mensajes
    private void mostrarMensaje(String msg) {
        mensaje.setText(msg);
    }

    // Confirma y actualiza el precio del libro
    @FXML
    void cambiarPrecio(ActionEvent event) {
        Libro libroSeleccionado = listadoLibros.getSelectionModel().getSelectedItem();
        String[] valores = {campoCambioPrecio.getText()};
        boolean confirmarTransaccion = daoSQL.actualizar(libroSeleccionado, valores);
        mostrarMensaje(confirmarTransaccion ? "El libro se ha actualizado" : "El libro no se ha podido actualizar");
        campoCambioPrecio.clear();
        List<Libro> libros = recargarListadoLibros();
        actualizarValoresFiltro(libros);
        resetEscena();
    }

    // Cancela el formulario y vuelve al panel principal
    @FXML
    void cancelarFormulario(ActionEvent event) {
        resetEscena();
    }

    // Recarga los listado de libros y los filtros
    @FXML
    List<Libro> recargarListadoLibros() {
        String[] filtro = new String[3];
        filtro[0] = filtroAutor.getText();
        filtro[1] = filtroTitulo.getText();
        filtro[2] = filtroPrecioMaxValor.getText();
        List<Libro> listaLibros = daoSQL.getLibros(filtro);
        mostrarLibros(listaLibros);
        return listaLibros;
    }

    // Modifica los valores del slider
    private void actualizarValoresFiltro(List<Libro> libros) {
        double precioMin = Integer.MAX_VALUE;
        double precioMax = Integer.MIN_VALUE;
        
        for (Libro libro : libros) {
            double precioLibro = libro.getPrice();
            if (precioLibro < precioMin)
                precioMin = precioLibro;
            else if (precioLibro > precioMin)
                precioMax = precioLibro;
        }

        sliderFiltroMaximo.setMax(precioMax);
        sliderFiltroMaximo.setMin(precioMin);
        sliderFiltroMaximo.setValue(precioMax);
    }

    // Cuando el filtro del precio se actualiza, este metodo actualiza el texto
    @FXML
    void modificarPrecioMaximo() {
        filtroPrecioMaxValor.setText(String.format(Locale.US, "%.2f", sliderFiltroMaximo.getValue()));
        recargarListadoLibros();
    }

    // Muestra el formulario para agregar un libro
    @FXML
    void mostrarFormularioLibro(ActionEvent event) {
        resetEscena();
        panelFormularioLibro.setVisible(true);
    }

    // Se encarga de mostrar todos los libros en la lista
    private void mostrarLibros(List<Libro> libros) {
        listadoLibros.getItems().clear();
        listadoLibros.getItems().addAll(libros);
    }

    // Muestra el libro seleccionado
    @FXML
    void mostrarLibro(MouseEvent event) {
        panelInformacionLibro.setVisible(true);
        Libro libroSeleccionado = listadoLibros.getSelectionModel().getSelectedItem();
        id.setText(Integer.toString(libroSeleccionado.getId()));
        autor.setText(libroSeleccionado.getAutor());
        titulo.setText(libroSeleccionado.getTitulo());
        precio.setText(Double.toString(libroSeleccionado.getPrice()));
        cantidad.setText(Integer.toString(libroSeleccionado.getCantidad()));
    }

    // Limpia todos los filtros
    @FXML
    void limpiarFiltros(ActionEvent event) {
        filtroAutor.clear();
        filtroTitulo.clear();
        sliderFiltroMaximo.setValue(sliderFiltroMaximo.getMax());
        filtroPrecioMaxValor.setText("");
        recargarListadoLibros();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        daoSQL = new LibrosSQLDAO(); 
        List<Libro> libros = recargarListadoLibros();
        actualizarValoresFiltro(libros);
    }
}

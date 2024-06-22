CREATE DATABASE IF NOT EXISTS libros;

USE libros;

CREATE TABLE IF NOT EXISTS libros (
	id INT(5) NOT NULL AUTO_INCREMENT,
  	titulo VARCHAR(40) NOT NULL,
  	autor VARCHAR(40) NOT NULL,
  	precio FLOAT(5,2) NOT NULL,
  	cantidad INT(5) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO libros(titulo, autor, precio, cantidad) VALUES ('El camino del samurai', 'Haruki Murakami', 25.99, 100);
INSERT INTO libros(titulo, autor, precio, cantidad) VALUES ('La ciudad y los perros', 'Mario Vargas Llosa', 20.50, 75);
INSERT INTO libros(titulo, autor, precio, cantidad) VALUES ('Cien años de soledad', 'Gabriel García Márquez', 18.75, 120);
INSERT INTO libros(titulo, autor, precio, cantidad) VALUES ('El principito', 'Antoine de Saint-Exupéry', 12.99, 150);
INSERT INTO libros(titulo, autor, precio, cantidad) VALUES ('1984', 'George Orwell', 14.99, 90);
INSERT INTO libros(titulo, autor, precio, cantidad) VALUES ('El código Da Vinci', 'Dan Brown', 22.95, 80);
INSERT INTO libros(titulo, autor, precio, cantidad) VALUES ('Orgullo y prejuicio', 'Jane Austen', 16.50, 110);
INSERT INTO libros(titulo, autor, precio, cantidad) VALUES ('El señor de los anillos', 'J.R.R. Tolkien', 28.75, 65);
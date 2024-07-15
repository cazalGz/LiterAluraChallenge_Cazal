package com.alura.cursos.LiterAluraChallenge.principal;

import com.alura.cursos.LiterAluraChallenge.model.*;
import com.alura.cursos.LiterAluraChallenge.repository.AutorRepository;
import com.alura.cursos.LiterAluraChallenge.repository.LibroRepository;
import com.alura.cursos.LiterAluraChallenge.service.ConsumoAPI;
import com.alura.cursos.LiterAluraChallenge.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private AutorRepository repositoroAutor;
    private LibroRepository repositorioLibro;

    @Autowired
    public Principal(AutorRepository autorRepository, LibroRepository libroRepository, ConsumoAPI consumoAPI,ConvierteDatos conversor){
        this.repositoroAutor=autorRepository;
        this.repositorioLibro=libroRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    obtenerLibro();
                    break;
                case 2:
                    listarAutoresRegistrados();
                    break;
                case 3:
                    listarLibrosRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    public void obtenerLibro(){
        //Busqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro Encontrado ");
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado");
        }

        //Instanciamiento de las clases libro y autor
        DatosLibro datosLibro = libroBuscado.get();
        DatosAutor datosAutor = datosLibro.autor().get(0);
        Autor autor = new Autor(datosAutor);
        Libro libro = new Libro(libroBuscado.get(),autor);

        //guardar datos en la base de datos
        repositoroAutor.save(autor); // Guardar el autor primero
        libro.setAutor(autor); // Asegurarse de que el libro tenga el autor referenciado
        repositorioLibro.save(libro); // Luego guardar el libro
    }


    public void listarAutoresRegistrados() {
        List<Autor> autores = repositoroAutor.findAll();
        autores.stream().forEach(System.out::println);
    }

    public void listarLibrosRegistrados() {
        List<Libro> libros = repositorioLibro.findAll();
        libros.stream().forEach(System.out::println);
    }

    public void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese un año");
        var anio = teclado.nextLine();
        List<Autor> autores = repositoroAutor.listarAutoresVivosEnAnio(anio);
        autores.stream().forEach(System.out::println);
    }

    public void listarLibrosPorIdioma() {
        System.out.println("Seleccione el idioma:");
        var idioma = teclado.nextLine();
        List<Libro> libros = repositorioLibro.buscarLibrosPorIdioma(idioma);
        libros.stream().forEach(System.out::println);
    }





}

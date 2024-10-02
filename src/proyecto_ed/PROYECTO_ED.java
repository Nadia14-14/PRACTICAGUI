/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_ed;

import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.*;


public class PROYECTO_ED {
    private static final String CSV_FILE = "C:\\Users\\HP\\Documents\\NetBeansProjects\\PROYECTO_ED\\src\\proyecto_ed\\DataBase.csv";
    private static final String CSV_SPLIT_BY = ",";
    private static LinkedList<LinkedList<String>> articulos = new LinkedList<>();

    public static void cargarArticulosDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(CSV_SPLIT_BY);
                LinkedList<String> row = new LinkedList<>(Arrays.asList(fields));
                articulos.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarArticulosEnArchivo() {
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            for (List<String> row : articulos) {
                writer.write(String.join(CSV_SPLIT_BY, row) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    public static void darDeAlta(Scanner scanner) {
        System.out.println("Agregar nuevo artículo:");
        System.out.print("Proveedor: ");
        String proveedor = scanner.nextLine();
        System.out.print("Marca de artículo: ");
        String marca = scanner.nextLine();
        System.out.print("Código de artículo: ");
        String codigo = scanner.nextLine();
        System.out.print("Descripción de artículo: ");
        String descripcion = scanner.nextLine();
        System.out.print("Precio de articulo: ");
        String precio = scanner.nextLine();
        
        LinkedList<String> newRow = new LinkedList<>(Arrays.asList(proveedor, marca, codigo, descripcion, precio));
        articulos.add(newRow);
        guardarArticulosEnArchivo();
        System.out.println("El producto ha sido añadido con éxito.");
    }

    private static void Costoventa(List<String> product) {
        for (String field : product) {
            System.out.print(field + "\t");
        }
        
        double precio = Double.parseDouble(product.get(4));
        double iva = precio * 0.16; 
        double utilidad = precio * 0.20;
        double costoVenta = precio + iva + utilidad;
        System.out.println("Costo de venta: " + costoVenta);
    }

    public static void buscarPorCodigo(Scanner scanner) {
        System.out.print("Ingrese el código del producto que desea buscar: ");
        String codigoP = scanner.nextLine();

        boolean encontrado = false;
        
        for (List<String> row : articulos) {
            if (row.get(2).equals(codigoP)) {
                encontrado = true;
                System.out.println("El producto ha sido encontrado:");
                Costoventa(row);
                break;
            }   
        }
        if (!encontrado) {
            System.out.println("El producto con el código " + codigoP + " no fue encontrado.");
        }
    }

    public static void buscarPorMarca(Scanner scanner) {
        System.out.print("Ingrese la marca del artículo que desea buscar: ");
        String marcaP = scanner.nextLine();
        boolean encontrado = false;
        for (List<String> row : articulos) {
            if (row.get(1).equalsIgnoreCase(marcaP)) {
                if (!encontrado) {
                    System.out.println("El producto ha sido encontrado:");
                    encontrado = true;
                }
                Costoventa(row);
            } 
        } 
        if (!encontrado) {
            System.out.println("No se encontraron productos con la marca " + marcaP);
        }
    }

    public static void buscarPorPalabrasClave(Scanner scanner) {
        System.out.print("Ingrese las palabras clave para buscar en la descripción: ");
        String palabrasClave = scanner.nextLine();
        String palabras [] = palabrasClave.toLowerCase().split(" ");
        boolean encontrado = false;

        for (List<String> row : articulos) {
            String descripcion = row.get(3).toLowerCase();
            boolean match = true;
            for (String palabra : palabras) {
                if (!descripcion.contains(palabra)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                if (!encontrado) {
                    System.out.println("Productos encontrados:");
                    encontrado = true;
                }
                Costoventa(row);
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron productos que coincidan con las palabras clave.");
        }
    }

    public static void modificarProducto(Scanner scanner) {
        System.out.print("Ingrese el código del producto que desea hacer modificaciones: \n ");
        System.out.print("Código de artículo: ");
        String codigoP = scanner.nextLine();
        
        boolean encontrado = false;
        for (List<String> row : articulos) {
            if (row.get(2).equals(codigoP)) {
                System.out.println("El producto ha sido encontrado:");
                for (int i = 0; i < row.size(); i++) {
                    System.out.println(i + ". " + row.get(i));
                }
                System.out.print("Ingrese el número del campo que desea modificar: ");
                int campo = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer del scanner
                System.out.print("Ingrese el nuevo valor: ");
                String nuevoValor = scanner.nextLine();
                row.set(campo, nuevoValor);
                encontrado = true;
                System.out.println("El producto fue modificado correctamente.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("El producto con el código " + codigoP + " no fue encontrado.");
        }
        guardarArticulosEnArchivo();
    }

    public static void eliminarPorCodigo(Scanner scanner) {
        System.out.print("Ingrese el código del producto que desea eliminar: ");
        String codigoP = scanner.nextLine();
        
        Iterator<LinkedList<String>> iterator = articulos.iterator();
        boolean encontrado = false;
        
        while (iterator.hasNext()) {
            List<String> row = iterator.next();
            if (row.get(2).equals(codigoP)) {
                iterator.remove();
                encontrado = true;
                System.out.println("El artículo ha sido eliminado con éxito.");
                break;
            } 
        }
        if (!encontrado) {
            System.out.println("El artículo con el código " + codigoP + " no fue encontrado.");
        }
        guardarArticulosEnArchivo();
    }

    public static void leerDesdeArchivoCSV() {
        articulos.clear();
        cargarArticulosDesdeArchivo();
        System.out.println("Se han cargado los artículos desde el archivo CSV.");
    }

    public static void menu() {
        System.out.println("¿Qué acción desea realizar?: \n");
        System.out.println("1. Alta de artículos");
        System.out.println("2. Consultar artículos");
        System.out.println("3. Modificación de artículos");
        System.out.println("4. Eliminación de artículos");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void menu_consultar() {
        System.out.println("\t");
        System.out.println("1. Consultar por marca");
        System.out.println("2. Consultar por código");
        System.out.println("3. Consultar por palabras clave");
        System.out.println("4. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    public static void menu_eliminar() {
        System.out.println("\n");
        System.out.println("Eliminar artículo:");
        System.out.println("1. Eliminar por código");
        System.out.println("2. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(String[] args) {
        leerDesdeArchivoCSV();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            menu();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (java.util.NoSuchElementException e) {
                System.err.println("Error: No se proporcionó una entrada válida.");
                scanner.nextLine();
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    darDeAlta(scanner);
                    break;
                case 2:
                    int opcionConsulta;
                    do {
                        menu_consultar();
                        opcionConsulta = scanner.nextInt();
                        scanner.nextLine();
                        switch (opcionConsulta) {
                            case 1:
                                buscarPorMarca(scanner);
                                break;
                            case 2:
                                buscarPorCodigo(scanner);
                                break;
                            case 3:
                                buscarPorPalabrasClave(scanner);
                                break;
                            case 4:
                                break;
                            default:
                                System.out.println("Opción inválida. Por favor seleccione una opción válida.");
                        }
                        if (opcionConsulta != 4) {
                            System.out.print("¿Desea realizar otra consulta? (S/N): ");
                            String respuesta = scanner.nextLine().toUpperCase();
                            if (!respuesta.equals("S")) break;
                        }
                    } while (opcionConsulta != 4);
                    break;
                case 3:
                    modificarProducto(scanner);
                    break;
                case 4:
                    int opcionEliminar;
                    do {
                        menu_eliminar();
                        opcionEliminar = scanner.nextInt();
                        scanner.nextLine();
                        switch (opcionEliminar) {
                            case 1:
                                eliminarPorCodigo(scanner);
                                break;
                            case 2:
                                break;
                            default:
                                System.out.println("Opción inválida. Por favor seleccione una opción válida.");
                        }
                        if (opcionEliminar != 2) {
                            System.out.print("¿Desea realizar otra eliminación? (S/N): ");
                            String respuesta = scanner.nextLine().toUpperCase();
                            if (!respuesta.equals("S")) break;
                        }
                    } while (opcionEliminar != 2);
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor seleccione una opción válida.");
            }
        } while (opcion != 5);
    }
}

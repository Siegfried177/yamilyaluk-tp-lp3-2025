package py.edu.uc.lp32025.mapeable;

import py.edu.uc.lp32025.mapeable.Mapeable;
import py.edu.uc.lp32025.domain.Empleado;
import py.edu.uc.lp32025.domain.Contratista; // Asumimos que tienes una clase Contratista concreta
import py.edu.uc.lp32025.mapeable.PosicionGPS;
import py.edu.uc.lp32025.mapeable.Avatar;

import java.util.ArrayList;
import java.util.List;

public class DemoMapeable {

    public static void main(String[] args) {

        // 1. Crear instancias de diferentes clases que implementan Mapeable
        System.out.println("--- 1. Creando Elementos Mapeables ---");

        // El Empleado MOCK ya tiene datos de prueba
        Empleado empleadoMock = new Empleado();

        // Creamos una instancia de Contratista (asumiendo que implementa los métodos abstractos y Mapeable)
        Contratista contratistaReal = new Contratista(
                "Juan",
                "Perez",
                "505050",
                java.time.LocalDate.of(1980, 5, 5)
        );
        // Asignamos datos MOCK/prueba para el Contratista
        contratistaReal.setPosicionGPS(new PosicionGPS(-33.4489,-70.6693));
        contratistaReal.setAvatar(new Avatar("http://img.com/juan_perez.png","Juan_Contrata"));

        // 2. Usar una lista polimórfica de la interfaz Mapeable
        List<Mapeable> elementosParaMapa = new ArrayList<>();
        elementosParaMapa.add(empleadoMock);
        elementosParaMapa.add(contratistaReal);

        // 3. Demostración del Polimorfismo
        System.out.println("\n--- 2. Recorriendo Elementos Polimórficos (Mapeable) ---");

        for (Mapeable elemento : elementosParaMapa) {

            // Llamamos a los métodos de la interfaz sin saber si es Empleado o Contratista.
            PosicionGPS ubicacion = elemento.ubicarElemento();
            Avatar avatar = elemento.obtenerImagen();

            // Demostración de que el elemento base también es una Persona (casting seguro)
            String nombreClase = elemento.getClass().getSimpleName();

            System.out.println("-------------------------------------");
            System.out.println("Tipo de Entidad: " + nombreClase);

            // Resultados del contrato Mapeable
            System.out.println("  -> Ubicación GPS: " + ubicacion);
            System.out.println("  -> Avatar Obtenido: " + avatar);

            // Demostración de Herencia (accediendo a campos de Persona)
            if (elemento instanceof Contratista c) {
                System.out.println("  -> Documento de Persona: " + c.getNumeroDocumento());
            } else if (elemento instanceof Empleado e) {
                System.out.println("  -> Salario (Método Abstr.): " + e.calcularSalario());
            }
        }
    }
}
// src/main/java/py/edu/uc/lp32025/DemoMapeable.java
package py.edu.uc.lp32025.demo;

import py.edu.uc.lp32025.interfaces.Mapeable;
import py.edu.uc.lp32025.util.MapeableDataFactory;
import py.edu.uc.lp32025.util.MapeableDisplayUtil;

import java.util.List;

public class DemoMapeable {

    public static void main(String[] args) {

        // 🚨 NO SE NECESITA LOGGER AQUÍ, pero podemos usar el de la fábrica.
        System.out.println("--- INICIANDO DEMOSTRACIÓN DE POLIMORFISMO DE INTERFAZ (MAPEABLE) ---");

        // 1. CREACIÓN: La fábrica ahora registrará su progreso
        List<Mapeable> elementosParaMapa = MapeableDataFactory.crearDatosDePrueba();

        // 2. PROCESAMIENTO/PRESENTACIÓN: La utilidad ahora registrará la salida
        MapeableDisplayUtil.mostrarInfoMapeable(elementosParaMapa);

        System.out.println("--- FIN DE LA DEMOSTRACIÓN (Ver logs para la salida detallada) ---");
    }
}
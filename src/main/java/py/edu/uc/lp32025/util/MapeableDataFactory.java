// src/main/java/py/edu/uc/lp32025/util/MapeableDataFactory.java
package py.edu.uc.lp32025.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.edu.uc.lp32025.domain.*;
import py.edu.uc.lp32025.domain.Edificio;
import py.edu.uc.lp32025.interfaces.Mapeable;
import py.edu.uc.lp32025.domain.PosicionGPS;
import py.edu.uc.lp32025.domain.Vehiculo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MapeableDataFactory {

    // 🚨 Definición del logger estático por clase (SLF4J Static)
    private static final Logger logger = LoggerFactory.getLogger(MapeableDataFactory.class);
    /**
     * Crea una lista de objetos Mapeable de diferentes jerarquías.
     * @return List<Mapeable> con datos de prueba.
     */
    public static List<Mapeable> crearDatosDePrueba() {
        logger.info("Iniciando la creación de datos de prueba para Mapeable.");
        List<Mapeable> elementosParaMapa = new ArrayList<>();

        // ... Lógica de creación de EmpleadoTiempoCompleto, Contratista, Vehiculo, Edificio ...

        EmpleadoTiempoCompleto empleadoCompleto = new EmpleadoTiempoCompleto(
                "Batch", "Demo", "2375649", LocalDate.of(1991, 1, 1), new BigDecimal("4500000.00"), "IT");
        empleadoCompleto.setPosicionGPS(new PosicionGPS(-25.3006,-57.6359));
        elementosParaMapa.add(empleadoCompleto);
        logger.debug("Creado: EmpleadoTiempoCompleto (B001)");


        Contratista contratistaReal = new Contratista(
                "Juan", "Perez", "1234567", LocalDate.of(1980, 5, 5));
        contratistaReal.setPosicionGPS(new PosicionGPS(-33.4489,-70.6693));
        contratistaReal.setMontoPorProyecto(new BigDecimal("1500000"));
        contratistaReal.setProyectosCompletados(3);
        elementosParaMapa.add(contratistaReal);
        logger.debug("Creado: Contratista (505050)");


        Vehiculo miVehiculo = new Vehiculo(
                "ABC-123",
                "Camioneta",
                new PosicionGPS(-25.3000, -57.6000),
                "http://img.com/icono_coche.png"
        );
        elementosParaMapa.add(miVehiculo);
        logger.debug("Creado: Vehiculo (ABC-123)");


        Edificio miEdificio = new Edificio(
                "Sede Central UC",
                "Calle Falsa 123",
                new PosicionGPS(-25.2800, -57.6400),
                "http://img.com/foto_uc.png"
        );
        elementosParaMapa.add(miEdificio);
        logger.debug("Creado: Edificio (Sede Central UC)");

        logger.info("Datos de prueba creados exitosamente. Total de elementos: {}", elementosParaMapa.size());
        return elementosParaMapa;
    }
}
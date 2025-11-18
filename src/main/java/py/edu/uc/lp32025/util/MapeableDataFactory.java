// src/main/java/py/edu/uc/lp32025/util/MapeableDataFactory.java
package py.edu.uc.lp32025.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.edu.uc.lp32025.domain.*;
import py.edu.uc.lp32025.interfaces.Mapeable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MapeableDataFactory {

    private static final Logger logger = LoggerFactory.getLogger(MapeableDataFactory.class);

    /**
     * Crea una lista de objetos Mapeable de diferentes jerarquías.
     * @return List<Mapeable> con datos de prueba.
     */
    public static List<Mapeable> crearDatosDePrueba() {
        logger.info("Iniciando la creación de datos de prueba para Mapeable.");
        List<Mapeable> elementosParaMapa = new ArrayList<>();

        // Campos comunes para la demo
        LocalDate fechaContratoBase = LocalDate.now().minusYears(3);
        Avatar avatarDefault = new Avatar("http://demo.img/avatar.png", "default_img");

        // ------------------------------------------------------------------
        // 1. CREACIÓN DE EMPLEADOTIEMPOCOMPLETO (ETC)
        // Usa el constructor completo: Persona fields + Empleado fields + ETC fields
        // ------------------------------------------------------------------
        EmpleadoTiempoCompleto empleadoCompleto = new EmpleadoTiempoCompleto(
                "Batch",
                "Demo",
                "2375649",
                LocalDate.of(1991, 1, 1),
                new PosicionGPS(-25.3006, -57.6359), // PosicionGPS
                avatarDefault,                     // Avatar
                fechaContratoBase,                 // FechaContratacion
                new BigDecimal("4500000.00"),      // SalarioMensual (Específico ETC)
                "IT"                               // Departamento (Específico ETC)
        );
        elementosParaMapa.add(empleadoCompleto);
        logger.debug("Creado: EmpleadoTiempoCompleto (Batch Demo)");

        // ------------------------------------------------------------------
        // 2. CREACIÓN DE CONTRATISTA
        // Usa el constructor completo: Persona fields + Empleado fields + Contratista fields
        // ------------------------------------------------------------------
        Contratista contratistaReal = new Contratista(
                "Juan",
                "Perez",
                "1234567",
                LocalDate.of(1980, 5, 5),
                new PosicionGPS(-33.4489, -70.6693), // PosicionGPS
                avatarDefault,                     // Avatar
                fechaContratoBase.plusYears(1),    // FechaContratacion
                new BigDecimal("1500000"),         // MontoPorProyecto (Específico Contratista)
                3,                                 // ProyectosCompletados (Específico Contratista)
                LocalDate.now().plusMonths(6)      // FechaFinContrato (Específico Contratista)
        );
        elementosParaMapa.add(contratistaReal);
        logger.debug("Creado: Contratista (Juan Perez)");

        // ------------------------------------------------------------------
        // 3. CREACIÓN DE GERENTE (Para probar la lógica de vacaciones)
        // ------------------------------------------------------------------
        Gerente gerenteDemo = new Gerente(
                "Jefe",
                "Maximo",
                "9990001",
                LocalDate.of(1975, 1, 1),
                new PosicionGPS(-25.2600, -57.5500),
                avatarDefault,
                LocalDate.of(2010, 1, 1), // Antigüedad > 5 años
                15, // Años de Antiguedad (para demo)
                "Dirección General"
        );
        elementosParaMapa.add(gerenteDemo);
        logger.debug("Creado: Gerente (Jefe Maximo)");


        // ------------------------------------------------------------------
        // 4. CREACIÓN DE VEHICULO
        // ------------------------------------------------------------------
        Vehiculo miVehiculo = new Vehiculo(
                "ABC-123",
                "Camioneta",
                new PosicionGPS(-25.3000, -57.6000),
                "http://img.com/icono_coche.png"
        );
        elementosParaMapa.add(miVehiculo);
        logger.debug("Creado: Vehiculo (ABC-123)");


        // ------------------------------------------------------------------
        // 5. CREACIÓN DE EDIFICIO
        // ------------------------------------------------------------------
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
// src/main/java/py/edu/uc/lp32025/util/MapeableDisplayUtil.java
package py.edu.uc.lp32025.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.uc.lp32025.domain.*;
import py.edu.uc.lp32025.interfaces.Mapeable;

import java.util.List;

/**
 * Utilidad para procesar y mostrar la información de cualquier lista de objetos Mapeable.
 */
public class MapeableDisplayUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapeableDisplayUtil.class);

    /**
     * Recorre la lista de elementos Mapeables y registra su información.
     */
    public static void mostrarInfoMapeable(List<Mapeable> elementos) {

        logger.info("--- Iniciando recorrido de Elementos Polimórficos (Mapeable) ---");

        for (Mapeable elemento : elementos) {

            // 1. Contrato Mapeable (Polimorfismo de Interfaz)
            PosicionGPS ubicacion = elemento.ubicarElemento();
            Avatar avatar = elemento.obtenerImagen();

            String nombreClase = elemento.getClass().getSimpleName();

            // Usamos logger.info para la salida principal
            logger.info("-------------------------------------");
            logger.info("Tipo de Entidad: {}", nombreClase);
            logger.info("  -> Ubicación GPS: {}", ubicacion);
            logger.info("  -> Avatar Obtenido: {}", avatar);

            // 2. Lógica Específica (Polimorfismo de Clase/Herencia)
            if (elemento instanceof Empleado e) {
                // Lógica específica para la jerarquía Empleado
                logger.debug("  -> Es Empleado. Documento: {}", e.getNumeroDocumento());
                logger.info("  -> Salario Bruto: {}", e.calcularSalario());
            } else if (elemento instanceof Vehiculo v) {
                // Lógica específica para Vehiculo
                logger.info("  -> Es Vehículo. Placa: {}", v.getPlaca());
            } else if (elemento instanceof Edificio ed) {
                // Lógica específica para Edificio
                logger.info("  -> Es Edificio. Nombre: {}", ed.getNombre());
            }
        }
        logger.info("--- Finalizado recorrido de Elementos Polimórficos ---");
    }
}
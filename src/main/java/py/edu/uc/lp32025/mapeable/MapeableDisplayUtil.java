package py.edu.uc.lp32025.mapeable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class MapeableDisplayUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapeableDisplayUtil.class);

    private MapeableDisplayUtil() { }

    public static void mostrarResumen(List<Mapeable> items) {
        logger.info("=== Mapeable Display Util: resumen de {} elementos ===", items == null ? 0 : items.size());
        if (items == null || items.isEmpty()) {
            logger.info("No hay elementos para mostrar.");
            return;
        }

        for (Mapeable m : items) {
            try {
                String type = m.getClass().getSimpleName();
                PosicionGPS pos = m.ubicarElemento();
                Avatar av = m.obtenerImagen();
                String summary = m.getDisplaySummary();

                logger.info("Tipo: {}, Summary: {}", type, summary);
                if (pos != null) {
                    logger.debug(" -> Posición: {}", pos);
                }
                if (av != null) {
                    logger.debug(" -> Avatar: {}", av);
                }
            } catch (Exception e) {
                logger.error("Error mostrando elemento mapeable de clase {}: {}", m == null ? "null" : m.getClass().getSimpleName(), e.getMessage(), e);
            }
        }
        logger.info("=== Fin del listado ===");
    }
}

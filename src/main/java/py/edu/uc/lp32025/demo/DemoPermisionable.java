package py.edu.uc.lp32025.demo;

// ... (Imports)
import lombok.extern.slf4j.Slf4j;
import py.edu.uc.lp32025.domain.Empleado; // ⬅️ Importamos Empleado (clase base para ID)
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.interfaces.GerentePermisionable;
import py.edu.uc.lp32025.interfaces.Permisionable;
import py.edu.uc.lp32025.util.PermisionableDataFactory;

import java.time.LocalDate;
import java.util.List;
// ...

@Slf4j
public class DemoPermisionable {

    public static void main(String[] args) {
        // ... (Log de inicio)
        List<Permisionable> listaPermisionables = PermisionableDataFactory.getEmpleadosPermisionables();
        LocalDate hoy = LocalDate.now();

        // ------------------------------------------------
        // DEMOSTRACIÓN POLIMÓRFICA
        // ------------------------------------------------

        for (Permisionable p : listaPermisionables) {

            // Paso 1: Obtener el ID de forma segura (Todos descienden de Empleado/Persona)
            Long empleadoId = null;
            String tipoEntidad = p.getClass().getSimpleName();

            // Usamos un cast seguro a Empleado para obtener el ID y el nombre
            if (p instanceof Empleado empleado) { // ⬅️ Safe cast a Empleado
                empleadoId = empleado.getId();
                tipoEntidad = empleado.getNombre() + " (" + tipoEntidad + ")";
            }

            // Si por alguna razón no tiene ID, saltamos (medida de seguridad)
            if (empleadoId == null) continue;

            // Caso Base: Intentar solicitar 5 días (Válido para todos)
            log.info("\n--- [{}] Intentando Solicitud Base de Vacaciones (5 días) ---", tipoEntidad);

            try {
                // Intentamos solicitar 5 días.
                p.solicitarVacaciones(
                        empleadoId, // Usamos el ID seguro
                        hoy.plusDays(10),
                        hoy.plusDays(14)
                );
            }
            catch (PermisoNoConcedidoException e) {
                log.warn("❌ Solicitud Rechazada: {}", e.getMessage());
            }
            catch (DiasInsuficientesException e) {
                System.out.println("No hay suficientes días disponibles: " + e.getMessage());
            }

            // Caso 2: Intentar solicitar permiso especial (Matrimonio)
            log.info("--- [{}] Intentando Solicitud de Permiso Especial ---", tipoEntidad);
            try {
                // Intentamos solicitar 3 días por Matrimonio (normalmente válido).
                p.solicitarPermisoEspecial(
                        empleadoId,
                        hoy.plusDays(5),
                        hoy.plusDays(7),
                        "MATRIMONIO"
                );
            } catch (PermisoNoConcedidoException e) {
                log.warn("❌ Solicitud Rechazada: {}", e.getMessage());
            }

            // ------------------------------------------------
            // CASOS DE PRUEBA ESPECÍFICOS DE GERENTE
            // ------------------------------------------------

            // Usamos Pattern Matching para verificar si es un GerentePermisionable y castear
            if (p instanceof GerentePermisionable gp) {

                log.info("--- [GERENTE ID:{}] Funcionalidad Extendida GerentePermisionable ---", empleadoId);

                // Caso G1: Prueba del método exclusivo del Gerente
                try {
                    boolean autorizado = gp.autorizarPermisoSubordinado(1L, "Aprobado por buena performance.");
                    log.info("✅ Autorización de Subordinado (ID: 1) Exitosa: {}", autorizado);
                } catch (PermisoNoConcedidoException e) {
                    log.error("❌ Fallo al autorizar permiso: {}", e.getMessage());
                }

                // Caso G2: Prueba de la regla especial de vacaciones (> 20 días)
                log.info("--- [GERENTE ID:{}] Solicitud Extrema de Vacaciones (23 días) ---", empleadoId);
                try {
                    gp.solicitarVacaciones(
                            empleadoId,
                            hoy.plusDays(30),
                            hoy.plusDays(52) // 23 días de diferencia
                    );
                } catch (PermisoNoConcedidoException e) {
                    log.warn("❌ Solicitud Rechazada (Gerente): {}", e.getMessage());
                }
                catch (DiasInsuficientesException e) {
                    System.out.println("No hay suficientes días disponibles: " + e.getMessage());
                }
            }
            // Nota: El cast a EmpleadoTiempoCompleto para obtener el ID ya no es necesario,
            // pues usamos el cast a Empleado al inicio del bucle.
        }

        log.info("\n=============================================");
        log.info("  DEMOSTRACIÓN FINALIZADA");
        log.info("=============================================");
    }
}
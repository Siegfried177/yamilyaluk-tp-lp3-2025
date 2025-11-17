package py.edu.uc.lp32025.demo;

import lombok.extern.slf4j.Slf4j; // ⬅️ Nuevo: Inyecta el logger SLF4J
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.exception.RecursoNoEncontradoException;
import py.edu.uc.lp32025.repository.PersonaRepository;
import py.edu.uc.lp32025.service.PermisosService;
import py.edu.uc.lp32025.util.PermisionableDataFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j // Usando Lombok para el logger
public class DemoPermisionable {

    private static final PersonaRepository MOCK_REPOSITORY = new PersonaRepository() {

        @Override
        public List<Persona> findAll(Sort sort) {
            return List.of();
        }

        @Override
        public Page<Persona> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Persona> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends Persona> List<S> saveAllAndFlush(Iterable<S> entities) {
            return List.of();
        }

        @Override
        public void deleteAllInBatch(Iterable<Persona> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<Long> longs) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Persona getOne(Long aLong) {
            return null;
        }

        @Override
        public Persona getById(Long aLong) {
            return null;
        }

        @Override
        public Persona getReferenceById(Long aLong) {
            return null;
        }

        @Override
        public <S extends Persona> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Persona> List<S> findAll(Example<S> example) {
            return List.of();
        }

        @Override
        public <S extends Persona> List<S> findAll(Example<S> example, Sort sort) {
            return List.of();
        }

        @Override
        public <S extends Persona> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Persona> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Persona> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends Persona, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }

        @Override
        public Optional<Persona> findById(Long id) {
            return Optional.ofNullable(PermisionableDataFactory.getRepository().get(id));
        }

        @Override
        public Persona findByNumeroDocumento(String numeroDocumento) {
            return null; // Not needed for the demo
        }

        @Override
        public List<Persona> findByApellido(String apellido) {
            return List.of(); // Not needed
        }

        @Override
        public List<Persona> findByNombreContainingIgnoreCase(String nombre) {
            return List.of(); // Not needed
        }

        // All JpaRepository methods → one line fallback
        private RuntimeException unsupported() {
            return new UnsupportedOperationException("Not implemented in MOCK repository");
        }

        @Override
        public <S extends Persona> List<S> saveAll(Iterable<S> entities) {
            return List.of();
        }

        @Override public List<Persona> findAll() { throw unsupported(); }
        @Override public List<Persona> findAllById(Iterable<Long> ids) { throw unsupported(); }
        @Override public <S extends Persona> S save(S entity) { throw unsupported(); }
        @Override public void deleteById(Long id) { throw unsupported(); }
        @Override public void delete(Persona entity) { throw unsupported(); }

        @Override
        public void deleteAllById(Iterable<? extends Long> longs) {

        }

        @Override
        public void deleteAll(Iterable<? extends Persona> entities) {

        }

        @Override public void deleteAll() { throw unsupported(); }
        @Override public long count() { throw unsupported(); }
        @Override public boolean existsById(Long id) { throw unsupported(); }
    };


    public static void main(String[] args) {
        log.info("=============================================");
        log.info("  DEMOSTRACIÓN DE POLIMORFISMO PERMISIONABLE ");
        log.info("=============================================");

        // Simulación de inyección de dependencias (Spring)
        // Nota: PermisosService ya tiene @Slf4j
        PermisosService permisosService = new PermisosService(MOCK_REPOSITORY);

        LocalDate hoy = LocalDate.now();

        // ------------------------------------------------
        // CASO 1: Éxito en Solicitud de Vacaciones (ID 1, 10 días)
        // ------------------------------------------------
        log.info("\n--- CASO 1: Solicitud Válida de Vacaciones (ID 1, 10 días)");
        try {
            permisosService.procesarSolicitudVacaciones(
                    1L,
                    hoy.plusDays(30),
                    hoy.plusDays(39) // 10 días de ausencia
            );
        } catch (PermisoNoConcedidoException e) {
            log.error("ERROR INESPERADO: {}", e.getMessage());
        } catch (RecursoNoEncontradoException e) {
            log.error("ERROR: {}", e.getMessage());
        }


        // ------------------------------------------------
        // CASO 2: Fallo por Antigüedad Insuficiente (ID 2)
        // ------------------------------------------------
        log.info("\n--- CASO 2: Solicitud Inválida: Antigüedad Insuficiente (ID 2)");
        try {
            permisosService.procesarSolicitudVacaciones(
                    2L,
                    hoy.plusDays(10),
                    hoy.plusDays(15)
            );
        } catch (PermisoNoConcedidoException e) {
            // El servicio lanza la excepción, y aquí la capturamos.
            log.warn("❌ Fallo Esperado (Antigüedad): {}", e.getMessage());
        } catch (RecursoNoEncontradoException e) {
            log.error("ERROR: {}", e.getMessage());
        }


        // ------------------------------------------------
        // CASO 5: Fallo en Permiso Especial por Exceso (ID 3)
        // ------------------------------------------------
        log.info("\n--- CASO 5: Solicitud Inválida: Exceso de Días por Matrimonio (ID 3)");
        try {
            permisosService.procesarSolicitudPermisoEspecial(
                    3L,
                    hoy.plusDays(1),
                    hoy.plusDays(4), // 4 días de ausencia (Matrimonio max 3)
                    "MATRIMONIO"
            );
        } catch (PermisoNoConcedidoException e) {
            log.warn("❌ Fallo Esperado (Exceso de Días Permiso): {}", e.getMessage());
        } catch (RecursoNoEncontradoException e) {
            log.error("ERROR: {}", e.getMessage());
        }

        log.info("\n=============================================");
        log.info("  DEMOSTRACIÓN FINALIZADA");
        log.info("=============================================");
    }
}
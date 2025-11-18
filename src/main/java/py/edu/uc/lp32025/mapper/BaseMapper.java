package py.edu.uc.lp32025.mapper;

import py.edu.uc.lp32025.domain.Persona; // Usaremos Persona como la Entidad base
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase base abstracta para todos los mappers de la aplicación.
 * Proporciona implementaciones por defecto para conversiones de lista.
 * D: DTO de destino
 * E: Entidad de origen
 */
public abstract class BaseMapper<D, E extends Persona> implements BaseMapperInterface<D, E> {

    // =========================================================
    // IMPLEMENTACIONES DE LISTA (Reutilización de código)
    // =========================================================

    @Override
    public List<D> toDtoList(List<E> entityList) {
        if (entityList == null) {
            return List.of();
        }
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> toEntityList(List<D> dtoList) {
        if (dtoList == null) {
            return List.of();
        }
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    // =========================================================
    // MÉTODOS ABSTRACTOS (OBLIGATORIO)
    // =========================================================

    /**
     * Convierte la Entidad (que debe heredar de Persona) al DTO (D).
     * @param entity La entidad de origen.
     * @return El DTO de destino.
     */
    @Override
    public abstract D toDto(E entity);

    /**
     * Convierte el DTO (D) a la Entidad (E).
     * @param dto El DTO de origen.
     * @return La entidad de destino.
     */
    @Override
    public abstract E toEntity(D dto);
}

// ----------------------------------------------------
// Nota: Necesitas esta interfaz para que el código compile:
// ----------------------------------------------------
interface BaseMapperInterface<D, E> {
    D toDto(E entity);
    E toEntity(D dto);
    List<D> toDtoList(List<E> entityList);
    List<E> toEntityList(List<D> dtoList);
}
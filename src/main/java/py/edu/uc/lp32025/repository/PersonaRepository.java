package py.edu.uc.lp32025.repository;

import py.edu.uc.lp32025.domain.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PersonaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Crear o actualizar
    public Persona save(Persona persona) {
        if (persona.getId() == null) {
            entityManager.persist(persona);  // inserta
            return persona;
        } else {
            return entityManager.merge(persona);  // actualiza
        }
    }

    // Buscar por ID
    public Optional<Persona> findById(Long id) {
        Persona persona = entityManager.find(Persona.class, id);
        return Optional.ofNullable(persona);
    }

    // Listar todos
    public List<Persona> findAll() {
        return entityManager.createQuery("SELECT p FROM Persona p", Persona.class)
                .getResultList();
    }

    // Eliminar por ID
    public void deleteById(Long id) {
        Persona persona = entityManager.find(Persona.class, id);
        if (persona != null) {
            entityManager.remove(persona);
        }
    }
}

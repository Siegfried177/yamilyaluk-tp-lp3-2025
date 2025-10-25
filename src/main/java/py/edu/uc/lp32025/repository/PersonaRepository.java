package py.edu.uc.lp32025.repository;

import py.edu.uc.lp32025.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    // Ejemplo de método personalizado opcional
    List<Persona> findByApellido(String apellido);

    Persona findByNumeroDocumento(String numeroDocumento);
}

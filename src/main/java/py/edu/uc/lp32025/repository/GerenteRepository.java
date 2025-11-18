package py.edu.uc.lp32025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.uc.lp32025.domain.Gerente;

import java.util.List;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {
    // Spring Data JPA automáticamente genera métodos CRUD para la entidad Gerente

    // Aquí puedes añadir métodos de consulta específicos para Gerente si los necesitas:
    List<Gerente> findByAreaResponsabilidad(String area);
}
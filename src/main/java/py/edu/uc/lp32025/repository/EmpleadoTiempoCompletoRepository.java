package py.edu.uc.lp32025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;

import java.util.List;

public interface EmpleadoTiempoCompletoRepository extends JpaRepository<EmpleadoTiempoCompleto, Long> {

    // Método específico: buscar empleados por departamento
    List<EmpleadoTiempoCompleto> findByDepartamento(String departamento);
}

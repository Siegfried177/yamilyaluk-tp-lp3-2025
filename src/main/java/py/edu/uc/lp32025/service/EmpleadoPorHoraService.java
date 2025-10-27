package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.repository.EmpleadoPorHoraRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoPorHoraService {

    private final EmpleadoPorHoraRepository repository;

    public EmpleadoPorHoraService(EmpleadoPorHoraRepository repository) {
        this.repository = repository;
    }

    public List<EmpleadoPorHora> findAll() {
        return repository.findAll();
    }

    public Optional<EmpleadoPorHora> findById(Long id) {
        return repository.findById(id);
    }

    public EmpleadoPorHora save(EmpleadoPorHora empleado) {
        return repository.save(empleado);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

package py.edu.uc.lp32025.interfaces;

import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;

/**
 * Extiende la funcionalidad de permisos para incluir métodos exclusivos de Gerentes.
 */
public interface GerentePermisionable extends Permisionable { // ⬅️ Extiende la interfaz base

    /**
     * Método exclusivo para gerentes: Autorizar el permiso de un subordinado.
     * @param subordinadoId ID del empleado cuya solicitud se está autorizando.
     * @param comentarioGerente Notas de la autorización.
     * @return true si la solicitud es autorizada.
     * @throws PermisoNoConcedidoException Si el gerente no puede autorizar por alguna razón.
     */
    boolean autorizarPermisoSubordinado(Long subordinadoId, String comentarioGerente)
            throws PermisoNoConcedidoException;
}
package py.edu.uc.lp32025.interfaces;

import py.edu.uc.lp32025.domain.Avatar;
import py.edu.uc.lp32025.domain.PosicionGPS;

public interface Mapeable {

    /**
     * Devuelve la posición GPS del elemento.
     */
    PosicionGPS ubicarElemento();

    /**
     * Devuelve información del avatar / imagen y nick asociado al elemento.
     */
    Avatar obtenerImagen();

    /**
     * Método por defecto para obtener una línea corta de información para display/log.
     */
    default String getDisplaySummary() {
        PosicionGPS p = ubicarElemento();
        Avatar a = obtenerImagen();
        String coord = (p == null) ? "sin-pos" : String.format("lat=%.6f,lng=%.6f", p.getLatitud(), p.getLongitud());
        String nick = (a == null || a.getNick() == null) ? "no-nick" : a.getNick();
        return String.format("%s @ %s", nick, coord);
    }
}

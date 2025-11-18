package py.edu.uc.lp32025.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Clase utilitaria con métodos estáticos para la conversión de tipos de datos comunes en mapeo.
 */
public final class MapperUtils { // Se declara final para evitar herencia y un constructor privado

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private MapperUtils() {
        // Constructor privado para evitar instanciación
    }

    public static String mapBigDecimalToString(BigDecimal value, int scale) {
        if (Objects.isNull(value)) {
            return "";
        }
        return value.setScale(scale, RoundingMode.HALF_UP).toPlainString();
    }

    public static String mapLocalDateToString(LocalDate date) {
        if (Objects.isNull(date)) {
            return "";
        }
        return date.format(DATE_FORMATTER);
    }

    public static String mapNullableString(String value) {
        return Objects.requireNonNullElse(value, "");
    }
}
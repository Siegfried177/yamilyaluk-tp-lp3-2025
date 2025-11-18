package py.edu.uc.lp32025.mapper;

import py.edu.uc.lp32025.domain.Persona;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Clase base que contiene métodos utilitarios estáticos para mapeo de tipos de datos básicos.
 * Las subclases deben implementar el método toDto(Persona).
 */
public abstract class BaseMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    // =========================================================
    // MÉTODOS ESTÁTICOS PARA CONVERSIONES BÁSICAS
    // =========================================================

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

    // =========================================================
    // MÉTODO ABSTRACTO DE MAPEADO (OBLIGATORIO)
    // =========================================================

    /**
     * Convierte una entidad Persona a un DTO concreto.
     * Las subclases decidirán qué tipo de DTO devolver.
     */
    public abstract Object toDto(Persona entity);
}

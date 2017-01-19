package ru.alcereo.exoption;

/**
 * Функциональный интерфейс
 * supplier с исключением
 * @param <RETURN>
 * @param <EXCEPTION>
 */
@FunctionalInterface
public interface Supplier<RETURN, EXCEPTION extends Exception> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    RETURN get() throws EXCEPTION;

}

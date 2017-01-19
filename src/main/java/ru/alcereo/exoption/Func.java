package ru.alcereo.exoption;

/**
 * Функциональный интерфейс функции
 * которая может вызвать исключение
 *
 * @param <PARAMETER>
 *     Тип параметра
 * @param <RETURN>
 *     Тип результата
 * @param <EXCEPTION>
 *     Тип исключения
 */
@FunctionalInterface
public interface Func<PARAMETER, RETURN, EXCEPTION extends Exception>{
    RETURN execute(PARAMETER value) throws EXCEPTION;
}

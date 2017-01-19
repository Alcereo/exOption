package ru.alcereo.exoption;

/**
 * Функциональный интерфейс функции оборачивания
 * исключения в свой тип исключения
 * @param <EXCEPTION>
 */
@FunctionalInterface
public interface Exceptioned<EXCEPTION extends Throwable> {
        EXCEPTION getNewException(Throwable cause);
}

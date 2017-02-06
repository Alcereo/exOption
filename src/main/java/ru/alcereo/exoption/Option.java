package ru.alcereo.exoption;


import java.util.function.Function;

/**
 * Монада для хранения значений
 * @param <TYPE>
 */
public abstract class Option<TYPE, EXCEPTION extends Exception> {

    public static final None NONE = new None();

    public abstract <R, E extends Exception> Option<R,E> map(Func<TYPE,R,E> func);

    public abstract <R, E extends Exception> Option<R,E> flatMap(Func<TYPE,Option<R,E>,E> func);

    public abstract <E extends Exception> Option<TYPE,E> filter(Func<TYPE, Boolean, E> filterPredicate);

    public abstract TYPE getOrElse(TYPE valueElse);

    /**
     * Получение содержания <code>Option-а</code>
     * @param function
     *  Функция применяемая к содержанию, для возврата, в случае, когда
     *  внутри содержится исключение - {@link ExcOpt}
     * @param valueIfNone
     *  Значение, возвращаемое, если внутри содержится {@link None}
     * @return
     *  Возвращает хранимое значение:
     *  <ul>
     *      <li>
     *          Eсли содержится значение, то это значение
     *      </li>
     *      <li>
     *          Eсли внутри исключение, тогда применяет функцию и возращает резульат
     *      </li>
     *      <li>
     *          если внутри {@link None}, возвращает указанный параметр
     *      </li>
     *  </ul>
     */
    public abstract TYPE getOrElseWithExc(Function<EXCEPTION, TYPE> function, TYPE valueIfNone);

    public abstract Option<TYPE, EXCEPTION> throwException() throws EXCEPTION;

    public abstract <W extends Throwable> Option<TYPE, EXCEPTION> wrapAndTrowException(Exceptioned<W> exceptioned) throws W;

    public abstract <W extends Exception> Option<TYPE,W> wrapException(Exceptioned<W> exceptioned);

    public abstract <W extends Exception> Option<TYPE,W> wrapNoneWithException(Exceptioned<W> exceptioned);

    public abstract boolean isException();

    public abstract boolean isNone();

    public abstract String getExceptionMessage();


    static <R,E extends Exception> Option<R,E> some(R value){
        if (value == null)
            return none();
        else
            return new Some<>(value);
    }

    public static <R,E extends Exception> Option<R,E> asException(E e){
        return new ExcOpt<R,E>(e);
    }

    @SuppressWarnings("unchecked")
    static <R,E extends Exception> Option<R,E> none(){
        return NONE;
    }

    @SuppressWarnings("unchecked")
    public static <R, E extends Exception> Option<R,E> asOption(R value){
        if (value == null)
            return NONE;
        else
            return some(value);
    }

    @SuppressWarnings("unchecked")
    public static <R, E extends Exception> Option<R,E> asOption(Supplier<R,E> func){
        try{
            return some(func.get());
        } catch (Exception e) {
            return new ExcOpt<>((E)e);
        }
    }

    public static <R> Option<R, NullPointerException> asNotNullWithExceptionOption(R value){
        if (value == null)
            return new ExcOpt<>(new NullPointerException());
        else
            return some(value);
    }

}

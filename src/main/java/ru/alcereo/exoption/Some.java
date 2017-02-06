package ru.alcereo.exoption;


import java.io.Serializable;
import java.util.function.Function;

class Some<TYPE, EXCEPTION extends Exception> extends Option<TYPE, EXCEPTION> implements Serializable{

    private static final long serialVersionUID = 1L;

    private final TYPE value;

    public Some(TYPE value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R, E extends Exception> Option<R,E> map(Func<TYPE, R, E> func){
        try {
            return some(func.execute(value));
        } catch (Throwable e) {
            return new ExcOpt<>((E)e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R, E extends Exception> Option<R,E> flatMap(Func<TYPE,Option<R,E>,E> func){
        try {
            return func.execute(value);
        } catch (Throwable e) {
            return new ExcOpt<>((E)e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends Exception> Option<TYPE,E> filter(Func<TYPE, Boolean, E> filterPredicate){
        try {
            if (filterPredicate.execute(value))
                return (Option<TYPE, E>) this;
            else
                return none();
        } catch (Throwable e) {
            return new ExcOpt<>((E)e);
        }
    }

    @Override
    public TYPE getOrElse(TYPE valueElse) {
        return value;
    }

    @Override
    public TYPE getOrElseWithExc(Function<EXCEPTION, TYPE> function, TYPE valueIfNone) {
        return value;
    }

    @Override
    public Option throwException(){
        return this;
    }

    @Override
    public Option wrapNoneWithException(Exceptioned exceptioned) {
        return this;
    }

    @Override
    public boolean isException() {
        return false;
    }

    @Override
    public boolean isNone() {
        return false;
    }

    @Override
    public String getExceptionMessage() {
        return "";
    }

    @Override
    public Option wrapAndTrowException(Exceptioned exceptioned){
        return this;
    }

    @Override
    public Option wrapException(Exceptioned exceptioned) {
        return this;
    }
}


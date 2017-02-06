package ru.alcereo.exoption;


import java.io.Serializable;
import java.util.function.Function;

class None<TYPE, EXCEPTION extends Exception> extends Option<TYPE, EXCEPTION> implements Serializable{

    private static final long serialVersionUID = 1L;

    @Override
    public Option map(Func func){
        return this;
    }

    @Override
    public Option flatMap(Func func) {
        return this;
    }

    @Override
    public Option filter(Func filterPredicate) {
        return this;
    }

    @Override
    public TYPE getOrElse(TYPE valueElse) {
        return valueElse;
    }

    @Override
    public TYPE getOrElseWithExc(Function<EXCEPTION, TYPE> function, TYPE valueIfNone) {
        return valueIfNone;
    }

    @Override
    public Option throwException(){
        return this;
    }

    @Override
    public <WRAPPED_EXCEPTION extends Exception> Option<TYPE, WRAPPED_EXCEPTION>
    wrapNoneWithException(Exceptioned<WRAPPED_EXCEPTION> exceptioned) {
        return new ExcOpt<>(exceptioned.getNewException(new NullPointerException()));
    }

    @Override
    public boolean isException() {
        return false;
    }

    @Override
    public boolean isNone() {
        return true;
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


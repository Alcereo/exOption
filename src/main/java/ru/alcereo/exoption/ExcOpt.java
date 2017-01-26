package ru.alcereo.exoption;

import java.io.Serializable;
import java.util.Arrays;


public class ExcOpt<TYPE, EXCEPTION extends Exception> extends Option<TYPE,EXCEPTION> implements Serializable{

    private static final long serialVersionUID = 1L;

    private final EXCEPTION exception;

    @SuppressWarnings("unchecked")
    @Override
    public Option map(Func func){
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Option flatMap(Func func){
        return this;
    }

    @Override
    public Option filter(Func filterPredicate){
        return this;
    }

    @Override
    public TYPE getOrElse(TYPE valueElse){
        return valueElse;
    }

    ExcOpt(EXCEPTION exception) {
        this.exception = exception;
    }

    public Option throwException() throws EXCEPTION {
        throw exception;
    }

    @Override
    public <WRAPPED_EXCEPTION extends Throwable> Option<TYPE, EXCEPTION>
    wrapAndTrowException(Exceptioned<WRAPPED_EXCEPTION> exceptioned) throws WRAPPED_EXCEPTION {

        WRAPPED_EXCEPTION t = exceptioned.getNewException(exception);
        StackTraceElement[] stackArr = t.getStackTrace();
        t.setStackTrace(Arrays.copyOfRange(stackArr,2,stackArr.length));

        throw t;
    }

    @Override
    public <WRAPPED_EXCEPTION extends Exception> Option<TYPE, WRAPPED_EXCEPTION>
    wrapException(Exceptioned<WRAPPED_EXCEPTION> exceptioned) {
        WRAPPED_EXCEPTION t = exceptioned.getNewException(exception);
        StackTraceElement[] stackArr = t.getStackTrace();
        t.setStackTrace(Arrays.copyOfRange(stackArr,2,stackArr.length));

        return new ExcOpt<>(t);
    }

    @Override
    public Option wrapNoneWithException(Exceptioned exceptioned) {
        return this;
    }

    @Override
    public boolean isException() {
        return true;
    }

    @Override
    public boolean isNone() {
        return false;
    }

    @Override
    public String getExceptionMessage() {
        return exception.getMessage();
    }
}

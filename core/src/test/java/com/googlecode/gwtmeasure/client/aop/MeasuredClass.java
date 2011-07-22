package com.googlecode.gwtmeasure.client.aop;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dmitry.buzdin
 */
public class MeasuredClass<T> implements IsMeasured {

    public boolean called;
    public Object object;

    public void goToServer() {
        this.called = true;
    }

    public int getResult() {
        return 42;
    }

    public int [] array() {
        return new int[] {1, 2, 3};
    }

    public Set<String> strings() {
        return new HashSet<String>();
    }

    public void passObject(Object object) {
        this.object = object;
    }

    public void passObjects(Object object1, Object object2) {
        this.object = object2;
    }

    public void passVarargs(Object ... objects) {
        this.object = objects[0];
    }

    public final void notMeasured() {
        called = true;
    }

    public void throwsRuntimeException() {
        throw new NullPointerException();
    }

    public void throwsCheckedException() throws IOException {
        throw new IOException();
    }

    public T returnsGenericType() {
        return (T) new Object();
    }

    public Status returnsEnum() {
        return Status.OK;
    }

    public static enum Status {
        OK, FAIL
    }

}

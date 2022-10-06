/*
 * Decompiled with CFR 0_122.
 */
package just.monika.IQ救不了主播你因为你反编译我端子.management.values;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import just.monika.IQ救不了主播你因为你反编译我端子.module.Module;

public class Value<T> {
    String name;
    Module module;
    T value;
    List<Value> values;

    public Value(String name, T value, Module module) {
        this.name = name;
        this.value = value;
        this.module = module;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public String getValueName() {
        return this.name;
    }

    public void setValueName(String name) {
        this.name = name;
    }

    public Module getModule() {
        return this.module;
    }

    public List<Value> getValues() {
        return this.values;
    }

    @Target(value={ElementType.FIELD})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Val {
        public String name() default "null";

        public double minVal() default 1.0;

        public double maxVal() default 5.0;

        public double valInc() default 0.5;
    }

}


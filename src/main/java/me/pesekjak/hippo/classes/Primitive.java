package me.pesekjak.hippo.classes;

import org.objectweb.asm.Opcodes;

public enum Primitive {
    BOOLEAN("boolean", "Z", Opcodes.T_BOOLEAN, Boolean.class),
    CHAR("char", "C", Opcodes.T_CHAR, Character.class),
    BYTE("byte", "B", Opcodes.T_BYTE, Byte.class),
    SHORT("short", "S", Opcodes.T_SHORT, Short.class),
    INT("int", "I", Opcodes.T_INT, Integer.class),
    FLOAT("float", "F", Opcodes.T_FLOAT, Float.class),
    LONG("long", "J", Opcodes.T_LONG, Long.class),
    DOUBLE("double", "D", Opcodes.T_DOUBLE, Double.class),
    VOID("void", "V", 0, Void.class),
    NONE(null, null, 0, null);

    private final String primitive;
    private final String descriptor;
    private final int typeValue;
    private final Class<?> classCounterpart;

    Primitive(String primitive, String descriptor, int typeValue, Class<?> classCounterpart) {
        this.primitive = primitive;
        this.descriptor = descriptor;
        this.typeValue = typeValue;
        this.classCounterpart = classCounterpart;
    }

    public static Primitive fromDescriptor(String descriptor) {
        return switch (descriptor) {
            case "Z" -> Primitive.BOOLEAN;
            case "C" -> Primitive.CHAR;
            case "B" -> Primitive.BYTE;
            case "S" -> Primitive.SHORT;
            case "I" -> Primitive.INT;
            case "F" -> Primitive.FLOAT;
            case "J" -> Primitive.LONG;
            case "D" -> Primitive.DOUBLE;
            case "V" -> Primitive.VOID;
            default -> Primitive.NONE;
        };
    }

    public String getPrimitive() {
        return primitive;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public int getTypeValue() {
        return typeValue;
    }

    public Class<?> getClassCounterpart() {
        return classCounterpart;
    }

}

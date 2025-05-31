package org.project.utils;

public class MapUtils {

    public static <T> T getObject(Object item, Class<T> tClass) {
        if(item != null) {
            if(tClass.getTypeName().equals("java.lang.Long")) {
                item = item != "" ? Long.valueOf(item.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.Integer")) {
                item = item != "" ? Integer.valueOf(item.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.String")) {
                item = item.toString();
            }
            else if ( tClass.getTypeName().equals("java.lang.Boolean")) {
                item = item != "" ? Boolean.valueOf(item.toString()) : null;
            }
            else if (tClass.getTypeName().equals("java.lang.Double")) {
                item = item != "" ? Double.valueOf(item.toString()) : null;
            }
            else if (tClass.getTypeName().equals("java.lang.Float")) {
                item = item != "" ? Float.valueOf(item.toString()) : null;
            }
            else if (tClass.getTypeName().equals("java.lang.Byte")) {
                item = item != "" ? Byte.valueOf(item.toString()) : null;
            }
            else if (tClass.getTypeName().equals("java.lang.Short")) {
                item = item != "" ? Short.valueOf(item.toString()) : null;
            }
            else if (tClass.getTypeName().equals("java.lang.Character")) {
                item = item != "" ? item.toString().charAt(0) : null;
            }
            else {
                throw new IllegalArgumentException("Unsupported type: " + tClass.getTypeName());
            }
            return tClass.cast(item);
        }
        return null;
    }
}
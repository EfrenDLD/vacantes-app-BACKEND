package com.unsij.backend.vacantes_app.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Map;

public class JsonUtils {

    public static Object obtObject(Map<String, Object> map, String key){
        if(map==null || key == null || key.isEmpty()) return null;

        String[] keys = key.split("\\.");
        Object current = map;

        for(String k : keys) {
            if(current instanceof Map){
                current = ((Map<String, Object>) current).get(k);
            }else return null;
        }
        return current;
    }

    public static String obtString (Map<String, Object> map, String key) {
        Object value = obtObject(map, key);
        if(value == null) return null;
        return value instanceof String ? (String) value : null;
    }

    public static Integer obtInteger(Map<String, Object> map, String key) {
        Object value = obtObject(map, key);
        if (value == null) return null;
        if (value instanceof Integer) return (Integer) value;

        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }


    public static Double obtDouble (Map<String, Object> map, String key) {
        Object value = obtObject(map, key);
        if(value == null){
            return null;
        }
        if(value instanceof Double) return (Double) value;

        if (value instanceof Integer) return ((Integer) value).doubleValue();

        if (value instanceof Long) return ((Long) value).doubleValue();

        if(value instanceof String){
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir a Double.");
                return null;
            }
        }
        return null;
    }

    public static Long obtLong (Map<String, Object> map, String key) {
        Object value = obtObject(map, key);
        if(value == null) return null;
        if(value instanceof Long) return (Long) value;

        try {
            if(value instanceof String) return Long.parseLong((String) value);
            if(value instanceof Number) return ((Number) value).longValue();
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }

    public static Date obtDate(Map<String, Object> map, String key) {
        String value = obtString(map, key);
        if (value == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(value, formatter);  // Convertir el String a LocalDate
            // Convertir el LocalDate a Date
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalDate obtLocalDate(Map<String, Object> map, String key) {
        String value = obtString(map,key);
        if(value == null) return null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return  LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalTime obtLocalTime(Map<String, Object> map, String key) {
        String value = obtString(map,key);
        if(value == null) return null;
        try {
            return  LocalTime.parse(value);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    public static boolean obtBoolean(Map<String, Object> map, String key) {
        Object value = obtObject(map, key);

        if (value instanceof Boolean) return (boolean) value;

        if (value instanceof String) {
            String strValue = ((String) value).trim().toLowerCase();
            return "true".equals(strValue);
        }

        if (value instanceof Number) {
            return ((Number) value).intValue() != 0; // 0 es false, cualquier otro número es true
        }

        return false; // Si el valor es null o no es convertible a boolean, retorna false
    }
}
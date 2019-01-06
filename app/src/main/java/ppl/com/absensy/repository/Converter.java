package ppl.com.absensy.repository;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * This class exists to support having java Date column type in Room's database's table
 */
public class Converter {
    private Converter() {
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

package ppl.com.absensy.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ppl.com.absensy.model.AbsenceDetail;
import ppl.com.absensy.model.Subject;

@Database(entities = {Subject.class, AbsenceDetail.class},
        version = 1)
@TypeConverters(value = {Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract SubjectDao subjectDao();

    public abstract AbsenceDetailDao absenceDetailDao();

}

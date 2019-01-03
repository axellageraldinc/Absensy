package ppl.com.absensy.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;
import ppl.com.absensy.model.Subject;

@Dao
public interface SubjectDao {
    @Insert
    void save(Subject subject);

    @Update
    void update(Subject subject);

    @Query(value = "select * from subjects")
    Single<List<Subject>> findAll();

    @Delete
    void delete(Subject subject);
}

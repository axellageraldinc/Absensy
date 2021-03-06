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

    @Query(value = "select * from subjects order by class_schedule asc")
    Single<List<Subject>> findAll();

    @Query(value = "select * from subjects where id = :subjectId")
    Single<Subject> findById(String subjectId);

    @Delete
    void delete(Subject subject);
}

package ppl.com.absensy.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import ppl.com.absensy.model.AbsenceDetail;

@Dao
public interface AbsenceDetailDao {
    @Insert
    void save(AbsenceDetail absenceDetail);

    @Query(value = "select * from absence_details where subject_id = :subjectId")
    Single<List<AbsenceDetail>> findAllBySubjectId(String subjectId);
}

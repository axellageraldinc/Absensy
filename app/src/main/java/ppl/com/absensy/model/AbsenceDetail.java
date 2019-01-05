package ppl.com.absensy.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "absence_details",
        foreignKeys = @ForeignKey(
                entity = Subject.class,
                parentColumns = "id",
                childColumns = "subject_id",
                onDelete = CASCADE)) // delete all absence details on subject delete
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDetail {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "absence_date")
    private Date absenceDate;

    @ColumnInfo(name = "subject_id")
    private String subjectId;
}

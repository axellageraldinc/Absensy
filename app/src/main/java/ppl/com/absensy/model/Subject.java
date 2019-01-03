package ppl.com.absensy.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "subjects")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "absence_amount")
    private int absenceAmount;

    @ColumnInfo(name = "class_schedule")
    private Date classSchedule;

}

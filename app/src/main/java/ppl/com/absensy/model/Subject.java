package ppl.com.absensy.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

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
public class Subject implements Parcelable {

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
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

    protected Subject(Parcel in) {
        id = in.readString();
        name = in.readString();
        absenceAmount = in.readInt();
        classSchedule = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(absenceAmount);
        dest.writeLong(classSchedule.getTime());
    }
}

package ppl.com.absensy.interactor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

import ppl.com.absensy.contract.MatkulAdapterContract;
import ppl.com.absensy.model.MataKuliah;

public class MatkulAdapterInteractorImpl extends SQLiteOpenHelper implements MatkulAdapterContract.Interactor {

    private static final String DB_NAME = "absensy";
    private static final int DB_VERSION = 1;

    private static final String TABLE_MATA_KULIAH = "mata_kuliah";
    private static final String ID_MATA_KULIAH = "id";
    private static final String NAMA_MATA_KULIAH = "nama";
    private static final String JUMLAH_KOSONG = "jumlah_kosong";

    @Inject
    public MatkulAdapterInteractorImpl(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public int updateJumlahKosongMataKuliah(MataKuliah mataKuliah) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(JUMLAH_KOSONG, mataKuliah.getJumlahKosong());
        int rowsAffected = db.update(TABLE_MATA_KULIAH, cv, ID_MATA_KULIAH + " = ?",
                new String[]{String.valueOf(mataKuliah.getId())});
        db.close();
        return rowsAffected;
    }

    @Override
    public int deleteMataKuliah(String mataKuliahId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int status = db.delete(TABLE_MATA_KULIAH, ID_MATA_KULIAH + " = ?", new String[]{String.valueOf(mataKuliahId)});
        db.close();
        return status;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTableMatakuliah = "CREATE TABLE IF NOT EXISTS " +
                TABLE_MATA_KULIAH + "(" +
                ID_MATA_KULIAH + " TEXT PRIMARY KEY, " +
                NAMA_MATA_KULIAH + " TEXT, " +
                JUMLAH_KOSONG + " INTEGER" + ")";
        db.execSQL(queryCreateTableMatakuliah);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATA_KULIAH);
        onCreate(db);
    }
}

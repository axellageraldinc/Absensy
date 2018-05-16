package ppl.com.absensy.interactor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ppl.com.absensy.contract.HomeContract;
import ppl.com.absensy.model.MataKuliah;

public class HomeInteractorImpl extends SQLiteOpenHelper implements HomeContract.Interactor {

    private static final String DB_NAME = "absensy";
    private static final int DB_VERSION = 1;

    private static final String TABLE_MATA_KULIAH = "mata_kuliah";
    private static final String ID_MATA_KULIAH = "id";
    private static final String NAMA_MATA_KULIAH = "nama";
    private static final String JUMLAH_KOSONG = "jumlah_kosong";

    @Inject
    public HomeInteractorImpl(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public long saveMataKuliah(MataKuliah mataKuliah) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID_MATA_KULIAH, mataKuliah.getId());
        cv.put(NAMA_MATA_KULIAH, mataKuliah.getNama());
        cv.put(JUMLAH_KOSONG, mataKuliah.getJumlahKosong());
        long status = db.insert(TABLE_MATA_KULIAH, null, cv);
        db.close();
        return status;
    }

    @Override
    public List<MataKuliah> getAllMataKuliah() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<MataKuliah> mataKuliahList = new ArrayList<>();
        String querySelectAllMataKuliah = "SELECT * FROM " + TABLE_MATA_KULIAH;
        Cursor cursor = db.rawQuery(querySelectAllMataKuliah, null);
        if(cursor.moveToFirst()){
            do{
                MataKuliah mataKuliah = new MataKuliah(
                        cursor.getString(cursor.getColumnIndex(ID_MATA_KULIAH)),
                        cursor.getString(cursor.getColumnIndex(NAMA_MATA_KULIAH)),
                        cursor.getInt(cursor.getColumnIndex(JUMLAH_KOSONG)));
                mataKuliahList.add(mataKuliah);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mataKuliahList;
    }

    @Override
    public boolean isMatkulNameExists(MataKuliah mataKuliah) {
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = NAMA_MATA_KULIAH + "=?";
        Cursor cursor = db.query(TABLE_MATA_KULIAH,
                null,
                whereClause,
                new String[]{ mataKuliah.getNama() },
                null,
                null,
                null);
        int result = cursor.getColumnCount();
        return result != 0;
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

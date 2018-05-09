package ppl.com.absensy.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import ppl.com.absensy.BuildConfig;
import ppl.com.absensy.model.MataKuliah;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class SqliteHelperTest {

    SqliteHelper sqliteHelper;
    @Mock
    SQLiteDatabase db;
    @Mock
    SQLiteOpenHelper sqLiteOpenHelper;


    private static final String TABLE_MATA_KULIAH = "mata_kuliah";
    private static final String ID_MATA_KULIAH = "id";
    private static final String NAMA_MATA_KULIAH = "nama";
    private static final String JUMLAH_KOSONG = "jumlah_kosong";

    MataKuliah mataKuliah = new MataKuliah("id", "nama makul", 0);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sqliteHelper = new SqliteHelper(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void saveMataKuliahTestSuccess() {
        ContentValues cv = new ContentValues();
        cv.put(ID_MATA_KULIAH, mataKuliah.getId());
        cv.put(NAMA_MATA_KULIAH, mataKuliah.getNama());
        cv.put(JUMLAH_KOSONG, mataKuliah.getJumlahKosong());

        BDDMockito.given(db.insert(TABLE_MATA_KULIAH, null, cv)).willReturn(1L);

        long result = sqliteHelper.saveMataKuliah(mataKuliah);

        Assert.assertThat(result, Matchers.equalTo(1L));
    }

    @Test
    public void deleteMataKuliahTestSuccess() {

        sqliteHelper.saveMataKuliah(mataKuliah);

        BDDMockito.given(db.delete(TABLE_MATA_KULIAH, ID_MATA_KULIAH + " = ?", new String[]{String.valueOf(mataKuliah.getId())}))
                        .willReturn(1);

        int result = sqliteHelper.deleteMataKuliah(mataKuliah);

        Assert.assertThat(result, Matchers.equalTo(1));
    }

    @Test
    public void updateJumlahKosongMataKuliahTestSuccess() {

        sqliteHelper.saveMataKuliah(mataKuliah);

        ContentValues cv = new ContentValues();
        cv.put(JUMLAH_KOSONG, mataKuliah.getJumlahKosong());
        BDDMockito.given(db.update(TABLE_MATA_KULIAH, cv, ID_MATA_KULIAH + " = ?",
                new String[]{String.valueOf(mataKuliah.getId())}))
                .willReturn(1);

        int result = sqliteHelper.updateJumlahKosongMataKuliah(mataKuliah);

        Assert.assertThat(result, Matchers.equalTo(1));
    }

    @Test
    public void getAllMataKuliahTestSuccess() {
        List<MataKuliah> mataKuliahList = new ArrayList<>();
        MataKuliah mataKuliah1 = new MataKuliah("id", "nama makul", 0);
        mataKuliahList.add(mataKuliah1);

        sqliteHelper.saveMataKuliah(mataKuliah1);

        List<MataKuliah> list = sqliteHelper.getAllMataKuliah();

        Assert.assertThat(list, Matchers.notNullValue());
        Assert.assertThat(list.isEmpty(), org.hamcrest.Matchers.equalTo(false));
    }

    @Test
    public void onCreate() {
        String queryCreateTableMatakuliah = "CREATE TABLE IF NOT EXISTS " +
                TABLE_MATA_KULIAH + "(" +
                ID_MATA_KULIAH + " TEXT PRIMARY KEY, " +
                NAMA_MATA_KULIAH + " TEXT, " +
                JUMLAH_KOSONG + " INTEGER" + ")";
        db.execSQL(queryCreateTableMatakuliah);
    }
}
package ppl.com.absensy.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import ppl.com.absensy.model.MataKuliah;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MatkulAdapterInteractorImplTest {

    HomeInteractorImpl homeInteractor;
    MatkulAdapterInteractorImpl matkulAdapterInteractor;

    MataKuliah mataKuliah = new MataKuliah("1", "makul", 0);

    @Before
    public void setUp() throws Exception {
        homeInteractor = new HomeInteractorImpl(RuntimeEnvironment.application.getApplicationContext());
        matkulAdapterInteractor = new MatkulAdapterInteractorImpl(RuntimeEnvironment.application.getApplicationContext());
    }

    @Test
    public void updateJumlahKosongMataKuliah() {
        homeInteractor.saveMataKuliah(mataKuliah);
        int rowsAffected = matkulAdapterInteractor.updateJumlahKosongMataKuliah(mataKuliah);
        assertThat(rowsAffected, equalTo(1));
    }

    @Test
    public void deleteMataKuliah() {
        homeInteractor.saveMataKuliah(mataKuliah);
        int rowsAffected = matkulAdapterInteractor.deleteMataKuliah(mataKuliah.getId());
        assertThat(rowsAffected, equalTo(1));
    }


}
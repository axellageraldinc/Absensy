package ppl.com.absensy.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import ppl.com.absensy.model.MataKuliah;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class HomeInteractorImplTest {

    HomeInteractorImpl homeInteractor;

    MataKuliah mataKuliah = new MataKuliah("1", "makul", 0);

    @Before
    public void setUp() throws Exception {
        homeInteractor = new HomeInteractorImpl(RuntimeEnvironment.application.getApplicationContext());
    }

    @Test
    public void saveMataKuliah() {
        long status = homeInteractor.saveMataKuliah(mataKuliah);
        assertThat(status, equalTo(1L));
    }

    @Test
    public void getAllMataKuliah() {
        saveMataKuliah();
        List<MataKuliah> mataKuliahList = homeInteractor.getAllMataKuliah();
        assertThat(mataKuliahList, notNullValue());
        assertThat(mataKuliahList.isEmpty(), equalTo(false));
    }

    @Test
    public void mataKuliahExists() {
        saveMataKuliah();
        boolean isExists = homeInteractor.isMatkulNameExists(mataKuliah);
        assertThat(isExists, equalTo(true));
    }

}
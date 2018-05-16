package ppl.com.absensy.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import ppl.com.absensy.component.DaggerHomeInteractorComponent;
import ppl.com.absensy.component.DaggerHomePresenterComponent;
import ppl.com.absensy.contract.HomeContract;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.module.HomeInteractorModule;
import ppl.com.absensy.module.HomePresenterModule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class HomePresenterImplTest {

    HomeContract.Interactor homeInteractor;
    HomeContract.Presenter homePresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        homeInteractor = DaggerHomeInteractorComponent.builder()
                .homeInteractorModule(new HomeInteractorModule(RuntimeEnvironment.application.getApplicationContext()))
                .build()
                .provideHomeInteractor();
        homePresenter = DaggerHomePresenterComponent.builder()
                .homePresenterModule(new HomePresenterModule(homeInteractor))
                .build()
                .provideHomePresenter();
    }

    @Test
    public void getAllMataKuliahFromDatabase() {
        saveMataKuliah();
        List<MataKuliah> mataKuliahList = homePresenter.getAllMataKuliahFromDatabase();
        assertThat(mataKuliahList, notNullValue());
        assertThat(mataKuliahList.isEmpty(), equalTo(false));
    }

    @Test
    public void saveMataKuliah() {
        long status = homePresenter.saveMataKuliah("1", "makul", 0);
        assertThat(status, equalTo(1L));
    }

    @Test
    public void saveMataKuliahGagalKarenaNamaMataKuliahAngka() {
        long status = homePresenter.saveMataKuliah("1", "12345", 0);
        assertThat(status, equalTo(-2L));
    }

    @Test
    public void checkMataKuliah() {
        MataKuliah mataKuliah =
                new MataKuliah("1", "makul", 0);
        homePresenter.saveMataKuliah(mataKuliah.getId(), mataKuliah.getNama(),
                mataKuliah.getJumlahKosong());
        boolean isMatkulNameExists =
                homePresenter.isMatkulNameExists(mataKuliah.getNama());
        assertThat(isMatkulNameExists, equalTo(true));
    }

}
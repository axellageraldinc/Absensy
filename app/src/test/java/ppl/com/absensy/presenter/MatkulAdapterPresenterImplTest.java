package ppl.com.absensy.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import ppl.com.absensy.component.DaggerHomeInteractorComponent;
import ppl.com.absensy.component.DaggerHomePresenterComponent;
import ppl.com.absensy.component.DaggerMatkulAdapterInteractorComponent;
import ppl.com.absensy.component.DaggerMatkulAdapterPresenterComponent;
import ppl.com.absensy.interactor.HomeInteractorImpl;
import ppl.com.absensy.interactor.MatkulAdapterInteractorImpl;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.module.HomeInteractorModule;
import ppl.com.absensy.module.HomePresenterModule;
import ppl.com.absensy.module.MatkulAdapterInteractorModule;
import ppl.com.absensy.module.MatkulAdapterPresenterModule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MatkulAdapterPresenterImplTest {

    HomePresenterImpl homePresenter;
    MatkulAdapterPresenterImpl matkulAdapterPresenter;
    HomeInteractorImpl homeInteractor;
    MatkulAdapterInteractorImpl matkulAdapterInteractor;

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
        matkulAdapterInteractor = DaggerMatkulAdapterInteractorComponent.builder()
                .matkulAdapterInteractorModule(new MatkulAdapterInteractorModule(RuntimeEnvironment.application.getApplicationContext()))
                .build()
                .provideMatkulAdapterInteractor();
        matkulAdapterPresenter = DaggerMatkulAdapterPresenterComponent.builder()
                .matkulAdapterPresenterModule(new MatkulAdapterPresenterModule(RuntimeEnvironment.application.getApplicationContext(), matkulAdapterInteractor))
                .build()
                .provideMatkulAdapterPresenter();
    }

    @Test
    public void deleteMataKuliah() {
        long statusInsert = homePresenter.saveMataKuliah("1", "makul", 0);
        assertThat(statusInsert, equalTo(1L));
        int statusDelete = matkulAdapterPresenter.deleteMataKuliah("1");
        assertThat(statusDelete, equalTo(1));
    }

    @Test
    public void updateJumlahAbsen() {
        long statusInsert = homePresenter.saveMataKuliah("1", "makul", 0);
        assertThat(statusInsert, equalTo(1L));
        int statusUpdate = matkulAdapterPresenter.updateJumlahKosongMataKuliah(new MataKuliah("1", "makul", 0));
        assertThat(statusUpdate, equalTo(1));
    }
}
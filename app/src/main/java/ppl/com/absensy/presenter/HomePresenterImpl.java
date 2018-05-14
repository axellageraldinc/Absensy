package ppl.com.absensy.presenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import ppl.com.absensy.component.DaggerHomeInteractorComponent;
import ppl.com.absensy.contract.HomeContract;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.module.HomeInteractorModule;

public class HomePresenterImpl implements HomeContract.Presenter {

    private Context context;
    private HomeContract.Interactor homeInteractor;

    @Inject
    public HomePresenterImpl(Context context) {
        this.context = context;
        homeInteractor = DaggerHomeInteractorComponent.builder()
                .homeInteractorModule(new HomeInteractorModule(context))
                .build()
                .provideHomeInteractor();
    }

    @Override
    public List<MataKuliah> getAllMataKuliahFromDatabase() {
        return homeInteractor.getAllMataKuliah();
    }

    @Override
    public long saveMataKuliah(String id, String namaMataKuliah, int jumlahKosong) {
        return homeInteractor.saveMataKuliah(new MataKuliah(id, namaMataKuliah, jumlahKosong));
    }
}

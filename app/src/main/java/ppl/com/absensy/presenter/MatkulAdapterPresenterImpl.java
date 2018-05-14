package ppl.com.absensy.presenter;

import android.content.Context;

import javax.inject.Inject;

import ppl.com.absensy.component.DaggerMatkulAdapterInteractorComponent;
import ppl.com.absensy.contract.MatkulAdapterContract;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.module.MatkulAdapterInteractorModule;

public class MatkulAdapterPresenterImpl implements MatkulAdapterContract.Presenter {

    private Context context;
    private MatkulAdapterContract.Interactor matkulAdapterInteractor;

    @Inject
    public MatkulAdapterPresenterImpl(Context context) {
        this.context = context;
        matkulAdapterInteractor = DaggerMatkulAdapterInteractorComponent.builder()
                .matkulAdapterInteractorModule(new MatkulAdapterInteractorModule(context))
                .build()
                .provideMatkulAdapterInteractor();
    }

    @Override
    public int updateJumlahKosongMataKuliah(MataKuliah mataKuliah) {
        return matkulAdapterInteractor.updateJumlahKosongMataKuliah(mataKuliah);
    }

    @Override
    public int deleteMataKuliah(String mataKuliahId) {
        return matkulAdapterInteractor.deleteMataKuliah(mataKuliahId);
    }
}

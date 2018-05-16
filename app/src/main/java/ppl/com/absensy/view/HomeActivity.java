package ppl.com.absensy.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ppl.com.absensy.R;
import ppl.com.absensy.component.DaggerHomePresenterComponent;
import ppl.com.absensy.contract.HomeContract;
import ppl.com.absensy.interactor.HomeInteractorImpl;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.module.HomePresenterModule;

public class HomeActivity extends AppCompatActivity implements HomeContract.View, Button.OnClickListener {

    private FloatingActionButton btnAddMataKuliah;

    private Dialog dialogAddMataKuliah;
    private EditText txtInputNamaMataKuliah, txtInputJumlahKosongMataKuliah;
    private Button btnSimpanMataKuliah;

    private RecyclerView recViewMatkul;
    private RecyclerView.Adapter adapter;
    private List<MataKuliah> listMataKuliah = new ArrayList<>();

    private HomeContract.Presenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePresenter = DaggerHomePresenterComponent.builder()
                .homePresenterModule(new HomePresenterModule(new HomeInteractorImpl(HomeActivity.this)))
                .build()
                .provideHomePresenter();

        initViews();

        showDaftarMataKuliah(homePresenter.getAllMataKuliahFromDatabase());
    }

    private void initViews(){
        btnAddMataKuliah = findViewById(R.id.btnAddMataKuliah);
        btnAddMataKuliah.setOnClickListener(this);

        dialogAddMataKuliah = new Dialog(HomeActivity.this);
        dialogAddMataKuliah.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddMataKuliah.setContentView(R.layout.dialog_add_matkul);
        dialogAddMataKuliah.setCancelable(true);
        txtInputNamaMataKuliah = dialogAddMataKuliah.findViewById(R.id.txtInputMataKuliah);
        txtInputJumlahKosongMataKuliah = dialogAddMataKuliah.findViewById(R.id.txtInputJumlahKosong);
        btnSimpanMataKuliah = dialogAddMataKuliah.findViewById(R.id.btnSimpanMataKuliah);
        btnSimpanMataKuliah.setOnClickListener(this);

        recViewMatkul = findViewById(R.id.recyclerViewMataKuliah);
        recViewMatkul.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MatkulAdapter(listMataKuliah, this);
        recViewMatkul.setAdapter(adapter);
    }

    @Override
    public void showDaftarMataKuliah(List<MataKuliah> mataKuliahList) {
        listMataKuliah.clear();
        listMataKuliah.addAll(mataKuliahList);
        adapter.notifyDataSetChanged();
    }

    private void resetInputMataKuliah(){
        txtInputNamaMataKuliah.setText("");
        txtInputJumlahKosongMataKuliah.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddMataKuliah:
                dialogAddMataKuliah.show();
                break;
            case R.id.btnSimpanMataKuliah:
                long status = homePresenter
                        .saveMataKuliah(
                                UUID.randomUUID().toString(),
                                txtInputNamaMataKuliah.getText().toString(),
                                Integer.parseInt(txtInputJumlahKosongMataKuliah.getText().toString()));
                if(status!=-1 && status!=-2){
                    resetInputMataKuliah();
                    dialogAddMataKuliah.dismiss();
                    showDaftarMataKuliah(homePresenter.getAllMataKuliahFromDatabase());
                    Toast.makeText(this, "Berhasil menambahkan mata kuliah", Toast.LENGTH_SHORT).show();
                } else if(status==-1){
                    Toast.makeText(this, "Gagal menambahkan mata kuliah baru", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, "Nama mata kuliah tidak boleh angka", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

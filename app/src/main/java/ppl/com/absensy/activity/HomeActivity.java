package ppl.com.absensy.activity;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ppl.com.absensy.MatkulAdapter;
import ppl.com.absensy.R;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.repository.SqliteHelper;

public class HomeActivity extends AppCompatActivity {

    private SqliteHelper sqliteHelper;

    private FloatingActionButton btnAddMataKuliah;

    private Dialog dialogAddMataKuliah;
    private EditText txtInputNamaMataKuliah, txtInputJumlahKosongMataKuliah;
    private Button btnSimpanMataKuliah;

    private RecyclerView recViewMatkul;
    private RecyclerView.Adapter adapter;
    private List<MataKuliah> listMataKuliah = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sqliteHelper = new SqliteHelper(getApplicationContext());

        btnAddMataKuliah = findViewById(R.id.btnAddMataKuliah);
        btnAddMataKuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddMataKuliah.show();
            }
        });

        initRecyclerView();
        initDialogAddMataKuliah();
    }

    private void initRecyclerView() {
        recViewMatkul = findViewById(R.id.recyclerViewMataKuliah);
        recViewMatkul.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MatkulAdapter(listMataKuliah, this);
        recViewMatkul.setAdapter(adapter);
        refreshAdapter();
    }

    private void initDialogAddMataKuliah(){
        dialogAddMataKuliah = new Dialog(HomeActivity.this);
        dialogAddMataKuliah.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddMataKuliah.setContentView(R.layout.dialog_add_matkul);
        dialogAddMataKuliah.setCancelable(true);
        txtInputNamaMataKuliah = dialogAddMataKuliah.findViewById(R.id.txtInputMataKuliah);
        txtInputJumlahKosongMataKuliah = dialogAddMataKuliah.findViewById(R.id.txtInputJumlahKosong);
        btnSimpanMataKuliah = dialogAddMataKuliah.findViewById(R.id.btnSimpanMataKuliah);
        btnSimpanMataKuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long status = saveMataKuliahToSqliteDatabase(getUserInputOfMataKuliah());
                if(status!=-1){
                    resetInputMataKuliah();
                    dialogAddMataKuliah.dismiss();
                    refreshAdapter();
                } else{
                    Toast.makeText(HomeActivity.this, "Gagal menambahkan mata kuliah baru", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetInputMataKuliah(){
        txtInputNamaMataKuliah.setText("");
        txtInputJumlahKosongMataKuliah.setText("");
    }

    private MataKuliah getUserInputOfMataKuliah(){
        return new MataKuliah(
                UUID.randomUUID().toString(),
                txtInputNamaMataKuliah.getText().toString(),
                Integer.parseInt(txtInputJumlahKosongMataKuliah.getText().toString()));
    }

    private long saveMataKuliahToSqliteDatabase(MataKuliah mataKuliah){
        return sqliteHelper.saveMataKuliah(mataKuliah);
    }

    private void setDataToArrayList(List<MataKuliah> list) {
        listMataKuliah.clear();
        listMataKuliah.addAll(list);
    }

    private void refreshAdapter() {
        List<MataKuliah> list = sqliteHelper.getAllMataKuliah();
        setDataToArrayList(list);
        adapter.notifyDataSetChanged();
    }

}

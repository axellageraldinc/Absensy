package ppl.com.absensy.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ppl.com.absensy.R;
import ppl.com.absensy.component.DaggerMatkulAdapterPresenterComponent;
import ppl.com.absensy.contract.MatkulAdapterContract;
import ppl.com.absensy.interactor.MatkulAdapterInteractorImpl;
import ppl.com.absensy.model.MataKuliah;
import ppl.com.absensy.module.MatkulAdapterPresenterModule;

public class MatkulAdapter extends RecyclerView.Adapter<MatkulAdapter.ViewHolder> {

    private List<MataKuliah> listMataKuliah;
    private MatkulAdapterContract.Presenter matkulAdapterPresenter;

    public MatkulAdapter(List<MataKuliah> listMataKuliah, Context context) {
        this.listMataKuliah = listMataKuliah;
        matkulAdapterPresenter = DaggerMatkulAdapterPresenterComponent.builder()
                .matkulAdapterPresenterModule(new MatkulAdapterPresenterModule(context, new MatkulAdapterInteractorImpl(context)))
                .build()
                .provideMatkulAdapterPresenter();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_mata_kuliah, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MataKuliah mataKuliah = listMataKuliah.get(position);
        holder.txtId.setText(mataKuliah.getNama());
        holder.txtMatkul.setText(mataKuliah.getNama());
        holder.txtKosong.setText(String.valueOf(mataKuliah.getJumlahKosong()));
    }

    @Override
    public int getItemCount() {
        return listMataKuliah.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView txtId, txtMatkul, txtKosong;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            txtId = itemView.findViewById(R.id.txtId);
            txtMatkul = itemView.findViewById(R.id.txtMataKuliah);
            txtKosong = itemView.findViewById(R.id.txtJumlahKosong);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createDialogAbsen().show();
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    createDialogDelete().show();
                    return true;
                }
            });
        }

        private Dialog createDialogAbsen() {
            final MataKuliah mataKuliah = listMataKuliah.get(getLayoutPosition());
            final Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_absen_matkul);
            dialog.setCancelable(true);
            TextView txtJumlahAbsen = dialog.findViewById(R.id.txtJumlahKosong);
            Button btnAddAbsen = dialog.findViewById(R.id.btnAddAbsen);
            dialog.setTitle(mataKuliah.getNama());
            txtJumlahAbsen.setText("Kosong : " + String.valueOf(mataKuliah.getJumlahKosong()));
            btnAddAbsen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int jumlahKosong = mataKuliah.getJumlahKosong();
                    mataKuliah.setJumlahKosong(jumlahKosong+1);
                    matkulAdapterPresenter.updateJumlahKosongMataKuliah(mataKuliah);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            return dialog;
        }

        private Dialog createDialogDelete() {
            return new AlertDialog.Builder(view.getContext())
                    .setMessage("Apakah anda akan menghapus mata kuliah ini?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteMataKuliah();
                            dialogInterface.dismiss();
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
        }

        private void deleteMataKuliah() {
            int position = getLayoutPosition();
            MataKuliah mataKuliah = listMataKuliah.get(position);
            if (matkulAdapterPresenter.deleteMataKuliah(mataKuliah.getId()) > 0) {
                listMataKuliah.remove(position);
                Toast.makeText(view.getContext(),
                        mataKuliah.getNama() + " terhapus",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(),
                        mataKuliah.getNama() + " tidak terhapus",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

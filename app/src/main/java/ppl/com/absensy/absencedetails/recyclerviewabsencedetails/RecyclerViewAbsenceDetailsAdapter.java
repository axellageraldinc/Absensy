package ppl.com.absensy.absencedetails.recyclerviewabsencedetails;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ppl.com.absensy.R;
import ppl.com.absensy.base.RecyclerViewHelper;
import ppl.com.absensy.model.AbsenceDetail;

public class RecyclerViewAbsenceDetailsAdapter extends RecyclerView.Adapter<RecyclerViewAbsenceDetailsAdapter.AbsenceDetailsViewHolder> implements RecyclerViewHelper<AbsenceDetail> {

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private List<AbsenceDetail> absenceDetailList = new ArrayList<>();

    @NonNull
    @Override
    public AbsenceDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_absence_detail, viewGroup, false);
        return new AbsenceDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenceDetailsViewHolder absenceDetailsViewHolder, int i) {
        AbsenceDetail absenceDetail = absenceDetailList.get(i);
        absenceDetailsViewHolder.tvAbsenceDate.setText(String.format("Kamu absen pada %s", simpleDateFormat.format(absenceDetail.getAbsenceDate())));
    }

    @Override
    public int getItemCount() {
        return absenceDetailList.size();
    }

    @Override
    public void updateData(List<AbsenceDetail> data) {
        absenceDetailList.clear();
        absenceDetailList.addAll(data);
        notifyDataSetChanged();
    }

    class AbsenceDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAbsenceDate;

        public AbsenceDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAbsenceDate = itemView.findViewById(R.id.tvAbsenceDate);
        }
    }
}

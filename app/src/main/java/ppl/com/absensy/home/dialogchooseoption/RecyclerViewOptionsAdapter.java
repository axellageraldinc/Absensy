package ppl.com.absensy.home.dialogchooseoption;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ppl.com.absensy.R;
import ppl.com.absensy.base.RecyclerViewHelper;

public class RecyclerViewOptionsAdapter extends RecyclerView.Adapter<RecyclerViewOptionsAdapter.RecyclerViewOptionViewHolder> implements RecyclerViewHelper<String> {

    private List<String> options = new ArrayList<>();
    private Listener listener;

    public RecyclerViewOptionsAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewOptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_option, viewGroup, false);
        return new RecyclerViewOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewOptionViewHolder recyclerViewOptionViewHolder, int i) {
        String option = options.get(i);
        recyclerViewOptionViewHolder.bind(i);
        recyclerViewOptionViewHolder.tvOption.setText(option);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    @Override
    public void updateData(List<String> data) {
        options.clear();
        options.addAll(data);
        notifyDataSetChanged();
    }

    class RecyclerViewOptionViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layoutOption;
        private TextView tvOption;
        public RecyclerViewOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutOption = itemView.findViewById(R.id.layoutOption);
            tvOption = itemView.findViewById(R.id.tvOption);
        }
        void bind(final int chosenOptionIndex) {
            layoutOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(chosenOptionIndex);
                }
            });
        }
    }

    public interface Listener {
        void onItemClick(int chosenOptionIndex);
    }
}

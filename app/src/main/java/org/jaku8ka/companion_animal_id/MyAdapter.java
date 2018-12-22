package org.jaku8ka.companion_animal_id;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.jaku8ka.companion_animal_id.database.TaskEntry;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    final private ItemClickListener mOnClickListener;
    private List<TaskEntry> mTaskEntries;

    private Context mContext;

    public MyAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaskEntry taskEntry = mTaskEntries.get(position);

        String nameOfPet = taskEntry.getNameOfPet();
        holder.tvName.setText(nameOfPet);

        holder.btnOdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "Date Picker Odc");

            }
        });

        holder.btnVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "Date Picker Vac");
            }
        });
        }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<TaskEntry> getTasks() {
        return mTaskEntries;
    }

    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName;
        private TextView tvLastOdc;
        private TextView tvLastVac;
        private Button btnOdc;
        private Button btnVac;

        public MyViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);
            view.setOnClickListener(this);

            btnOdc = view.findViewById(R.id.btn_par);
            view.setOnClickListener(this);

            btnVac = view.findViewById(R.id.btn_vac);
            view.setOnClickListener(this);

            tvLastOdc = view.findViewById(R.id.tv_date_par);
            tvLastVac = view.findViewById(R.id.tv_date_vac);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = mTaskEntries.get(getAdapterPosition()).getId();
            mOnClickListener.onItemClickListener(clickedPosition);
        }
    }
}

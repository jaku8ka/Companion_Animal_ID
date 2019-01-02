package org.jaku8ka.companion_animal_id;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

        final TaskEntry taskEntry = mTaskEntries.get(position);

        String nameOfPet = taskEntry.getNameOfPet();
        holder.tvName.setText(nameOfPet);

        String dateOfOdc = taskEntry.getDateOfOdc();
        holder.tvLastOdc.setText(dateOfOdc);

        String dateOfVac = taskEntry.getDateOfVac();
        holder.tvLastVac.setText(dateOfVac);

        switch (taskEntry.getPetType()) {
            case 0:
                holder.ivPet.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_cat));
                break;

            case 1:
                holder.ivPet.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_dog));
                break;

            case 2:
                holder.ivPet.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_rabbit));
                break;
        }


        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = AlertDialogFragment.newInstance(taskEntry);
                dialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "Delete");
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
        private ImageView ivPet;
        private Button btnDel;
        private TextView tvLastOdc;
        private TextView tvLastVac;

        private MyViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);
            view.setOnClickListener(this);

            ivPet = view.findViewById(R.id.iv_pet);

            btnDel = view.findViewById(R.id.btn_delete);

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

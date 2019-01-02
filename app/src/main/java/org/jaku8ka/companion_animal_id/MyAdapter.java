package org.jaku8ka.companion_animal_id;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jaku8ka.companion_animal_id.database.TaskEntry;

import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        String nextOdc = null;
        try {
            Date dateOdc = dateFormat.parse(dateOfOdc);
            int monthValue = taskEntry.getNextOdc();
            int myMonthValue;
            switch (monthValue) {
                case 0:
                    myMonthValue = 1;
                    break;
                case 1:
                    myMonthValue = 3;
                    break;
                case 2:
                    myMonthValue = 6;
                    break;
                    default:
                        myMonthValue = 0;
            }
            dateOdc.setMonth(dateOdc.getMonth() + myMonthValue);
            nextOdc = dateFormat.format(dateOdc);
            System.out.println(nextOdc);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateOfOdc.isEmpty()) {
            holder.tvLastOdc.setText("///");
            holder.tvNextOdc.setText("///");
        } else {
            holder.tvLastOdc.setText(dateOfOdc);
            holder.tvNextOdc.setText(nextOdc);
        }

        String dateOfVac = taskEntry.getDateOfVac();

        String nextVac = null;
        try {
            Date dateVac = dateFormat.parse(dateOfVac);
            int monthValue = taskEntry.getNextVac();
            int myMonthValue;
            switch (monthValue) {
                case 0:
                    myMonthValue = 6;
                    break;
                case 1:
                    myMonthValue = 12;
                    break;
                case 2:
                    myMonthValue = 24;
                    break;
                default:
                    myMonthValue = 0;
            }
            dateVac.setMonth(dateVac.getMonth() + myMonthValue);
            nextVac = dateFormat.format(dateVac);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateOfVac.isEmpty()) {
            holder.tvLastVac.setText("///");
            holder.tvNextVac.setText("///");
        } else {
            holder.tvLastVac.setText(dateOfVac);
            holder.tvNextVac.setText(nextVac);
        }

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
        private TextView tvNextOdc;
        private TextView tvNextVac;
        private ConstraintLayout constraintLayout;


        private MyViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);

            ivPet = view.findViewById(R.id.iv_pet);

            btnDel = view.findViewById(R.id.btn_delete);

            tvLastOdc = view.findViewById(R.id.tv_date_par);
            tvLastVac = view.findViewById(R.id.tv_date_vac);
            tvNextOdc = view.findViewById(R.id.tv_date_next_par);
            tvNextVac = view.findViewById(R.id.tv_date_next_vac);

            constraintLayout = view.findViewById(R.id.con_layout);
            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = mTaskEntries.get(getAdapterPosition()).getId();
            mOnClickListener.onItemClickListener(clickedPosition);
        }
    }
}

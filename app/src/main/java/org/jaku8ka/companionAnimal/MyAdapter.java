package org.jaku8ka.companionAnimal;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jaku8ka.companionAnimal.database.TaskEntry;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    final private ItemClickListener mOnClickListener;
    private List<TaskEntry> mTaskEntries;
    private ArrayList<Long> mDate = new ArrayList<>();

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
        HelperClass helper = new HelperClass();

        try {
            String birth = taskEntry.getDateOfBirth();
            StringTokenizer tokens = new StringTokenizer(birth, ".");
            int spinnerValue = taskEntry.getPetType();

            int day = Integer.parseInt(tokens.nextToken().trim());
            int month = Integer.parseInt(tokens.nextToken().trim());
            int year = Integer.parseInt(tokens.nextToken().trim());

            String birthNormalAge = helper.getAge(year, month, day);
            String birthPetAge = helper.getAgePet(Integer.parseInt(birthNormalAge), spinnerValue);

            holder.tvAgeNormal.setText(birthNormalAge + "r.");
            holder.tvAgePet.setText("(" + birthPetAge + "r.)");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NoSuchElementException n) {
            n.printStackTrace();
        }

        String nameOfPet = taskEntry.getNameOfPet();
        holder.tvName.setText(nameOfPet);

        String dateOfOdc = taskEntry.getDateOfOdc();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date dateOdc = null;
        String nextOdc = null;
        try {
            dateOdc = dateFormat.parse(dateOfOdc);
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
        Date dateVac = null;
        String nextVac = null;
        try {
            dateVac = dateFormat.parse(dateOfVac);
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

        String currentDate = dateFormat.format(new Date());

        if (!(dateOfOdc.isEmpty())) {
            long odcCount = (helper.getNumberOfDays(dateOfOdc, nextOdc, "dd.MM.yyyy"));
            long odcDays = (helper.getNumberOfDays(currentDate, nextOdc, "dd.MM.yyyy"));
            int odcProgress = Math.toIntExact(odcCount - odcDays);
            holder.pbOdc.setMax(Math.toIntExact(odcCount));
            String sOdcCount = String.valueOf(odcDays);
            if (Integer.parseInt(sOdcCount) == 1) {
                holder.tvOdcCount.setText(sOdcCount + " den");
            } else holder.tvOdcCount.setText(sOdcCount + " dni");
            holder.pbOdc.setProgress(odcProgress, true);

            LayerDrawable layerDrawableOdc = (LayerDrawable) holder.pbOdc.getProgressDrawable();
            Drawable progressDrawableOdc = layerDrawableOdc.findDrawableByLayerId(android.R.id.progress);

            Float typeOdc = helper.getPbPercent(odcProgress, odcCount);
            if (typeOdc < 33.0) {
                progressDrawableOdc.setColorFilter(mContext.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            } else if (typeOdc < 66.0) {
                progressDrawableOdc.setColorFilter(mContext.getColor(R.color.colorOrange), PorterDuff.Mode.SRC_IN);
            } else
                progressDrawableOdc.setColorFilter(mContext.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        } else {
            holder.tvOdcCount.setText(R.string.countdown);
            holder.pbOdc.setProgress(0);
        }

        if (!(dateOfVac.isEmpty())) {
            long vacCount = (helper.getNumberOfDays(dateOfVac, nextVac, "dd.MM.yyyy"));
            long vacDays = (helper.getNumberOfDays(currentDate, nextVac, "dd.MM.yyyy"));
            int vacProgress = Math.toIntExact(vacCount - vacDays);
            holder.pbVac.setMax(Math.toIntExact(vacCount));
            String sVacCount = String.valueOf(vacDays);
            if (Integer.parseInt(sVacCount) == 1) {
                holder.tvVacCount.setText(sVacCount + " den");
            } else holder.tvVacCount.setText(sVacCount + " dni");
            holder.pbVac.setProgress(vacProgress, true);

            LayerDrawable layerDrawableVac = (LayerDrawable) holder.pbVac.getProgressDrawable();
            Drawable progressDrawableVac = layerDrawableVac.findDrawableByLayerId(android.R.id.progress);

            Float typeOdc = helper.getPbPercent(vacProgress, vacCount);
            if (typeOdc < 33.0) {
                progressDrawableVac.setColorFilter(mContext.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            } else if (typeOdc < 66.0) {
                progressDrawableVac.setColorFilter(mContext.getColor(R.color.colorOrange), PorterDuff.Mode.SRC_IN);
            } else
                progressDrawableVac.setColorFilter(mContext.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        } else {
            holder.tvVacCount.setText(R.string.countdown);
            holder.pbVac.setProgress(0);
        }

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = AlertDialogFragment.newInstance(taskEntry);
                dialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "Delete");
            }
        });

        Calendar calendar = Calendar.getInstance();
        long todayDate = calendar.getTimeInMillis();

        try {
            if (todayDate <= dateOdc.getTime())
                mDate.add(dateOdc.getTime());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (todayDate <= dateVac.getTime())
                mDate.add(dateVac.getTime());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            int minIndex = mDate.indexOf(Collections.min(mDate));
            long finalDate = mDate.get(minIndex);

            if (todayDate <= finalDate) {
                NotificationScheduler.scheduleNotification(mContext, finalDate + 3600*8000);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NoSuchElementException nsee) {
            nsee.printStackTrace();
        }
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
        private TextView tvAgeNormal;
        private TextView tvAgePet;
        private ProgressBar pbOdc;
        private ProgressBar pbVac;
        private TextView tvOdcCount;
        private TextView tvVacCount;
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
            tvAgeNormal = view.findViewById(R.id.age_normal);
            tvAgePet = view.findViewById(R.id.age_pet);
            pbOdc = view.findViewById(R.id.pb_odc);
            pbVac = view.findViewById(R.id.pb_ock);
            tvOdcCount = view.findViewById(R.id.tv_odc_count);
            tvVacCount = view.findViewById(R.id.tv_vac_count);

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

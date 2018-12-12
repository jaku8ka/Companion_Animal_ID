package org.jaku8ka.companion_animal_id;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.jaku8ka.companion_animal_id.database.AppDatabase;
import org.jaku8ka.companion_animal_id.database.TaskEntry;

import java.util.Calendar;

public class AddPetActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;

    private int mTaskId = DEFAULT_TASK_ID;

    private AppDatabase mDb;

    Button mButton;

    EditText mNameOfPet;
    EditText mTypeOfPet;
    EditText mDateOfBirth;
    EditText mSex;
    EditText mSpecies;
    EditText mColorOfPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText(R.string.btn_pet_upd);
            if (mTaskId == DEFAULT_TASK_ID) {

                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        final TaskEntry task = mDb.taskDao().loadTaskById(mTaskId);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI(task);
                            }
                        });
                    }
                });
            }
        }
    }

    private void initViews() {
        mNameOfPet = findViewById(R.id.name_of_pet);
        mTypeOfPet = findViewById(R.id.type_of_pet);
        mDateOfBirth = findViewById(R.id.date_of_birth);
        mSex = findViewById(R.id.sex);
        mSpecies = findViewById(R.id.species);
        mColorOfPet = findViewById(R.id.color_of_pet);

        mButton = findViewById(R.id.add_pet_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }
        mNameOfPet.setText(task.getNameOfPet());
        mTypeOfPet.setText(task.getPetType());
        mDateOfBirth.setText(task.getDateOfBirth());
        mSex.setText(task.getSex());
        mSpecies.setText(task.getSpecies());
        mColorOfPet.setText(task.getColorOfPet());
    }

    public void onSaveButtonClicked() {

        String nameOfPet = mNameOfPet.getText().toString();
        String typeOfPet = mTypeOfPet.getText().toString();
        String dateOfBirth = mDateOfBirth.getText().toString();
        String sex = mSex.getText().toString();
        String species = mSpecies.getText().toString();
        String colorOfPet = mColorOfPet.getText().toString();

        if (nameOfPet.isEmpty()) {
            Toast toast = Toast.makeText(this, "Zadaj meno zvierata!", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            final TaskEntry task = new TaskEntry(nameOfPet, typeOfPet, dateOfBirth, sex, species,
                    colorOfPet);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mTaskId == DEFAULT_TASK_ID) {
                        mDb.taskDao().insertTask(task);
                    } else {
                        task.setId(mTaskId);
                        mDb.taskDao().updateTask(task);
                    }
                    finish();
                }
            });

        }

    }

    public void showDatePickerDialog(View view) {
        DialogFragment date = new DatePickerFragment();

        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        ((DatePickerFragment) date).setCallBack(onDate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            mDateOfBirth.setText(String.valueOf(day) + "." + String.valueOf(month + 1) + "." + String.valueOf(year));
        }
    };

    public Dialog onCreateDialog(Bundle savedInstanceSaved) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_pet);
        builder.setItems(R.array.pet_types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                
            }
        });
        return builder.create();
    }
}

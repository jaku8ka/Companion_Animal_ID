package org.jaku8ka.companion_animal_id;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jaku8ka.companion_animal_id.database.AppDatabase;
import org.jaku8ka.companion_animal_id.database.TaskEntry;

import java.util.Calendar;

public class AddPetActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    public static final String INSTANCE_DATE = "instanceDate";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;

    private int mTaskId = DEFAULT_TASK_ID;

    private AppDatabase mDb;

    private int sexSpinner;
    private int petSpinner;

    Button mButton;

    EditText mNameOfPet;
    Spinner sTypeOfPet;
    TextView mDateOfBirth;
    Spinner sSex;
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

                final LiveData<TaskEntry> task = mDb.taskDao().loadTaskById(mTaskId);

                task.observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(@Nullable TaskEntry taskEntry) {
                        task.removeObserver(this);
                        populateUI(taskEntry);
                    }
                });
            }
        }
    }


    private void initViews() {

        mNameOfPet = findViewById(R.id.name_of_pet);
        mDateOfBirth = findViewById(R.id.date_of_birth);

        sSex = findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(this, R.array.sex_types, R.layout.spinner_item);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSex.setAdapter(adapterSex);
        sSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sexSpinner = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpecies = findViewById(R.id.species);
        mColorOfPet = findViewById(R.id.color_of_pet);

        sTypeOfPet = findViewById(R.id.type_of_pet);
        ArrayAdapter<CharSequence> adapterPet = ArrayAdapter.createFromResource(this, R.array.pet_types, R.layout.spinner_item);
        adapterPet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTypeOfPet.setAdapter(adapterPet);
        sTypeOfPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                petSpinner = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
        sTypeOfPet.setSelection(task.getPetType());
        mDateOfBirth.setText(task.getDateOfBirth());
        sSex.setSelection(task.getSex());
        mSpecies.setText(task.getSpecies());
        mColorOfPet.setText(task.getColorOfPet());
    }

    public void onSaveButtonClicked() {

        String nameOfPet = mNameOfPet.getText().toString();
        int typeOfPet = petSpinner;
        String dateOfBirth = mDateOfBirth.getText().toString();
        int sex = sexSpinner;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        MenuItem menuItem = menu.findItem(R.id.action_setting);
        menuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                onSaveButtonClicked();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String petName = mNameOfPet.getText().toString();
        int petType = petSpinner;
        String petDate = mDateOfBirth.getText().toString();
        int petSex = sexSpinner;
        String petSpecies = mSpecies.getText().toString();
        String petColor = mColorOfPet.getText().toString();

        outState.putString("savedName", petName);
        outState.putInt("savedType", petType);
        outState.putString("savedDate", petDate);
        outState.putInt("savedSex", petSex);
        outState.putString("savedSpecies", petSpecies);
        outState.putString("savedColor", petColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String petName = savedInstanceState.getString("savedName");
        int petType = savedInstanceState.getInt("savedType");
        String petDate = savedInstanceState.getString("savedDate");
        int petSex = savedInstanceState.getInt("savedSex");
        String petSpecies = savedInstanceState.getString("savedSpecies");
        String petColor = savedInstanceState.getString("savedColor");

        mNameOfPet.setText(petName);
        sTypeOfPet.setSelection(petType);
        mDateOfBirth.setText(petDate);
        sSex.setSelection(petSex);
        mSpecies.setText(petSpecies);
        mColorOfPet.setText(petColor);
    }
}

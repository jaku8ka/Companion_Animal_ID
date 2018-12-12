package org.jaku8ka.companion_animal_id;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jaku8ka.companion_animal_id.database.AppDatabase;
import org.jaku8ka.companion_animal_id.database.TaskEntry;

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
    EditText mTypeOfFur;
    EditText mDifferences;
    EditText mDateOfChip;
    EditText mChipNumber;

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
        mTypeOfFur = findViewById(R.id.type_of_fur);
        mDifferences = findViewById(R.id.differences);
        mDateOfChip = findViewById(R.id.date_of_chip);
        mChipNumber = findViewById(R.id.chip_number);

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
            mTypeOfPet.setText(task.getChipNumber());
            mDateOfBirth.setText(task.getDateOfBirth());
            mSex.setText(task.getSex());
            mSpecies.setText(task.getSpecies());
            mColorOfPet.setText(task.getColorOfPet());
            mTypeOfFur.setText(task.getFur());
            mDifferences.setText(task.getDifferences());
            mDateOfChip.setText(task.getChipDate());
            mChipNumber.setText(task.getChipNumber());
    }

    public void onSaveButtonClicked() {

        String nameOfPet = mNameOfPet.getText().toString();
        String typeOfPet = mTypeOfPet.getText().toString();
        String dateOfBirth = mDateOfBirth.getText().toString();
        String sex = mSex.getText().toString();
        String species = mSpecies.getText().toString();
        String colorOfPet = mColorOfPet.getText().toString();
        String typeOfFur = mTypeOfFur.getText().toString();
        String differences = mDifferences.getText().toString();
        String dateOfChip = mDateOfChip.getText().toString();
        String chipNumber = mChipNumber.getText().toString();

        if (nameOfPet.isEmpty()) {
            Toast toast = Toast.makeText(this, "Zadaj meno zvierata!", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            final TaskEntry task = new TaskEntry(nameOfPet, typeOfPet, dateOfBirth, sex, species,
                    colorOfPet, typeOfFur, differences, dateOfChip, chipNumber);
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
}

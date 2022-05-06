package com.example.smartcarmqttapp.screens.quiz;

<<<<<<< HEAD:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/PracticeTheoryActivity.java
import android.content.Context;
=======
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
>>>>>>> master:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/screens/quiz/PracticeTheoryActivity.java
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/PracticeTheoryActivity.java
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
=======
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
>>>>>>> master:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/screens/quiz/PracticeTheoryActivity.java
import android.widget.TextView;

<<<<<<< HEAD:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/PracticeTheoryActivity.java
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
=======
import com.example.smartcarmqttapp.Navigation;
import com.example.smartcarmqttapp.R;
import com.example.smartcarmqttapp.database.CrushersDataBase;
import com.example.smartcarmqttapp.model.Question;
>>>>>>> master:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/screens/quiz/PracticeTheoryActivity.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
<<<<<<< HEAD:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/PracticeTheoryActivity.java
import java.util.Map;

public class PracticeTheoryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private TextView warningTextView;
    private LinearLayout timerContainer;
    private LinearLayout numOfQuestionsContainer;
    private SeekBar timerSeekBar;
    private SeekBar numOfQuestionsSeekBar;
    private TextView timerTextView;
    private TextView numOfQuestionsTextView;

    private List<Question> allQuestions;

    private final ArrayList<String> quizModes = new ArrayList<>(Arrays.asList(
            "Practice Quiz",
            "Theory Exam",
            "Review"
    ));

    private final ArrayList<String> quizModesDescription = new ArrayList<>(Arrays.asList(
            "Take a Quiz on a specific category",
            "Take an Exam with all categories",
            "Practice reivew questions"
    ));

    private final ArrayList<Integer> quizModeImages = new ArrayList<>(Arrays.asList(
       R.drawable.quiz_logo,
       R.drawable.exam_logo,
       R.drawable.review_logo
    ));
=======

public class PracticeTheoryActivity extends AppCompatActivity {

    private Dialog settingsDialog;
    private ImageView settingsButton;

    private int questionCount = 0;
    private String categoryValue = "";

    //Countdown timer
    public static final int TEN_MIN_IN_MILLIS = 600000;
    public static final int FIFTEEN_MIN_IN_MILLIS = 900000;
    public static final int TWENTY_MIN_IN_MILLIS = 1200000;
    private TextView timer;
    private Switch enableTimer;
    private Dialog timerDialog;
    private Button tenMin, fifteenMin, twentyMin;
    private Spinner spin;

>>>>>>> master:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/screens/quiz/PracticeTheoryActivity.java

    private Map<String, List<Question>> categoryQuestions;

<<<<<<< HEAD:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/PracticeTheoryActivity.java
    private String selectedCategory;
    private String selectedMode;
    public static List<Question> selectedQuestions;
=======
    private static int MILLIS = 0;
>>>>>>> master:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/screens/quiz/PracticeTheoryActivity.java

    private int EXAM_TIME_MILLIS = 30*60*1000; // exam time in millis
    private int MILLIS = 0; // default time in millis
    private int numOfQuestions = 10; // default num of questions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MILLIS = 0;
        super.onCreate(savedInstanceState);
<<<<<<< HEAD:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/PracticeTheoryActivity.java
        setContentView(R.layout.theory_section);
        CrushersDataBase db = new CrushersDataBase(this);
        allQuestions = db.getAllQuestions();
        this.categoryQuestions = groupQuestionsByCategory(allQuestions);

        initializeElements();
        initializeNavBar();

        addModesToModeListView();
        addCategoriesToCategoryListView();

    }

    // Initializes all interactive view instances
    private void initializeElements() {
        warningTextView = findViewById(R.id.warningTextView);
        timerContainer = findViewById(R.id.timerContainer);
        numOfQuestionsContainer = findViewById(R.id.numOfQuestionsContainer);
        numOfQuestionsSeekBar = findViewById(R.id.numOfQuestionsSeekBar);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        numOfQuestionsTextView = findViewById(R.id.numOfQuestionsTextView);

        numOfQuestionsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println("Selected Questions: " + (i+1));
                numOfQuestions = i+1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MILLIS = (int)(600000*(1 + ((double)i/timerSeekBar.getMax())));
                System.out.println("Selected Timer: 10min + " + MILLIS);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Groups Questions according to their categories
     * @param questions - List of questions to group
     * @return map of categories and their corresponding list of questions
     */
    private Map<String, List<Question>> groupQuestionsByCategory(List<Question> questions) {
        Map<String, List<Question>> categories = new HashMap<>();
        for(Question question: questions) {
            String category = question.getCategory();
            if(categories.containsKey(category)) {
                categories.get(category).add(question);
            } else {
                List<Question> singleQuestion = Arrays.asList(question);
                categories.put(category, new ArrayList<>(singleQuestion));
            }
        }
        return categories;
    }

    // Populates Mode ListView with pre-defined rows
    private void addModesToModeListView() {

        ModeAdapter modeAdapter = new ModeAdapter(this, quizModes, quizModesDescription, quizModeImages);
        ListView modeListView = findViewById(R.id.listMode);
        modeListView.setOnItemClickListener((adapterView, view, position, id) -> {
            selectedMode = quizModes.get(position);
            if(position == 1) {
                timerContainer.setVisibility(View.INVISIBLE);
                numOfQuestionsContainer.setVisibility(View.INVISIBLE);
                timerTextView.setVisibility(View.INVISIBLE);
                numOfQuestionsTextView.setVisibility(View.INVISIBLE);
            } else {
                timerContainer.setVisibility(View.VISIBLE);
                numOfQuestionsContainer.setVisibility(View.VISIBLE);
                timerTextView.setVisibility(View.VISIBLE);
                numOfQuestionsTextView.setVisibility(View.VISIBLE);
            }
        });

        modeListView.setAdapter(modeAdapter);
    }

    // Populates Category ListView with pre-defined strings
    private void addCategoriesToCategoryListView() {
        ArrayList<String> categories = new ArrayList<>(this.categoryQuestions.keySet());
        ListView categoryListView = findViewById(R.id.listCategory);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        categoryListView.setAdapter(adapter);
        categoryListView.setOnItemClickListener((adapterView, view, position, id) -> {
            selectedCategory = categories.get(position);
        });
    }

    // Adapter for filling rows in Mode ListView
    class ModeAdapter extends ArrayAdapter<String> {
        Context ctx;
        ArrayList<String> titles;
        ArrayList<String> subtitles;
        ArrayList<Integer> logos;

        ModeAdapter(
                Context ctx,
                ArrayList<String> titles,
                ArrayList<String> subtitles,
                ArrayList<Integer> logos
        ) {
            super(ctx, R.layout.row, R.id.textViewTitle, titles);
            this.ctx = ctx;
            this.titles = titles;
            this.subtitles = subtitles;
            this.logos = logos;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater li = (LayoutInflater)getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = li.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.quizLogo);
            TextView title = row.findViewById(R.id.textViewTitle);
            TextView subtitle = row.findViewById(R.id.textViewSubtitle);

            images.setImageResource(quizModeImages.get(position));
            title.setText(quizModes.get(position));
            subtitle.setText(quizModesDescription.get(position));

            return row;
        }
    }

    public void startQuiz(View view) {
        if(selectedMode == null) {
            warningTextView.setText("Please select a mode!");
            return;
        }
        if(selectedMode.equals(quizModes.get(0)) && selectedCategory == null) {
            warningTextView.setText("Please select a category!");
            return;
        }

        selectedQuestions = new ArrayList<>();
        if(selectedMode.equals(quizModes.get(0))) { // Get Questions only from selected category
            selectedQuestions = categoryQuestions.get(selectedCategory);
            Collections.shuffle(selectedQuestions);
            selectedQuestions = selectedQuestions.subList(0, numOfQuestions);
            // get only N questions
        } else if(selectedMode.equals(quizModes.get(1))) { // Get Questions from all categories
            selectedQuestions = allQuestions;
            MILLIS = EXAM_TIME_MILLIS;
        } else {
            for(Question question: allQuestions) { // Get Questions that need to be reviewed
                if(question.getNeedsReview() == 1) {
                    selectedQuestions.add(question);
                }
            }
        }

        Intent intent = new Intent(PracticeTheoryActivity.this, QuizQuestionActivity.class);
        intent.putExtra("TIMER_VALUE", MILLIS);
        startActivity(intent);
        // I tried passing
    }

    private void initializeNavBar() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.practiceTheory);
=======
        setContentView(R.layout.activity_practice_theory);
        Navigation.initializeNavigation(this, R.id.practiceTheory);

        goToQuiz();

        //Pass values to next screen for display, db query, and textview display
>>>>>>> master:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/screens/quiz/PracticeTheoryActivity.java

        Intent intent = this.getIntent();
        intent.putExtra("option_timer", 0);
        intent.putExtra("option_numOfQuestions", 0);
        intent.putExtra("option_category", "categoryName");

<<<<<<< HEAD:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/PracticeTheoryActivity.java
=======
        //Countdown timer
        enableTimer = findViewById(R.id.enableTimer);
        tenMin = findViewById(R.id.tenMin);
        fifteenMin = findViewById(R.id.fifteenMin);
        twentyMin = findViewById(R.id.twentyMin);
        timer = findViewById(R.id.timer);
        timerDialog = new Dialog(this);

        enableTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(enableTimer.isChecked()) {
                    timerDialog.setContentView(R.layout.timer_dialog);
                    timerDialog.show();
                }
                else {
                    timerDialog.cancel();
                    MILLIS = 0;
                }

            }
        });

        settingsDialog = new Dialog(this);
        settingsButton = findViewById(R.id.settingsImage);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.setContentView(R.layout.settings_dialog);
                settingsDialog.show();

                Button button = settingsDialog.findViewById(R.id.confirmBtn);

                String[] category = {"No Category", "Safety and Best Practices", "Environment", "Basic Traffic Rules and Signs"};

                spin = settingsDialog.findViewById(R.id.dropDown);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //
                    }
                });

                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter aa = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, category);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spin.setAdapter(aa);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText enterQnumber = settingsDialog.findViewById(R.id.editTextNumber);

                        if(!enterQnumber.getText().toString().equals("")){
                            questionCount = Integer.parseInt(enterQnumber.getText().toString());
                            if(questionCount > 10 || questionCount < 1){
                                TextView t = settingsDialog.findViewById(R.id.warningForNums);
                                t.setText("Enter a number ranging between 1 - 10");
                                t.setTextColor(Color.RED);
                            }
                        }
                        categoryValue = spin.getSelectedItem().toString();
                        settingsDialog.cancel();
                        Toast.makeText(getBaseContext(),
                                "Setting successfully updated!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void goToQuiz(){
        Button button = findViewById(R.id.practiceQuiz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the user hasnt entered the settings
                if(questionCount == 0 && categoryValue.equals("")){
                    Toast.makeText(getBaseContext(),
                            "No settings chosen -- Loading Random Quiz",
                            Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(PracticeTheoryActivity.this, QuizQuestionActivity.class);
                intent.putExtra("TIMER_VALUE", MILLIS);
                intent.putExtra("OPTION_QUESTIONS", questionCount);
                if(categoryValue.equals("")) intent.putExtra("CATEGORY_SELECTED", "");
                else intent.putExtra("CATEGORY_SELECTED", categoryValue);

                startActivity(intent);
            }
        });
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.tenMin:
                MILLIS = TEN_MIN_IN_MILLIS;
                timerDialog.cancel();
                break;
            case R.id.fifteenMin:
                MILLIS = FIFTEEN_MIN_IN_MILLIS;
                timerDialog.cancel();
                break;
            case R.id.twentyMin:
                MILLIS = TWENTY_MIN_IN_MILLIS;
                timerDialog.cancel();
                break;
            default:
                break;
        }

>>>>>>> master:SmartCarMQTTApp/app/src/main/java/com/example/smartcarmqttapp/screens/quiz/PracticeTheoryActivity.java
    }

}
package com.example.smartcarmqttapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PracticeTheoryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Dialog settingsDialog;
    private ImageView settingsButton;

    private int questionCount = 0;

    //Countdown timer
    public static final int TEN_MIN_IN_MILLIS = 600000;
    public static final int FIFTEEN_MIN_IN_MILLIS = 900000;
    public static final int TWENTY_MIN_IN_MILLIS = 1200000;
    private TextView timer;
    private Switch enableTimer;
    private Dialog timerDialog;
    private Button tenMin, fifteenMin, twentyMin;

    private static int MILLIS;

    private TextView numOfQuestionsTextView;
    private TextView timerTextView;
    private SeekBar numOfQuestionsSeekBar;
    private SeekBar timerSeekBar;

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

    private Map<String, List<Question>> categoryQuestions;

    private String selectedCategory;
    private String selectedMode;
    public static List<Question> selectedQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theory_section);
        CrushersDataBase db = new CrushersDataBase(this);
        allQuestions = db.getAllQuestions();
        this.categoryQuestions = groupQuestionsByCategory(allQuestions);

        initializeElements();
        initializeNavBar();

        addModesToModeListView();
        addCategoriesToCategoryListView();

    }

    private void initializeElements() {
        numOfQuestionsTextView = findViewById(R.id.numOfQuestionsTextView);
        numOfQuestionsSeekBar = findViewById(R.id.numOfQuestionsSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        timerSeekBar = findViewById(R.id.timerSeekBar);

        numOfQuestionsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println("Selected Questions: " + (i+1));
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

                System.out.println("Selected Timer: 10min + " + 10*(i)/timerSeekBar.getMax());
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
                numOfQuestionsTextView.setVisibility(View.INVISIBLE);
                numOfQuestionsSeekBar.setVisibility(View.INVISIBLE);
                timerTextView.setVisibility(View.INVISIBLE);
                timerSeekBar.setVisibility(View.INVISIBLE);
            } else {
                numOfQuestionsTextView.setVisibility(View.VISIBLE);
                numOfQuestionsSeekBar.setVisibility(View.VISIBLE);
                timerTextView.setVisibility(View.VISIBLE);
                timerSeekBar.setVisibility(View.VISIBLE);
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
            System.out.println("Mode not selected");
            return;
        }
        if(selectedMode.equals(quizModes.get(0)) && selectedCategory == null) {
            System.out.println("Category not selected");
            return;
        }

        selectedQuestions = new ArrayList<Question>();
        if(selectedMode.equals(quizModes.get(0))) { // Get Questions only from selected category
            selectedQuestions = categoryQuestions.get(selectedCategory);
            // get only N questions
        } else if(selectedMode.equals(quizModes.get(1))) { // Get Questions from all categories
            selectedQuestions = allQuestions;
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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.connectedCar:
                        startActivity(new Intent(getApplicationContext(), ConnectedCarActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.practiceDriving:
                        startActivity(new Intent(getApplicationContext(), PracticeDrivingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.practiceTheory:
                        return true;

                    case R.id.aboutUs:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

}
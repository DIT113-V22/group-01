package com.example.smartcarmqttapp.screens.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarmqttapp.R;
import com.example.smartcarmqttapp.database.CrushersDataBase;
import com.example.smartcarmqttapp.database.CrushersDataBaseManager;
import com.example.smartcarmqttapp.model.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    private Map<String, List<Question>> categoryQuestions;

    public static String selectedCategory;
    private String selectedMode;

    private int EXAM_TIME_MILLIS = 30*60*1000; // exam time in millis
    private int MILLIS = 0; // default time in millis
    public static int numOfQuestions = 0; // default num of questions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MILLIS = 0;
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
            if(position == 0) {
                timerContainer.setVisibility(View.VISIBLE);
                numOfQuestionsContainer.setVisibility(View.VISIBLE);
                timerTextView.setVisibility(View.VISIBLE);
                numOfQuestionsTextView.setVisibility(View.VISIBLE);
                MILLIS = 0;
                numOfQuestions = 10;
            }else if(position == 1) {
                timerContainer.setVisibility(View.INVISIBLE);
                numOfQuestionsContainer.setVisibility(View.INVISIBLE);
                timerTextView.setVisibility(View.INVISIBLE);
                numOfQuestionsTextView.setVisibility(View.INVISIBLE);
                MILLIS = EXAM_TIME_MILLIS;
                selectedCategory = "No Category";
                numOfQuestions = 0;
            } else {
                timerContainer.setVisibility(View.VISIBLE);
                numOfQuestionsContainer.setVisibility(View.VISIBLE);
                timerTextView.setVisibility(View.VISIBLE);
                numOfQuestionsTextView.setVisibility(View.VISIBLE);
//                selectedCategory = "Review";
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
            selectedCategory = "No Category";
        }

//        selectedQuestions = new ArrayList<>();
//        if(selectedMode.equals(quizModes.get(0))) { // Get Questions only from selected category
//            selectedQuestions = categoryQuestions.get(selectedCategory);
//            Collections.shuffle(selectedQuestions);
//            selectedQuestions = selectedQuestions.subList(0, numOfQuestions);
//            // get only N questions
//        } else if(selectedMode.equals(quizModes.get(1))) { // Get Questions from all categories
//            selectedQuestions = allQuestions;
//            MILLIS = EXAM_TIME_MILLIS;
//        } else {
//            for(Question question: allQuestions) { // Get Questions that need to be reviewed
//                if(question.getNeedsReview() == 1) {
//                    selectedQuestions.add(question);
//                }
//            }
//        }

        Intent intent = new Intent(PracticeTheoryActivity.this, QuizQuestionActivity.class);
        intent.putExtra("TIMER_VALUE", MILLIS);
        intent.putExtra("numOfQuestions", numOfQuestions);
        intent.putExtra("category", selectedCategory);
        startActivity(intent);
        // I tried passing
    }

    private void initializeNavBar() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.practiceTheory);

        Intent intent = this.getIntent();
        intent.putExtra("option_timer", 0);
        intent.putExtra("option_numOfQuestions", 0);
        intent.putExtra("option_category", "categoryName");

    }

}
package com.example.smartcarmqttapp.screens.quiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.smartcarmqttapp.Navigation;
import com.example.smartcarmqttapp.R;
import com.example.smartcarmqttapp.database.CrushersDataBase;
import com.example.smartcarmqttapp.model.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PracticeTheoryActivity extends AppCompatActivity {

    private TextView warningTextView;
    private ConstraintLayout timerContainer;
    private ConstraintLayout numOfQuestionsContainer;
    private TextView timerTextView;
    private TextView numOfQuestionsTextView;

    private final int BUTTON_PRESSED_COLOR = Color.rgb(142, 36, 170);
    private final int BUTTON_COLOR = Color.rgb(186, 104, 200);
    private final int VIEW_COLOR = Color.rgb(255, 255, 255);
    private final int SELECTED_VIEW_COLOR = Color.rgb(220, 220, 220);

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
        try {
            allQuestions = db.getAllQuestions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.categoryQuestions = groupQuestionsByCategory(allQuestions);

        initializeElements();
        Navigation.initializeNavigation(this, R.id.practiceTheory);

        addModesToModeListView();
        addCategoriesToCategoryListView();

    }

    // Initializes all interactive view instances
    private void initializeElements() {
        warningTextView = findViewById(R.id.warningTextView);
        timerContainer = findViewById(R.id.timerContainer);
        numOfQuestionsContainer = findViewById(R.id.numOfQuestionsContainer);
        timerTextView = findViewById(R.id.timerTextView);
        numOfQuestionsTextView = findViewById(R.id.numOfQuestionsTextView);

        Button timerOffButton = (Button)findViewById(R.id.timerOffButton);
        Button tenMinuteTimerButton = (Button)findViewById(R.id.tenMinuteTimerButton);
        Button fifteenMinuteTimerButton = (Button)findViewById(R.id.fifteenMinuteTimerButton);
        Button fiveQuestionButton = (Button)findViewById(R.id.fiveQuestionButton);
        Button tenQuestionButton = (Button)findViewById(R.id.tenQuestionButton);
        Button fifteenQuestionButton = (Button)findViewById(R.id.fifteenQuestionButton);

        timerOffButton.setBackgroundColor(BUTTON_COLOR);
        tenMinuteTimerButton.setBackgroundColor(BUTTON_COLOR);
        fifteenMinuteTimerButton.setBackgroundColor(BUTTON_COLOR);
        fiveQuestionButton.setBackgroundColor(BUTTON_COLOR);
        tenQuestionButton.setBackgroundColor(BUTTON_COLOR);
        fifteenQuestionButton.setBackgroundColor(BUTTON_COLOR);

        timerOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MILLIS = 0;
                timerOffButton.setBackgroundColor(BUTTON_PRESSED_COLOR);
                tenMinuteTimerButton.setBackgroundColor(BUTTON_COLOR);
                fifteenMinuteTimerButton.setBackgroundColor(BUTTON_COLOR);
            }
        });

        tenMinuteTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MILLIS = 10*60*1000;
                timerOffButton.setBackgroundColor(BUTTON_COLOR);
                tenMinuteTimerButton.setBackgroundColor(BUTTON_PRESSED_COLOR);
                fifteenMinuteTimerButton.setBackgroundColor(BUTTON_COLOR);
            }
        });

        fifteenMinuteTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MILLIS = 15*60*1000;
                timerOffButton.setBackgroundColor(BUTTON_COLOR);
                tenMinuteTimerButton.setBackgroundColor(BUTTON_COLOR);
                fifteenMinuteTimerButton.setBackgroundColor(BUTTON_PRESSED_COLOR);
            }
        });

        // Number of Questions buttons

        fiveQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numOfQuestions = 5;
                fiveQuestionButton.setBackgroundColor(BUTTON_PRESSED_COLOR);
                tenQuestionButton.setBackgroundColor(BUTTON_COLOR);
                fifteenQuestionButton.setBackgroundColor(BUTTON_COLOR);
            }
        });

        tenQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numOfQuestions = 10;
                fiveQuestionButton.setBackgroundColor(BUTTON_COLOR);
                tenQuestionButton.setBackgroundColor(BUTTON_PRESSED_COLOR);
                fifteenQuestionButton.setBackgroundColor(BUTTON_COLOR);
            }
        });

        fifteenQuestionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                numOfQuestions = 15;
                fiveQuestionButton.setBackgroundColor(BUTTON_COLOR);
                tenQuestionButton.setBackgroundColor(BUTTON_COLOR);
                fifteenQuestionButton.setBackgroundColor(BUTTON_PRESSED_COLOR);
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

        modeListView.setAdapter(modeAdapter);


        View[] modeViews = new View[3];

        modeListView.setOnItemClickListener((adapterView, view, position, id) -> {
            if(modeViews[position] == null) modeViews[position] = view;
            if(modeViews[0] != null) modeViews[0].setBackgroundColor(VIEW_COLOR);
            if(modeViews[1] != null) modeViews[1].setBackgroundColor(VIEW_COLOR);
            if(modeViews[2] != null) modeViews[2].setBackgroundColor(VIEW_COLOR);

            view.setBackgroundColor(SELECTED_VIEW_COLOR);

            selectedMode = quizModes.get(position);

            if(position == 0) {
                timerContainer.setVisibility(View.VISIBLE);
                numOfQuestionsContainer.setVisibility(View.VISIBLE);
                timerTextView.setVisibility(View.VISIBLE);
                numOfQuestionsTextView.setVisibility(View.VISIBLE);
                findViewById(R.id.cardCategory).setVisibility(View.VISIBLE);
                MILLIS = 0;
                numOfQuestions = 15;

            }else if(position == 1) {
                timerContainer.setVisibility(View.INVISIBLE);
                numOfQuestionsContainer.setVisibility(View.INVISIBLE);
                timerTextView.setVisibility(View.INVISIBLE);
                numOfQuestionsTextView.setVisibility(View.INVISIBLE);
                findViewById(R.id.cardCategory).setVisibility(View.INVISIBLE);
                MILLIS = EXAM_TIME_MILLIS;
                selectedCategory = "No Category";
                numOfQuestions = 0;
            } else {
                timerContainer.setVisibility(View.VISIBLE);
                numOfQuestionsContainer.setVisibility(View.VISIBLE);
                timerTextView.setVisibility(View.VISIBLE);
                numOfQuestionsTextView.setVisibility(View.VISIBLE);
                findViewById(R.id.cardCategory).setVisibility(View.VISIBLE);
//                selectedCategory = "Review";
            }
        });

    }

    // Populates Category ListView with pre-defined strings
    private void addCategoriesToCategoryListView() {
        ArrayList<String> categories = new ArrayList<>(this.categoryQuestions.keySet());
        ListView categoryListView = findViewById(R.id.listCategory);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        categoryListView.setAdapter(adapter);

        View[] categoryViews = new View[3];

        categoryListView.setOnItemClickListener((adapterView, view, position, id) -> {

            if(categoryViews[position] == null) categoryViews[position] = view;
            if(categoryViews[0] != null) categoryViews[0].setBackgroundColor(VIEW_COLOR);
            if(categoryViews[1] != null) categoryViews[1].setBackgroundColor(VIEW_COLOR);
            if(categoryViews[2] != null) categoryViews[2].setBackgroundColor(VIEW_COLOR);

            view.setBackgroundColor(SELECTED_VIEW_COLOR);
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

        Intent intent = new Intent(PracticeTheoryActivity.this, QuizQuestionActivity.class);
        intent.putExtra("TIMER_VALUE", MILLIS);
        intent.putExtra("numOfQuestions", numOfQuestions);
        intent.putExtra("category", selectedCategory);
        startActivity(intent);
        // I tried passing
    }


}

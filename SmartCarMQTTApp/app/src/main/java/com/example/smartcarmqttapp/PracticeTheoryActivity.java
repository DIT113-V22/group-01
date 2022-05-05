package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theory_section);
//        goToQuiz();
        CrushersDataBase db = new CrushersDataBase(this);
        List<Question> questions = db.getAllQuestions();
        this.categoryQuestions = groupQuestionsByCategory(questions);

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

        addModesToModeListView();
        addCategoriesToCategoryListView();

        //Countdown timer
//        enableTimer = findViewById(R.id.enableTimer);
//
//        tenMin = findViewById(R.id.tenMin);
//
//        fifteenMin = findViewById(R.id.fifteenMin);
//
//        twentyMin = findViewById(R.id.twentyMin);
//
//        timerDialog = new Dialog(this);
//
//        timer = findViewById(R.id.timer);
//
//        enableTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(enableTimer.isChecked()) {
//                    timerDialog.setContentView(R.layout.timer_dialog);
//                    timerDialog.show();
//                }
//                else {
//                    timerDialog.cancel();
//                    MILLIS = 0;
//                }
//
//            }
//        });
//
//        settingsDialog = new Dialog(this);
//        settingsButton = findViewById(R.id.settingsImage);
//
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                settingsDialog.setContentView(R.layout.settings_dialog);
//                settingsDialog.show();
//
//                Button button = settingsDialog.findViewById(R.id.confirmBtn);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        EditText enterQnumber = settingsDialog.findViewById(R.id.editTextNumber);
//
//                        questionCount = Integer.parseInt(enterQnumber.getText().toString());
//                        if(questionCount > 10 || questionCount < 1){
//                            TextView t = settingsDialog.findViewById(R.id.warningForNums);
//                            t.setText("Enter a number ranging between 1 - 10");
//                            t.setTextColor(Color.RED);
//                        }
//                        else{
//                            settingsDialog.cancel();
//                            Toast.makeText(getBaseContext(),
//                                    "Setting successfully updated!",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });


    }

//    public void goToQuiz(){
//        Button button = findViewById(R.id.practiceQuiz);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PracticeTheoryActivity.this, QuizQuestionActivity.class);
//                intent.putExtra("TIMER_VALUE", MILLIS);
//                startActivity(intent);
//
//            }
//        });
//    }
//
//    public void buttonOnClick(View view) {
//        switch (view.getId()) {
//            case R.id.tenMin:
//                MILLIS = TEN_MIN_IN_MILLIS;
//                timerDialog.cancel();
//                break;
//            case R.id.fifteenMin:
//                MILLIS = FIFTEEN_MIN_IN_MILLIS;
//                timerDialog.cancel();
//                break;
//            case R.id.twentyMin:
//                MILLIS = TWENTY_MIN_IN_MILLIS;
//                timerDialog.cancel();
//                break;
//            default:
//                break;
//        }
//
//    }

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

    private void addModesToModeListView() {

        ModeAdapter modeAdapter = new ModeAdapter(this, quizModes, quizModesDescription, quizModeImages);
        ListView modeListView = findViewById(R.id.listMode);

        modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0) {
                    System.out.println("test");
                }
            }
        });

        modeListView.setAdapter(modeAdapter);
    }

    private void addCategoriesToCategoryListView() {
        ListView categoryListView = findViewById(R.id.listCategory);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.categoryQuestions.keySet().toArray());
        categoryListView.setAdapter(adapter);
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

}
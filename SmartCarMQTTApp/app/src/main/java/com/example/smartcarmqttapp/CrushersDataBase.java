package com.example.smartcarmqttapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.smartcarmqttapp.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrushersDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CrushersDataBase.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;


    public CrushersDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String CREATE_TABLE = "CREATE TABLE " + QuestionsTable.TABLE_NAME+
            " (" + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuestionsTable.COLUMN_QUESTION + " TEXT," +
            QuestionsTable.COLUMN_ANSWER1 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER2 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER3 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER4 + " TEXT, " +
            QuestionsTable.COLUMN_CORRECT_ANSWER + " INTEGER," +
            QuestionsTable.COLUMN_EXPLANATION + " TEXT, " +
            QuestionsTable.COLUMN_NEEDS_REVIEW + " INTEGER, " +
            QuestionsTable.COLUMN_CATEGORY + " TEXT" + ")";
    db.execSQL(CREATE_TABLE);
    populateQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int pastVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
    onCreate(db);
    }

    private List<Question> createQuestionsAddToList(){
        //Place holder question values (3 topics(categories), 15 questions each)
        return Arrays.asList(
                //CATEGORY 1: Basic Traffic Rules and Signs
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Basic Traffic Rules and Signs"),

                //CATEGORY 2: Environment
                new Question("Which substances are found in vehicle exhaust fumes and harmful to humans and/or the environment?",
                        "Oxygen, hydrogen and helium",
                        "Nitrogen, radon and argon",
                        "Nitric oxide, carbon dioxide and carbon monoxide",
                        "Sulfur dioxide, ammonia, and ozone",
                        3,
                        "Oxides are harmful to not only the environment by causing acidification and eutrophication, but also impact our mucus membranes and airways.",
                        0,
                        "Environment"),

                new Question("For environmental reasons, you must not wash your car on a paved driveway that slopes down towards the street. Why not?",
                        "The street can be damaged by chemicals that run off from your car",
                        "Chemicals and oil residues will run off into manholes and end up in the nearest watercourse",
                        "Car cleaning products evaporate and contribute towards the greenhouse effect",
                        "It takes more water for me to wash my car myself compared with a professional car wash",
                        2,
                        "Manholes divert rainwater to the nearest watercourse and do not purify the water of any chemicals.",
                        0,
                        "Environment"),

                new Question("Does a car-roof box affect fuel consumption?",
                        "Yes, but only if the roof box is loaded",
                        "Yes, in any case",
                        "No, it has no effect",
                        "Yes, but only if the roof box is heavily-loaded",
                        2,
                        "Roof boxes increase the car’s air resistance and therefore increase fuel consumption as well",
                        0,
                        "Environment"),

                new Question("What is the advantage of friction tyres compared to summer tyres?",
                        "They decrease fuel consumption",
                        "They make the car easier to steer",
                        "They improve road grip in winter conditions",
                        "They make the car drive faster",
                        3,
                        "Winter tyres improve road grip to minimize sliding and accidents during winter conditions",
                        0,
                        "Environment"),

                new Question("You are approaching a junction where you intend turn. Which way driving is best from an environmental point of view?",
                        "Put the gear into neutral before making the turn",
                        "Accelerating quickly before making the turn",
                        "Only using the service brakes before making the turn",
                        "Engine braking before making the turn",
                        4,
                        "Engine braking does not use any fuel from the engine cylinders and relies instead on the car’s kinetic energy. This means that no additional fuel is combusted and emissions are therefore decreased.",
                        0,
                        "Environment"),

                new Question("How can you reduce your emissions?",
                        "By always starting in a high gear",
                        "By constantly driving in a low gear",
                        "By keeping a steady speed and driving at a low RPM",
                        "By engine braking as little as possible",
                        3,
                        "If you accelerate and brake frequently, your fuel consumption will increase. You should therefore try to keep a steady speed. Some vehicles are equipped with cruise control which makes it easier for you to maintain a steady speed.\n" +
                                "The higher the RPM, the higher your fuel consumption. You should therefore drive in as high a gear as possible.",
                        0,
                        "Environment"),

                new Question("From an environmental perspective, is it better to start your engine before or after scraping frost off your windshield?",
                        "It is better because the engine gets to warm up before I begin driving",
                        "It is not better because it increases fuel consumption unnecessarily ",
                        "It makes no difference",
                        "It is better because my arm won't get as tired and the engine will help melt the ice ",
                        1,
                        "Warming up your engine by letting it run idle increases emissions as the engine uses up fuel unnecessarily when you are not driving.",
                        0,
                        "Environment"),

                new Question("How is fuel consumption impacted by having low tyre pressure?",
                        "It is not affected",
                        "It is reduced",
                        "It is increased",
                        "Depends on vehicle weight",
                        3,
                        "To reduce fuel consumption, it is important that you regularly check the air pressure in your tires. Too low air pressure leads to increased fuel consumption and impaired road conditions.",
                        0,
                        "Environment"),

                new Question("Which of the following options does not affect fuel consumption?",
                        "Air conditioning",
                        "Vehicle weight",
                        "Mounted roof box",
                        "The condition of my service brakes",
                        4,
                        "Service brakes do not impact fuel consumption because they do not require fuel to be activated.",
                        0,
                        "Environment"),

                new Question("Shorter journeys mean more emissions per kilometre compared with longer journeys. What percentage of car journeys made in Sweden are shorter than five kilometres?",
                        "65 %",
                        "20 %",
                        "35 %",
                        "50 %",
                        4,
                        "Daily commuting, shopping, etc",
                        0,
                        "Environment"),

                new Question("How can you reduce your emissions and thereby reduce your impact on the environment?",
                        "By using tyres that have low rolling resistance and the correct air pressure",
                        "By accelerating quickly when overtaking",
                        "By starting the engine and letting it run idle while I scrape the windshields in order to prevent a cold start",
                        "By making my car trips as short and fast as possible.",
                        1,
                        "By reducing tyre friction and increasing tyre air pressure, less fuel will be consumed as there is overall less rolling resistance from the tyres.",
                        0,
                        "Environment"),

                new Question("Which fuel is best from an environmental perspective?",
                        "Ethanol",
                        "Diesel",
                        "Petrol",
                        "Compressed Natural Gas",
                        1,
                        "Ethanol is best from an environmental perspective as it is a renewable fuel and therefore does not have as much of an impact on the greenhouse effect.",
                        0,
                        "Environment"),

                new Question("Which of the following options determine how much vehicle tax you will have to pay on your car?",
                        "The year of manufacture",
                        "Carbon dioxide emissions",
                        "The total weight",
                        "Gross weight",
                        2,
                        "Vehicle tax is determined based on your vehicle’s emissions of carbon dioxide (CO2).",
                        0,
                        "Environment"),

                new Question("Generally, does the size of the car’s engine affect fuel consumption?",
                        "Yes, a big engine will normally uses more fuel",
                        "Yes, a big engine will normally uses less fuel",
                        "No, the size of the engine does not affect fuel consumption",
                        "No, engine performance and efficiency is the only factor",
                        1,
                        "Statistically, larger engines use more fuel. This is to be expected as it increases vehicle load.",
                        0,
                        "Environment"),

                new Question("You are about to go out for a drive. Will the length of your journey affect your car’s emissions per kilometre?",
                        "Yes, emissions per kilometre are greater on longer journeys than on shorter ones",
                        "Yes, emissions per kilometre are greater on shorter journeys than on longer ones",
                        "No, the length of the journey has no effect on emissions per kilometre",
                        "No, if I drive fast enough the fuel consumption will be the same",
                        2,
                        "The catalytic converter removes harmful emissions from the car’s exhaust fumes. It only works when it has reached its working temperature. During cold starts and short journeys, exhaust fumes will therefore not be purified and the emission of hazardous substances will be significantly increased.",
                        0,
                        "Environment"),




                //CATEGORY 3: Driving Safety and Best Practices
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices"),
                new Question("placeholder question","answer1","answer2","answer3","answer4", 1, "explanation", 0, "Safety and Best Practices")
                );
    }

    private void populateQuestionsTable(){
        List<Question> questions = createQuestionsAddToList();
        for (Question question : questions) {
            createQuestion(question);
        }
    }

    private void createQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_ANSWER1, question.getFirstAnswer());
        cv.put(QuestionsTable.COLUMN_ANSWER2, question.getSecondAnswer());
        cv.put(QuestionsTable.COLUMN_ANSWER3, question.getThirdAnswer());
        cv.put(QuestionsTable.COLUMN_ANSWER4, question.getFourthAnswer());
        cv.put(QuestionsTable.COLUMN_CORRECT_ANSWER, question.getCorrectAnswer());
        cv.put(QuestionsTable.COLUMN_EXPLANATION, question.getExplanation());
        cv.put(QuestionsTable.COLUMN_NEEDS_REVIEW, question.getNeedsReview());
        cv.put(QuestionsTable.COLUMN_CATEGORY, question.getCategory());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions(){
    List<Question> questionList = new ArrayList<>();
    db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

    if(cursor.moveToFirst()){
        do {
        Question question = new Question();
        question.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_QUESTION)));
        question.setFirstAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER1)));
        question.setSecondAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER2)));
        question.setThirdAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER3)));
        question.setFourthAnswer(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER4)));
        question.setCorrectAnswer(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_CORRECT_ANSWER)));
        question.setExplanation(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_EXPLANATION)));
        question.setNeedsReview(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_NEEDS_REVIEW)));
        question.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_CATEGORY)));

        questionList.add(question);

        } while (cursor.moveToNext());
    }
        cursor.close();
        return questionList;
    }

}

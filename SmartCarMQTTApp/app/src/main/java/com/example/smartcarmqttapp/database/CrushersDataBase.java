package com.example.smartcarmqttapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartcarmqttapp.model.Question;
import com.example.smartcarmqttapp.database.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrushersDataBase extends SQLiteOpenHelper {

    public Context context;
    public static final String DATABASE_NAME = "crushersDataBase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "crushersDataBase";

    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_SCORE = "SCORE";
    public static final String COLUMN_CORRECT_ANSWERS = "CORRECT_ANSWERS";
    public static final String COLUMN_WRONG_ANSWERS = "WRONG_ANSWERS";
    public static final String COLUMN_CATEGORY = "CATEGORY";

    private SQLiteDatabase db;


    public CrushersDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /*
    SQL Table for Results of a current Quiz

    --------------------------------------------------------------------
    | _ID  |  SCORE  |  CORRECT_ANSWERS  |  WRONG_ANSWERS  | CATEGORY  |
    --------------------------------------------------------------------
    |      |         |                   |                 |           |
    --------------------------------------------------------------------
    */

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String table = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   COLUMN_SCORE + " TEXT, " +
                   COLUMN_CORRECT_ANSWERS + " TEXT, " +
                   COLUMN_WRONG_ANSWERS + " TEXT, " +
                   COLUMN_CATEGORY + " TEXT " +") ";
        db.execSQL(table);

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private List<Question> createQuestionsAddToList(){
        //Place holder question values (3 topics(categories), 15 questions each)
        return Arrays.asList(
                //CATEGORY 1: Basic Traffic Rules and Signs
                new Question("Your friend’s car has broken down on the motorway and he asks you to tow him. Is this allowed?",
                        "Yes, but I may not drive faster than 20 km/h",
                        "Yes, but it must be done on the hard shoulder and only up until the nearest exit",
                        "Yes, there are no specific rules, but it must be done without posing any danger to other vehicles",
                        "No",
                        2,
                        "Towing on a motorway/clearway is not permitted. Exception: If a car makes an emergency stop on a motorway/clearway, then it can be towed away, but it can only be towed along the hard shoulder and only up until the nearest suitable exit.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("In which situation does the so-called priority-to-the-right rule apply?",
                        "When driving out of a petrol station",
                        "When driving on a major road and approaching a junction",
                        "When approaching a junction in a built-up area without road signs",
                        "When driving out of a car park",
                        3,
                        "When approaching a junction in a built-up area without road signs",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("When is it permitted to park on the left-hand side of the road?",
                        "Parking on the left is never permitted",
                        "On a one-way street",
                        "On roads with a speed limit of 30 km/h",
                        "In home zones (access roads)",
                        2,
                        "Cars may only stop or park on the right-hand side of the road, in the direction of traffic. Exception: If the road is one-way, or if there is a railway or tramway track on the right-hand side, then cars may park on the left-hand side of the road.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("In which case are all of these vehicles permitted to drive on a motorway?",
                        "Cars, buses and mopeds",
                        "Trucks, cars and tractors",
                        "Light motorcycles, cars and trucks",
                        "Busses, tractors and trucks",
                        3,
                        "Not permitted on motorways: Cyclists, pedestrians, mopeds, tractors and heavy machinery",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("Which vehicles must be fitted with red triangular reflectors at their rear?",
                        "Goods vehicles",
                        "Motorised equipment",
                        "Mopeds",
                        "Trailers",
                        4,
                        "There are several different vehicles that have reflectors, but only trailers are required to have red triangular reflectors at the rear.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("You intend to turn around when you are approaching a roundabout. Are you allowed to use the roundabout to turn around (i.e. drive all the way around the roundabout and come back on the same road in the opposite direction)?",
                        "Yes, but it is not recommended",
                        "Yes, this is perfectly safe",
                        "No, I must either go straight or exit the roundabout at the right or the left",
                        "No, because this could disrupt other cars' movement through the roundabout",
                        2,
                        "Using a roundabout to turn is recommended as this is considered safer than making a U-turn.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("What is the highway speed limit?",
                        "150km/hr",
                        "110km/hr",
                        "130km/hr",
                        "125km/hr",
                        2,
                        "110km/hr is generally highway speed limit, although newer highways such as the E4 and E6 increase this to 120km/hr.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("You have stopped for a red light at a pedestrian crossing. When it turns green, there are still children on the pedestrian crossing. What should you do?",
                        "Start driving slowly and wave at them to make them hurry up",
                        "Use my horn to make them hurry so as not to obstruct traffic",
                        "Wait until everyone has passed the crossing before I start driving",
                        "Start driving since the light is green",
                        3,
                        "Although the light is green, you should hold off on doing anything until all of the pedestrians have cleared the crossing.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("You are driving on a motorway with a coupled braked trailer. How fast are you allowed to drive?",
                        "70 km/h",
                        "80 km/h",
                        "100 km/h",
                        "110 km/h",
                        2,
                        "A car with a coupled braked trailer may not drive faster than 80 km/h.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("You are driving on a motorway and approaching an entry. Which of these statements is correct?",
                        "I must give way to any vehicles that are about to enter the motorway",
                        "I must move into the left-hand lane to avoid collision",
                        "I must always slow down",
                        "I should drive in a manner that facilitates other vehicles entering the motorway",
                        4,
                        "When approaching an entry on a motorway, you should facilitate the entrance of other vehicles onto the motorway. There is a mutual interaction here, which means that neither you nor the traffic on the entry have to give way.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("Which of the following vehicles are you permitted to drive with a category B driving licence?",
                        "Private car with trailer, regardless of its weight",
                        "Private car with a total weight of less than 3.5 tonnes and motorised equipment",
                        "Forklift",
                        "Private car and heavy motorcycle",
                        2,
                        "If the total weight exceeds 3.5 tonnes, then the vehicle counts as heavy goods vehicle. A category B licence does not entitle you to drive a heavy goods vehicle. You need a category A licence for heavy motorcycle.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("You have projecting cargo that protrudes by two metres at the front and the back of your car. Do you need to mark the cargo when driving in the dark?",
                        "Yes, with white reflectors and a white light at the front and with red reflectors and a red light at the rear",
                        "Yes, with white reflectors and a white light at the front and at the rear",
                        "Yes, with red reflectors and a red light at the front and at the rear",
                        "No, the cargo does not need to be marked",
                        1,
                        "Load which protrudes at the front of the vehicle or by more than one metre at the rear of the vehicle must be marked with red/yellow flags in daylight. In the dark, the load must be marked with red lights and red reflectors at the rear and white lights and white reflectors at the front.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("You are driving on a road with a speed limit of 70 km/h. You see a bus in front of you that has just stopped to set down passengers. What should you do?",
                        "On this road, I must give way to the bus driver before he drives back on to the carriageway",
                        "On this road, the bus driver must give way before he drives back on to the carriageway",
                        "Bus drivers must always give way, regardless of the speed limit",
                        "I must always give way to buses, regardless of speed limit",
                        2,
                        "If you are driving on a road with a speed limit of 50 km/h or lower, you are obliged to give way to buses which indicate that they are driving out from a bus stop. If the road has several lanes, then this rule only applies if you are driving in the right lane. You do not have a duty to give way if the speed limit is 60 km/h or higher.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("Which of these statements about yellow curb markings is true?",
                        "I can park here but for no longer than 24 hours",
                        "I can park here but the car's wheels must not mount the curb",
                        "I can stop here but not park here",
                        "I cannot park or stop here",
                        3,
                        "The yellow marking indicates that you are allowed to stop here but that parking is prohibited.",
                        0,
                        "Basic Traffic Rules and Signs"),

                new Question("You are driving in a roundabout. A car approaching from the right is about to enter the roundabout. Which options is correct?",
                        "I should stop and give way to the other car",
                        "According to the priority-to-the-right rule, I must give way to the other car",
                        "The car must give way to me since I am already driving in the roundabout",
                        "There is mutual interaction between the vehicles; neither I nor the car is required to give way",
                        3,
                        "All vehicles entering a roundabout must give way to traffic that is already in the roundabout.",
                        0,
                        "Basic Traffic Rules and Signs"),


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
                new Question("You are driving at night and notice a reflector at the side of the road far ahead. How should you act?",
                        "I should reduce my speed and be ready to act",
                        "I should use my horn to signal the pedestrian",
                        "I should wait and refrain from doing anything until I get closer",
                        "I should drive over to the left-hand side of the road",
                        1,
                        "The earlier you plan and adjust your speed, the easier it will be to avoid getting into a dangerous traffic situation.",
                        0,
                        "Safety and Best Practices"),

                new Question("How tall does a child have to be to sit in a seat with an airbag?",
                        "At least 80 cm",
                        "At least 100 cm",
                        "At least 120 cm",
                        "At Least 140 cm",
                        4,
                        "140 cm is the legal height requirement",
                        0,
                        "Safety and Best Practices"),

                new Question("What is meant by a single-vehicle accident?",
                        "An accident in which two vehicles collide",
                        "An accident between a cyclist or pedestrian and a motor vehicle",
                        "An accident involving only one vehicle",
                        "An accident in which only one person dies",
                        3,
                        "Single vehicle accidents are the most common type of accident outside of built-up areas. Many accidents occur at dawn and when it is dark. This is partially due to tiredness.",
                        0,
                        "Safety and Best Practices"),

                new Question("What is the purpose of the so-called three-second rule?",
                        "To assess how long my stopping distance is",
                        "To assess how long my reaction distance is",
                        "To assess where I will meet oncoming vehicles",
                        "To assess the minimum distance I should keep from the vehicle in front of me",
                        4,
                        "You can use the three-second rule by selecting an object such as a verge reflector post or a pit in the road. When the vehicle in front of you passes the mark/object, you start counting the seconds. If it takes less than three seconds until you pass the mark/object, then you are driving too close.",
                        0,
                        "Safety and Best Practices"),

                new Question("You are out driving at night, in the dark, and you are approaching another vehicle. Where should you position your car?",
                        "Towards the right-hand side of the road",
                        "On the hard shoulder",
                        "In the middle of my lane",
                        "Towards the centre line",
                        4,
                        "It can be difficult to spot obstacles or animals at the side of the road in the dark. You should therefore position your car towards the centre line so as not to drive too closely to the right-hand side of the road",
                        0,
                        "Safety and Best Practices"),

                new Question("What is the legal drink-drive limit?",
                        "0.3 parts per thousand",
                        "0.15 parts per thousand",
                        "0.2 parts per thousand",
                        "0.5 parts per thousand",
                        3,
                        "The limit for drink-driving in Sweden is 0.2 parts per thousand.",
                        0,
                        "Safety and Best Practices"),

                new Question("You have been driving at a high speed over a long period of time and become speed blind. What is the greatest risk of speed blindness?",
                        "I will start driving too slowly unless I keep a close eye on the speedometre",
                        "I will miss road signs",
                        "I will misjudge the vehicle's braking distance",
                        "My range of visibility will become shorter",
                        3,
                        "Speed blindness means that you find it difficult to judge what speed you are doing and it feels as if you are driving more slowly than you really are. This may cause you to misjudge distances,",
                        0,
                        "Safety and Best Practices"),

                new Question("Which of these statements is true about inexperienced drivers?",
                        "They often fix their gaze close in front of the car",
                        "They often fix their gaze far away from the car",
                        "They often have a more mobile gaze",
                        "More aware of their actions and consequences",
                        1,
                        "Newly qualified drivers- Keep their gaze too close to the car - Focus more on stationary objects - Are more passive and scan smaller areas",
                        0,
                        "Safety and Best Practices"),

                new Question("What is the most common type of accident caused by a tired driver?",
                        "Accidents when overtaking",
                        "Single vehicle accidents",
                        "Head-on collisions",
                        "Rear-end collisions",
                        2,
                        "Tired drivers are more likely to crash their car according to Trafikverket",
                        0,
                        "Safety and Best Practices"),

                new Question("Which of these statements about reaction time is true?",
                        "It is shorter at higher speeds",
                        "It is longer at higher speeds",
                        "It is normally shorter when using ABS brakes",
                        "It will be longer if I need to choose between different ways to react",
                        4,
                        "If you need to choose between different ways to react then your reaction time will be longer.",
                        0,
                        "Safety and Best Practices"),

                new Question("What is the best way to improve road safety?",
                        "Only drive during daytime",
                        "If you have more time, you can plan your driving and drive in peace without having to rush or stress. Stress can cause your driving ability to deteriorate.",
                        "Strictly follow all of the traffic rules",
                        "Always give way",
                        2,
                        "Stress can impact decision making and driving ability. If you have more time, you can plan your driving and drive in peace without having to rush or stress.",
                        0,
                        "Safety and Best Practices"),

                new Question("What is the most important thing to consider when it comes to driving safely around a bend?",
                        "Adjusting speed",
                        "Driving in a lower gear",
                        "Braking gently as you go around the bend",
                        "Driving fast",
                        1,
                        "It is very important that you adjust your speed when going around bends, especially in slippery road conditions.",
                        0,
                        "Safety and Best Practices"),

                new Question("What is the best way to avoid ending up in a dangerous situation?",
                        "Driving at a low speed all the time",
                        "Keeping good safety margins",
                        "Driving in the dark often",
                        "Engine brake in emergency situation",
                        2,
                        "The best way to avoid dangerous situations is to drive defensively: take it easy, plan your journey and keep good safety margins.",
                        0,
                        "Safety and Best Practices"),

                new Question("You are driving at 30 km/h and increase your speed to 60 km/h. How will your braking distance be affected by this acceleration?",
                        "It will double",
                        "It will increase threefold",
                        "It will increase fourfold",
                        "It will not be affected",
                        3,
                        "The braking distance changes exponentially.",
                        0,
                        "Safety and Best Practices"),

                new Question("When should you use your rear fog light?",
                        "When there is poor visibility and it is difficult for the driver behind to see me",
                        "When parking on the hard shoulder of a country road in the dark",
                        "When stopping on a road in a residential area in the dark to pick up a friend",
                        "When stopping due to engine failure",
                        1,
                        "The rear fog lamps emit a very strong and dazzling light. You should therefore only use your rear fog lights when visibility is significantly reduced.",
                        0,
                        "Safety and Best Practices")
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

    public List<Question> getCategoryQuestions(String category){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *" +
                                        " FROM " + QuestionsTable.TABLE_NAME +
                                        " WHERE " + QuestionsTable.COLUMN_CATEGORY + " = '" + category + "' ", null);

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

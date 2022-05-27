package com.example.smartcarmqttapp;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());


        ViewInteraction materialButton =
        onView(withId(R.id.fifteenQuestionButton)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click plus button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton3.perform(click());

        assert getText(onView(withId(R.id.questionCount))).equals("1 / 15");
    }

    @Test
    public void _QuizShouldDisplayTenQuestions() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());


        ViewInteraction materialButton =
                onView(withId(R.id.tenQuestionButton)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isEnabled(); // no constraints, they are checked above
                            }

                            @Override
                            public String getDescription() {
                                return "click plus button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                view.performClick();
                            }
                        }
                );

        ViewInteraction materialButton1 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton1.perform(click());

        assert getText(onView(withId(R.id.questionCount))).equals("1 / 10");

    }

    @Test
    public void _QuizShouldDisplayFiveQuestions() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());


        ViewInteraction materialButton =
                onView(withId(R.id.fiveQuestionButton)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isEnabled(); // no constraints, they are checked above
                            }

                            @Override
                            public String getDescription() {
                                return "click plus button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                view.performClick();
                            }
                        }
                );

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton2.perform(click());

        assert getText(onView(withId(R.id.questionCount))).equals("1 / 5");
    }

    @Test
    public void _StartingTheoryExam() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(1);
        linearLayout.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton2.perform(click());

        assert getText(onView(withId(R.id.questionCount))).equals("1 / 45");
        assert getText(onView(withId(R.id.timer))).equals("29:59");
    }

    @Test
    public void _SettingTenMinutes() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());


        ViewInteraction materialButton =
                onView(withId(R.id.tenMinuteTimerButton)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isEnabled(); // no constraints, they are checked above
                            }

                            @Override
                            public String getDescription() {
                                return "click plus button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                view.performClick();
                            }
                        }
                );

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton2.perform(click());

        assert getText(onView(withId(R.id.timer))).equals("09:59");

    }

    @Test
    public void _FifteenQuestion_FifteenMinutes() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());

        ViewInteraction materialButton2 =
                onView(withId(R.id.fifteenMinuteTimerButton)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isEnabled(); // no constraints, they are checked above
                            }

                            @Override
                            public String getDescription() {
                                return "click plus button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                view.performClick();
                            }
                        }
                );


        ViewInteraction materialButton =
                onView(withId(R.id.fifteenQuestionButton)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isEnabled(); // no constraints, they are checked above
                            }

                            @Override
                            public String getDescription() {
                                return "click plus button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                view.performClick();
                            }
                        }
                );


        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton3.perform(click());

        assert getText(onView(withId(R.id.questionCount))).equals("1 / 15");
        assert getText(onView(withId(R.id.timer))).equals("14:59");

    }

    @Test
    public void _SkippingQuestion() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton3.perform(click());

        ViewInteraction scrollView = onView(
                allOf(withId(R.id.scrollview)));
        scrollView.perform(ViewActions.swipeUp());

        ViewInteraction materialButton =
                onView(withId(R.id.nextQuestionBTN)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isEnabled(); // no constraints, they are checked above
                            }

                            @Override
                            public String getDescription() {
                                return "click plus button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                view.performClick();
                            }
                        }
                );
        materialButton.perform(doubleClick());

        scrollView.perform(ViewActions.swipeDown());

        assert getText(onView(withId(R.id.questionCount))).equals("2 / 15");

    }

    @Test
    public void _CheckingAnswerWithoutOptionShouldDisplayMessage() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton3.perform(click());

        ViewInteraction scrollView = onView(
                allOf(withId(R.id.scrollview)));
        scrollView.perform(ViewActions.swipeUp());

        ViewInteraction materialButton =
                onView(withId(R.id.checkAnswer)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isEnabled(); // no constraints, they are checked above
                            }

                            @Override
                            public String getDescription() {
                                return "click plus button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                view.performClick();
                            }
                        }
                );

        assert getText(onView(withId(R.id.selectQuestion))).equals("Select an answer or skip by pressing 'Next Question' twice");

    }

    @Test
    public void _QuizShouldDisplayBasicTrafficRulesCategory() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listCategory)))
                .atPosition(0);
        linearLayout2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton3.perform(click());

        assert getText(onView(withId(R.id.categoryText))).equals("Basic Traffic Rules and Signs");
    }

    @Test
    public void _QuizShouldDisplaySafetyAndBestPracticesCategory() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listCategory)))
                .atPosition(1);
        linearLayout2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton3.perform(click());

        assert getText(onView(withId(R.id.categoryText))).equals("Safety and Best Practices");
    }

    @Test
    public void _QuizShouldDisplayEnvironmentCategory() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView = onView(
                allOf(withId(R.id.theoryCard)));
        cardView.perform(click());


        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listMode)))
                .atPosition(0);
        linearLayout.perform(click());

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listCategory)))
                .atPosition(2);
        linearLayout2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.startQuizButton)));
        materialButton3.perform(click());

        assert getText(onView(withId(R.id.categoryText))).equals("Environment");
    }
    
    public String getText(ViewInteraction matcher){
        String[] text = {null};
        matcher.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "Text of the view";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view;
                text[0] = tv.getText().toString();
            }

        });
        return text[0];
    }

}

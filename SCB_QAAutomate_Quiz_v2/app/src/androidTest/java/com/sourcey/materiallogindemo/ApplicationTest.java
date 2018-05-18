package com.sourcey.materiallogindemo;

import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
//@RunWith(Parameterized.class)
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void Login_Fail() {
        PerformLogin("t.t@test.com","password", "Fail");
    }

    @Test
    public void Signup_Success() {
        PerformSignUp("test name","test", "t.t@test.com",
                "0891234567", "testpwd", "testpwd", "Pass");
        PerformLogOut();
        PerformLogin("t.t@test.com","testpwd", "Pass");
    }

    public void PerformLogin(String email, String password, String expResult){
        onView(withId(R.id.input_email))
                .perform(replaceText(email), closeSoftKeyboard());

        onView(withId(R.id.input_password))
                .perform(replaceText(password), closeSoftKeyboard());

        onView(withId(R.id.btn_login)).perform(click());

        CheckIsHomeScreen(expResult,"login");
    }

    private void PerformSignUp(String name, String address, String email, String mobile, String pwd, String rePwd, String expResult){

        onView(withId(R.id.link_signup)).perform(click());

        onView(withId(R.id.input_name))
                .perform(replaceText(name), closeSoftKeyboard());
        onView(withId(R.id.input_address))
                .perform(replaceText(address), closeSoftKeyboard());
        onView(withId(R.id.input_email))
                .perform(replaceText(email), closeSoftKeyboard());
        onView(withId(R.id.input_mobile))
                .perform(replaceText(mobile), closeSoftKeyboard());
        onView(withId(R.id.input_password))
                .perform(replaceText(pwd), closeSoftKeyboard());
        onView(withId(R.id.input_reEnterPassword))
                .perform(replaceText(rePwd), closeSoftKeyboard());

        onView(withId(R.id.btn_signup)).perform(scrollTo(),click());

        CheckIsHomeScreen(expResult, "signup");
    }

    private void CheckIsHomeScreen(String expResult, String caller){
        //assertTrue(mActivityTestRule.getActivity().isFinishing());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(expResult == "Pass"){
            onView(withId(R.id.btn_logout)).check(matches(isDisplayed()));
        }else{
            if(caller == "login"){
                onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
            }else{
                onView(withId(R.id.btn_signup)).check(matches(isDisplayed()));
            }
        }
    }

    private void PerformLogOut(){
        onView(withId(R.id.btn_logout)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
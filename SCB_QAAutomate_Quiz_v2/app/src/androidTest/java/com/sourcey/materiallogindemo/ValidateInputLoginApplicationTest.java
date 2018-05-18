package com.sourcey.materiallogindemo;

import android.app.Application;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(Parameterized.class)
public class ValidateInputLoginApplicationTest extends ApplicationTestCase<Application> {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", "123456", "enter a valid email address",""},
                {"test", "test123", "enter a valid email address",""},
                {"email@", "test123", "enter a valid email address",""},
                {"test.com", "test123", "enter a valid email address",""},

                {"test@test.com", "", "","between 4 and 10 alphanumeric characters"},
                {"test@test.com", "1", "","between 4 and 10 alphanumeric characters"},
                {"test@test.com", "12345678901", "","between 4 and 10 alphanumeric characters"},
                });
    }

    private final String mEmail;
    private final String mPassword;
    private final String mExpectedEmailError;
    private final String mExpectedPwdError;

    public ValidateInputLoginApplicationTest(String email, String password, String emailError, String pwdError) {
        super(Application.class);
        this.mEmail = email;
        this.mPassword = password;
        this.mExpectedEmailError = emailError;
        this.mExpectedPwdError = pwdError;
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void Login_Validate_Email_Password() {
        //Act
        PerformLogin(mEmail,mPassword);

        //Assert
        if(mExpectedEmailError != ""){
            onView(withId(R.id.input_email)).check(matches(hasErrorText(mExpectedEmailError)));
        }else{
            onView(withId(R.id.input_email)).check(matches(hasNoErrorText()));
        }
        if(mExpectedPwdError != ""){
            onView(withId(R.id.input_password)).check(matches(hasErrorText(mExpectedPwdError)));
        }else{
            onView(withId(R.id.input_password)).check(matches(hasNoErrorText()));
        }
    }

    private void PerformLogin(String email, String password){
        onView(withId(R.id.input_email))
                .perform(replaceText(email), closeSoftKeyboard());

        onView(withId(R.id.input_password))
                .perform(replaceText(password), closeSoftKeyboard());

        onView(withId(R.id.btn_login)).perform(click());
    }

    public static Matcher<View> hasNoErrorText() {
        return new BoundedMatcher<View, EditText>(EditText.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("has no error text: ");
            }

            @Override
            protected boolean matchesSafely(EditText view) {
                return view.getError() == null;
            }
        };
    }
}
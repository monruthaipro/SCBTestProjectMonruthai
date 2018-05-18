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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(Parameterized.class)
public class ValidateInputSignupApplicationTest extends ApplicationTestCase<Application> {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //Test Name
                {"", "test", "0891234567","test@test.com","test123", "test123","at least 3 characters","","","","",""},
                {"te", "test", "0891234567","test@test.com","test123", "test123","at least 3 characters","","","","",""},

                //Test Address
                {"test name", "", "0891234567","test@test.com","test123", "test123","","Enter Valid Address","","","",""},

                 //Test Mobile
                {"test name", "test", "", "test@test.com","test123", "test123","","","Enter Valid Mobile Number","","",""},
                {"test name", "test", "089123456", "test@test.com","test123", "test123","","","Enter Valid Mobile Number","","",""},
                {"test name", "test", "08912345678", "test@test.com","test123", "test123","","","Enter Valid Mobile Number","","",""},

                //Test Email
                {"test name", "test", "0891234567", "","test123", "test123","","","","enter a valid email address","",""},
                {"test name", "test", "0891234567", "test","test123", "test123","","","","enter a valid email address","",""},
                {"test name", "test", "0891234567", "email@","test123", "test123","","","","enter a valid email address","",""},

                //Test password
                {"test name", "test", "0891234567","test@test.com","", "","","","","","between 4 and 10 alphanumeric characters","Password Do not match"},
                {"test name", "test", "0891234567","test@test.com","tes", "tes","","","","","between 4 and 10 alphanumeric characters","Password Do not match"},
                {"test name", "test", "0891234567","test@test.com","test1234567", "test1234567","","","","","between 4 and 10 alphanumeric characters","Password Do not match"},

                //Test Reenter password
                {"test name", "test", "0891234567","test@test.com","test123", "test","","","","","","Password Do not match"},
        });
    }

    //PerformSignUp("test name","test", mEmail,
    //        "0891234567", mPassword, "testpwd");

    private final String mName;
    private final String mAddress;
    private final String mMobile;
    private final String mEmail;
    private final String mPassword;
    private final String mRePassword;
    private final String mExpNameError;
    private final String mExpAddressError;
    private final String mExpMobileError;
    private final String mExpEmailError;
    private final String mExpPwdError;
    private final String mExpRePasswordError;

    public ValidateInputSignupApplicationTest(
            String name, String address, String mobile, String email, String password, String rePassword
            ,String nameError, String addressError, String mobileError, String emailError, String pwdError, String repwdError) {
        super(Application.class);
        this.mName = name;
        this.mAddress = address;
        this.mMobile = mobile;
        this.mEmail = email;
        this.mPassword = password;
        this.mRePassword = rePassword;

        this.mExpNameError = nameError;
        this.mExpAddressError = addressError;
        this.mExpMobileError = mobileError;
        this.mExpEmailError = emailError;
        this.mExpPwdError = pwdError;
        this.mExpRePasswordError = repwdError;
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void Signup_Validate_Inputs() {
        //Act
        PerformSignUp(mName,mAddress,mMobile,mEmail,mPassword,mRePassword);

        //Assert
        if(mExpNameError != ""){
            onView(withId(R.id.input_name)).check(matches(hasErrorText(mExpNameError)));
        }else{
            onView(withId(R.id.input_name)).check(matches(hasNoErrorText()));
        }
        if(mExpAddressError != ""){
            onView(withId(R.id.input_address)).check(matches(hasErrorText(mExpAddressError)));
        }else{
            onView(withId(R.id.input_address)).check(matches(hasNoErrorText()));
        }
        if(mExpMobileError != ""){
            onView(withId(R.id.input_mobile)).check(matches(hasErrorText(mExpMobileError)));
        }else{
            onView(withId(R.id.input_mobile)).check(matches(hasNoErrorText()));
        }
        if(mExpEmailError != ""){
            onView(withId(R.id.input_email)).check(matches(hasErrorText(mExpEmailError)));
        }else{
            onView(withId(R.id.input_email)).check(matches(hasNoErrorText()));
        }
        if(mExpPwdError != ""){
            onView(withId(R.id.input_password)).check(matches(hasErrorText(mExpPwdError)));
        }else{
            onView(withId(R.id.input_password)).check(matches(hasNoErrorText()));
        }
        if(mExpRePasswordError != ""){
            onView(withId(R.id.input_reEnterPassword)).check(matches(hasErrorText(mExpRePasswordError)));
        }else{
            onView(withId(R.id.input_reEnterPassword)).check(matches(hasNoErrorText()));
        }


    }

    private void PerformSignUp(String name, String address, String mobile, String email, String pwd, String rePwd){

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

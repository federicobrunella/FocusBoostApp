package com.example.focusboostapp

import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.firebase.auth.FirebaseAuth

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class SignInActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<SignInActivity>
            = ActivityScenarioRule(SignInActivity::class.java)

    private lateinit var firebaseAuth: FirebaseAuth

    @Test
    fun checkLoginTextFields() {
        val username = "brunella.federico@gmail.com"
        val password = "password"

        // Scrivo nell'editText username
        onView(withId(R.id.emailEt))
            .perform(typeText(username), closeSoftKeyboard())

        // Scrivo nell'editText password
        onView(withId(R.id.passET))
            .perform(typeText(password), closeSoftKeyboard())

        //Controllo che il testo sia cambiato in entrambi i campi di inserimento
        onView(withId(R.id.emailEt))
            .check(matches(withText(username)))

        onView(withId(R.id.passET))
            .check(matches(withText(password)))

    }
}
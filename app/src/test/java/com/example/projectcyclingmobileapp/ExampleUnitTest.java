package com.example.projectcyclingmobileapp;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito;

import android.content.Context;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(MockitoJUnitRunner.class)
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    //Mocking all the necessary items to be able to use them in various test cases

    @Mock private Admin mockAdmin;
    @Mock private EditText emailTextBar;
    @Mock private EditText passwordTextBar;

    @Mock private Button loginButton;
    private LogIn logIn;

    @Mock private Editable emailEditable;

    @Mock private Editable passwordEditable;

    @Before
    public void setup(){
        logIn = new LogIn();
        logIn.emailTextBar = mock(TextInputEditText.class);
        logIn.passwordTextBar = mock(TextInputEditText.class);
        logIn.loginButton = loginButton;
        Editable emailEditable = Mockito.mock(Editable.class);
        Editable passwordEditable = Mockito.mock(Editable.class);
        mockAdmin = mock(Admin.class);

    }

    /**@Test
    public void testSignIn) {
        // Mock the input values
        when(emailTextBar.getText()).thenReturn("valid_email@gmail.com");
        when(passwordTextBar.getText()).thenReturn("password");

        // Mocking FirebaseAuth so that we can use a version not linked to the true one in the test
        FirebaseAuth mAuth = Mockito.mock(FirebaseAuth.class);
        logIn.mAuth = mAuth;

        // Mock a successful sign-in task
        Task<AuthResult> successTask = Mockito.mock(Task.class);
        when(successTask.isSuccessful()).thenReturn(true);

        when(mAuth.signInWithEmailAndPassword("valid_email@gmail.com", "password")).thenReturn(successTask);

        // Mock the behavior of startActivity
        Intent mockIntent = Mockito.mock(Intent.class);
        when(logIn.getApplicationContext()).thenReturn(Mockito.mock(Context.class));
        when(emailTextBar.getText()).thenReturn(emailEditable);
        when(passwordTextBar.getText()).thenReturn(passwordEditable);

        when(emailEditable.toString()).thenReturn("valid_email@gmail.com");
        when(passwordEditable.toString()).thenReturn("password");

        // Simulate clicking of log in button
        logIn.loginButton.performClick();

        // Confirm that the program has continued on to next activity (welcome)
        verify(logIn).startActivity(mockIntent);
    }


    @Test
    public void testAdminLogin(){
        assertEquals(mockAdmin.userName.getText(), "admin");
    }**/
}
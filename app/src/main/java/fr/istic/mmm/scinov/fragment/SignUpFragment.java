package fr.istic.mmm.scinov.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fr.istic.mmm.scinov.R;

public class SignUpFragment extends Fragment {

    private EditText emailField;
    private EditText passwordField;
    private Button signUpBtn;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailField = view.findViewById(R.id.sign_up_field_email);
        passwordField = view.findViewById(R.id.sign_up_field_password);
        signUpBtn = view.findViewById(R.id.sign_up_btn_sign_up);
        progressBar = view.findViewById(R.id.sign_up_progress_bar);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if(email.isEmpty()){
            emailField.setError("Email is required");
            emailField.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailField.setError("Please enter a valid email");
            emailField.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordField.setError("Password is required");
            passwordField.requestFocus();
            return;
        }

        if(password.length() < 6){
            passwordField.setError("Password should be at least 6 characters");
            passwordField.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"User registration succeeded",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(),"User registration failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}

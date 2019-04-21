package es.anjon.dyl.storysharing.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

import es.anjon.dyl.storysharing.R;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private static final String PACKAGE_NAME = "es.anjon.dyl.storysharing";
    private static final String URL = "https://story-share-app.firebaseapp.com/";

    public static Fragment newInstance() {
        Fragment frag = new LoginFragment();
        frag.setRetainInstance(true);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl(URL)
                .setHandleCodeInApp(true)
                .setIOSBundleId(PACKAGE_NAME)
                .setAndroidPackageName(PACKAGE_NAME,true,"12")
                .build();

        final EditText editText = view.findViewById(R.id.email);
        final AppCompatButton loginButton = view.findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString();
                Log.d(TAG, "Sending email to: " + email);
                auth.sendSignInLinkToEmail(email, actionCodeSettings)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Snackbar.make(view, "Login email sent", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                    Snackbar.make(view, "Login email unable to be sent", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        return view;
    }

}

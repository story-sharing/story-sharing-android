package es.anjon.dyl.storysharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.anjon.dyl.storysharing.fragment.GroupsFragment;
import es.anjon.dyl.storysharing.fragment.LoginFragment;
import es.anjon.dyl.storysharing.fragment.StoriesFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        if (intent.getData() != null) {
            // Login attempt from email link
            String emailLink = intent.getData().toString();
            if (auth.isSignInWithEmailLink(emailLink)) {
                String email = "dyl@anjon.es";
                loginWithEmail(email, emailLink);
            }
        } else if (auth.getCurrentUser() == null) {
            // Not logged in so present login fragment
            Fragment frag = LoginFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment frag = null;
        int id = item.getItemId();
        if (id == R.id.nav_stories) {
            frag = StoriesFragment.newInstance();
        } else if (id == R.id.nav_groups) {
            frag = GroupsFragment.newInstance();
        } else if (id == R.id.nav_share) {
            frag = StoriesFragment.newInstance();
        } else if (id == R.id.nav_send) {
            frag = StoriesFragment.newInstance();
        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loginWithEmail(String email, String emailLink) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailLink(email, emailLink)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Successfully signed in with email link");
                            AuthResult result = task.getResult();
                            if (result.getAdditionalUserInfo().isNewUser()) {
                                //TODO Create new user in db
                                Log.d(TAG, "Create new user");
                            } else {
                                //TODO Get user from db
                                Log.d(TAG, "Existing user");
                            }
                        } else {
                            Log.e(TAG, "Error signing in with email link", task.getException());
                        }
                    }
                });
    }

}

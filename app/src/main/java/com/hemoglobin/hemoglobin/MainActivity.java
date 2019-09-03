package com.hemoglobin.hemoglobin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.jar.Attributes;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;

    private FloatingActionButton Donate;

    private static final String SELECTED_ITEM_ID = "selected_item_id";
    private static final String FIRST_TIME = "first_time";
    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mSelectedId;
    private boolean mUserSawDrawer = false;

    private RelativeLayout No_Donations;

    private RecyclerView mDiaryLists;

    private LinearLayoutManager mLayoutManager;

    private DatabaseReference mDonationsDatabase;
    private FirebaseUser mCurrentUser;
    private String mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        Donate = findViewById(R.id.donation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hemoglobin");

        if (mAuth.getCurrentUser() != null) {

            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            mUsersDatabase.keepSynced(true);

            Donate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, ProfileViewActivity.class);
                    startActivity(intent);

                }
            });
        }
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mCurrentUserId = mCurrentUser.getUid().toString();

//        No_Donations = (RelativeLayout) findViewById(R.id.no_donations);

        mDonationsDatabase = FirebaseDatabase.getInstance().getReference().child("Donations").child(mCurrentUserId);
        mDonationsDatabase.keepSynced(true);

        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mDiaryLists = (RecyclerView) findViewById(R.id.diary_lists);
        mDiaryLists.setHasFixedSize(true);
        mDiaryLists.setLayoutManager(mLayoutManager);

        mDrawer = (NavigationView) findViewById(R.id.nav_view);
        mDrawer.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if (!didUserSeeDrawer()) {
            showDrawer();
            markDrawerSeen();
        } else {
            hideDrawer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser mCurrentUser = mAuth.getCurrentUser();

        if (mCurrentUser == null) {

            sendToLogin();

        }

        FirebaseRecyclerAdapter<Donations, SelfiesViewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<Donations, SelfiesViewHolder>(


                Donations.class,
                R.layout.single_donation_layout,
                SelfiesViewHolder.class,
                mDonationsDatabase


        ) {
            @Override
            protected void populateViewHolder(final SelfiesViewHolder viewHolder, final Donations userSelfies, int i) {

                final String selfie_key = getRef(i).getKey();

                mDonationsDatabase.child(selfie_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        viewHolder.SetBank_Name(userSelfies.getBlood_bank_name(),userSelfies.getBlood_bank_place());
                        viewHolder.SetDate(userSelfies.getDonated_date(),userSelfies.getDonated_time(), userSelfies.exp_date);

                        String today_date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        if (userSelfies.exp_date.equals(today_date)){
                            viewHolder.Valid.setBackgroundColor(getResources().getColor(R.color.colorRed));
                            viewHolder.Days.setText("Exp");
                        }else{
                            viewHolder.Valid.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            viewHolder.Days.setText("Live");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

        };
        mDiaryLists.setAdapter(firebaseRecyclerAdapter);

    }
    public static class SelfiesViewHolder extends RecyclerView.ViewHolder {

        View mView;
        private TextView Name, Date, Time, Place, Days;
        private CircleImageView Valid;

        public SelfiesViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            Date = mView.findViewById(R.id.date);
            Name = mView.findViewById(R.id.approve_bank_name);
            Place = mView.findViewById(R.id.place);
            Time = mView.findViewById(R.id.time);
            Valid = mView.findViewById(R.id.valid);
            Days = mView.findViewById(R.id.days);

        }

        public void SetBank_Name(String name, String place) {

            Name.setText(name);
            Place.setText(place);

        }


        public void SetDate(String date, String time, String exp) {

            Date.setText(date);
            Time.setText(time);
        }

    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

    private boolean didUserSeeDrawer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = sharedPreferences.getBoolean(FIRST_TIME, false);
        return mUserSawDrawer;
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void navigate(int mSelectedId) {
        Intent intent = null;

        if (mSelectedId == R.id.profile) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, DonarMapActivity.class);
            startActivity(intent);
        }
        if (mSelectedId == R.id.update) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, ProfileEditActivity.class);
            startActivity(intent);
        }
        if (mSelectedId == R.id.setting) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        if (mSelectedId == R.id.share) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Hemoglobin App From Play Store");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if (mSelectedId == R.id.logout) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            FirebaseAuth.getInstance().signOut();
            sendToLogin();
        }

    }

    private void markDrawerSeen() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME, mUserSawDrawer).apply();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();

        navigate(mSelectedId);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }
}

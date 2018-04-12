package com.example.buzzchat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mUsersList;

    private DatabaseReference mUsersDatabse;

    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolbar = (Toolbar) findViewById(R.id.users_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsersDatabse = FirebaseDatabase.getInstance().getReference().child("Users");

        mUsersList = (RecyclerView) findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        query = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
                holder.setName(model.getName());
            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_single_layout, parent, false);

                return new UsersViewHolder(view);
            }

        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name) {
            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);
        }
    }

}

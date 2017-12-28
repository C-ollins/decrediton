package com.decrediton.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.decrediton.Adapter.SeedAdapter;
import com.decrediton.Data.Seed;
import com.decrediton.MainActivity;
import com.decrediton.R;
import com.decrediton.Util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Macsleven on 25/12/2017.
 */

public class ConfrimSeedActivity extends AppCompatActivity {
    private List<Seed> seedList = new ArrayList<>();
    private List<Seed> confirmSeedList = new ArrayList<>();;
    private SeedAdapter seedAdapter;
    private TextView confirmview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_seed_page);
        Button buttonSeedConfirm=(Button)findViewById(R.id.button_confirm_seed);
        buttonSeedConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!confirmSeedList.isEmpty()) {
                    seedList.addAll(confirmSeedList);
                    confirmSeedList.clear();
                    seedAdapter.notifyDataSetChanged();
                    confirmview.setText("");

                }
               /* Intent i = new Intent(MainPage.this, MainPage.class);
                startActivity(i);
                // close this activity
                finish();*/
            }
        });
        confirmview =(TextView)findViewById(R.id.seed_display_confirm);
        //Setup List
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        seedAdapter = new SeedAdapter(seedList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Seed seed = seedList.get(position);

                if(confirmSeedList.size()==32){
                    // check for match
                    Intent i = new Intent(ConfrimSeedActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    confirmSeedList.add(seed);
                    seedList.remove(position);
                    seedAdapter.notifyDataSetChanged();
                    confirmview.append(seed.getSeed()+" ");

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(seedAdapter);

        prepareMovieData();
    }


    private void prepareMovieData() {
        Seed seed = new Seed("replace");
        seedList.add(seed);

        seed = new Seed("March");
        seedList.add(seed);

        seed = new Seed("April");
        seedList.add(seed);
         seed = new Seed("replace");
        seedList.add(seed);
        seed = new Seed("January");
        seedList.add(seed);

        seed = new Seed("Febuary");
        seedList.add(seed);

        seed = new Seed("March");
        seedList.add(seed);

        seed = new Seed("April");
        seedList.add(seed);
         seed = new Seed("replace");
        seedList.add(seed);
        seed = new Seed("January");
        seedList.add(seed);

        seed = new Seed("Febuary");
        seedList.add(seed);

        seed = new Seed("March");
        seedList.add(seed);

        seed = new Seed("April");
        seedList.add(seed);
         seed = new Seed("replace");
        seedList.add(seed);
        seed = new Seed("January");
        seedList.add(seed);

        seed = new Seed("Febuary");
        seedList.add(seed);

        seed = new Seed("March");
        seedList.add(seed);

        seed = new Seed("April");
        seedList.add(seed);
        seed = new Seed("replace");
        seedList.add(seed);
        seed = new Seed("January");
        seedList.add(seed);

        seed = new Seed("Febuary");
        seedList.add(seed);

        seed = new Seed("March");
        seedList.add(seed);

        seed = new Seed("April");
        seedList.add(seed);
       seed = new Seed("replace");
        seedList.add(seed);
        seed = new Seed("January");
        seedList.add(seed);

        seed = new Seed("Febuary");
        seedList.add(seed);

        seed = new Seed("March");
        seedList.add(seed);

        seed = new Seed("April");
        seedList.add(seed);
        seed = new Seed("replace");
        seedList.add(seed);
        seed = new Seed("January");
        seedList.add(seed);

        seed = new Seed("Febuary");
        seedList.add(seed);

        seed = new Seed("March");
        seedList.add(seed);

        seed = new Seed("April");
        seedList.add(seed);

        seedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
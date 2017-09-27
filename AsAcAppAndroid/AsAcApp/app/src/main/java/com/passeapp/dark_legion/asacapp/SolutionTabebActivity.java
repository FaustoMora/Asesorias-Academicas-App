package com.passeapp.dark_legion.asacapp;


import android.content.DialogInterface;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.widget.TextView;

import java.util.ArrayList;

import static com.passeapp.dark_legion.asacapp.VariablesActivity.actualQuestion;


public class SolutionTabebActivity extends AppCompatActivity implements SolutionFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ArrayList<SolutionFragment> fragments;
    public MyTextView tabTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_tabeb);
        initializeFragmentTabs();
        this.tabTitle = (MyTextView)findViewById(R.id.tabTitle);
        tabTitle.setText(VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas().get(0).getActualIndex());
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mViewPager, true);


        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();
                tabTitle.setText(VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                        .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas().get(position).getActualIndex());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tabTitle.setText(VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                        .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas().get(position).getActualIndex());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tabTitle.setText(VariablesActivity.lstMaterias.get(VariablesActivity.actualIndexMateria).getLstTemas().get(VariablesActivity.actualIndexTema)
                        .getLstTest().get(VariablesActivity.actualIndexTest).getLstPreguntas().get(position).getActualIndex());
            }
        });



        if(getActionBar() != null){
            getActionBar().hide();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solution_tabeb, menu);
        return true;
    }

    public void initializeFragmentTabs(){
        fragments = new ArrayList<>();
        for (QuestionClass aux: VariablesActivity.actualTest.getLstPreguntas()) {
            SolutionFragment fragment = new SolutionFragment();
            fragment.setActualQuestion(aux);
            fragments.add(fragment);
        }

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment frg = fragments.get(position);
            return frg;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(SolutionTabebActivity.this, R.style.myDialogStyle));
        builder.setMessage("Deseas salir de la solución?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fragments.clear();
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}

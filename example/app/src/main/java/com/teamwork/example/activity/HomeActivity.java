package com.teamwork.example.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.teamwork.example.R;
import com.teamwork.example.ExampleActivity;
import com.teamwork.example.fragment.CompaniesFragment;
import com.teamwork.example.fragment.HomeFragment;
import com.teamwork.example.fragment.PeopleFragment;
import com.teamwork.example.pojo.response.CompaniesResponsePojo;
import com.teamwork.example.pojo.response.PeopleResponsePojo;
import com.teamwork.example.pojo.response.ProjectsResponsePojo;

public class HomeActivity extends ExampleActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        HomeFragment.HomeCallback, CompaniesFragment.CompaniesCallback, PeopleFragment.PeopleCallback{

    //VIEWS
    protected BottomNavigationViewEx bottomNavigationViewCustom;

    //FRAGMENTS
    private HomeFragment homeFragment;
    private CompaniesFragment companiesFragment;
    private PeopleFragment peopleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponents();
    }

    @Override
    public void initComponents() {
        myContext = this;

        bottomNavigationViewCustom = findViewById(R.id.home_bottom_navigation_bar_custom);

        homeFragment = HomeFragment.newInstance();
        companiesFragment = CompaniesFragment.newInstance();
        peopleFragment = PeopleFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_container, homeFragment);
        transaction.add(R.id.home_container, companiesFragment);
        transaction.add(R.id.home_container, peopleFragment);

        transaction.hide(companiesFragment);
        transaction.hide(peopleFragment);

        transaction.commit();

        bottomNavigationViewCustom.setOnNavigationItemSelectedListener(this);
        bottomNavigationViewCustom.enableAnimation(false);
        bottomNavigationViewCustom.enableShiftingMode(false);
        bottomNavigationViewCustom.enableItemShiftingMode(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_home:
                selectFragmentsTab(homeFragment);

                return true;
            case R.id.menu_item_companies:
                selectFragmentsTab(companiesFragment);

                return true;
            case R.id.menu_item_people:
                selectFragmentsTab(peopleFragment);

                return true;
        }

        return false;
    }

    private void selectFragmentsTab(Fragment selectedFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.example_transition_fade_in, R.anim.example_transition_fade_out);

        transaction.hide(homeFragment);
        transaction.hide(companiesFragment);
        transaction.hide(peopleFragment);

        transaction.show(selectedFragment);
        transaction.commit();
    }






    // HOME FRAGMENT CALLBACK

    @Override
    public void onRequestLoadProjects(int pageCursor) {
        if(projectService != null) {
            projectService.listProjects(pageCursor);
        }
    }

    @Override
    public void onRequestFavouriteProject(String projectId, boolean favourite) {
        if(projectService != null) {
            projectService.requestFavouriteProject(projectId, favourite);
        }
    }




    // COMPANIES FRAGMENT CALLBACK

    @Override
    public void onRequestLoadCompanies(int pageCursor) {
        if(companyService != null){
            companyService.listCompanies(pageCursor);
        }
    }



    // COMPANIES FRAGMENT CALLBACK

    @Override
    public void onRequestLoadPeople(int pageCursor) {
        if(personService != null){
            personService.listPeople(pageCursor);
        }
    }







    //---------------- PROJECT SERVICE CALLBACK ------------------//

    @Override
    public void onListProjectsResponse(ProjectsResponsePojo response){
        super.onListProjectsResponse(response);

        homeFragment.onProjectsRetrieved(response);
    }





    //---------------- COMPANY SERVICE CALLBACK ------------------//

    @Override
    public void onListCompaniesResponse(CompaniesResponsePojo response) {
        super.onListCompaniesResponse(response);

        companiesFragment.onCompaniesRetrieved(response);
    }





    //---------------- PERSON SERVICE CALLBACK ------------------//

    @Override
    public void onListPeopleResponse(PeopleResponsePojo response) {
        super.onListPeopleResponse(response);

        peopleFragment.onPeopleRetrieved(response);
    }
}

package com.teamwork.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.teamwork.example.pojo.response.CompaniesResponsePojo;
import com.teamwork.example.pojo.response.ExampleResponsePojo;
import com.teamwork.example.pojo.response.PeopleResponsePojo;
import com.teamwork.example.pojo.response.ProjectsResponsePojo;
import com.teamwork.example.pojo.response.TasksResponsePojo;
import com.teamwork.example.service.company.callback.CompanyServiceCallback;
import com.teamwork.example.service.company.contract.CompanyService;
import com.teamwork.example.service.person.callback.PersonServiceCallback;
import com.teamwork.example.service.person.contract.PersonService;
import com.teamwork.example.service.project.callback.ProjectServiceCallback;
import com.teamwork.example.service.project.contract.ProjectService;
import com.teamwork.example.util.EnumUtil;
import com.teamwork.example.util.FunctionUtil;
import com.teamwork.example.util.ServiceFactoryUtil;

//Good to have an activity where all project activities extends from. Here I can manage all server responses and take common decisions.
public abstract class ExampleActivity extends AppCompatActivity implements ProjectServiceCallback, CompanyServiceCallback, PersonServiceCallback{
    protected Context myContext;

    protected ProjectService projectService;
    protected CompanyService companyService;
    protected PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //All 3 services are available for any activity without needy to implement service callbacks.
        projectService = ServiceFactoryUtil.createProjectService(this);
        companyService = ServiceFactoryUtil.createCompanyService(this);
        personService = ServiceFactoryUtil.createPersonService(this);
    }

    //Avoiding memory leak
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(projectService != null){
            projectService.releaseCallback();
            projectService = null;
        }

        if(companyService != null){
            companyService.releaseCallback();
            companyService = null;
        }

        if(personService != null){
            personService.releaseCallback();
            personService = null;
        }
    }

    public abstract void initComponents();

    private void manageRequestError(ExampleResponsePojo response){
        if(!response.getCode().equals(EnumUtil.RESPONSE_CODE.SUCCESS)){
            if(response.getCode().equals(EnumUtil.RESPONSE_CODE.UNAUTHORIZED_ACCESS)){
                //TODO
                //Log the user out or perform silent login

            }else{
                FunctionUtil.showSnakeMessage(findViewById(R.id.main_layout), response.getCode().getMessage());
            }
        }
    }





    //---------------- PROJECT SERVICE CALLBACK ------------------//

    @Override
    public void onProjectRequestError(EnumUtil.RESPONSE_CODE error){
        FunctionUtil.showSnakeMessage(findViewById(R.id.main_layout), error.getMessage());
    }

    @Override
    public void onListProjectsResponse(ProjectsResponsePojo response){
        manageRequestError(response);
    }

    @Override
    public void onRequestFavouriteProjectResponse(ExampleResponsePojo response) {
        manageRequestError(response);
    }

    @Override
    public void onListProjectTasksResponse(TasksResponsePojo response) {
        manageRequestError(response);
    }




    //---------------- COMPANY SERVICE CALLBACK ------------------//

    @Override
    public void onCompanyRequestError(EnumUtil.RESPONSE_CODE error) {
        FunctionUtil.showSnakeMessage(findViewById(R.id.main_layout), error.getMessage());
    }

    @Override
    public void onListCompaniesResponse(CompaniesResponsePojo response) {
        manageRequestError(response);
    }




    //---------------- PERSON SERVICE CALLBACK ------------------//

    @Override
    public void onPersonRequestError(EnumUtil.RESPONSE_CODE error) {
        FunctionUtil.showSnakeMessage(findViewById(R.id.main_layout), error.getMessage());
    }

    @Override
    public void onListPeopleResponse(PeopleResponsePojo response) {
        manageRequestError(response);
    }
}

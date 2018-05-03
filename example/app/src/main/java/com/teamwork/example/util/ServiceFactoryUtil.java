package com.teamwork.example.util;

import com.teamwork.example.service.company.callback.CompanyServiceCallback;
import com.teamwork.example.service.company.contract.CompanyService;
import com.teamwork.example.service.company.impl.CompanyServiceRetrofitImpl;
import com.teamwork.example.service.person.callback.PersonServiceCallback;
import com.teamwork.example.service.person.contract.PersonService;
import com.teamwork.example.service.person.impl.PersonServiceRetrofitImpl;
import com.teamwork.example.service.project.callback.ProjectServiceCallback;
import com.teamwork.example.service.project.contract.ProjectService;
import com.teamwork.example.service.project.impl.ProjectServiceRetrofitImpl;

//This class is not necessary for this project but I created as a way to show how I would do in a bigger project
//It's good to have a service factory in case another way to manage http request is chosen instead of Retrofit.
//We can easily swap the http request managers just changing this class to provide a different implementation of service interfaces.
public class ServiceFactoryUtil {

    public static ProjectService createProjectService(ProjectServiceCallback callback){
        return new ProjectServiceRetrofitImpl(callback);
    }

    public static CompanyService createCompanyService(CompanyServiceCallback callback){
        return new CompanyServiceRetrofitImpl(callback);
    }

    public static PersonService createPersonService(PersonServiceCallback callback){
        return new PersonServiceRetrofitImpl(callback);
    }
}

package com.teamwork.example.service.company.contract;


public interface CompanyService {

    //This method is present in all service interfaces.
    // In future, should create an interface to put this method in and make all service interfaces extends of it
    void releaseCallback();

    void listCompanies(int pageCursor);
}
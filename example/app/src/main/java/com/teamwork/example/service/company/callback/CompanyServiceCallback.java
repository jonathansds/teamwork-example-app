package com.teamwork.example.service.company.callback;

import com.teamwork.example.pojo.response.CompaniesResponsePojo;
import com.teamwork.example.util.EnumUtil;

public interface CompanyServiceCallback {

    void onCompanyRequestError(EnumUtil.RESPONSE_CODE error);

    void onListCompaniesResponse(CompaniesResponsePojo response);
}

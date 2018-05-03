package com.teamwork.example.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class TWCompany {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("contacts")
    String contacts;

    @SerializedName("zip")
    String zip;

    @SerializedName("address_one")
    String addressOne;

    @SerializedName("address_two")
    String addressTwo;

    @SerializedName("city")
    String city;

    @SerializedName("state")
    String state;

    @SerializedName("country")
    String country;

    @SerializedName("countrycode")
    String countryCode;

    @SerializedName("email_one")
    String emailOne;

    @SerializedName("email_two")
    String emailTwo;

    @SerializedName("email_three")
    String emailThree;

    @SerializedName("phone")
    String phone;

    @SerializedName("fax")
    String fax;

    @SerializedName("isowner")
    String isOwner;

    @SerializedName("industry")
    String industry;

    @SerializedName("logo-URL")
    String logoURL;

    @SerializedName("website")
    String websiteURL;

    @SerializedName("cid")
    String cid;

    @SerializedName("accounts")
    String accounts;

    @SerializedName("company_name_url")
    String companyNameURL;

    @SerializedName("can_see_private")
    boolean canSeePrivate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEmailOne() {
        return emailOne;
    }

    public void setEmailOne(String emailOne) {
        this.emailOne = emailOne;
    }

    public String getEmailTwo() {
        return emailTwo;
    }

    public void setEmailTwo(String emailTwo) {
        this.emailTwo = emailTwo;
    }

    public String getEmailThree() {
        return emailThree;
    }

    public void setEmailThree(String emailThree) {
        this.emailThree = emailThree;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getCompanyNameURL() {
        return companyNameURL;
    }

    public void setCompanyNameURL(String companyNameURL) {
        this.companyNameURL = companyNameURL;
    }

    public boolean isCanSeePrivate() {
        return canSeePrivate;
    }

    public void setCanSeePrivate(boolean canSeePrivate) {
        this.canSeePrivate = canSeePrivate;
    }
}

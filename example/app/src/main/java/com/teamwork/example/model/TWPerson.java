package com.teamwork.example.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class TWPerson {

    @SerializedName("id")
    String id;

    @SerializedName("pid")
    String pid;

    @SerializedName("company-id")
    String companyId;

    @SerializedName("company-name")
    String companyName;

    @SerializedName("title")
    String title;

    @SerializedName("first-name")
    String firstName;

    @SerializedName("last-name")
    String lastName;

    @SerializedName("user-name")
    String nickname;

    @SerializedName("avatar-url")
    String avatarURL;

    @SerializedName("user-type")
    String userType;

    @SerializedName("administrator")
    boolean admin;

    @SerializedName("site-owner")
    boolean siteOwner;

    @SerializedName("deleted")
    boolean deleted;

    @SerializedName("has-access-to-new-projects")
    boolean hasAccessNewProjects;

    @SerializedName("in-owner-company")
    boolean inOwnerCompany;

    @SerializedName("address-zip")
    String zipCode;

    @SerializedName("address-line-1")
    String addressOne;

    @SerializedName("address-line-2")
    String addressTwo;

    @SerializedName("address-city")
    String city;

    @SerializedName("address-state")
    String state;

    @SerializedName("address-country")
    String country;

    @SerializedName("email-address")
    String email;

    @SerializedName("email-alt-1")
    String altEmailOne;

    @SerializedName("email-alt-2")
    String altEmailTwo;

    @SerializedName("email-alt-3")
    String altEmailThree;

    @SerializedName("twitter")
    String twitter;

    @SerializedName("phone-number-mobile")
    String phoneNumberMobile;

    @SerializedName("phone-number-home")
    String phoneNumberHome;

    @SerializedName("phone-number-office")
    String phoneNumberOffice;

    @SerializedName("phone-number-office-ext")
    String phoneNumberOfficeExt;

    @SerializedName("phone-number-fax")
    String fax;

    @SerializedName("created-at")
    String dateCreated;

    @SerializedName("userUUID")
    String userUUID;

    @SerializedName("privateNotes")
    String privateNotes;

    @SerializedName("im-service")
    String imService;

    @SerializedName("im-handle")
    String imHandle;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isSiteOwner() {
        return siteOwner;
    }

    public void setSiteOwner(boolean siteOwner) {
        this.siteOwner = siteOwner;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isHasAccessNewProjects() {
        return hasAccessNewProjects;
    }

    public void setHasAccessNewProjects(boolean hasAccessNewProjects) {
        this.hasAccessNewProjects = hasAccessNewProjects;
    }

    public boolean isInOwnerCompany() {
        return inOwnerCompany;
    }

    public void setInOwnerCompany(boolean inOwnerCompany) {
        this.inOwnerCompany = inOwnerCompany;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAltEmailOne() {
        return altEmailOne;
    }

    public void setAltEmailOne(String altEmailOne) {
        this.altEmailOne = altEmailOne;
    }

    public String getAltEmailTwo() {
        return altEmailTwo;
    }

    public void setAltEmailTwo(String altEmailTwo) {
        this.altEmailTwo = altEmailTwo;
    }

    public String getAltEmailThree() {
        return altEmailThree;
    }

    public void setAltEmailThree(String altEmailThree) {
        this.altEmailThree = altEmailThree;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getPhoneNumberMobile() {
        return phoneNumberMobile;
    }

    public void setPhoneNumberMobile(String phoneNumberMobile) {
        this.phoneNumberMobile = phoneNumberMobile;
    }

    public String getPhoneNumberHome() {
        return phoneNumberHome;
    }

    public void setPhoneNumberHome(String phoneNumberHome) {
        this.phoneNumberHome = phoneNumberHome;
    }

    public String getPhoneNumberOffice() {
        return phoneNumberOffice;
    }

    public void setPhoneNumberOffice(String phoneNumberOffice) {
        this.phoneNumberOffice = phoneNumberOffice;
    }

    public String getPhoneNumberOfficeExt() {
        return phoneNumberOfficeExt;
    }

    public void setPhoneNumberOfficeExt(String phoneNumberOfficeExt) {
        this.phoneNumberOfficeExt = phoneNumberOfficeExt;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getPrivateNotes() {
        return privateNotes;
    }

    public void setPrivateNotes(String privateNotes) {
        this.privateNotes = privateNotes;
    }

    public String getImService() {
        return imService;
    }

    public void setImService(String imService) {
        this.imService = imService;
    }

    public String getImHandle() {
        return imHandle;
    }

    public void setImHandle(String imHandle) {
        this.imHandle = imHandle;
    }
}


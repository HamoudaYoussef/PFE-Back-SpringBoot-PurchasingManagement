package biz.picosoft.demo.client.kernel.model.global;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * A Scheduler.
 */

@Embeddable
public class Address implements Serializable {


    private static final long serialVersionUID = 1L;
    private String companyName;
    private String personName;
    private String addressLine1;
    private String addressLine2 = "";
    private String city;
    private String state;
    private String postalCode;
    private String countryCode;
    private String countryName;
    private String phoneNumber;
    private String phoneExtension;
    private String faxNumber;
    private String email;

    @JsonSerialize(using = GeometrySerializer.class)
    private Point position;

    public Address() {

    }

    public Address(String email) {
        this.email = email;
    }

    public Address(String addressLine1, String postalCode, String countryCode, String phoneNumber) {
        this.addressLine1 = addressLine1;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        if (addressLine1 == null)
            this.addressLine1 = addressLine1;
        else
            this.addressLine1 = addressLine1.trim().replaceAll("\\s{2,}", " ");
    }

    public String getstate() {
        return state;
    }

    public Address state(String ouvernorat) {
        this.state = ouvernorat;
        return this;
    }

    public void setstate(String state) {
        if (state == null)
            this.state = state;
        else
            this.state = state.trim().replaceAll("\\s{2,}", " ");

    }

    public Address addressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        if (addressLine2 == null)
            this.addressLine2 = addressLine2;
        else
            this.addressLine2 = addressLine2.trim().replaceAll("\\s{2,}", " ");
    }

    public Address addressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if (companyName == null)
            this.companyName = companyName;
        else
            this.companyName = companyName.trim().replaceAll("\\s{2,}", " ");
    }

    public Address companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null)
            this.city = city;
        else
            this.city = city.trim().replaceAll("\\s{2,}", " ");
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        if (postalCode == null)
            this.postalCode = postalCode;
        else
            this.postalCode = postalCode.trim().replaceAll("\\s{2,}", " ");
    }

    public Address postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        if (countryCode == null)
            this.countryCode = countryCode;
        else
            this.countryCode = countryCode.trim().replaceAll("\\s{2,}", " ");
    }

    public Address countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        if (countryName == null)
            this.countryName = countryName;
        else
            this.countryName = countryName.trim().replaceAll("\\s{2,}", " ");
    }

    public Address countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        if (personName == null)
            this.personName = personName;
        else
            this.personName = personName.trim().replaceAll("\\s{2,}", " ");
    }

    public Address personName(String personName) {
        this.personName = personName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null)
            this.phoneNumber = phoneNumber;
        else
            this.phoneNumber = phoneNumber.trim().replaceAll("\\s{2,}", " ");

    }

    public Address phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(String phoneExtension) {
        if (phoneExtension == null)
            this.phoneExtension = phoneExtension;
        else
            this.phoneExtension = phoneExtension.trim().replaceAll("\\s{2,}", " ");
    }

    public Address phoneExtension(String phoneExtension) {
        this.phoneExtension = phoneExtension;
        return this;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        if (faxNumber == null)
            this.faxNumber = faxNumber;
        else
            this.faxNumber = faxNumber.trim().replaceAll("\\s{2,}", " ");
    }

    public Address faxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null)
            this.email = email;
        else
            this.email = email.trim().replaceAll("\\s{2,}", " ");
    }

    public Address email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", companyName='" + companyName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", personName='" + personName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneExtension='" + phoneExtension + '\'' +
                ", faxNumber='" + faxNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


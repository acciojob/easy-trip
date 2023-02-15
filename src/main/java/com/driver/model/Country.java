package com.driver.model;

import javax.persistence.*;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private CountryName countryName;
    private String code;

    @OneToOne
    @JoinColumn
    private User user; //this field is null in case a virtual connection is made to this country

    @ManyToOne
    @JoinColumn
    private ServiceProvider serviceProvider; //this field is non-null only when a virtual connection is made to this country using this serviceProvider

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CountryName getCountryName() {
        return countryName;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void enrich(String countryName) throws Exception{
        String updatedName = countryName.toUpperCase();
        if(updatedName.equals("IND")){
            this.setCountryName(CountryName.IND);
            this.setCode(CountryName.IND.toCode());
        } else if(updatedName.equals("USA")){
            this.setCountryName(CountryName.USA);
            this.setCode(CountryName.USA.toCode());
        } else if(updatedName.equals("AUS")){
            this.setCountryName(CountryName.AUS);
            this.setCode(CountryName.AUS.toCode());
        } else if(updatedName.equals("CHI")){
            this.setCountryName(CountryName.CHI);
            this.setCode(CountryName.CHI.toCode());
        } else if(updatedName.equals("JPN")){
            this.setCountryName(CountryName.JPN);
            this.setCode(CountryName.JPN.toCode());
        } else {
            throw new Exception("Country not found");
        }
    }
}

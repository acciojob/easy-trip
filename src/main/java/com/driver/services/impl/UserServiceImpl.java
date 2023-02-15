package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
        //Add a user in the given country
        //If country is not found, throw "Country not found exception
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setConnected(false);
        user.setConnectionList(new ArrayList<>());
        user.setServiceProviderList(new ArrayList<>());
        Country country = new Country();
        country.enrich(countryName);

        country.setUser(user);
        user.setOriginalCountry(country);
        user = userRepository3.save(user); // this is to get the userId assigned
        user.setOriginalIp(new String(user.getOriginalCountry().getCode() + "." + user.getId()));
        user = userRepository3.save(user);
        return user;
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();
        List<ServiceProvider> serviceProviderList = user.getServiceProviderList();
        List<User> userList = serviceProvider.getUsers();
        serviceProviderList.add(serviceProvider);
        user.setServiceProviderList(serviceProviderList);
        userList.add(user);
        serviceProvider.setUsers(userList);
        serviceProviderRepository3.save(serviceProvider);
        return user;
    }
}

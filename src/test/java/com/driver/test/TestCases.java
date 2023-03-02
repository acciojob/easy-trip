package com.driver.test;

import com.driver.model.*;
import com.driver.repository.*;
import com.driver.services.UserService;
import com.driver.services.impl.*;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TestCases {

    @InjectMocks
    AdminServiceImpl adminService;
    @InjectMocks
    UserServiceImpl userService;
    @InjectMocks
    ConnectionServiceImpl connectionService;

    @Mock
    AdminRepository adminRepository1;

    @Mock
    ServiceProviderRepository serviceProviderRepository1;
    @Mock
    CountryRepository countryRepository1;

    @Mock
    UserRepository userRepository3;
    @Mock
    ServiceProviderRepository serviceProviderRepository3;
    @Mock
    CountryRepository countryRepository3;

    @Mock
    UserRepository userRepository2;
    @Mock
    ServiceProviderRepository serviceProviderRepository2;

    @Mock
    ConnectionRepository connectionRepository2;

    @Test
    public void testRegister() {
        String username = "admin1";
        String password = "password1";
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setServiceProviders(new ArrayList<>());

        when(adminRepository1.save(admin)).thenReturn(admin);
        Admin returnedAdmin = adminService.register(username, password);

        assertEquals(admin.getUsername(), returnedAdmin.getUsername());
        assertEquals(admin.getPassword(), returnedAdmin.getPassword());
        verify(adminRepository1, times(1)).save(any());
    }

    @Test
    public void testAddServiceProvider() {
        int adminId = 1;
        String providerName = "Provider1";
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setUsername("admin1");
        admin.setPassword("password1");
        when(adminRepository1.findById(adminId)).thenReturn(Optional.of(admin));
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        ServiceProvider serviceProvider = new ServiceProvider();
        admin.setServiceProviders(serviceProviderList);

        Admin returnedAdmin = adminService.addServiceProvider(adminId, providerName);
        assertEquals(1, returnedAdmin.getId());
        assertEquals("admin1", returnedAdmin.getUsername());
        assertEquals("password1", returnedAdmin.getPassword());
        assertEquals(1, returnedAdmin.getServiceProviders().size());
        ServiceProvider provider = returnedAdmin.getServiceProviders().get(0);
        assertEquals("Provider1", provider.getName());
        assertEquals(admin, provider.getAdmin());
        verify(adminRepository1, times(1)).findById(any());
        verify(adminRepository1, times(1)).save(any());
        verify(serviceProviderRepository1, never()).save(any());
    }

    @Test
    public void testAddCountrySuccess() throws Exception {
        int serviceProviderId = 1;
        String countryName = "inD";
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(serviceProviderId);

        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        country.setServiceProvider(serviceProvider);

        countryList.add(country);
        serviceProvider.setCountryList(countryList);

        when(serviceProviderRepository1.findById(serviceProviderId)).thenReturn(Optional.of(serviceProvider));
        when(serviceProviderRepository1.save(serviceProvider)).thenReturn(serviceProvider);

        ServiceProvider returnedServiceProvider = adminService.addCountry(serviceProviderId, countryName);
        assertEquals(returnedServiceProvider.getCountryList().get(0).getCountryName(), CountryName.IND);
        assertEquals(returnedServiceProvider.getCountryList().get(0).getCountryName().toCode(), CountryName.IND.toCode());
        verify(serviceProviderRepository1, times(1)).save(any());
        verify(serviceProviderRepository1, times(1)).findById(any());
        verify(countryRepository1, never()).save(any());
    }

    @Test
    public void testAddCountrySuccess_1() throws Exception {
        int serviceProviderId = 1;
        String countryName = "inD";
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(serviceProviderId);

        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        country.setServiceProvider(serviceProvider);

        countryList.add(country);
        serviceProvider.setCountryList(countryList);

        when(serviceProviderRepository1.findById(serviceProviderId)).thenReturn(Optional.of(serviceProvider));
        when(serviceProviderRepository1.save(serviceProvider)).thenReturn(serviceProvider);

        ServiceProvider returnedServiceProvider = adminService.addCountry(serviceProviderId, countryName);
        assertEquals(returnedServiceProvider.getCountryList().get(0).getCountryName(), CountryName.IND);
        assertEquals(returnedServiceProvider.getCountryList().get(0).getCountryName().toCode(), CountryName.IND.toCode());
        verify(serviceProviderRepository1, times(1)).save(any());
        verify(serviceProviderRepository1, times(1)).findById(any());
        verify(countryRepository1, never()).save(any());
    }

    @Test
    public void testAddCountrySuccess_2() throws Exception {
        int serviceProviderId = 1;
        String countryName = "inD";
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(serviceProviderId);

        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        country.setServiceProvider(serviceProvider);

        countryList.add(country);
        serviceProvider.setCountryList(countryList);

        when(serviceProviderRepository1.findById(serviceProviderId)).thenReturn(Optional.of(serviceProvider));
        when(serviceProviderRepository1.save(serviceProvider)).thenReturn(serviceProvider);

        ServiceProvider returnedServiceProvider = adminService.addCountry(serviceProviderId, countryName);
        assertEquals(returnedServiceProvider.getCountryList().get(0).getCountryName(), CountryName.IND);
        assertEquals(returnedServiceProvider.getCountryList().get(0).getCountryName().toCode(), CountryName.IND.toCode());
        verify(serviceProviderRepository1, times(1)).save(any());
        verify(serviceProviderRepository1, times(1)).findById(any());
        verify(countryRepository1, never()).save(any());
    }

    @Test
    public void testAddCountryException() throws Exception {
        int serviceProviderId = 1;
        String countryName = "XYZ";

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(serviceProviderId);

        when(serviceProviderRepository1.findById(serviceProviderId)).thenReturn(Optional.of(serviceProvider));
        try {
            adminService.addCountry(serviceProviderId, countryName);
        } catch (Exception e){
            assertEquals(e.getMessage(), "Country not found");
        }
    }

    @Test
    public void testSuccessfulUserRegistration() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password");

        when(userRepository3.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User actual = userService.register("user1", "password", "cHi");

        // Assert
        assertEquals(CountryName.CHI, actual.getOriginalCountry().getCountryName());
        assertEquals(CountryName.CHI.toCode(), actual.getOriginalCountry().getCountryName().toCode());
        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getPassword(), actual.getPassword());
        assertEquals(false, actual.getConnected());
        assertEquals("004.0", actual.getOriginalIp());
        verify(userRepository3, atMost(2)).save(any());
        verify(countryRepository3, never()).save(any());
    }

    @Test
    public void testSuccessfulUserRegistration_1() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password");

        when(userRepository3.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User actual = userService.register("user1", "password", "cHi");

        // Assert
        assertEquals(CountryName.CHI, actual.getOriginalCountry().getCountryName());
        assertEquals(CountryName.CHI.toCode(), actual.getOriginalCountry().getCountryName().toCode());
        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getPassword(), actual.getPassword());
        assertEquals(false, actual.getConnected());
        assertEquals("004.0", actual.getOriginalIp());
        verify(userRepository3, atMost(2)).save(any());
        verify(countryRepository3, never()).save(any());
    }

    @Test
    public void testCountryNotFoundException() throws Exception {
        when(userRepository3.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        try {
            userService.register("user1", "password", "UNK");
        } catch (Exception e){
            assertEquals(e.getMessage(), "Country not found");
        }
        verify(userRepository3, times(0)).save(any());
        verify(countryRepository3, times(0)).save(any());
    }

    @Test
    public void testSubscribe_Success() {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("John");
        user.setPassword("password");
        user.setOriginalIp("192.168.1.1");
        user.setConnected(false);
        user.setMaskedIp(null);
        user.setServiceProviderList(new ArrayList<ServiceProvider>());
        user.setOriginalCountry(null);
        user.setConnectionList(new ArrayList<Connection>());

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setName("Service Provider 1");
        serviceProvider.setAdmin(null);
        serviceProvider.setCountryList(new ArrayList<Country>());
        serviceProvider.setUsers(new ArrayList<User>());
        serviceProvider.setConnectionList(new ArrayList<Connection>());

        when(userRepository3.findById(any())).thenReturn(Optional.of(user));
        when(serviceProviderRepository3.findById(any())).thenReturn(Optional.of(serviceProvider));

        //Act
        User result = userService.subscribe(user.getId(), serviceProvider.getId());

        //Assert
        assertNotNull(result);
        assertEquals(1, result.getServiceProviderList().size());
        assertEquals(serviceProvider.getName(), result.getServiceProviderList().get(0).getName());
        assertEquals(1, serviceProvider.getUsers().size());
        assertEquals(user.getUsername(), serviceProvider.getUsers().get(0).getUsername());
        verify(userRepository3, times(1)).findById(any());
        verify(serviceProviderRepository3, times(1)).findById(any());
        verify(serviceProviderRepository3, atMost(1)).save(any());
        verify(userRepository3, atMost(1)).save(any());
    }

    @Test
    public void testConnect_Success() throws Exception {
        User user = new User();
        user.setId(1);
        user.setConnected(false);
        Country originalCountry = new Country();
        originalCountry.setCountryName(CountryName.USA);
        originalCountry.setCode(CountryName.USA.toCode());
        user.setOriginalCountry(originalCountry);
        user.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider1 = new ServiceProvider();
        serviceProvider1.setId(2);
        serviceProvider1.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider2 = new ServiceProvider();
        serviceProvider2.setId(3);
        serviceProvider2.setConnectionList(new ArrayList<>());
        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        countryList.add(country);
        serviceProvider.setCountryList(new ArrayList<>());
        serviceProvider1.setCountryList(countryList);
        serviceProvider2.setCountryList(countryList);
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        serviceProviderList.add(serviceProvider);
        serviceProviderList.add(serviceProvider1);
        serviceProviderList.add(serviceProvider2);
        user.setServiceProviderList(serviceProviderList);

        when(userRepository2.findById(any())).thenReturn(Optional.of(user));

        User result = connectionService.connect(1, "ind");

        assertEquals(1, result.getId());
        assertTrue(result.getConnected());
        assertEquals("001.2.1", result.getMaskedIp());
        assertEquals(1, result.getConnectionList().size());
        ServiceProvider serviceProviderResult = result.getConnectionList().get(0).getServiceProvider();
        assertEquals(2, serviceProviderResult.getId());
        assertEquals(1, serviceProviderResult.getConnectionList().size());
        verify(userRepository2, times(1)).findById(any());
        verify(userRepository2, times(1)).save(any());
        verify(serviceProviderRepository2, times(1)).save(any());
        verify(connectionRepository2, never()).save(any());
    }

    @Test
    public void testConnect_Success_1() throws Exception {
        User user = new User();
        user.setId(1);
        user.setConnected(false);
        Country originalCountry = new Country();
        originalCountry.setCountryName(CountryName.USA);
        originalCountry.setCode(CountryName.USA.toCode());
        user.setOriginalCountry(originalCountry);
        user.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider1 = new ServiceProvider();
        serviceProvider1.setId(2);
        serviceProvider1.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider2 = new ServiceProvider();
        serviceProvider2.setId(3);
        serviceProvider2.setConnectionList(new ArrayList<>());
        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        countryList.add(country);
        serviceProvider.setCountryList(new ArrayList<>());
        serviceProvider1.setCountryList(countryList);
        serviceProvider2.setCountryList(countryList);
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        serviceProviderList.add(serviceProvider);
        serviceProviderList.add(serviceProvider1);
        serviceProviderList.add(serviceProvider2);
        user.setServiceProviderList(serviceProviderList);

        when(userRepository2.findById(any())).thenReturn(Optional.of(user));

        User result = connectionService.connect(1, "ind");

        assertEquals(1, result.getId());
        assertTrue(result.getConnected());
        assertEquals("001.2.1", result.getMaskedIp());
        assertEquals(1, result.getConnectionList().size());
        ServiceProvider serviceProviderResult = result.getConnectionList().get(0).getServiceProvider();
        assertEquals(2, serviceProviderResult.getId());
        assertEquals(1, serviceProviderResult.getConnectionList().size());
        verify(userRepository2, times(1)).findById(any());
        verify(userRepository2, times(1)).save(any());
        verify(serviceProviderRepository2, times(1)).save(any());
        verify(connectionRepository2, never()).save(any());
    }

    @Test
    public void testConnect_Success1() throws Exception {
        User user = new User();
        user.setId(1);
        user.setConnected(false);
        Country originalCountry = new Country();
        originalCountry.setCountryName(CountryName.USA);
        originalCountry.setCode(CountryName.USA.toCode());
        user.setOriginalCountry(originalCountry);
        user.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        countryList.add(country);
        serviceProvider.setCountryList(countryList);
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        serviceProviderList.add(serviceProvider);
        user.setServiceProviderList(serviceProviderList);

        when(userRepository2.findById(any())).thenReturn(Optional.of(user));

        User result = connectionService.connect(1, "uSa");

        assertEquals(1, result.getId());
        assertTrue(result.getConnected().equals(false));
        verify(userRepository2, atMost(1)).findById(any());
        verify(userRepository2, atMost(1)).save(any());
        verify(serviceProviderRepository2, never()).save(any());
        verify(connectionRepository2, never()).save(any());
    }

    @Test
    public void testConnect_Success1_2() throws Exception {
        User user = new User();
        user.setId(1);
        user.setConnected(false);
        Country originalCountry = new Country();
        originalCountry.setCountryName(CountryName.USA);
        originalCountry.setCode(CountryName.USA.toCode());
        user.setOriginalCountry(originalCountry);
        user.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        countryList.add(country);
        serviceProvider.setCountryList(countryList);
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        serviceProviderList.add(serviceProvider);
        user.setServiceProviderList(serviceProviderList);

        when(userRepository2.findById(any())).thenReturn(Optional.of(user));

        User result = connectionService.connect(1, "uSa");

        assertEquals(1, result.getId());
        assertTrue(result.getConnected().equals(false));
        verify(userRepository2, atMost(1)).findById(any());
        verify(userRepository2, atMost(1)).save(any());
        verify(serviceProviderRepository2, never()).save(any());
        verify(connectionRepository2, never()).save(any());
    }

    @Test
    public void testConnect_Success2() throws Exception {
        User user = new User();
        user.setId(1);
        user.setConnected(false);
        Country originalCountry = new Country();
        originalCountry.setCountryName(CountryName.USA);
        originalCountry.setCode(CountryName.USA.toCode());
        user.setOriginalCountry(originalCountry);
        user.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        Country country1 = new Country();
        country1.setCountryName(CountryName.CHI);
        country1.setCode(CountryName.CHI.toCode());
        countryList.add(country);
        countryList.add(country1);
        serviceProvider.setCountryList(countryList);
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        serviceProviderList.add(serviceProvider);
        user.setServiceProviderList(serviceProviderList);

        when(userRepository2.findById(any())).thenReturn(Optional.of(user));

        User result = connectionService.connect(1, "chi");

        assertEquals(1, result.getId());
        assertTrue(result.getConnected());
        assertEquals("004.1.1", result.getMaskedIp());
        assertEquals(1, result.getConnectionList().size());
        assertEquals(1, result.getServiceProviderList().size());
        verify(userRepository2).findById(any());
        verify(userRepository2).save(any());
        verify(serviceProviderRepository2).save(any());

        try{
            User result1 = connectionService.connect(1, "inD");
        } catch (Exception e){
            assertEquals(e.getMessage(), "Already connected");
        }
    }

    @Test
    public void testConnect_Success3() throws Exception {
        User user = new User();
        user.setId(1);
        user.setConnected(false);
        Country originalCountry = new Country();
        originalCountry.setCountryName(CountryName.USA);
        originalCountry.setCode(CountryName.USA.toCode());
        user.setOriginalCountry(originalCountry);
        user.setConnectionList(new ArrayList<>());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        Country country1 = new Country();
        country1.setCountryName(CountryName.CHI);
        country1.setCode(CountryName.CHI.toCode());
        countryList.add(country);
        serviceProvider.setCountryList(countryList);
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        serviceProviderList.add(serviceProvider);
        user.setServiceProviderList(serviceProviderList);

        when(userRepository2.findById(any())).thenReturn(Optional.of(user));

        try{
            User result1 = connectionService.connect(1, "chi");
        } catch (Exception e){
            assertEquals(e.getMessage(), "Unable to connect");
        }
    }

    @Test
    public void testDisconnect_Success() throws Exception {
        User user = new User();
        user.setId(1);
        user.setConnected(false);
        user.setMaskedIp(null);
        Country originalCountry = new Country();
        originalCountry.setCountryName(CountryName.USA);
        originalCountry.setCode(CountryName.USA.toCode());
        user.setOriginalCountry(originalCountry);
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        List<Country> countryList = new ArrayList<>();
        countryList.add(country);
        user.setConnectionList(new ArrayList<>());
        user.setConnected(true);
        user.setMaskedIp("001.1.1");

        when(userRepository2.findById(1)).thenReturn(java.util.Optional.of(user));

        User result = connectionService.disconnect(1);

        verify(userRepository2, times(1)).findById(any());
        verify(userRepository2, times(1)).save(any());
        verify(connectionRepository2, never()).save(any());
        assertEquals(1, result.getId());
        assertEquals(false, result.getConnected());
        assertEquals(CountryName.USA, result.getOriginalCountry().getCountryName());
    }

    @Test
    public void testDisconnect_AlreadyDisconnected() throws Exception {
        User user = new User();
        user.setId(1);
        user.setConnected(false);
        when(userRepository2.findById(1)).thenReturn(java.util.Optional.of(user));

        try {
            User result = connectionService.disconnect(1);
        } catch (Exception e){
            assertEquals(e.getMessage(), "Already disconnected");
        }

        verify(userRepository2, times(1)).findById(1);
        verify(userRepository2, times(0)).save(user);
    }

    @Test
    public void testCommunicateSuccessful() throws Exception {
        User sender = new User();
        sender.setId(1);
        sender.setConnected(false);
        sender.setConnectionList(new ArrayList<>());
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        sender.setOriginalCountry(country);
        Country country1 = new Country();
        country1.setCountryName(CountryName.USA);
        country1.setCode(CountryName.USA.toCode());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        serviceProvider.setCountryList(Arrays.asList(country, country1));
        sender.setServiceProviderList(Arrays.asList(serviceProvider));
        when(userRepository2.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository2.save(any())).thenReturn(sender);

        User receiver = new User();
        receiver.setId(2);
        receiver.setConnected(true);
        receiver.setMaskedIp("002.1.2");
        when(userRepository2.findById(2)).thenReturn(Optional.of(receiver));

        User result = connectionService.communicate(1, 2);
        assertEquals(1, result.getId());
        assertTrue(result.getConnected());
        assertEquals("002.1.1", result.getMaskedIp());

        verify(userRepository2, times(1)).save(any());
    }

    @Test
    public void testCommunicateSuccessful_1() throws Exception {
        User sender = new User();
        sender.setId(1);
        sender.setConnected(false);
        sender.setConnectionList(new ArrayList<>());
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        sender.setOriginalCountry(country);
        Country country1 = new Country();
        country1.setCountryName(CountryName.USA);
        country1.setCode(CountryName.USA.toCode());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        serviceProvider.setCountryList(Arrays.asList(country, country1));
        sender.setServiceProviderList(Arrays.asList(serviceProvider));
        when(userRepository2.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository2.save(any())).thenReturn(sender);

        User receiver = new User();
        receiver.setId(2);
        receiver.setConnected(true);
        receiver.setMaskedIp("002.1.2");
        when(userRepository2.findById(2)).thenReturn(Optional.of(receiver));

        User result = connectionService.communicate(1, 2);
        assertEquals(1, result.getId());
        assertTrue(result.getConnected());
        assertEquals("002.1.1", result.getMaskedIp());

        verify(userRepository2, times(1)).save(any());
    }

    @Test
    public void testCommunicateSuccessful2() throws Exception {
        User sender = new User();
        sender.setId(1);
        sender.setConnected(false);
        sender.setConnectionList(new ArrayList<>());
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        sender.setOriginalCountry(country);
        Country country1 = new Country();
        country1.setCountryName(CountryName.USA);
        country1.setCode(CountryName.USA.toCode());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        serviceProvider.setCountryList(Arrays.asList(country, country1));
        sender.setServiceProviderList(Arrays.asList(serviceProvider));
        when(userRepository2.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository2.save(any())).thenReturn(sender);

        User receiver = new User();
        receiver.setId(2);
        receiver.setConnected(true);
        receiver.setMaskedIp("001.1.2");
        when(userRepository2.findById(2)).thenReturn(Optional.of(receiver));

        User result = connectionService.communicate(1, 2);
        assertEquals(1, result.getId());
        assertFalse(result.getConnected());

        verify(userRepository2, never()).save(any());
    }

    @Test
    public void testCommunicateSuccessful2_1() throws Exception {
        User sender = new User();
        sender.setId(1);
        sender.setConnected(false);
        sender.setConnectionList(new ArrayList<>());
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        sender.setOriginalCountry(country);
        Country country1 = new Country();
        country1.setCountryName(CountryName.USA);
        country1.setCode(CountryName.USA.toCode());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        serviceProvider.setCountryList(Arrays.asList(country, country1));
        sender.setServiceProviderList(Arrays.asList(serviceProvider));
        when(userRepository2.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository2.save(any())).thenReturn(sender);

        User receiver = new User();
        receiver.setId(2);
        receiver.setConnected(true);
        receiver.setMaskedIp("001.1.2");
        when(userRepository2.findById(2)).thenReturn(Optional.of(receiver));

        User result = connectionService.communicate(1, 2);
        assertEquals(1, result.getId());
        assertFalse(result.getConnected());

        verify(userRepository2, never()).save(any());
    }

    @Test
    public void testCommunicate_Unsuccessful() throws Exception {
        User sender = new User();
        sender.setId(1);
        sender.setConnected(false);
        sender.setConnectionList(new ArrayList<>());
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        sender.setOriginalCountry(country);
        Country country1 = new Country();
        country1.setCountryName(CountryName.USA);
        country1.setCode(CountryName.USA.toCode());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        serviceProvider.setCountryList(Arrays.asList(country, country1));
        sender.setServiceProviderList(Arrays.asList(serviceProvider));
        when(userRepository2.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository2.save(any())).thenReturn(sender);

        User receiver = new User();
        receiver.setId(2);
        receiver.setConnected(true);
        receiver.setMaskedIp("003.1.2");
        when(userRepository2.findById(2)).thenReturn(Optional.of(receiver));

        try {
            User result = connectionService.communicate(1, 2);
        } catch(Exception e){
            assertEquals(e.getMessage(), "Cannot establish communication");
        }

    }

    @Test
    public void testCommunicateSuccessful1() throws Exception {
        User sender = new User();
        sender.setId(1);
        sender.setConnected(false);
        sender.setConnectionList(new ArrayList<>());
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        sender.setOriginalCountry(country);
        Country country1 = new Country();
        country1.setCountryName(CountryName.CHI);
        country1.setCode(CountryName.CHI.toCode());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        serviceProvider.setCountryList(Arrays.asList(country, country1));
        sender.setServiceProviderList(Arrays.asList(serviceProvider));
        when(userRepository2.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository2.save(any())).thenReturn(sender);

        User receiver = new User();
        receiver.setId(2);
        receiver.setConnected(false);
        receiver.setOriginalCountry(country1);
        receiver.setOriginalIp("004.2");
        when(userRepository2.findById(2)).thenReturn(Optional.of(receiver));

        User result = connectionService.communicate(1, 2);
        assertEquals(1, result.getId());
        assertTrue(result.getConnected());
        assertEquals("004.1.1", result.getMaskedIp());

        verify(userRepository2, times(1)).save(any());

    }

    @Test
    public void testCommunicateSuccessful3() throws Exception {
        User sender = new User();
        sender.setId(1);
        sender.setConnected(false);
        sender.setConnectionList(new ArrayList<>());
        Country country = new Country();
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        sender.setOriginalCountry(country);
        Country country1 = new Country();
        country1.setCountryName(CountryName.CHI);
        country1.setCode(CountryName.CHI.toCode());
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1);
        serviceProvider.setConnectionList(new ArrayList<>());
        serviceProvider.setCountryList(Arrays.asList(country, country1));
        sender.setServiceProviderList(Arrays.asList(serviceProvider));
        when(userRepository2.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository2.save(any())).thenReturn(sender);

        User receiver = new User();
        receiver.setId(2);
        receiver.setConnected(false);
        receiver.setOriginalCountry(country);
        receiver.setOriginalIp("001.2");
        when(userRepository2.findById(2)).thenReturn(Optional.of(receiver));

        User result = connectionService.communicate(1, 2);
        assertEquals(1, result.getId());
        assertFalse(result.getConnected());

        verify(userRepository2, never()).save(any());

    }
}


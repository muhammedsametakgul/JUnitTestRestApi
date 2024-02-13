package com.sametakgul.accounts.service;

import com.sametakgul.accounts.dto.CustomerDto;
import org.springframework.stereotype.Service;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);


    boolean updateAccount(CustomerDto customerDto);


    boolean deleteAccount(String mobileNumber);
}

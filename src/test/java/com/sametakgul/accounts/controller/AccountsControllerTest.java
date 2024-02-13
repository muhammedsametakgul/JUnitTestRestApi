package com.sametakgul.accounts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sametakgul.accounts.constants.AccountsConstants;
import com.sametakgul.accounts.dto.AccountsDto;
import com.sametakgul.accounts.dto.CustomerDto;
import com.sametakgul.accounts.entity.Accounts;
import com.sametakgul.accounts.entity.Customer;
import com.sametakgul.accounts.exception.CustomerAlreadyExistsException;
import com.sametakgul.accounts.mapper.CustomerMapper;
import com.sametakgul.accounts.repository.AccountsRepository;
import com.sametakgul.accounts.repository.CustomerRepository;
import com.sametakgul.accounts.service.IAccountsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
 class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private IAccountsService accountsService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAccount() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setMobileNumber("1234567890");

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("123 Main Street");


        customerDto.setAccountsDto(accountsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated());

    }

    @Test
    void testFetchAccountDetails() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setMobileNumber("1234567890");

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("123 Main Street");

        customerDto.setAccountsDto(accountsDto);

        accountsService.createAccount(customerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/fetch")
                        .param("mobileNumber", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void testUpdateAccountDetails() throws Exception {
        // Müşteri ve hesap bilgilerini oluşturun
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setMobileNumber("1234567892");

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("123 Main Street");

        customerDto.setAccountsDto(accountsDto);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated());


        customerDto.setName("Updated John Doe");


        // Güncellenmiş bilgileri doğrulayın
        mockMvc.perform(MockMvcRequestBuilders.put("/api/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteAccountDetails() throws Exception {
        // Müşteri ve hesap bilgilerini oluşturun
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setMobileNumber("1234567892");

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("123 Main Street");

        customerDto.setAccountsDto(accountsDto);
        accountsService.createAccount(customerDto);
        String mobileNumber= customerDto.getMobileNumber();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete")
                        .param("mobileNumber", mobileNumber))
                .andExpect(status().isOk());

    }
}

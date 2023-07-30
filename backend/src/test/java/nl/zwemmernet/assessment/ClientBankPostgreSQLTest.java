package nl.zwemmernet.assessment;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.zwemmernet.assessment.model.Client;
import nl.zwemmernet.assessment.model.ClientFetch;
import nl.zwemmernet.assessment.model.Bank;

import nl.zwemmernet.assessment.repository.ClientRepository;
import nl.zwemmernet.assessment.repository.BankRepository;


@SpringBootTest
@AutoConfigureMockMvc
class ClientBankPostgreSQLTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Autowire the ObjectMapper


    @Autowired
    BankRepository bankRepository;

    @Autowired
    ClientRepository clientRepository;

    /*
     * Bank used for testing.
     */
    Long bankId = 999L;
    String bankName = "TEST";
    String bankCode = "TEST";
    List<ClientFetch> listClientFetch = null;
    Bank bank = new Bank(bankId, bankName, bankCode, listClientFetch);

    /*
     * Client used for testing.
     */
    Long clientId = 999L;
    String bsn = "123456789";
    String mobilePhoneNumber = "123456789";
    String firstname = "Niels";
    String surname = "Zwemmer";
    String address = "SomeStreet SomeNumber, Zipcode, City, Province";
    String bankAccountNumber = "NL00ABNA0000000000";
    double bankAccountBalance = 1000.0;

    Client client = new Client(clientId, bsn, mobilePhoneNumber, firstname, surname, address, bankAccountNumber, bankAccountBalance, bankId);

    // Persistant IDs for use throughout the tests.
    Long clientIdToUse;
    Long bankIdToUse;

    @BeforeEach
    void createDatabaseEntries() {
        // Occupy the bank table in the database.
        Bank newBank = bankRepository.save(bank);
        bankIdToUse = newBank.getId();

        // Necessary since adding client requires existing bank ID.
        client.setBank_id(bankIdToUse);

        // Occupy the client table in the database.
        Client newClient = clientRepository.save(client);
        clientIdToUse = newClient.getId();
    }

    @Test
    void testManualAddClientToDatabase() throws Exception {
        // Change the clientBSN and Bank account number since they need to be unique for each additional client.
        String newUniqueBsn = "1234567890";
        String newUniqueBankAccountNumber = "NL00ABNA0000000001";

        // Set the new values which can't overlap.
        client.setBsn(newUniqueBsn);
        client.setBank_account_number(newUniqueBankAccountNumber);

        // Convert the Client object to JSON
        String newClientJson = objectMapper.writeValueAsString(client);

        mockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newClientJson))
            .andExpect(status().isCreated()) // HTTP 201
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", not(clientIdToUse)))
            .andExpect(jsonPath("$.bsn", is(newUniqueBsn)))
            .andExpect(jsonPath("$.mobile_phone_number", is(mobilePhoneNumber)))
            .andExpect(jsonPath("$.firstname", is(firstname)))
            .andExpect(jsonPath("$.surname", is(surname)))
            .andExpect(jsonPath("$.address", is(address)))
            .andExpect(jsonPath("$.bank_account_number", is(newUniqueBankAccountNumber)))
            .andExpect(jsonPath("$.bank_account_balance", is(bankAccountBalance)))
            .andExpect(jsonPath("$.bank_id", is(client.getBank_id().intValue())));
    }

    @Test
    void testManualAddBankToDatabase() throws Exception {
        // Convert the Bank object to JSON
        String newBankJson = objectMapper.writeValueAsString(bank);
        
        mockMvc.perform(post("/api/banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newBankJson))
            .andExpect(status().isCreated()) // HTTP 201
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", not(bank.getId())))
            .andExpect(jsonPath("$.bank_name", is(bankName)))
            .andExpect(jsonPath("$.bank_code", is(bankCode)));
    }

    @Test
    void testGetAllClients_NonEmptyTable() throws Exception {
        mockMvc.perform(get("/api/clients"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void testGetAllClients_EmptyTable() throws Exception {
        // Clear the clients table.
        clientRepository.deleteAll();

        mockMvc.perform(get("/api/clients"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllBanks_NonEmptyTable() throws Exception {
        mockMvc.perform(get("/api/banks"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void testGetAllBanks_EmptyTable() throws Exception {
        // Clear the clients table as well, 
        // required since clearing bank table will fail otherwise due to FK dependency.
        clientRepository.deleteAll();
        bankRepository.deleteAll();

        mockMvc.perform(get("/api/banks"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateClient() throws Exception {

        String newBSN = "12345678901";
        String newFirstName = "Not Niels";
        String newSurName = "Not Zwemmer";
        double newAccountBalance = 2000.0;

        // Create the updated client with new information
        Client updatedClient = new Client();
        updatedClient.setId(clientIdToUse);
        updatedClient.setBsn(newBSN);
        updatedClient.setFirstname(newFirstName);
        updatedClient.setSurname(newSurName);
        updatedClient.setBank_account_balance(newAccountBalance);
        updatedClient.setMobile_phone_number(client.getMobile_phone_number()); // Include unmodified fields
        updatedClient.setAddress(client.getAddress()); // Include unmodified fields
        updatedClient.setBank_account_number(client.getBank_account_number()); // Include unmodified fields
        
        String updatedClientJson = objectMapper.writeValueAsString(updatedClient);

        mockMvc.perform(put("/api/clients/{id}", clientIdToUse)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedClientJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(clientIdToUse.intValue())))
                .andExpect(jsonPath("$.bsn", is(newBSN)))
                .andExpect(jsonPath("$.firstname", is(newFirstName)))
                .andExpect(jsonPath("$.surname", is(newSurName)))
                .andExpect(jsonPath("$.bank_account_balance", is(2000.0)));
    }

    @Test
    void testDeleteClient() throws Exception {
        mockMvc.perform(delete("/api/clients/{id}", clientIdToUse))
                .andExpect(status().isNoContent());

        // Still no content if tried second time. This is desired behaviour.
        mockMvc.perform(delete("/api/clients/{id}", clientIdToUse))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/clients/{id}", clientIdToUse))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBank() throws Exception {
        // Error if just try to delete, needs clients clear first.
        mockMvc.perform(delete("/api/banks/{id}", bankIdToUse))
                .andExpect(status().isInternalServerError());

        // Verify bank is there still.
        mockMvc.perform(get("/api/banks/{id}", bankIdToUse))
                .andExpect(status().isOk());

        // Delete all clients.
        mockMvc.perform(delete("/api/clients"))
                .andExpect(status().isNoContent());

        // Check all clients are gone
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isNoContent());

        // Try again now!
        mockMvc.perform(delete("/api/banks/{id}", bankIdToUse))
                .andExpect(status().isNoContent());

        // Should no longer return this bank.
        mockMvc.perform(get("/api/banks/{id}", bankIdToUse))
                .andExpect(status().isNotFound());
    }

    @AfterEach
    void cleanDatabase() {
        clientRepository.deleteAll();
        bankRepository.deleteAll();
    }
}


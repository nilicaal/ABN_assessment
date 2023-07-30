package nl.zwemmernet.assessment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.zwemmernet.assessment.repository.BankRepository;
import nl.zwemmernet.assessment.repository.ClientRepository;
import nl.zwemmernet.assessment.model.Bank;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BankController {
    @Autowired
	BankRepository bankRepository;

    @Autowired
	ClientRepository clientRepository;

    @GetMapping("/banks")
    public ResponseEntity<List<Bank>> getAllBanksFromBank() {
        try {
            List<Bank> banks = new ArrayList<>();
            bankRepository.findAll().forEach(banks::add);
            
            if (banks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(banks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get a single bank from the PostgreSQL database based on their ID.
     * Using the findById(long id) method from the JpaRepository through the bankRepository interface.
     * 
     * @param id The ID of the bank to retrieve.
     * @return ResponseEntity with a bank's information in JSON format (if the bank exists), or NOT_FOUND if the bank is not found.
     */
    @GetMapping("/banks/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable("id") long id) {
        Optional<Bank> bankData = bankRepository.findById(id);

        if (bankData.isPresent()) {
            return new ResponseEntity<>(bankData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new bank and store it in the PostgreSQL database.
     * Using the save(S entity) method from the JpaRepository through the bankRepository interface.
     * 
     * @param bank The bank object to be created.
     * @return ResponseEntity with the newly created bank's information in JSON format, or INTERNAL_SERVER_ERROR if there was an error.
     */
    @PostMapping("/banks")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        try {
            Bank savedBank = bankRepository.save(bank);
            return new ResponseEntity<>(savedBank, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing bank in the PostgreSQL database.
     * Using the save(S entity) method from the JpaRepository through the bankRepository interface.
     * FIXME: Authentication idea -> Should only be allowed if currently authenticated user tries to update.
     * 
     * @param id     The ID of the bank to update.
     * @param bank The updated bank object.
     * @return ResponseEntity with the updated bank's information in JSON format (if the bank exists), or NOT_FOUND if the bank is not found.
     */
    @PutMapping("/banks/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable("id") long id, @RequestBody Bank bank) {
        Optional<Bank> bankData = bankRepository.findById(id);
        if (bankData.isPresent()) {
            Bank savedBank = bankRepository.save(bank);
            return new ResponseEntity<>(savedBank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete all banks in the PostgreSQL database.
     * Using the deleteAll() method from the JpaRepository through the bankRepository interface.
     * FIXME: Should use proper authentication, right now the bank stores their password in plaintext.
     * 
     * @return ResponseEntity with NO_CONTENT if all banks were deleted successfully, or INTERNAL_SERVER_ERROR if there was an error.
     */
    @DeleteMapping("/banks")
    public ResponseEntity<HttpStatus> deleteAllBanks() {
        try {
            List<Bank> banks = bankRepository.findAll();
            
            for (Bank bank : banks) {
                int numberOfClientsWithBank = clientRepository.countClientsByBankId(bank.getId());
                if (numberOfClientsWithBank == 0) {
                    bankRepository.delete(bank);
                }
            }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    /**
     * Delete a single bank from the PostgreSQL database based on their ID.
     * Using the deleteById(long id) method from the JpaRepository through the bankRepository interface.
     * 
     * @param id The ID of the bank to delete.
     * @return ResponseEntity with NO_CONTENT if the bank was deleted successfully, or INTERNAL_SERVER_ERROR if there was an error.
     */
    @DeleteMapping("/banks/{id}")
    public ResponseEntity<HttpStatus> deleteBank(@PathVariable("id") long id) {
        try {
            bankRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

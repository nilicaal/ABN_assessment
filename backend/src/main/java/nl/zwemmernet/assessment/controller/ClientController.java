/**
* ClientController handles all inputs directed at clients through the API. 
* - The GET calls return JSON to the client.
* - The POST and PUT calls require JSON to be sent to the server.
* - The DELETE calls require an <id> to be sent as a single number.
*   This id corresponds with the user.id in the PostgreSQL database (PK).
*
* Currently implemented:
*--------------------------------------------------------------------------------------------------
*| METHOD | URL
*--------------------------------------------------------------------------------------------------
*| GET    | http://localhost:8080/api/clients      -> Return a list of all clients.
*| GET    | http://localhost:8080/api/clients/<id> -> Return Client by <id>.
*| POST   | http://localhost:8080/api/clients      -> Add new client to database.
*| PUT    | http://localhost:8080/api/clients/<id> -> Update client information, store in database.
*| DELETE | http://localhost:8080/api/clients      -> Delete ALL clients from database.
*| DELETE | http://localhost:8080/api/clients/<id> -> Delete Client by <id>.
*--------------------------------------------------------------------------------------------------.
*/

package nl.zwemmernet.assessment.controller;

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

import nl.zwemmernet.assessment.repository.ClientFetchRepository;
import nl.zwemmernet.assessment.repository.ClientRepository;
import nl.zwemmernet.assessment.model.Client;
import nl.zwemmernet.assessment.model.ClientFetch;
import nl.zwemmernet.assessment.dto.ClientDTO;

import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

	@Autowired
	ClientFetchRepository clientFetchRepository;

    /**
     * Get a list of all clients from the PostgreSQL database.
     * Using the findAll() method from the JpaRepository through the clientRepository interface.
     * 
     * @return ResponseEntity with a list of clients in JSON format (if not empty), or NO_CONTENT if there are no clients.
     */
    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        try {
            List<ClientFetch> clientsFetched = clientFetchRepository.findAll();
            if (clientsFetched.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            // Convert Client objects to ClientDTO objects
            List<ClientDTO> clientDTOs = clientsFetched.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get a single client from the PostgreSQL database based on their ID.
     * Using the findById(long id) method from the JpaRepository through the clientRepository interface.
     * 
     * @param id The ID of the client to retrieve.
     * @return ResponseEntity with a client's information in JSON format (if the client exists), or NOT_FOUND if the client is not found.
     */
    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") long id) {
        // For finding bank name.
        Optional<ClientFetch> clientFetchData = clientFetchRepository.findById(id);

        // For finding the bankID.
        Optional<Client> clientData = clientRepository.findById(id);

        if (clientFetchData.isPresent()) {
            ClientDTO clientDTO = convertToDto(clientFetchData.get());
            clientDTO.setBank_id(clientData.get().getBank_id());
            return new ResponseEntity<>(clientDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new client and store it in the PostgreSQL database.
     * Using the save(S entity) method from the JpaRepository through the clientRepository interface.
     * 
     * @param client The client object to be created.
     * @return ResponseEntity with the newly created client's information in JSON format, or INTERNAL_SERVER_ERROR if there was an error.
     */
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            Client savedClient = clientRepository.save(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing client in the PostgreSQL database.
     * Using the save(S entity) method from the JpaRepository through the clientRepository interface.
     * FIXME: Authentication idea -> Should only be allowed if currently authenticated user tries to update.
     * 
     * @param id     The ID of the client to update.
     * @param client The updated client object.
     * @return ResponseEntity with the updated client's information in JSON format (if the client exists), or NOT_FOUND if the client is not found.
     */
    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        Optional<Client> clientData = clientRepository.findById(id);
        if (clientData.isPresent()) {
            Client savedClient = clientRepository.save(client);
            return new ResponseEntity<>(savedClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Delete all clients in the PostgreSQL database.
     * Using the deleteAll() method from the JpaRepository through the clientRepository interface.
     * FIXME: Should use proper authentication, right now the client stores their password in plaintext.
     * 
     * @return ResponseEntity with NO_CONTENT if all clients were deleted successfully, or INTERNAL_SERVER_ERROR if there was an error.
     */
    @DeleteMapping("/clients")
    public ResponseEntity<HttpStatus> deleteAllClients() {
        try {
            clientRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a single client from the PostgreSQL database based on their ID.
     * Using the deleteById(long id) method from the JpaRepository through the clientRepository interface.
     * 
     * @param id The ID of the client to delete.
     * @return ResponseEntity with NO_CONTENT if the client was deleted successfully, or INTERNAL_SERVER_ERROR if there was an error.
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") long id) {
        try {
            clientRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Convert Client entity to ClientDTO.
     *
     * @param clientFetch The ClientFetch entity to convert.
     * @return The converted ClientDTO.
     */
    private ClientDTO convertToDto(ClientFetch client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setBsn(client.getBsn());
        clientDTO.setMobile_phone_number(client.getMobile_phone_number());
        clientDTO.setFirstname(client.getFirstname());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setBank_account_number(client.getBank_account_number());
        clientDTO.setBank_account_balance(client.getBank_account_balance());
		// clientDTO.setBank_id(client.getBank_id());
        if (client.getBank() != null) {
            clientDTO.setBank_name(client.getBank().getBank_name());
        }
        return clientDTO;
    }
}

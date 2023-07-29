package nl.zwemmernet.assessment.controller;

// package nl.zwemmernet.assessment.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import nl.zwemmernet.assessment.repository.ClientRepository;
import nl.zwemmernet.assessment.model.Client;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ClientController {

	@Autowired
	ClientRepository clientRepository;

	@GetMapping("/clients")
	public ResponseEntity<List<Client>> getAllClients() {
		try {
			List<Client> clients = new ArrayList<>();
			clientRepository.findAll().forEach(clients::add);

			if (clients.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(clients, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/clients/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable("id") long id) {
		Optional<Client> clientData = clientRepository.findById(id);

		if (clientData.isPresent()) {
			return new ResponseEntity<>(clientData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/clients")
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		try {
			// Set the hashed password and salt in the client object
			// DNF: need more time to also implement full security.
			// BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			// client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));
			// Save the client to the database
			Client savedClient = clientRepository.save(client);
			return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/clients/{id}")
	public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
		Optional<Client> clientData = clientRepository.findById(id);
		if (clientData.isPresent()) {
			// Client _client = clientData.get();
			// _client.setBsn(client.getBsn());
			// _client.setMobile_phone_number(client.getMobile_phone_number());
			// _client.setFirstname(client.getFirstname());
			// _client.setSurname(client.getSurname());
			// _client.setAddress(client.getAddress());
			// _client.setPassword(client.getPassword());
			// _client.setSalt(client.getSalt());
			// _client.setBank_account_number(client.getBank_account_number());
			// _client.setBank_account_balance(client.getBank_account_balance());
			// _client.setBank_id(client.getBank_id());
			return new ResponseEntity<>(clientRepository.save(client), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/clients/{id}")
	public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") long id) {
		try {
			clientRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/clients")
	public ResponseEntity<HttpStatus> deleteAllClients() {
		try {
			clientRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// @GetMapping("/clients/published")
	// public ResponseEntity<List<Client>> findByPublished() {
	// try {
	// List<Client> clients = clientRepository.findByPublished(true);

	// if (clients.isEmpty()) {
	// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	// }
	// return new ResponseEntity<>(clients, HttpStatus.OK);
	// } catch (Exception e) {
	// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	// }
}
package nl.zwemmernet.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.zwemmernet.assessment.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
  // List<Client> findByBsn(String bsn);
  // List<Client> findBySurnameContaining(String surname);
}
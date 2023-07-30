package nl.zwemmernet.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.zwemmernet.assessment.model.ClientFetch;

public interface BankRepository extends JpaRepository<ClientFetch, Long> {
  /*
   * Custom functions may be declared here, which can then return the required type.
   * For example:
   * List<Client> findByBsn(String bsn);
   * This would mean that calling findByBsn(String bsn) in the ClientController class, 
   * would return a List<Client> Object.
   * 
   * Future work could use this knowledge.
   */
}

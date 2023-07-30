package nl.zwemmernet.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.zwemmernet.assessment.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "SELECT COUNT(*) FROM clients WHERE bank_id = :bank_id", nativeQuery = true)
    int countClientsByBankId(@Param("bank_id") Long bank_id);
}

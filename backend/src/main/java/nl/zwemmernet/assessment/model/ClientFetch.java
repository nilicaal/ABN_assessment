package nl.zwemmernet.assessment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class ClientFetch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "bsn")
    private String bsn;

    @Column(name = "mobile_phone_number")
    private String mobile_phone_number;

    // Update the data types for custom types
    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "bank_account_number")
    private String bank_account_number;

    @Column(name = "bank_account_balance")
    private double bank_account_balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    @JsonBackReference
    private Bank bank;

    @Override
    public String toString() {
        return "User [id=" + id + ", bsn=" + bsn + ", firstname=" + firstname + "]";
    }
}

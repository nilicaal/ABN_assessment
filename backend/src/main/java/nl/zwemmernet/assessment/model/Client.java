package nl.zwemmernet.assessment.model;

import jakarta.persistence.*; // for Spring Boot 3
import lombok.Data; // Used to replace getter/setter.
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

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

    @Column(name = "password")
    @Setter // Lombok's @Setter annotation to generate the setter method
    private String password;

    // Update the data types for the remaining fields
    @Column(name = "bank_account_number")
    private String bank_account_number;

    @Column(name = "bank_account_balance")
    private double bank_account_balance;

    @Column(name = "bank_id")
    private Integer bank_id;

	@Override
	public String toString() {
		return "User [id=" + id + ", bsn=" + bsn + ", firstname=" + firstname + "]";
	}
}

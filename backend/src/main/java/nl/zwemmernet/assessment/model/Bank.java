package nl.zwemmernet.assessment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "bank_code")
    private String bank_code;

    @Column(name = "bank_name")
    private String bank_name;

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ClientFetch> clients;
}

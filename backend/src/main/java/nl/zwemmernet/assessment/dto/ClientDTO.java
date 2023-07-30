package nl.zwemmernet.assessment.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private long id;
    private String bsn;
    private String mobile_phone_number;
    private String firstname;
    private String surname;
    private String address;
    private String bank_account_number;
    private double bank_account_balance;
    private long bank_id;
    private String bank_name;

    @Override
    public String toString() {
        return "User [id=" + id + ", bsn=" + bsn + ", firstname=" + firstname + "bank_id" + bank_id + "]";
    }
}
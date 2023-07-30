<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <h4>New Client</h4>
      <form>
      <div class="form-group">
        <label for="bsn">BSN</label>
        <input class="form-control" id="bsn" required v-model="client.bsn" name="bsn" />
      </div>
      <div class="form-group">
        <label for="mobile_phone_number">Mobile Phone Number</label>
        <input class="form-control" id="mobile_phone_number" required v-model="client.mobile_phone_number" name="mobile_phone_number" />
      </div>
      <div class="form-group">
        <label for="firstname">First Name</label>
        <input class="form-control" id="firstname" required v-model="client.firstname" name="firstname" />
      </div>
      <div class="form-group">
        <label for="surname">Surname</label>
        <input class="form-control" id="surname" required v-model="client.surname" name="surname" />
      </div>
      <div class="form-group">
        <label for="address">Postcode</label>
        <input class="form-control" id="postcode" required v-model="client.postcode" name="postcode" />
      </div>
      <div class="form-group">
        <label for="address">Street Name and house number</label>
        <input class="form-control" id="street" required v-model="client.street" name="street" />
      </div>
      <div class="form-group">
        <label for="address">City</label>
        <input class="form-control" id="city" required v-model="client.city" name="city" />
      </div>
      <div class="form-group">
        <label for="address">Province</label>
        <input class="form-control" id="province" required v-model="client.province" name="province" />
      </div>
      <div class="form-group">
        <label for="bank_account_number">Bank Account Number</label>
        <input class="form-control" id="bank_account_number" required v-model="client.bank_account_number" name="bank_account_number" />
      </div>
      <div class="form-group">
        <label for="bank_id">Bank</label>
        <select class="form-control" id="bank_id" required v-model="client.bank_id">
          <option value="" disabled>Select Bank</option>
          <option v-for="bank in banks" :key="bank.id" :value="bank.id">{{ bank.bank_name }}</option>
        </select>
      </div>
  </form>
      <button @click="saveClient" class="btn btn-success">Submit</button>
      <p>{{ message }}</p>
    </div>
    <div v-else-if="submitted && !error">
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newClient">Add another Client</button>
    </div>
  </div>
</template>

<script>
import ClientDataService from "../services/ClientDataService";
import BankDataService from "../services/BankDataService";

export default {
  name: "add-client",
  data() {
    return {
      client: {
        id: null,
        bsn: "",
        mobile_phone_number: "",
        firstname: "",
        surname: "",
        address: "",
        password: "",
        bank_account_number: "",
        bank_account_balance: 0.0,
        bank_id: null,
      },
      submitted: false,
      error: false,
      invalidBankNumber: false,
      banks: [],
      bankNameToIdMap: {},
      message: "",
    };
  },
  methods: {
    saveClient() {
      // Validate bank account number format before submitting
      if (!this.validateBankAccountNumber()) {
        return;
      }

      const selectedBankId = this.bankNameToIdMap[this.client.bank_name];
      const selectedBank = this.banks.find(bank => bank.id === selectedBankId);
      if (selectedBank) {
        const bankCode = selectedBank.bank_code;
        const bankAccountNumber = this.client.bank_account_number;
        if (bankAccountNumber.includes(bankCode)) {
          const newBankAccountNumber = bankAccountNumber.replace(/^[A-Z]{2}[A-Z]{4}/, bankCode);
          this.client.bank_account_number = newBankAccountNumber;
          this.message = ""; // Clear the error message if the update is successful
        } else {
          // If the bank_code is not found in bank_account_number, set the error message
          this.message = "Cannot update bank account name with the selected bank code.";
          return;
        }
      }

      // Perform form validation for all fields
      if (
        !this.client.bsn ||
        !this.client.mobile_phone_number ||
        !this.client.firstname ||
        !this.client.surname ||
        !this.client.postcode ||
        !this.client.street ||
        !this.client.city ||
        !this.client.province ||
        !this.client.bank_account_number ||
        !this.client.bank_id
      ) {
        this.message = "Please fill in all fields.";
        return;
      }

      var data = {
        bsn: this.client.bsn,
        mobile_phone_number: this.client.mobile_phone_number,
        firstname: this.client.firstname,
        surname: this.client.surname,
        address: this.client.street + ", " + this.client.postcode + ", " + this.client.city + ", " + this.client.province,
        password: this.client.password,
        bank_account_number: this.client.bank_account_number,
        bank_account_balance: this.client.bank_account_balance,
        bank_id: selectedBankId,
      };

      ClientDataService.create(data)
        .then(response => {
          this.client.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
          this.error = false;
          this.message = "";
        })
        .catch(e => {
          this.error = true;
          this.message = "Could not create new client. Please check entered details and try again.";
          console.log(e);
        });
    },

    newClient() {
      this.error = false;
      this.submitted = false;
      this.client = {};
    },

      // Uses the data from the bank database to dynamically create the regular expression.
      validateBankAccountNumber() {
      const bankCodes = this.banks.map(bank => bank.bank_code).join("|");
      const bankAccountNumberRegex = new RegExp(`^(NL\\d{2})(${bankCodes})\\d{10}$`);
      if (!bankAccountNumberRegex.test(this.client.bank_account_number)) {
        this.invalidBankNumber = true;
        this.message = "Incorrect IBAN. Please use the following format: NL00BANK0000000000";
        return false;
      }
      this.invalidBankNumber = false;
      this.message = "";
      return true;
    }
  },

  mounted() {
    // Fetch the list of banks
    BankDataService.getAll()
      .then(response => {
        this.banks = response.data;
        this.bankNameToIdMap = this.banks.reduce((map, bank) => {
        map[bank.bank_name] = bank.id;
        return map;
      }, {});
      })
      .catch(e => {
        console.log(e);
      });
  },
  watch: {
    // Watch for changes to the bank_id property and update the bankNameToIdMap accordingly
    "client.bank_id": function (newValue) {
      if (newValue) {
        const selectedBank = this.banks.find(bank => bank.id === newValue);
        if (selectedBank) {
          this.client.bank_name = selectedBank.bank_name;
        }
      }
    },
  },
};
</script>

<style>
.submit-form {
  max-width: 800px;
  margin: auto;
}
</style>
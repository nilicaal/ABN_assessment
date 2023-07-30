<template>
    <div v-if="currentClient" class="edit-form">
      <h4>Client</h4>
      <form>
        <!-- Other form fields -->
        <div class="form-group">
          <label for="bsn">BSN</label>
          <input type="text" class="form-control" id="bsn" v-model="currentClient.bsn" />
        </div>
        <div class="form-group">
          <label for="mobile_phone_number">Mobile Phone Number</label>
          <input type="text" class="form-control" id="mobile_phone_number" v-model="currentClient.mobile_phone_number" />
        </div>
        <div class="form-group">
          <label for="firstname">First Name</label>
          <input type="text" class="form-control" id="firstname" v-model="currentClient.firstname" />
        </div>
        <div class="form-group">
          <label for="surname">Surname</label>
          <input type="text" class="form-control" id="surname" v-model="currentClient.surname" />
        </div>
        <div class="form-group">
          <label for="address">Address</label>
          <input type="text" class="form-control" id="address" v-model="currentClient.address" />
        </div>
        <div class="form-group">
          <label for="bank_account_number">Bank Account Number</label>
          <input type="text" class="form-control" id="bank_account_number" v-model="currentClient.bank_account_number" />
        </div>
        <div class="form-group">
          <label for="bank_account_balance">Bank Account Balance</label>
          <input type="text" class="form-control" id="bank_account_balance" v-model="currentClient.bank_account_balance" />
        </div>
        <div class="form-group">
          <label for="bank_id">Bank</label>
          <select class="form-control" id="bank_id" required v-model="currentClient.bank_id">
            <option value="" disabled>Select Bank</option>
            <option v-for="bank in banks" :key="bank.id" :value="bank.id">{{ bank.bank_name }}</option>
          </select>
        </div>
      </form>
      
      <router-link to="/clients" class="badge badge-primary">
        Cancel
      </router-link>
      <button class="badge badge-danger mr-2"
        @click="deleteClient"
      >
        Delete
      </button>
  
      <button type="submit" class="badge badge-success"
        @click="updateClient"
      >
        Update
      </button>
      <p>{{ message }}</p>
    </div>
  
    <div v-else>
      <br />
      <p>Click on a client to view their details.</p>
    </div>
  </template>
  
  <script>
  import ClientDataService from "../services/ClientDataService";
  import BankDataService from "../services/BankDataService";
  
  export default {
    name: "client-details",
    data() {
      return {
        currentClient: null,
        message: "",
        banks: [],
        bankNameToIdMap: {},
      };
    },
    methods: {
      getClient(id) {
        ClientDataService.get(id)
          .then(response => {
            this.currentClient = response.data;
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      },

      updateClient() {
        if (!this.validateBankAccountNumber()) {
         return;
        }

        // Perform form validation for all fields
        if (
          !this.currentClient.bsn ||
          !this.currentClient.mobile_phone_number ||
          !this.currentClient.firstname ||
          !this.currentClient.surname ||
          !this.currentClient.address ||
          !this.currentClient.bank_account_number ||
          !this.currentClient.bank_id
        ) {
          this.message = "Please fill in all fields.";
          return;
        }

        const selectedBank = this.banks.find(bank => bank.id === this.currentClient.bank_id);

        if (selectedBank) {
          const bankCode = selectedBank.bank_code;
          const bankAccountNumber = this.currentClient.bank_account_number;
          if (bankAccountNumber.includes(bankCode)) {
            const newBankAccountNumber = bankAccountNumber.replace(/^[A-Z]{2}[A-Z]{4}/, bankCode);
            this.currentClient.bank_account_number = newBankAccountNumber;
            this.message = ""; // Clear the error message if the update is successful
          } else {
            // If the bank_code is not found in bank_account_number, set the error message
            this.message = "Cannot update bank account name with the selected bank code.";
            return;
          }
        }

        ClientDataService.update(this.currentClient.id, this.currentClient)
          .then(response => {
            console.log(response.data);
            this.message = "Client successfully updated.";
          })
          .catch(e => {
            console.log(e);
            this.message = "Client could not be updated.";
          });
      },

      deleteClient() {
        ClientDataService.delete(this.currentClient.id)
          .then(response => {
            console.log(response.data);
            this.$router.push({ name: "clients" });
            this.message = "Client successfully deleted.";
          })
          .catch(e => {
            console.log(e);
            this.message = "Client could not be deleted.";
          });
      },

      // Uses the data from the bank database to dynamically create the regular expression.
      validateBankAccountNumber() {
      const bankCodes = this.banks.map(bank => bank.bank_code).join("|");
      const bankAccountNumberRegex = new RegExp(`^(NL\\d{2})(${bankCodes})\\d{10}$`);
      if (!bankAccountNumberRegex.test(this.currentClient.bank_account_number)) {
        this.invalidBankNumber = true;
        return false;
      }
      this.invalidBankNumber = false;
      return true;
    }
    },

    mounted() {
      this.message = '';
      this.getClient(this.$route.params.id);
  
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
        "currentClient.bank_id": function (newValue) {
          if (newValue) {
            const selectedBank = this.banks.find(bank => bank.id === newValue);
            if (selectedBank) {
              this.currentClient.bank_name = selectedBank.bank_name;
            }
          }
        },
      },
  };
  </script>
  
  <style>
  .edit-form {
    max-width: 400px;
    margin: auto;
  }
  </style>
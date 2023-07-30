<template>
    <div class="submit-form">
      <div v-if="!submitted">
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
          <label for="address">Address</label>
          <input class="form-control" id="address" required v-model="client.address" name="address" />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input class="form-control" id="password" required v-model="client.password" name="password" />
        </div>
        <div class="form-group">
          <label for="bank_account_number">Bank Account Number</label>
          <input class="form-control" id="bank_account_number" required v-model="client.bank_account_number" name="bank_account_number" />
        </div>
        <div class="form-group">
          <label for="bank_id">Bank ID</label>
          <input class="form-control" id="bank_id" required v-model="client.bank_id" name="bank_id" />
        </div>
        <button @click="saveClient" class="btn btn-success">Submit</button>
      </div>
      <div v-if="error"> 
        <h4> Could not create new client. Please check entered details and try again. </h4>
      </div>
      <div v-else-if="submitted && !error">
        <h4>You submitted successfully!</h4>
        <button class="btn btn-success" @click="newClient">Add another Client</button>
      </div>
    </div>
  </template>
  
  <script>
  import ClientDataService from "../services/ClientDataService";
  
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
          bank_id: 1,
        },
        submitted: false,
        error: false,
      };
    },
    methods: {
      saveClient() {
        var data = {
          bsn: this.client.bsn,
          mobile_phone_number: this.client.mobile_phone_number,
          firstname: this.client.firstname,
          surname: this.client.surname,
          address: this.client.address,
          password: this.client.password,
          bank_account_number: this.client.bank_account_number,
          bank_account_balance: this.client.bank_account_balance,
          bank_id: this.client.bank_id,
        };

        ClientDataService.create(data)
          .then(response => {
            this.client.id = response.data.id;
            console.log(response.data);
            this.submitted = true;
            this.error = false;
          })
          .catch(e => {
            this.error = true;
            console.log(e);
          });
      },

      newClient() {
        this.error = false;
        this.submitted = false;
        this.client = {};
      }
    }
  };
  </script>

  <style>
  .submit-form {
    max-width: 300px;
    margin: auto;
  }
  </style>
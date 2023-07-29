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
      <label for="bank_id">Bank ID</label>
      <input type="text" class="form-control" id="bank_id" v-model="currentClient.bank_id" />
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
  
  export default {
    name: "client-details",
    data() {
      return {
        currentClient: null,
        message: ''
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
        ClientDataService.update(this.currentClient.id, this.currentClient)
          .then(response => {
            console.log(response.data);
            this.message = 'The client was updated successfully!';
          })
          .catch(e => {
            console.log(e);
          });
      },
  
      deleteClient() {
        ClientDataService.delete(this.currentClient.id)
          .then(response => {
            console.log(response.data);
            this.$router.push({ name: "clients" });
          })
          .catch(e => {
            console.log(e);
          });
      }
    },
    mounted() {
      this.message = '';
      this.getClient(this.$route.params.id);
    }
  };
  </script>
  
  <style>
  .edit-form {
    max-width: 400px;
    margin: auto;
  }
  </style>
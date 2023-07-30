<template>
    <div v-if="currentBank" class="edit-form">
      <h4>Bank</h4>
      <form>
        <div class="form-group">
            <label for="bank_name">Bank Name</label>
            <input type="text" class="form-control" id="bank_name" v-model="currentBank.bank_name" />
        </div>
        <div class="form-group">
            <label for="bank_code">Bank Code</label>
            <input type="text" class="form-control" id="bank_code" v-model="currentBank.bank_code" />
        </div>
    </form>
  
      <router-link to="/banks" class="badge badge-primary">
        Cancel
      </router-link>
      <button class="badge badge-danger mr-2"
        @click="deleteBank"
      >
        Delete
      </button>
  
      <button type="submit" class="badge badge-success"
        @click="updateBank"
      >
        Update
      </button>
      <p>{{ message }}</p>
    </div>
  
    <div v-else>
      <br />
      <p>Click on a bank to view their details.</p>
    </div>
  </template>
  
  <script>
  import BankDataService from "../services/BankDataService";
  
  export default {
    name: "bank-details",
    data() {
      return {
        currentBank: null,
        message: ''
      };
    },
    methods: {
      getBank(id) {
        BankDataService.get(id)
          .then(response => {
            this.currentBank = response.data;
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      },

      updateBank() {
        BankDataService.update(this.currentBank.id, this.currentBank)
          .then(response => {
            console.log(response.data);
            this.message = 'The bank was updated successfully!';
          })
          .catch(e => {
            console.log(e);
            this.message = 'There was an error updating the bank.';
          });
      },
  
      deleteBank() {
        BankDataService.delete(this.currentBank.id)
          .then(response => {
            console.log(response.data);
            this.$router.push({ name: "banks" });
          })
          .catch(e => {
            console.log(e);
          });
      }
    },
    mounted() {
      this.message = '';
      this.getBank(this.$route.params.id);
    }
  };
  </script>
  
  <style>
  .edit-form {
    max-width: 400px;
    margin: auto;
  }
  </style>
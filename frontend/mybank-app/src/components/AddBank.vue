<template>
    <div class="submit-form">
      <div v-if="!submitted">
        <div class="form-group">
          <label for="bank_name">Bank name (e.g.: ABN Amro)</label>
          <input class="form-control" id="bank_name" required v-model="bank.bank_name" name="bank_name" />
        </div>
        <div class="form-group">
          <label for="bank_account_number">Bank code (e.g.: ABNA)</label>
          <input class="form-control" id="bank_code" required v-model="bank.bank_code" name="bank_code" />
        </div>
        <button @click="saveBank" class="btn btn-success">Submit</button>
        <p>{{ message }}</p>
      </div>
      <div v-if="error" class="error-message"> 
        <h4> Could not create new bank. Please check entered details and try again. </h4>
      </div>
      <div v-else-if="submitted && !error">
        <h4>You submitted successfully!</h4>
        <button class="btn btn-success" @click="newBank">Add another Bank</button>
      </div>
    </div>
  </template>
  <script>

import BankDataService from "../services/BankDataService";

export default {
  name: "add-bank",
  data() {
    return {
    bank: {
        bank_name: "",
        bank_code: "",
      },
      submitted: false,
      error: false,
    };
  },
  methods: {
    saveBank() {

      // Perform form validation for all fields
      if (
        !this.bank.bank_name ||
        !this.bank.bank_code
      ) {
        this.message = "Please fill in all fields.";
        return;
      }
      var data = {
        bank_name: this.bank.bank_name,
        bank_code: this.bank.bank_code,
      };

      BankDataService.create(data)
        .then(response => {
          this.bank.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
          this.error = false;
        })
        .catch(e => {
          this.error = true;
          console.log(e);
        });
    },

    newBank() {
      this.error = false;
      this.submitted = false;
      this.bank = {};
    },
  },
};
</script>


  <style>
  .submit-form {
    max-width: 300px;
    margin: auto;
  }
  </style>
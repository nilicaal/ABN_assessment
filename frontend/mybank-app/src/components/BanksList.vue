<template>
    <div class="list row">
      <div class="col-md-6">
        <h4>Banks List</h4>
        <ul class="list-group">
          <li class="list-group-item"
            :class="{ active: index == currentIndex }"
            v-for="(bank, index) in banks"
            :key="index"
            @click="setActiveBank(bank, index)"
          >
            {{ bank.bank_name }}
          </li>
        </ul>
        <button class="m-3 btn btn-sm btn-danger" @click="removeAllBanks">
          Remove All
        </button>
        <p>{{ message }}</p>
      </div>
      <div class="col-md-6">
        <div v-if="currentBank">
          <h4>Bank</h4>
          <div>
            <label><strong>Bank Name:</strong></label> {{ currentBank.bank_name }}
          </div>
          <div>
            <label><strong>Bank Code:</strong></label> {{ currentBank.bank_code }}
          </div>
          <router-link :to="'/banks/' + currentBank.id" class="badge badge-warning">Edit</router-link>
        </div>
        <div v-else>
          <br />
          <p>Click on a bank to view its details.</p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import BankDataService from "../services/BankDataService";
  
  export default {
    name: "banks-list",
    data() {
      return {
        banks: [],
        currentBank: null,
        currentIndex: -1,
        title: ""
      };
    },
    methods: {
      retrieveBanks() {
        BankDataService.getAll()
          .then(response => {
            this.banks = response.data;
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      },
  
      // Used for deleting banks menu.
      refreshList() {
        this.retrieveBanks();
        this.currentBank = null;
        this.currentIndex = -1;
      },

      // Used for the dropdown bank selection menu.
      setActiveBank(bank, index) {
        this.currentBank = bank;
        this.currentIndex = bank ? index : -1;
      },
  
      removeAllBanks() {
        BankDataService.deleteAll()
          .then(response => {
            console.log(response.data);
            this.refreshList();
            this.message = "All banks that had no clients are deleted!";
          })
          .catch(e => {
            console.log(e);
            this.message = "An error occurred, and no banks could be deleted.";
          });
      },
      
      searchTitle() {
        BankDataService.findByTitle(this.title)
          .then(response => {
            this.banks = response.data;
            this.setActiveBank(null);
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      }
    },
    mounted() {
      this.retrieveBanks();
    }
  };
  </script>
  
  <style>
  .list {
    text-align: left;
    max-width: 950px;
    margin: auto;
  }
  </style>
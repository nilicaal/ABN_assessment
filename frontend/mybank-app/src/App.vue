<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <router-link to="/" class="navbar-brand">MyBank</router-link>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/clients" class="nav-link">Clients</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/banks" class="nav-link">Banks</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/clients/add" class="nav-link">New Client</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/banks/add" class="nav-link">New Bank</router-link>
        </li>
      </div>
    </nav>

    <div class="container mt-3">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  name: "app"
};
</script>
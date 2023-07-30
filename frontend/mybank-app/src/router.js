import { createWebHistory, createRouter } from "vue-router";

const routes =  [
  {
    path: "/",
    alias: "/clients",
    name: "clients",
    component: () => import("./components/ClientsList")
  },
  {
    path: "/banks",
    name: "banks",
    component: () => import("./components/BanksList")
  },
  {
    path: "/clients/:id",
    name: "clients-details",
    component: () => import("./components/ClientDetails")
  },
  {
    path: "/clients/add",
    name: "clients-add",
    component: () => import("./components/AddClient")
  },
  {
    path: "/banks/add",
    name: "banks-add",
    component: () => import("./components/AddBank")
  },
  {
    path: "/banks/:id",
    name: "banks-details",
    component: () => import("./components/BankDetails")
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
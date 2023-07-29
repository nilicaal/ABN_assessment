import { createWebHistory, createRouter } from "vue-router";

const routes =  [
  {
    path: "/",
    alias: "/clients",
    name: "clients",
    component: () => import("./components/ClientsList")
  },
  {
    path: "/clients/:id",
    name: "clients-details",
    component: () => import("./components/ClientDetails")
  },
  {
    path: "/add",
    name: "add",
    component: () => import("./components/AddClient")
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
import http from "../http-common";

class BankDataService {
  getAll() {
    return http.get("/banks");
  }

  get(id) {
    return http.get(`/banks/${id}`);
  }

  create(data) {
    return http.post("/banks", data);
  }

  update(id, data) {
    return http.put(`/banks/${id}`, data);
  }

  delete(id) {
    return http.delete(`/banks/${id}`);
  }

  deleteAll() {
    return http.delete(`/banks`);
  }
}

export default new BankDataService();
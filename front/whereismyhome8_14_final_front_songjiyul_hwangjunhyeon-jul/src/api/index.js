import axios from "axios";

// local vue api axios instance
function apiInstance() {
  const instance = axios.create({
    baseURL: "http://localhost:9999/myHome",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}
function apiInstanceFormdata() {
  const instance = axios.create({
    baseURL: "http://localhost:9999/myHome",
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return instance;
}

export { apiInstance, apiInstanceFormdata };

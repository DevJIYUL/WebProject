import { apiInstance } from "./index.js";

const api = apiInstance();

async function selectAllSido(success, fail) {
  await api.get("/api/apt/sido").then(success).catch(fail);
}

async function selectGuGun(dongdto, success, fail) {
  await api.post("/api/apt/gugun", dongdto).then(success).catch(fail);
}

async function selectDong(dongdto, success, fail) {
  await api.post("/api/apt/dong", dongdto).then(success).catch(fail);
}

async function findAptDealList(param, success, fail) {
  await api.get(`/api/apt/list/dong/${param}`).then(success).catch(fail);
}
async function findApt(param, success, fail) {
  await api.get(`/api/apt/list/info/${param}`).then(success).catch(fail);
}

export { selectAllSido, selectGuGun, selectDong, findAptDealList, findApt };

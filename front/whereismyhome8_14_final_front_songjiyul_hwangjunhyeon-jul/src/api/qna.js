import { apiInstance } from "./index.js";
import router from "@/router";
const api = apiInstance();

async function findById(id, success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  await api.get(`/auth/quest/${id}`).then(success).catch(fail);
}

async function findAllList(success, fail) {
  await api.get("/api/quest").then(success).catch(fail);
}

async function insertQna(data, success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  await api.post("/auth/quest", data).then(success).catch(fail);
}

async function findByUsername(username, success, fail) {
  await api.get(`/api/quest/user/${username}`).then(success).catch(fail);
}

async function updateQna(qna, success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  await api.put("/auth/quest", qna).then(success).catch(fail);
}

async function updateAnswer(qna, success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  await api.put("/auth/answer", qna).then(success).catch(fail);
}

async function deleteById(param, success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  await api
    .delete(`/auth/quest/${param.username}/${param.id}`)
    .then(success)
    .catch(fail);
}
export {
  findById,
  findAllList,
  insertQna,
  findByUsername,
  updateQna,
  updateAnswer,
  deleteById,
};

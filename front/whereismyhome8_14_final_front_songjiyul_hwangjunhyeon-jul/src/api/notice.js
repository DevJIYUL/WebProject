import { apiInstance, apiInstanceFormdata } from "./index.js";
const api = apiInstance();

const formdata = apiInstanceFormdata();

async function selectAllNotice(success, fail) {
  await api.get("/api/notice").then(success).catch(fail);
}

async function selectNotice(params, success, fail) {
  console.log('notice detail -> ', params);
  await api
    .get("/api/notice/" + params)
    .then(success)
    .catch(fail);
}

async function updateNotice(params, success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  await api
    .post("/auth/modify", params, {
      headers: { "Content-Type": "multipart/form-data;charset=utf-8" },
    })
    .then(success)
    .catch(fail);
}

async function insertNotice(params, success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  await api
    .post("/auth/notice", params, {
      headers: { "Content-Type": "multipart/form-data;charset=utf-8" },
    })
    .then(success)
    .catch(fail);
}

async function down(params, success, fail) {
  await api
    .post("/api/download", params, {
      responseType: "blob",
    })
    .then(success)
    .catch(fail);
}
export { selectAllNotice, selectNotice, updateNotice, insertNotice, down };

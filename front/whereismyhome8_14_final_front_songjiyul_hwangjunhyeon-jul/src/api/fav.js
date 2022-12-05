import { apiInstance } from "./index.js";
import router from "@/router";
const api = apiInstance();

// async function insertUser(success, fail) {
//   await api.post("/user").then(success).catch(fail);
// }
async function checkFav(param, success, fail) {
  if (!$cookies.get("accessToken")) {
    router.push({ name: "LoginView" });
    return;
  }
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  console.log(param);
  await api
    .get(`/auth/fav/${param.username}/${param.dongCode}`)
    .then(success)
    .catch(fail);
}
async function insertFavLoc(param, success, fail) {
  if (!$cookies.get("accessToken")) {
    alert("로그인 해주세요😊")
    router.push({ name: "LoginView" });
    return;
  }
  console.log("Bearer " + $cookies.get("accessToken"));
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");

  console.log("API InsertFavLoc => ", param);
  await api.post(`/auth/fav/loc`, param).then(success).catch(fail);
}

async function removeFavLoc(param, success, fail) {
  if (!$cookies.get("accessToken")) {
    alert("로그인 해주세요😊")
    router.push({ name: "LoginView" });
    return;
  }
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");

  console.log("API RemoveFavLoc => ", param);
  await api
    .delete(`/auth/fav/loc/${param.username}/${param.dongCode}`)
    .then(success)
    .catch(fail);
}
export { checkFav, removeFavLoc, insertFavLoc };

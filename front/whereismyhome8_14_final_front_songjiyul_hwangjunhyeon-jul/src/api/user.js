import { apiInstance } from "./index.js";

const api = apiInstance();

async function authenticate(user, success, fail) {
  await api.post(`/api/authenticate`, user).then(success).catch(fail);
}

async function insertUser(data, success, fail) {
  console.log("data => ", data);
  // await api.post("/user", data).then(success).catch(fail);
  await api.post(`/api/signup`, data).then(success).catch(fail);
}
async function findById(username, success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("accessToken");
  await api.get(`/auth/user/${username}`).then(success).catch(fail);
}
async function tokenRegeneration(success, fail) {
  api.defaults.headers["Authorization"] =
    "Bearer " + $cookies.get("refreshToken"); //axios header에 refresh-token 셋팅
  console.log("RefreshToken 으로 Access Token 받기!!");
  console.log($cookies.get("refreshToken"));
  await api.post(`/auth/refreshtoken`).then(success).catch(fail);
}
async function logout(success, fail) {
  // api.defaults.headers["Authorization"] = "Bearer " + $cookies.get("refreshToken"); //axios header에 refresh-token 셋팅
  await api.get(`/api/logout`).then(success).catch(fail);
}

// 유저 정보
async function selectUserInfo(params, success, fail) {
  await api.get(`/auth/userinfo/${params}`).then(success).catch(fail);
}
// 유저 관심 지역
async function selectDong(params, success, fail) {
  await api.get(`/auth/userdong/${params}`).then(success).catch(fail);
}
async function selectKey(params,success,fail) {
  await api.get(`/auth/userkey/${params}`).then(success).catch(fail);
}
export {
  authenticate,
  insertUser,
  findById,
  tokenRegeneration,
  logout,
  selectUserInfo,
  selectDong,
  selectKey,
};

// import http from "@/util/http";
import Constant from "@/util/Constant.js";
import {
  authenticate,
  insertUser,
  findById,
  tokenRegeneration,
  logout,
  selectUserInfo,
  selectDong,
  selectKey
} from "@/api/user";
import router from "@/router";
const userStore = {
  namespaced: true,
  state: {
    colorDialog: false,
    accentColor: "",
    customColor: "",

    isAuth: false,
    isAuthError: false,
    validTokenFlag: false,
    auth: {
      username: "",
      role: "",
    },

    userInfo: [],
    userFavDong: [],
    userKey:[],
  },
  getters: {
    getColorDialog(state) {
      return state.colorDialog;
    },
    getAuth(state) {
      return state.auth;
    },
    getAccentColor(state) {
      return state.accentColor;
    },
    getCustomColor(state) {
      return state.customColor;
    },
    getValidTokenFlag(state) {
      return state.validTokenFlag;
    },

    getUserInfo(state) {
      return state.userInfo;
    },
    getFavDongList(state) {
      console.log("동 가져온다", state.userFavDong);
      return state.userFavDong;
    },
    getFavKeyList(state) {
      return state.userKey;
    }
  },
  mutations: {
    [Constant.GET_FAV_DONG]: (state, data) => {
      state.userFavDong = data;
    },
    [Constant.OPEN_COLOR_DIALOG]: (state) => {
      state.colorDialog = true;
    },
    [Constant.CLOSE_COLOR_DIALOG]: (state) => {
      state.colorDialog = false;
    },
    [Constant.GET_USER_INFORMATION]: (state, data) => {
      console.log("동 state에 저장");
      state.userInfo = data;
    },
    [Constant.GET_FAV_KEY]: (state, data) => {
      state.userKey = data;
    },
    // setting Custom-Color
    SET_CUSTOM_COLOR: (state, color) => {
      state.customColor = color;
    },
    SET_AUTH: (state, data) => {
      state.auth = data;
    },
    SET_VALID_TOKEN_FLAG: (state, flag) => {
      state.validTokenFlag = flag;
    },
  },
  actions: {
    // Login
    async login({ commit }, user) {
      await authenticate(
        user,
        (response) => {
          if (response.status === 200) {
            // console.log("login success token created!!!! >> ", accessToken, refreshToken);
            const authInfo = {
              username: response.data.username,
              role: response.data.roles[0],
            };
            console.log(authInfo);
            commit("SET_AUTH", authInfo);
            commit("SET_VALID_TOKEN_FLAG", true);
            $cookies.set("accessToken", response.data.accessToken);
            $cookies.set("refreshToken", response.data.refreshToken);
            $cookies.set("auth", authInfo);
            console.log(
              "auth\n",
              $cookies.get("auth"),
              "access\n",
              $cookies.get("accessToken"),
              "refresh\n",
              $cookies.get("refreshToken")
            );
            router.push({ name: "HomeView" });
          } else {
            commit("SET_VALID_TOKEN_FLAG", false);
          }
        },
        (error) => {
          if (error.response.status == 401) {
            alert("잘못된 비밀번호 입니다😥");
          }
        }
      );
    },

    // Auth Check
    async getUser({ commit, dispatch }, auth) {
      console.log("Auth Check Actions => ", auth);
      // console.log("2. getUserInfo() decodeToken :: ", decodeToken);
      await findById(
        auth.username,
        (response) => {
          if (response.status === 200) {
            console.log(response.data);
            commit("SET_AUTH", response.data);
            commit("SET_VALID_TOKEN_FLAG", true);
            // console.log("3. getUserInfo data >> ", data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.log(
            "getUser() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          commit("SET_VALID_TOKEN_FLAG", false);
          await dispatch("tokenRegeneration");
        }
      );
    },
    // post 에서 data parameter 가 없어서 문제 발생할 수도 있다.
    async tokenRegeneration({ commit, state }) {
      console.log("토큰 재발급 >> 기존 토큰 정보 : {}", $cookies.get("accessToken"));
      await tokenRegeneration(
        (response) => {
          console.log(response);
          if (response.status === 200) {
            // let accessToken = data["access-token"];
            let accessToken = response.data.accessToken;
            console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            $cookies.set("accessToken", accessToken);
            commit("SET_VALID_TOKEN_FLAG", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              (response) => {
                if (response.status === 200) {
                  console.log("리프레시 토큰 제거 성공");
                  $cookies.remove("accessToken");
                  $cookies.remove("refreshToken");
                  $cookies.remove("auth");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("로그아웃 되었습니다😂");
                // commit("SET_IS_LOGIN", false);
                commit("SET_AUTH", {
                  username: "",
                  role: "",
                });
                commit("SET_VALID_TOKEN_FLAG", false);
                router.push({ name: "HomeView" });
              },
              (error) => {
                console.log(error);
                // commit("SET_IS_LOGIN", false);
                commit("SET_AUTH", {
                  username: "",
                  role: "",
                });
              }
            );
          } else if (error.response.status == 403) {
            console.log("refreshToken 403 Error => ", error);
            alert("권한이 없습니다😥");
          }
        }
      );
    },

    async userLogout({ commit }) {
      await logout(
        (response) => {
          if (response.status === 200) {
            console.log("리프레시 토큰 제거 성공");
            $cookies.remove("accessToken");
            $cookies.remove("refreshToken");
            $cookies.remove("auth");
          } else {
            console.log("리프레시 토큰 제거 실패");
          }
          alert("로그아웃 되었습니다😂");
          // commit("SET_IS_LOGIN", false);
          commit("SET_AUTH", {
            username: "",
            role: "",
          });
          commit("SET_VALID_TOKEN_FLAG", false);
          router.push({ name: "HomeView" });
        },
        (error) => {
          console.log(error);
          // commit("SET_IS_LOGIN", false);
          commit("SET_AUTH", {
            username: "",
            role: "",
          });
        }
      );
    },

    [Constant.POST_USER]: async ({ commit }, signupdto) => {
      console.log("request = >", signupdto);
      await insertUser(
        signupdto,
        (response) => {
          // router로 로그인 페이지로 보내줘야하나?
          if (response.status == 200) {
            router.push({ name: "LoginView" });
          }
        },
        (error) => {
          console.log(error);
          if (error.response.status == 400) {
            alert(error.response.data.msg);
          }
          // error.response.status == 400 // BAD_REQUEST 실패
          // error.response.data.msg => 이유
        }
      );
    },

    // 유저 관심 지역가져오기
    [Constant.GET_USER_INFORMATION]: async ({ commit }, username) => {
      await selectUserInfo(
        username,
        ({ data }) => {
          commit(Constant.GET_USER_INFORMATION, data);
        },
        (error) => {
          console.log("에러입니다.");
          console.log(error);
        }
      );
    },
    [Constant.GET_FAV_DONG]: async ({ commit }, username) => {
      console.log(username);
      await selectDong(
        username,
        ({ data }) => {
          console.log("동뿌려짐");
          console.log("이거이 관심지여ㅑㄱ이다", data);
          console.log(data);
          commit(Constant.GET_FAV_DONG, data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    [Constant.GET_FAV_KEY]: async ({ commit }, username) => {
      console.log(username);
      await selectKey(
        username,
        ({ data }) => {
          console.log(data);
          commit(Constant.GET_FAV_KEY, data);
        },
        (error) => {
          console.log(error);
        }
      )
    }
  },
};
export default userStore;

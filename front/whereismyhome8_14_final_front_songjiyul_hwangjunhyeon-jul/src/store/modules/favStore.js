import Constant from "@/util/Constant.js";
import { checkFav, removeFavLoc, insertFavLoc } from "@/api/fav.js";
import router from "@/router";
import store from "@/store";

const favStore = {
  namespaced: true,
  state: {
    favFlag: false,
  },
  getters: {
    getFavFlag(state) {
      return state.favFlag;
    },
  },
  mutations: {
    [Constant.SET_FAV_FLAG]: (state, data) => {
      state.favFlag = data;
    },
    // [Constant.REMOVE_FAV_LOC]: (state, data) => {
    //   state.favFlag = data;
    // },
  },
  actions: {
    [Constant.CHECK_FAV_LOC]: async ({ commit }, param) => {
      console.log("checkFav => ", param);
      await checkFav(
        param,
        ({ status }) => {
          console.log(status);
          if (status == 200) {
            commit(Constant.SET_FAV_FLAG, true);
          } else {
            commit(Constant.SET_FAV_FLAG, false);
          }
        },
        async (error) => {
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
          }
          await checkFav(
            param,
            ({ status }) => {
              console.log(status);
              if (status == 200) {
                commit(Constant.SET_FAV_FLAG, true);
              } else {
                commit(Constant.SET_FAV_FLAG, false);
              }
            },
            () => {
              alert("로그인 다시 해주세요😂");
              router.push({name: "LoginView"});
            }
          );
        }
      );
    },
    [Constant.ADD_FAV_LOC]: async ({ commit }, param) => {
      console.log("Fav Store ADD_FAV_LOC => ", param);
      await insertFavLoc(
        {
          username: param.username,
          dongCode: param.dongCode,
        },
        (response) => {
          if (response.data == 200) {
            console.log("Success Insert ", response);
            commit(Constant.SET_FAV_FLAG, true);
          }
        },
        async (error) => {
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
          }
          await insertFavLoc(
            {
              username: param.username,
              dongCode: param.dongCode,
            },
            () => {
              commit(Constant.SET_FAV_FLAG, true);
            },
            () => {
              alert("로그인 해주세요😂");
              router.push({name: "LoginView"});
            }
          )
        }
      );
    },
    [Constant.REMOVE_FAV_LOC]: async ({ commit }, param) => {
      console.log(param);
      await removeFavLoc(
        param,
        (response) => {
          if (response.status == 200) {
            // Success
            commit(Constant.SET_FAV_FLAG, false);
          }
        },
        async (error) => {
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
          }
          await removeFavLoc(
            param,
            () => {
              commit(Constant.SET_FAV_FLAG, false);
            },
            () => {
              alert("로그인 해주세요😂");
              router.push({name: "LoginView"});
            }
          )
        })
    },
  },
};
export default favStore;

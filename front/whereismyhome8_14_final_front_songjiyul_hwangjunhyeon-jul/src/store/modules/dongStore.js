import Constant from "@/util/Constant.js";
import { selectAllSido, selectGuGun, selectDong } from "@/api/apt";
const dongStore = {
  namespaced: true,
  state: {
    // selectbox에 띄어 주기 위한 리스트
    sidoList: [],
    gugunList: [],
    dongList: [],
    dongCode: "",
    dongName: "",
  },
  getters: {
    getSidoList(state) {
      return state.sidoList;
    },
    getGuGunList(state) {
      return state.gugunList;
    },
    getDongList(state) {
      return state.dongList;
    },
    getDongCode(state) {
      return state.dongCode;
    },
    getDongName(state) {
      return state.dongName;
    },
  },
  actions: {
    [Constant.GET_SIDO]: async ({ commit }) => {
      await selectAllSido(
        ({ data }) => {
          console.log(data);
          commit(Constant.GET_SIDO, data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    [Constant.GET_GUGUN]: async ({ commit }, dongdto) => {
      await selectGuGun(
        dongdto,
        ({ data }) => {
          console.log(dongdto);
          commit(Constant.GET_GUGUN, data);
          commit(Constant.PUT_DONGCODE, "");
          commit(Constant.SET_DONGNAME, "");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    [Constant.GET_DONG]: async ({ commit }, dongdto) => {
      await selectDong(
        dongdto,
        ({ data }) => {
          console.log(dongdto);
          commit(Constant.GET_DONG, data);
          commit(Constant.PUT_DONGCODE, "");
          commit(Constant.SET_DONGNAME, "");
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
  mutations: {
    [Constant.GET_SIDO]: (state, data) => {
      state.sidoList = [];
      state.gugunList = [];
      state.dongList = [];
      for (let index = 0; index < data.length; index++) {
        state.sidoList.push(data[index].sidoName);
      }
    },
    [Constant.GET_GUGUN]: (state, data) => {
      state.gugunList = [];
      state.dongList = [];
      for (let index = 0; index < data.length; index++) {
        state.gugunList.push(data[index].gugunName);
      }
    },
    [Constant.GET_DONG]: (state, data) => {
      state.dongList = [];
      for (let index = 0; index < data.length; index++) {
        state.dongList.push({
          dongName: data[index].dongName,
          dongCode: data[index].dongCode,
        });
      }
      console.log(state.dongList);
    },
    [Constant.PUT_DONGCODE]: (state, data) => {
      state.dongCode = data;
    },
    [Constant.SET_DONGNAME]: (state, dongName) => {
      state.dongName = dongName;
    },
    [Constant.SET_SIDO_LIST]: (state, data) => {
      state.sidoList = data;
      state.gugunList = [];
      state.dongList = [];
      state.dongName = "";
      state.dongCode = "";
    },
  },
};
export default dongStore;

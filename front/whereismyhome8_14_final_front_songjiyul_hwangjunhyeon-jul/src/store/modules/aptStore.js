import Constant from "@/util/Constant";
import { findApt, findAptDealList } from "@/api/apt";
const aptStore = {
  namespaced: true,
  state: {
    dongName: "",
    dongCode: "",
    aptDealList: [],
    apt: {},
  },
  getters: {
    getDongNameResult(state) {
      return state.dongName;
    },
    getDongCodeResult(state) {
      return state.dongCode;
    },
    getAptDealList(state) {
      return state.aptDealList;
    },
    getAptListCount(state) {
      return state.aptDealList.length;
    },
    getApt(state) {
      return state.apt;
    },
  },
  mutations: {
    /////////////////////////////// Apt start /////////////////////////////////////
    [Constant.SELECT_APT_DEAL_LIST]: (state, data) => {
      state.aptDealList = data;
    },
    [Constant.SET_DONGCODE_RESULT]: (state, dongCode) => {
      state.dongCode = dongCode;
    },
    [Constant.SET_DONGNAME]: (state, dongName) => {
      state.dongName = dongName;
    },
    [Constant.SET_APT]: (state, data) => {
      console.log("SET_APT mutation => ", data);
      state.apt = data;
    },
    // /////////////////////////////// Apt end /////////////////////////////////////
  },
  actions: {
    [Constant.SELECT_APT_DEAL_LIST]: async ({ commit }, param) => {
      console.log(
        "Actions select apt deal list => ",
        param.dongName,
        param.dongCode,
        param.dealDate
      );
      await findAptDealList(
        param.dongCode + "-" + param.dealDate,
        (response) => {
          console.log("Actions : SELECT APT DEAL LIST Response!!!! ", response);
          if (response.status == 200) {
            console.log(response.data, param.dongCode, param.dongName);
            commit(Constant.SELECT_APT_DEAL_LIST, response.data);
            commit(Constant.SET_DONGCODE_RESULT, param.dongCode);
            commit(Constant.SET_DONGNAME, param.dongName);
          } else if (response.status == 204) {
            console.log(response.data, param.dongCode, param.dongName);
            commit(Constant.SELECT_APT_DEAL_LIST, []);
            commit(Constant.SET_DONGCODE_RESULT, param.dongCode);
            commit(Constant.SET_DONGNAME, param.dongName);
          } else {
            commit(Constant.SELECT_APT_DEAL_LIST, []);
            commit(Constant.SET_DONGCODE_RESULT, "");
            commit(Constant.SET_DONGNAME, "");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    [Constant.SELECT_APT]: async ({ commit }, param) => {
      console.log("SELECT_APT Actions => ", param);
      await findApt(
        param.aptCode + "-" + param.no,
        ({ data }) => {
          console.log(data);
          commit(Constant.SET_APT, data);
        },
        (error) => console.log(error)
      );
    },
    /////////////////////////////// Apt end /////////////////////////////////////
  },
};
export default aptStore;

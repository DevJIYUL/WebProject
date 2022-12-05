import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
// module
import qnaStore from "@/store/modules/qnaStore";
import userStore from "@/store/modules/userStore";
import aptStore from "@/store/modules/aptStore";
import noticeStore from "@/store/modules/noticeStore";
import dongStore from "@/store/modules/dongStore";
import favStore from "@/store/modules/favStore";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    qnaStore,
    userStore,
    aptStore,
    noticeStore,
    dongStore,
    favStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

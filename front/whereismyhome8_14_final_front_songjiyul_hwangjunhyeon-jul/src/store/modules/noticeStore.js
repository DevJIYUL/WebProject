// import Constant from "@/util/Constant";
import Constant from "@/util/Constant.js";
import router from "@/router";
import store from "@/store";
import {
  selectAllNotice,
  selectNotice,
  updateNotice,
  insertNotice,
  down,
} from "@/api/notice";
const noticeStore = {
  namespaced: true,
  state: {
    noticeList: [],
    noticeCount: 0,
    // noticeNoDetail: 0,
    noticeDetail: [],
  },
  getters: {
    getNoticeList(state) {
      return state.noticeList;
    },
    getNoticeCount(state) {
      return (state.noticeCount = state.noticeList.length);
    },
    getNoticeDetailJson(state) {
      return state.noticeDetail;
    },
  },
  actions: {
    [Constant.DOWNLOAD]: async ({ commit }, downloadJson) => {
      console.log("actions here");
      console.log(downloadJson);
      await down(
        downloadJson,
        (response) => {
          console.log(response);
          const fileObjectUrl = window.URL.createObjectURL(
            new Blob([response.data])
          );
          const link = document.createElement("a");
          link.href = fileObjectUrl;
          link.setAttribute("download", downloadJson.originalFile);
          document.body.appendChild(link);
          link.click();
          link.style.display = "none";
          console.log();
          alert("다운로드 완료");
        },
        (error) => {
          console.log("에러발생");
          console.log(error);
        }
      );
    },
    [Constant.POST_N]: async ({ commit, dispatch }, formdata) => {
      await insertNotice(
        formdata,
        ({ data }) => {
          console.log(data);
          dispatch(Constant.GET_NOTICE);
        },
        async (error) => {
          console.log("POST QNA => ", error);
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
            console.log("토큰 재발급 받은 후");
          } else if (error.response.status == 403) {
            console.log("토큰 문제가 아냐 권한이 없어");
            alert("글쓰기 권한이 없습니다");
            return;
          }
          console.log("재발급 했으니 다시 등록 수행");
          await insertNotice(
            formdata,
            ({ data }) => {
              console.log(data);
              dispatch(Constant.GET_NOTICE);
            },
            (error) => {
              if (error.response.status == 403) {
                alert("글쓰기 권한이 없습닏다");
                return;
              }
              alert("ERROR");
            }
          );
        }
      );
    },
    [Constant.GET_NOTICE]: async ({ commit }) => {
      await selectAllNotice(
        ({ data }) => {
          commit(Constant.GET_NOTICE, data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    [Constant.GET_NOTICE_DETAIL]: async ({ commit }, no) => {
      console.log("no => ", no);
      await selectNotice(
        no,
        ({ data }) => {
          console.log(data);
          commit(Constant.GET_NOTICE_DETAIL, data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    [Constant.MODIFY_NOTICE]: async ({ commit, dispatch }, formdata) => {
      await updateNotice(
        formdata,
        ({ data }) => {
          if (data == "success") {
            console.log(formdata.get("noticeNo"));
            router.push({ name: "NoticeList" });
            dispatch(Constant.GET_NOTICE);
          }
          console.log("modify api => ", data);
        },
        async (error) => {
          console.log("POST QNA => ", error);
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
            console.log("토큰 재발급 받은 후");
          } else if (error.response.status == 403) {
            console.log("토큰 문제가 아냐 권한이 없어");
            alert("공지사항 수정 권한이 없습니다");
            return;
          }
          console.log("재발급 했으니 다시 등록 수행");
          await updateNotice(
            formdata,
            ({ data }) => {
              if (data == "success") {
                console.log(formdata.get("noticeNo"));
                router.push({ name: "NoticeList" });
                dispatch(Constant.GET_NOTICE);
              }
              console.log("modify api => ", data);
            },
            (error) => {
              if (error.response.status == 403) {
                alert("글쓰기 권한이 없습닏다");
                return;
              }
              alert("ERROR");
            }
          );
        }
      );
    },
  },
  mutations: {
    [Constant.GET_NOTICE]: (state, data) => {
      state.noticeList = data;
      console.log(state.noticeList);
    },
    [Constant.GET_NOTICE_DETAIL]: (state, data) => {
      console.log(data);
      state.noticeDetail = data;
    },
  },
};

export default noticeStore;

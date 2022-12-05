import router from "@/router";
import Constant from "@/util/Constant.js";
import store from "@/store";
import {
  findById,
  findAllList,
  insertQna,
  findByUsername,
  updateQna,
  updateAnswer,
  deleteById,
} from "@/api/qna";

const qnaStore = {
  namespaced: true,
  state: {
    qnaDialog: false,
    qnaList: [],
    qna: {},
    qnaCount: 0,

    registerForm: {
      title: "",
      content: "",

      privateFlag: false,
      username: "ssafy",
    },
  },
  getters: {
    ///////////////////////// QnA start  ///////////////////////////////
    getQnaList(state) {
      return state.qnaList;
    },
    getQnaDialog(state) {
      return state.qnaDialog;
    },
    getQna(state) {
      return state.qna;
    },
    getQnaCount(state) {
      return (state.qnaCount = state.qnaList.length);
    },
    getRegisterForm(state) {
      return state.registerForm;
    },
    ///////////////////////// QnA end  ///////////////////////////////
  },
  mutations: {
    ////////////////////////////// QnA start  //////////////////////////////////////
    [Constant.SELECT_QNA_LIST]: (state, data) => {
      state.qnaList = data;
      console.log("Mutations => ", data);
    },

    [Constant.OPEN_QNA_DIALOG]: (state) => {
      console.log("Mutations : Open Qna Dialog ");
      state.qnaDialog = true;
    },
    [Constant.CLOSE_QNA_DIALOG]: (state) => {
      state.qnaDialog = false;
    },

    [Constant.SELECT_QNA]: (state, data) => {
      console.log("select qna mutations => ", data);
      state.qna = data;
    },
    [Constant.SELECT_BY_USERNAME]: (state, data) => {
      state.qnaList = data;
    },
    /////////////////////////////// Qna end /////////////////////////////////////
  },
  actions: {
    /////////////////////////////// Qna start /////////////////////////////////////
    [Constant.SELECT_QNA]: async ({ commit }, id) => {
      await findById(
        id,
        ({ data }) => {
          commit(Constant.SELECT_QNA, data);
        },
        async (error) => {
          console.log(error);
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
          }
          await findById(
            id,
            ({ data }) => {
              commit(Constant.SELECT_QNA, data);
            },
            (error) => {
              commit(Constant.CLOSE_QNA_DIALOG);
              commit(Constant.SELECT_QNA, {});
            }
          );
        }
      );
    },
    [Constant.SELECT_QNA_LIST]: async ({ commit }) => {
      await findAllList(
        ({ data }) => {
          commit(Constant.SELECT_QNA_LIST, data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    [Constant.OPEN_QNA_DIALOG]: ({ commit, dispatch }, id) => {
      commit(Constant.OPEN_QNA_DIALOG);
      dispatch(Constant.SELECT_QNA, id);
    },
    [Constant.POST_QNA]: async ({ dispatch }, qna) => {
      console.log("Actions=>", qna);
      await insertQna(
        qna,
        ({ data }) => {
          console.log("axios then => post QnA success ", data);
          if (data == "success") {
            dispatch(Constant.SELECT_QNA_LIST);
            router.push({ name: "QnaView" });
          } else {
            alert("등록 실패😥");
          }
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
          await insertQna(
            qna,
            ({ data }) => {
              console.log("axios then => post QnA success ", data);
              if (data == "success") {
                dispatch(Constant.SELECT_QNA_LIST);
                router.push({ name: "QnaView" });
              } else {
                alert("등록 실패😥");
              }
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

    [Constant.SELECT_BY_USERNAME]: async ({ commit }, username) => {
      if(!username) {
        alert("로그인 해주세요😀");
        router.push({ name: "LoginView" });
        return;
      }
      await findByUsername(
        username,
        ({ data }) => {
          commit(Constant.SELECT_QNA_LIST, data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    [Constant.MODIFY_QNA]: async ({ dispatch }, qna) => {
      await updateQna(
        qna,
        ({ data }) => {
          if (data == "success") {
            dispatch(Constant.SELECT_QNA_LIST);
            dispatch(Constant.SELECT_QNA, qna.id);
          } else {
            alert("수정 실패😯");
          }
        },
        async (error) => {
          console.log("POST QNA => ", error);
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
            console.log("토큰 재발급 받은 후");
          } else if (error.response.status == 403) {
            console.log("토큰 문제가 아냐 권한이 없어");
            alert("글 수정 권한이 없습니다");
            return;
          }

          await updateQna(
            qna,
            ({ data }) => {
              if (data == "success") {
                dispatch(Constant.SELECT_QNA_LIST);
                dispatch(Constant.SELECT_QNA, qna.id);
              } else {
                alert("수정 실패😯");
              }
            },
            (error) => {
              console.log("POST QNA => ", error);
              if (error.response.status == 403) {
                alert("글 수정 권한이 없습니다");
                return;
              }
              alert("ERROR");
            }
          );
        }
      );
    },
    [Constant.REGISTER_ANSWER]: async ({ dispatch }, qna) => {
      await updateAnswer(
        qna,
        ({ data }) => {
          console.log(data);
          if (data == "success") {
            dispatch(Constant.SELECT_QNA_LIST);
            dispatch(Constant.SELECT_QNA, qna.id);
          } else {
            alert("답변 등록 실패😯");
          }
        },
        async (error) => {
          console.log("POST QNA => ", error);
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
            console.log("토큰 재발급 받은 후");
          } else if (error.response.status == 403) {
            alert("답변 권한이 없습니다");
            return;
          }

          await updateAnswer(
            qna,
            ({ data }) => {
              console.log(data);
              if (data == "success") {
                dispatch(Constant.SELECT_QNA_LIST);
                dispatch(Constant.SELECT_QNA, qna.id);
              } else {
                alert("답변 등록 실패😯");
              }
            },
            (error) => {
              if (error.response.status == 403) {
                alert("답변 권한이 없습니다");
                return;
              }
              alert("ERROR");
            }
          );
        }
      );
    },
    [Constant.REMOVE_QNA]: async ({ dispatch }, param) => {
      await deleteById(
        param,
        ({ data }) => {
          if (data == "success") {
            dispatch(Constant.SELECT_QNA_LIST);
          } else {
            alert("Q&A 삭제 실패 😣");
          }
        },
        async (error) => {
          if (error.response.status == 401) {
            await store.dispatch("userStore/tokenRegeneration");
          } else if (error.response.status == 403) {
            alert("삭제 권한 없습니다");
            return;
          }
          await deleteById(
            param,
            ({ data }) => {
              if (data == "success") {
                dispatch(Constant.SELECT_QNA_LIST);
              } else {
                alert("Q&A 삭제 실패 😣");
              }
            },
            (error) => {
              if (error.response.status == 403) {
                alert("삭제 권한 없습니다");
                return;
              }
              alert("로그인 다시 해주세요😂");
              router.push({ name: "LoginView" });
            }
          );
        }
      );
    },
    /////////////////////////////// Qna end /////////////////////////////////////
  },
};

export default qnaStore;

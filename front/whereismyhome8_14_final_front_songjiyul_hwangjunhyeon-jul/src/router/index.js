import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/HomeView.vue";
import store from "@/store";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const auth = $cookies.get("auth");
  const validTokenFlag = store.getters["userStore/getValidTokenFlag"];
  const accessToken = $cookies.get("accessToken");

  console.log("로그인 처리 전", auth, accessToken);

  if (auth != null && accessToken) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("userStore/getUser", auth);
  }
  console.log(validTokenFlag, auth);
  if (!validTokenFlag || auth === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "LoginView" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "HomeView",
    component: Home,
  },
  {
    path: "/notice",
    name: "NoticeView",
    component: () => import("@/views/NoticeView.vue"),
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "NoticeList",
        component: () => import("@/components/notice/NoticeList.vue"),
      },
      {
        path: "detail",
        name: "NoticeDetail",
        component: () => import("@/components/notice/NoticeDetail.vue"),
      },
      {
        path: "modify",
        name: "NoticeModify",
        component: () => import("@/components/notice/NoticeModify.vue"),
      },
      {
        path: "write",
        name: "NoticeWrite",
        component: () => import("@/components/notice/NoticeWrite.vue"),
      },
    ],
  },
  {
    path: "/qna",
    name: "QnaView",
    component: () => import("@/views/QnaView.vue"),
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "QnaList",
        component: () => import("@/components/qna/QnaList.vue"),
      },
      {
        path: "register",
        name: "QnaRegisterView",
        beforeEnter: onlyAuthUser,
        component: () => import("@/views/QnaRegisterView.vue"),
      },
    ],
  },
  {
    path: "/mypage",
    name: "MyPageView",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/MyPageView.vue"),
  },
  {
    path: "/login",
    name: "LoginView",
    component: () => import("@/views/LoginView.vue"),
  },
  {
    path: "/signup",
    name: "SignupView",
    component: () => import("@/views/SignupView.vue"),
  },
  {
    path: "/apt",
    name: "AptView",
    component: () => import("@/views/AptView.vue"),
  },
  {
    path: "/map",
    name: "Map",
    component: () => import("@/component/map/MapView.vue"),
  },
  // {
  //   path: "/detail",
  //   name: "Detail",
  //   component: () => import("@/views/Detail.vue"),
  // },
  // Logout 어떻게 처리할지?
];

const router = new VueRouter({
  routes,
  scrollBehavior() {
    document.getElementById("app").scrollIntoView();
  },
});

export default router;

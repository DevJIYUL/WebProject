<template>
  <div>
    <v-navigation-drawer
      v-if="!$vuetify.breakpoint.mdAndUp"
      v-model="drawer"
      :clipped="$vuetify.breakpoint.mdAndUp"
      app
      color="primary"
      dark
    >
      <v-list color="primary" nav>
        <v-list-item
          v-for="(item, i) in barItems"
          :key="i"
          :to="{ name: item.name }"
          link
        >
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
      :clipped-left="$vuetify.breakpoint.mdAndUp"
      app
      color="white"
      elevate-on-scroll
      flat
    >
      <v-container :class="{ 'px-0': !$vuetify.breakpoint.mdAndUp }">
        <v-row
          :no-gutters="!$vuetify.breakpoint.mdAndUp"
          align="center"
          justify="space-between"
        >
          <v-col class="d-flex align-center">
            <v-app-bar-nav-icon
              v-if="!$vuetify.breakpoint.mdAndUp"
              @click.stop="drawer = !drawer"
            />
            <v-toolbar-title
              class="font-weight-bold text-h5 primary--text"
              style="cursor: pointer"
              @click="$router.push('/')"
            >
              <font-awesome-icon icon="fa-solid fa-house-chimney" />
              Select
              <span class="accent--text">HOMES</span>
            </v-toolbar-title>
          </v-col>
          <v-col v-if="$vuetify.breakpoint.mdAndUp" cols="6">
            <v-btn
              v-for="(item, i) in barItems"
              :key="i"
              :to="{ name: item.name }"
              class="text-capitalize"
              exact
              exact-active-class="accent--text"
              text
              >{{ item.title }}
            </v-btn>

            <!-- v-if accessToken -->
            <v-btn
              v-if="getAuth.username"
              class="text-capitalize"
              exact
              exact-active-class="accent--text"
              text
              @click="goToMyPage"
            >
              {{ getAuth.username }}
            </v-btn>
            <v-btn
              v-if="getAuth.username"
              class="text-capitalize"
              exact
              exact-active-class="accent--text"
              text
              @click="onClickLogout"
              >Logout</v-btn
            >
            <v-btn
              v-else
              class="text-capitalize"
              exact
              exact-active-class="accent--text"
              text
              @click="goToLogin"
              >Login</v-btn
            > 
          </v-col>

          <v-btn
            color="accent"
            class="ma-2"
            elevation="2"
            plain
            small
            @click="chooseColor"
          >
            Color 변경
          </v-btn>
        </v-row>
      </v-container>
    </v-app-bar>

    <v-dialog v-model="getColorDialog" max-width="500px" persistent>
      <v-card class="text-center">
        <v-card-title center>
          <v-row align="center" justify="center">
            <v-col>Custom Theme Color</v-col>
          </v-row>
        </v-card-title>
        <v-card-text>
          <v-row align="center" justify="center">
            <v-col><h4>Accent Color</h4> </v-col>
          </v-row>
          <v-row align="center" justify="center" class="mt-0">
            <v-col cols="12" md="8">
              <v-color-picker
                dot-size="12"
                swatches-max-height="144"
                hide-mode-switch
                mode="hexa"
                v-model="accentColor"
              ></v-color-picker>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-row align="center" justify="center">
            <v-col>
              <v-btn color="primary" text @click="changeColor">
                적용하기
              </v-btn>
            </v-col>
            <v-col>
              <v-btn color="primary" text @click="closeColorDialog">
                닫기
              </v-btn>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from "vuex";

const userStore = "userStore";
export default {
  data: () => ({
    drawer: null,
    colorDialog: false,
    accentColor: "",
    barItems: [
      {
        title: "Home",
        to: "/",
        name: "HomeView",
      },
      {
        title: "Apartment",
        to: "/apt",
        name: "AptView",
      },
      {
        title: "Notice",
        to: "/notice",
        name: "NoticeView",
      },
      {
        title: "QnA",
        to: "/qna",
        name: "QnaView",
      },
    ],
  }),
  created() {
    console.log(this.$vuetify.theme.themes.light.accent);
    this.accentColor = this.$vuetify.theme.themes.light.accent;
    console.log(this.accentColor);

    // const auth = $cookies.get("auth");
    // const validTokenFlag = store.getters["userStore/getValidTokenFlag"];
    // const accessToken = $cookies.get("accessToken");

    // console.log("로그인 처리 전", auth, accessToken);

    // if (auth != null && accessToken) {
    //   console.log("토큰 유효성 체크하러 가자!!!!");
    //   await store.dispatch("userStore/getUser", auth);
    // }
    // console.log(validTokenFlag, auth);
    // if (!validTokenFlag || auth === null) {
    //   alert("로그인이 필요한 페이지입니다..");
    //   // next({ name: "login" });
    //   router.push({ name: "LoginView" });
    // } else {
    //   console.log("로그인 했다!!!!!!!!!!!!!.");
    //   next();
    // }
  },
  computed: {
    ...mapGetters(userStore, ["getColorDialog", "getCustomColor", "getAuth"]),
  },
  methods: {
    // ...mapActions => logout 호출 할 수 있도록
    ...mapMutations(userStore, [
      "openColorDialog",
      "closeColorDialog",
      "SET_CUSTOM_COLOR",
    ]),
    ...mapActions(userStore, ["userLogout"]),
    onClickLogout() {
      console.log(
        "auth",
        this.$cookies.get("auth"),
        "access",
        this.$cookies.get("accessToken"),
        "refresh",
        this.$cookies.get("refreshToken")
      );
      this.userLogout();
      this.$cookies.remove("accessToken");
      this.$cookies.remove("refreshToken");
      this.$cookies.remove("auth");
      console.log(
        "auth",
        this.$cookies.get("auth"),
        "access",
        this.$cookies.get("accessToken"),
        "refresh",
        this.$cookies.get("refreshToken")
      );
    },
    chooseColor() {
      this.accentColor = this.getCustomColor;
      console.log(this.accentColor);
      this.openColorDialog();
    },
    changeColor() {
      let newColor = this.accentColor.substring(0, 7);
      this.SET_CUSTOM_COLOR(newColor);
      localStorage.setItem("customColor", newColor);
      this.$vuetify.theme.themes.light.accent = newColor;
      console.log("change Color => ", localStorage.getItem("customColor"));
      this.closeColorDialog();
    },

    goToMyPage() {
      this.$router.push({ name: "MyPageView" });
    },
    goToLogin() {
      this.$router.push({ name: "LoginView" });
    },
  },
};
</script>

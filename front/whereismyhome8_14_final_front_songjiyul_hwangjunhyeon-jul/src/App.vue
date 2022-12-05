<template>
  <v-app>
    <AppBar></AppBar>

    <v-container
      justify="center"
      align="center"
      class="mt-16"
      style="width: 70%"
    >
      <router-view></router-view>
    </v-container>

    <Footer></Footer>
  </v-app>
</template>

<script>
import { mapMutations, mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "App",

  components: {
    AppBar: () => import("@/components/layout/appbar.vue"),

    Footer: () => import("@/components/layout/footer.vue"),
  },
  data: () => ({}),
  created() {
    console.log("App created color => ", localStorage.getItem("customColor"));
    this.SET_CUSTOM_COLOR(
      localStorage.getItem("customColor")
        ? localStorage.getItem("customColor")
        : this.$vuetify.theme.themes.light.accent
    );
    const refToken = this.$cookies.get("refreshToken");
    if(refToken != null) {
      console.log(this.$cookies.get("auth"));
      this.getUser(this.$cookies.get("auth"));
    } else {
      this.SET_AUTH({
      username: "",
      role: "",
    });
    }

    // this.SET_AUTH();
  },
  methods: {
    ...mapMutations(userStore, ["SET_AUTH", "SET_CUSTOM_COLOR"]),
    ...mapActions(userStore, ["getUser"]),
  },
};
</script>

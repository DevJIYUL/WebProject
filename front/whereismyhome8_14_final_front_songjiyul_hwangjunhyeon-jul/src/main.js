import Vue from "vue";
import App from "@/App.vue";
import vuetify from "@/plugins/vuetify";
import router from "@/router";
import store from "@/store";

import VueCookies from "vue-cookies";

Vue.config.productionTip = false;

// vue-cookies, vuex, vuex-persistedstate  추가
Vue.use(VueCookies);

Vue.$cookies.config("7d");

new Vue({
  vuetify,
  store: store,
  router,
  render: (h) => h(App),
}).$mount("#app");

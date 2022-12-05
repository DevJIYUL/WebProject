import Vue from "vue";
import Vuetify from "vuetify/lib";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-regular-svg-icons";

Vue.component("font-awesome-icon", FontAwesomeIcon); // Register component globally
library.add(fas, faHeart); // Include needed icons
Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    options: {
      customProperties: true,
    },
    themes: {
      light: {
        primary: "#000",
        secondary: "#2fcc6e",
        accent: localStorage.getItem("customColor")
          ? localStorage.getItem("customColor")
          : "#0d7336",
        warning: "#ff3333",
      },
    },
  },
  icons: {},
});

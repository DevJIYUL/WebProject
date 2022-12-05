<template>
  <v-container>
    <search-input></search-input>
    <v-card min-height="40rem">
      <v-container>
        <div v-if="getDongNameResult != ''">
          <v-row class="mt-2" justify="center" align="center">
            <h3 class="mt-2 me-4" :color="getCustomColor">
              {{ getDongNameResult }}
            </h3>
            <div v-if="getAuth == null || !getFavFlag">
              <v-tooltip right color="accent">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    :color="getCustomColor"
                    icon
                    elevation="2"
                    v-bind="attrs"
                    v-on="on"
                    @click="insertFavLoc"
                  >
                    <font-awesome-icon icon="fa-regular fa-heart" />
                  </v-btn>
                </template>
                <span>관심지역 등록</span>
              </v-tooltip>
            </div>
            <div v-else>
              <v-tooltip right color="accent">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    :color="getCustomColor"
                    icon
                    elevation="2"
                    v-bind="attrs"
                    v-on="on"
                    @click="clearFavLoc"
                  >
                    <font-awesome-icon icon="fa-solid fa-heart" />
                  </v-btn>
                </template>
                <span>관심지역 해제</span>
              </v-tooltip>
            </div>
          </v-row>
        </div>
      </v-container>
      <br />
      <v-row>
        <v-col>
          <v-container>
            <apt-list></apt-list>
          </v-container>
        </v-col>
        <v-divider vertical></v-divider>
        <v-col>
          <v-container>
            <apt-detail></apt-detail>
          </v-container>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
import SearchInput from "@/components/apt/SearchInput.vue";
import AptList from "@/components/apt/AptList.vue";
import AptDetail from "@/components/apt/AptDetail.vue";
import Constant from "@/util/Constant";
import { mapGetters, mapMutations, mapActions } from "vuex";
import { bus } from "@/store/js/eventBus.js";

const aptStore = "aptStore";
const dongStore = "dongStore";
const userStore = "userStore";
const favStore = "favStore";

export default {
  name: "AptView",
  components: {
    SearchInput,
    AptList,
    AptDetail,
  },
  data() {
    return {
      favFlag: false,
    };
  },
  created() {
    this.favFlag = false;

    bus.$on("favCheck", async () => {
      console.log("evnetBus -> favCheck");
      console.log(this.getAuth.username, this.getDongCodeResult);
      console.log(this.getFavFlag);
      if (this.getAuth.username != "") {
        await this.checkFavLoc({
          username: this.getAuth.username,
          dongCode: this.getDongCodeResult,
        });
      } else {
        this.setFavFlag(false);
      }
      this.setApt({});
      console.log(this.getFavFlag);
    });
  },
  computed: {
    ...mapGetters(favStore, ["getFavFlag"]),
    ...mapGetters(userStore, ["getAuth", "getCustomColor"]),
    ...mapGetters(aptStore, ["getDongNameResult", "getDongCodeResult"]),
    ...mapGetters(dongStore, ["getDongName"]),
  },
  methods: {
    ...mapMutations(aptStore, [
      Constant.SET_DONGNAME,
      Constant.SET_FAV_FLAG,
      Constant.SET_APT,
    ]),
    ...mapActions(favStore, [
      Constant.CHECK_FAV_LOC,
      Constant.REMOVE_FAV_LOC,
      Constant.ADD_FAV_LOC,
    ]),
    clearFavLoc() {
      console.log({
        username: this.getAuth.username,
        dongCode: this.getDongCodeResult,
      });
      this.removeFavLoc({
        username: this.getAuth.username,
        dongCode: this.getDongCodeResult,
      });
    },
    insertFavLoc() {
      this.addFavLoc({
        username: this.getAuth.username,
        dongCode: this.getDongCodeResult,
      });
    },
  },
};
</script>

<style></style>

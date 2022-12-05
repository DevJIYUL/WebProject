<template>
  <v-container>
    <dong-search></dong-search>

    <v-row justify="center" align="center">
      <v-col cols="4">
        <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          :return-value.sync="date"
          transition="scale-transition"
          offset-y
          max-width="290px"
          min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="date"
              label="Picker in menu"
              prepend-icon="mdi-calendar"
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="date"
            :allowed-dates="allowedDates"
            type="month"
            no-title
            scrollable
          >
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="menu = false"> Cancel </v-btn>
            <v-btn text color="primary" @click="$refs.menu.save(date)"
              >OK</v-btn
            >
          </v-date-picker>
        </v-menu>
      </v-col>

      <v-col cols="4"></v-col>
      <v-col cols="2">
        <v-btn text x-large color="accent" @click="reset">
          <font-awesome-icon icon="fa-solid fa-rotate-left" />
          Reset
        </v-btn>
      </v-col>
      <v-col cols="2">
        <v-btn text x-large color="accent" @click="searchAll">
          <font-awesome-icon icon="fa-solid fa-magnifying-glass-location" />
          Search
        </v-btn>
      </v-col>
    </v-row>
    <hr />
  </v-container>
</template>

<script>
import DongSearch from "@/components/apt/DongSearch";
import Constant from "@/util/Constant";
import { mapGetters, mapActions, mapMutations } from "vuex";
import { bus } from "@/store/js/eventBus.js";

const dongStore = "dongStore";
const aptStore = "aptStore";
const userStore = "userStore";

export default {
  name: "SearchInput",
  components: {
    DongSearch,
  },
  data() {
    return {
      date: new Date().toISOString().substr(0, 7),
      menu: false,
    };
  },
  created() {
    this.date = new Date().toISOString().substr(0, 7);
  },
  computed: {
    ...mapGetters(dongStore, ["getDongCode", "getDongName", "getSidoList"]),
    ...mapGetters(userStore, ["getCustomColor"]),
  },
  methods: {
    ...mapActions(aptStore, [Constant.SELECT_APT_DEAL_LIST]),
    ...mapMutations(aptStore, [Constant.SET_DONGNAME]),
    allowedDates(val) {
      console.log(this.date);
      return val <= new Date().toISOString().substr(0, 7);
    },
    reset() {
      bus.$emit("reset");
      this.date = new Date().toISOString().substr(0, 7);
    },
    async searchAll() {
      if (this.getDongName != "") {
        console.log("Search Event Occur!! => ", this.getDongName, this.date);
        console.log({
          dongCode: this.getDongCode,
          dongName: this.getDongName,
          dealDate: this.date,
        });
        console.log(
          "SearchInput Click Search => ",
          this.getDongName
          // this.getDongCodeResult
        );
        await this.selectAptDealList({
          dongCode: this.getDongCode,
          dongName: this.getDongName,
          dealDate: this.date,
        });
        await bus.$emit("favCheck");
      } else {
        alert("검색어를 채워주세요😀");
      }
    },
  },
};
</script>

<style></style>

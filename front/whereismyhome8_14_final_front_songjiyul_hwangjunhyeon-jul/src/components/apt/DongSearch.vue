<template>
  <div>
    <v-container fluid>
      <v-row>
        <v-col class="v-flex" cols="4">
          <v-select
            :items="getSidoList"
            label="시"
            v-model="sidoName"
          ></v-select>
        </v-col>
        <v-col class="v-flex" cols="4">
          <v-select
            v-model="gugunName"
            :items="getGuGunList"
            no-data-text="도 or 시를 선택해주세요"
            label="구/군"
          ></v-select>
        </v-col>
        <v-col class="v-flex" cols="4">
          <v-select
            v-model="dongInfo"
            no-data-text="구 or 군을 선택해주세요"
            :items="getDongList"
            item-text="dongName"
            item-value="dongCode"
            label="동"
            return-object
            @change="clickDong"
          ></v-select>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
const dongStore = "dongStore";
import { mapActions, mapGetters, mapMutations } from "vuex";
import Constant from "@/util/Constant";
import { bus } from "@/store/js/eventBus.js";

export default {
  name: "DongSearch",
  data() {
    return {
      sidoName: "",
      gugunName: "",
      // dongName: "",
      // dongCode: "",
      dongInfo: {
        sidoName: "",
        gugunName: "",
        dongName: "",
        dongCode: "",
      },
    };
  },
  created() {
    this.getSido();

    bus.$on("reset", () => {
      this.sidoName = "";
      this.gugunName = "";
      this.dongInfo = {
        sidoName: "",
        gugunName: "",
        dongName: "",
        dongCode: "",
      };
      // this.dongName = "";
      this.setSidoList(this.getSidoList);
      this.putDongCode("");
    });
  },
  computed: {
    ...mapGetters(dongStore, ["getSidoList", "getGuGunList", "getDongList"]),
  },
  watch: {
    sidoName() {
      console.log("watch : " + this.sidoName);
      this.getGuGun({ sidoName: this.sidoName, gugunName: this.gugunName });
      this.gugunName = "";
      // this.dongName = "";
    },
    gugunName() {
      console.log("watch : " + this.gugunName);
      this.getDong({ sidoName: this.sidoName, gugunName: this.gugunName });
      // this.dongName = "";
      // this.dongCode = "";
    },
    // dongCode() {
    //   console.log("watch : " + this.dongCode);
    //   this.putDongCode(this.dongCode);
    //   this.setDongName(this.dongName);
    // },
  },
  methods: {
    ...mapActions(dongStore, [
      Constant.GET_SIDO,
      Constant.GET_GUGUN,
      Constant.GET_DONG,
    ]),
    ...mapMutations(dongStore, [
      Constant.PUT_DONGCODE,
      Constant.SET_DONGNAME,
      Constant.SET_SIDO_LIST,
    ]),
    async clickDong() {
      console.log(this.dongInfo.dongCode, this.dongInfo.dongName);
      await this.putDongCode(this.dongInfo.dongCode);
      await this.setDongName(this.dongInfo.dongName);
    },
  },
};
</script>

<style></style>

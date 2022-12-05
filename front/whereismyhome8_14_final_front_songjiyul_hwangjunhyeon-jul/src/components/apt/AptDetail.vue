<template>
  <v-card >
    <v-container v-if="getApt.apartmentName != null" justify="center" align="center">
      <v-btn color="accent" depressed>
        <!-- 집 값 그래프 모달창 띄우기 -> vue-chart.js  공부 후 구현하기 -->
        <font-awesome-icon icon="fa-solid fa-chart-line" />
        집 값 변동
      </v-btn>
      <!-- lng, lat 이용해 지도 주변 키워드에 해당하는 장소들 체크 구현하기 -->

      <h3 class="text-h4 font-weight-bold pt-3">
        {{ getApt.apartmentName }}
      </h3>
      <p class="text-h6 font-weight-regular pt-3 text--secondary">
        {{ getApt.dongName }}, {{ getApt.roadName }} - {{ getApt.jibun }}
      </p>
      <v-row justify="center" align="start">
        <v-col> 건축 연도 : {{ getApt.buildYear }}</v-col>
      </v-row>
      <v-row justify="center" align="start">
        <v-col>
          거래 일자 : {{ getApt.dealYear }} - {{ getApt.dealMonth }} -
          {{ getApt.dealDay }}</v-col
        >
      </v-row>
      <v-row justify="center" align="start">
        <v-col cols="4"> 층 : {{ getApt.floor }}</v-col>
        <v-col cols="4"> 면적 : {{ getApt.area }}</v-col>
        <v-col cols="4"></v-col>
      </v-row>
      <v-row justify="center" align="start">
        <v-col>
          거래 금액 :
          {{
            (parseInt(getApt.dealAmount.replace(",", "")) * 10000) | price
          }}</v-col
        >
      </v-row>

      <div class="d-flex align-center mt-5">
        <v-avatar color="accent" size="36">
          <v-icon dark>mdi-home</v-icon>
        </v-avatar>

        <div class="pl-2">Select Homes</div>
      </div>
    </v-container>
  </v-card>
</template>

<script>
// import Constant from "@/util/Constant";
import { mapGetters } from "vuex";

const userStore = "userStore";
const aptStore = "aptStore";
export default {
  name: "AptDetail",
  computed: {
    ...mapGetters(userStore, ["getCustomColor"]),
    ...mapGetters(aptStore, ["getApt"]),
  },
  filters: {
    price(val) {
      if (!val) return val;
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + " 원";
    },
  },
  methods: {},
};
</script>

<style></style>

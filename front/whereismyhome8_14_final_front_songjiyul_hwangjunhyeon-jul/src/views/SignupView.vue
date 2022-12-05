<template>
  <div>
    <v-container style="width: 65%">
      <v-form v-model="valid">
        <v-row>
          <v-col>
            <v-col cols="12">
              <v-text-field
                v-model="username"
                :counter="16"
                label="ID"
                required
              >
              </v-text-field>
            </v-col>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-col cols="12">
            <v-text-field
            v-model="password"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.required, rules.min]"
            :type="show1 ? 'text' : 'password'"
            name="input-10-1"
            label="Normal with hint text"
            hint="Password"
            counter
            outlined
            @click:append="show1 = !show1"
          ></v-text-field>
            </v-col>
          </v-col>
        </v-row>

        <v-row>
          <v-col>
            <v-col cols="12">
              <v-text-field
                v-model="email"
                align-self-start
                label="E-mail"
                required
              >
              </v-text-field>
            </v-col>
          </v-col>
          <v-col>
            <v-col class="v-flex" cols="12">
              <v-select
                v-model="domain"
                :items="domains"
                label="domain"
              ></v-select>
            </v-col>
          </v-col>
        </v-row>

        <!-- v-row 에  -->

        <dong-search></dong-search>
        <v-col cols="12">
          <v-select
            v-model="value"
            :items="keywords"
            item-text="keyword"
            item-value="code"
            attach
            chips
            label="관심 키워드"
            multiple
          ></v-select>
        </v-col>
      </v-form>

      <v-row>
        <v-col cols="2">
          <!-- class ms- : 왼쪽   me- : 오른쪽 -->
          <v-btn elevation="2" outlined rounded large color="red"
            >다 지우기</v-btn
          >
        </v-col>
        <v-col cols="2">
          <v-btn
            elevation="2"
            outlined
            rounded
            color="accent"
            large
            @click="registUser()"
            >회원 가입</v-btn
          >
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
const userStore = "userStore";
const dongStore = "dongStore";

import { mapActions, mapGetters } from "vuex";
import Constant from "@/util/Constant";
import DongSearch from "../components/apt/DongSearch.vue";
export default {
  name: "SignupView",
  components: {
    DongSearch,
  },
  computed: {
    ...mapGetters(dongStore, [
      "getSidoList",
      "getGuGunList",
      "getDongList",
      "getDongCode",
    ]),
  },
  created() {
    console.log(this.domain);
  },
  data() {
    return {
      username: "",
      password: "",
      show1: false,
      email: "",
      domain: "",
      residence: "",
      domains: ["naver.com", "gmail.com", "ssafy.com", "kakao.com"],
      rules: {
          required: value => !!value || 'Required.',
          min: v => v.length >= 4 || 'Min 4 characters',
          emailMatch: () => (`The email and password you entered don't match`),
        },
      keywords: [
        {
          keyword: "학원",
          code: "AC5",
        },
        {
          keyword: "숙박",
          code: "AD5",
        },
        {
          keyword: "중개업소",
          code: "AG2",
        },
        {
          keyword: "관광명소",
          code: "AT4",
        },
        {
          keyword: "은행",
          code: "BK9",
        },
        {
          keyword: "카페",
          code: "CE7",
        },
        {
          keyword: "편의점",
          code: "CS2",
        },
        {
          keyword: "문화시설",
          code: "CT1",
        },
        {
          keyword: "음식점",
          code: "FD6",
        },
        {
          keyword: "병원",
          code: "HP8",
        },
        {
          keyword: "대형마트",
          code: "MT1",
        },
        {
          keyword: "주유소",
          code: "OL7",
        },
        {
          keyword: "주차장",
          code: "PK6",
        },
        {
          keyword: "약국",
          code: "PM9",
        },
        {
          keyword: "공공기관",
          code: "PO3",
        },
        {
          keyword: "어린이집",
          code: "PS3",
        },
        {
          keyword: "학교",
          code: "SC4",
        },
        {
          keyword: "지하철역",
          code: "SW8",
        },
      ],
      value: [],
      FavoritecateDTOs: [],
    };
  },
  methods: {
    ...mapActions(userStore, [Constant.POST_USER]),
    async registUser() {
      if (!this.getDongCode) {
        alert("거주지를 입력해주세요😉");
        return;
      }
      console.log("Register User Before Axios => ", this.getDongCode);
      await this.postUser({
        username: this.username,
        password: this.password,
        email: this.email,
        domain: this.domain,
        residence: this.getDongCode,
        favoritecateDTOs: this.value,
      });
    },
  },
};
</script>

<style></style>

<template>
  <div>
    <v-row class="mt-8 mb-8" justify="center">
      <v-btn
        class="me-3"
        @click="[selectByUsername(getAuth.username), goToList()]"
      >
        <font-awesome-icon :color="getCustomColor" icon="fa-solid fa-neuter" />
        내 질문
      </v-btn>
      <v-btn class="me-3 ms-3" @click="[selectAllQna(), goToList()]">
        <font-awesome-icon
          :color="getCustomColor"
          icon="fa-solid fa-reply-all"
        />
        전체보기
      </v-btn>
      <router-link
        :to="{ name: 'QnaRegisterView' }"
        style="text-decoration: none"
      >
        <v-btn class="btn ms-3">
          <font-awesome-icon
            :color="getCustomColor"
            icon="fa-solid fa-question"
          />
          질문등록
        </v-btn>
      </router-link>
    </v-row>
    <router-view></router-view>
  </div>
</template>

<script>
import QnaList from "../components/qna/QnaList.vue";
import Constant from "@/util/Constant";

import { mapGetters, mapActions } from "vuex";

const qnaStore = "qnaStore";
const userStore = "userStore";
export default {
  name: "QnaView",
  components: { QnaList },

  data() {
    return {};
  },
  created() {
    this.selectAllQna();
  },
  computed: {
    ...mapGetters(userStore, ["getAuth", "getCustomColor"]),
  },
  // components: {
  //   QnaDetailModal,
  // },
  methods: {
    ...mapActions(qnaStore, [
      Constant.SELECT_BY_USERNAME,
      Constant.SELECT_QNA_LIST,
    ]),
    goToList() {
      this.$router.push({ name: "QnaList" });
    },
  },
};
</script>

<style></style>

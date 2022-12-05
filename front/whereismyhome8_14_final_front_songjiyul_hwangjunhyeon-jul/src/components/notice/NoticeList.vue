<template>
  <div style="min-height: 41rem">
    <v-data-table
      class="elevation-1"
      :items="getNoticeList"
      :headers="headers"
      :items-per-page="itemPerPage"
      :page.sync="page"
      hide-default-footer
    >

      <template v-slot:item="{ item }">
        <tr @click="goToDetail(item.noticeNo)">
          <td align="center" justify="center">{{ item.noticeNo }}</td>
          <td align="center" justify="center">{{ item.title }}</td>
          <td align="center" justify="center">{{ item.wdate }}</td>
          <td align="center" justify="center">{{ item.hit }}</td>
          <td align="center" justify="center">
            <font-awesome-icon
              v-if="item.fileInfos != null"
              :color="getCustomColor"
              icon="fa-solid fa-paperclip"
            />
            <font-awesome-icon
              v-else
              :color="$vuetify.theme.themes.light.warning"
              icon="fa-solid fa-xmark"
            />
          </td>
        </tr>
      </template>
    </v-data-table>
    <v-row v-if="getAuth.role == 'ROLE_ADMIN'" class="mt-5 me-5" align="center" justify="center">
      <v-tooltip right color="accent">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            :color="getCustomColor"
            elevation="2"
            v-bind="attrs"
            v-on="on"
            @click="goToWrite"
          >
            <font-awesome-icon icon="fa-solid fa-feather-pointed" />
          </v-btn>
        </template>
        <span>공지사항 글쓰기</span>
      </v-tooltip>
    </v-row>
    <v-pagination
      class="mt-5"
      circle
      v-model="page"
      :total-visible="9"
      :length="parseInt(getNoticeCount / itemPerPage) + 1"
    ></v-pagination>
    <!-- Table -->
  </div>
</template>

<script>
const noticeStore = "noticeStore";
const userStore = "userStore";

import { mapActions, mapGetters, mapMutations } from "vuex";
import Constant from "@/util/Constant";
import NoticeDetail from "@/components/notice/NoticeDetail.vue";
export default {
  name: "NoticeList",
  components: { NoticeDetail },
  computed: {
    ...mapGetters(noticeStore, ["getNoticeList", "getNoticeCount"]),
    ...mapGetters(userStore, ["getAuth"]),
  },
  created() {
    this.getNotice();
  },
  methods: {
    ...mapActions(noticeStore, [
      Constant.GET_NOTICE,
      Constant.GET_NOTICE_DETAIL,
    ]),
    // ...mapMutations(noticeStore,[Constant.SET_DETAIL_NO]),
    goToDetail(no) {
      console.log("이동 => ", no);
      this.getNoticeDetail(no);
      this.$router.push({ name: "NoticeDetail"});
    },
    goToWrite() {
      this.$router.push({ name: "NoticeWrite" });
    }
  },
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemPerPage: 10,
      headers: [
        {
          text: "글 번호",
          value: "id",
          sortable: false,
          width: "10%",
          align: "center",
        },

        {
          text: "제목",
          value: "title",
          sortable: false,
          width: "30%",
          align: "center",
        },
        {
          text: "등록일자",
          value: "wdate",
          sortable: false,
          width: "30%",
          align: "center",
        },
        {
          text: "조회수",
          value: "hit",
          sortable: false,
          width: "10%",
          align: "center",
        },
        {
          text: "파일여부",
          value: "fileInfos",
          sortable: false,
          width: "15%",
          align: "center",
        },
      ],
    };
  },
};
</script>

<style></style>

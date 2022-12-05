<template>
  <div>
    <v-data-table
      class="elevation-1"
      :items="getQnaList"
      :headers="headers"
      :items-per-page="itemPerPage"
      :page.sync="page"
      hide-default-footer
      no-data-text="검색 결과가 없습니다😥"
      @dblclick:row="selectQna"
    >
      <template v-slot:item="{ item }">
        <tr @dblclick="openQnaDialog(item.id)">
          <td align="center" justify="center">
            <font-awesome-icon
              v-if="item.privateFlag"
              :color="$vuetify.theme.themes.light.warning"
              icon="fa-solid fa-lock"
            />
            <font-awesome-icon
              v-else
              :color="getCustomColor"
              icon="fa-solid fa-lock-open"
            />
          </td>
          <td align="center" justify="center">{{ item.id }}</td>
          <td align="center" justify="center">{{ item.username }}</td>
          <td align="center" justify="center">{{ item.title }}</td>
          <td align="center" justify="center">{{ item.regDate }}</td>
          <td align="center" justify="center">
            <font-awesome-icon
              v-if="item.answeredFlag"
              :color="getCustomColor"
              icon="fa-solid fa-check-double"
            />
            <font-awesome-icon
              v-else
              :color="$vuetify.theme.themes.light.warning"
              icon="fa-solid fa-xmark"
            />
          </td>
          <td v-if="getAuth.role == 'ROLE_ADMIN'" align="center" justify="center">
            <v-tooltip right :color="$vuetify.theme.themes.light.warning">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  :color="$vuetify.theme.themes.light.warning"
                  icon
                  elevation="2"
                  x-large
                  v-bind="attrs"
                  v-on="on"
                  @click="removeQna({username: getAuth.username, id: item.id})"
                >
                  <font-awesome-icon
                    class="trash-hover"
                    icon="fa-solid fa-trash-can"
                  />
                </v-btn>
              </template>
              <span>DELETE</span>
            </v-tooltip>
          </td>
        </tr>
      </template>
    </v-data-table>

    <v-pagination
      class="mt-5"
      circle
      v-model="page"
      :total-visible="9"
      :length="parseInt(getQnaCount / itemPerPage) + 1"
    ></v-pagination>
    <!-- Table -->
    <qna-detail-modal></qna-detail-modal>
  </div>
</template>

<script>
import QnaDetailModal from "@/components/qna/QnaDetailModal";
import Constant from "@/util/Constant";

import { mapGetters, mapActions } from "vuex";

const qnaStore = "qnaStore";
const userStore = "userStore";

export default {
  components: {
    QnaDetailModal,
  },
  name: "QnaList",

  // created() {
  //   this.selectAllQna();
  // },
  computed: {
    ...mapGetters(qnaStore, ["getQnaList", "getQnaDialog", "getQnaCount"]),
    ...mapGetters(userStore, ["getAuth", "getCustomColor"]),
  },
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemPerPage: 10,
      headers: [
        {
          text: "🔑",
          value: "Private",
          sortable: false,
          width: "5%",
          align: "center",
        },
        {
          text: "글 번호",
          value: "id",
          sortable: false,
          width: "10%",
          align: "center",
        },
        {
          text: "작성자",
          value: "username",
          sortable: false,
          width: "15%",
          align: "center",
        },
        {
          text: "제목",
          value: "title",
          sortable: false,
          width: "45%",
          align: "center",
        },
        {
          text: "등록일자",
          value: "regDate",
          sortable: false,
          width: "15%",
          align: "center",
        },
        {
          text: "답변",
          value: "Answered",
          sortable: false,
          width: "10%",
          align: "center",
        },
      ],
    };
  },
  methods: {
    ...mapActions(qnaStore, [
      Constant.SELECT_QNA_LIST,
      Constant.OPEN_QNA_DIALOG,
      Constant.SELECT_QNA,
      Constant.REMOVE_QNA,
    ]),
  },
};
</script>

<style scoped></style>

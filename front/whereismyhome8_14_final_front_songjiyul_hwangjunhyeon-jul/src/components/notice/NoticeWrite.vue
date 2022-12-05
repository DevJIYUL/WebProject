<template>
  <v-container fluid style="min-height: 41rem">
    <!-- <v-text-field
      name="input-7-1"
      outlined
      label="글 번호"
      readonly
      v-model="getNoticeDetailJson.noticeNo"
    ></v-text-field> -->
    <!-- <v-text-field
      name="input-7-1"
      outlined
      label="글 작성 날짜"
      readonly
      v-model="getNoticeDetailJson.wdate"
    ></v-text-field> -->
    <v-text-field
      name="input-7-1"
      outlined
      label="title"
      clearable
      auto-grow
      v-model.lazy="title"
    ></v-text-field>
    <v-textarea
      name="input-7-1"
      outlined
      label="content"
      clearable
      auto-grow
      v-model.lazy="content"
    ></v-textarea>
    <v-file-input
      label="파일 올리기"
      multiple
      truncate-length="20"
      v-model="tempShow"
      @change="temp(tempShow)"
    ></v-file-input>
    <ul v-for="(index, i) in addInfos" :key="i">
      <li>
        <v-row>
          <p>
            {{ index.name }}
          </p>
          <!-- <p v-if="index.originalFile">
                    {{index.originalFile}}
                </p>
                <p v-else>
                    {{index.name}}
                </p> -->
          <label @click="deleteFile(index)"> ❌ </label>
        </v-row>
      </li>
    </ul>

    <v-row>
      <v-col cols="2">
        <v-btn color="accent" @click="gotoList()">돌아가기</v-btn>
      </v-col>
      <v-col cols="2">
        <v-btn color="accent" @click="postNotice()">공지 쓰기</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
const noticeStore = "noticeStore";
import Constant from "@/util/Constant";
import { mapActions } from "vuex";
export default {
  name: "NoticeWrite",
  data() {
    return {
      title: "",
      content: "",
      // deleteInfos:[],
      addInfos: [],
      // tempInfos:[],
      tempShow: [],
      // modifyFlag: false,
    };
  },
  computed: {},
  methods: {
    ...mapActions(noticeStore, [Constant.POST_N]),
    postNotice() {
      const formData = new FormData();
      formData.append("title", this.title);
      formData.append("content", this.content);
      for (let index = 0; index < this.addInfos.length; index++) {
        formData.append("addInfos", this.addInfos[index]);
      }
      this.postN(formData);
      this.$router.push({ name: "NoticeList" });
    },
    gotoList() {
      this.$router.push({ name: "NoticeList" });
    },
    temp(data) {
      for (let index = 0; index < data.length; index++) {
        this.addInfos.push(data[index]);
      }
      console.log(this.addInfos);
      this.tempShow = [];
    },
    deleteFile(item) {
      var p = -1;
      for (let index = 0; index < this.addInfos.length; index++) {
        if (this.addInfos[index].name == item.name) {
          p = index;
          break;
        }
      }
      if (p >= 0) {
        this.addInfos.splice(p, 1);
      }
    },
  },
};
</script>

<style></style>

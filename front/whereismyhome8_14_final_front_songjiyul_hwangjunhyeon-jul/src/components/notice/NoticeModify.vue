<template>
  <v-container fluid>
    <v-text-field
      name="input-7-1"
      outlined
      label="글 번호"
      readonly
      v-model="getNoticeDetailJson.noticeNo"
    ></v-text-field>
    <v-text-field
      name="input-7-1"
      outlined
      label="글 작성 날짜"
      readonly
      v-model="getNoticeDetailJson.wdate"
    ></v-text-field>
    <v-text-field
      name="input-7-1"
      outlined
      label="title"
      clearable
      auto-grow
      v-model.lazy="modifyTitle"
    ></v-text-field>
    <v-textarea
      name="input-7-1"
      outlined
      label="content"
      clearable
      auto-grow
      v-model.lazy="modifyContent"
    ></v-textarea>
    <v-file-input
      label="파일 올리기"
      multiple
      truncate-length="20"
      v-model="tempInfos"
      @change="temp(tempInfos)"
    ></v-file-input>
    <ul v-for="(index, i) in tempShow" :key="i">
      <li>
        <v-row>
          <p>
            {{ index }} 
            <v-tooltip right :color="$vuetify.theme.themes.light.warning">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  :color="$vuetify.theme.themes.light.warning"
                  elevation="2"
                  v-bind="attrs"
                  v-on="on"
                  icon
                  @click="deleteFile(index)"
                >
                  <font-awesome-icon  icon="fa-solid fa-xmark" />
                </v-btn>
              </template>
              <span>파일 취소</span>
            </v-tooltip>
          </p>
          <!-- <p v-if="index.originalFile">
                    {{index.originalFile}}
                </p>
                <p v-else>
                    {{index.name}}
                </p> -->
          <label> </label> 
        </v-row>
      </li>
    </ul>
    <v-row>
      <v-col cols="2">
        <v-btn color="accent" @click="gotoDetail(getNoticeDetailJson.noticeNo)"
          >돌아가기</v-btn
        >
      </v-col>
      <v-col cols="2">
        <v-btn color="accent" @click="setDetail()">수정하기</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
const noticeStore = "noticeStore";
import Constant from "@/util/Constant";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "NoticeModify",
  data() {
    return {
      modifyTitle: "",
      modifyContent: "",
      deleteInfos: [],
      addInfos: [],
      tempInfos: [],
      tempShow: [],
      modifyFlag: false,
    };
  },
  created() {
    console.log("created() 실행");
    this.modifyTitle = this.getNoticeDetailJson.title;
    this.modifyContent = this.getNoticeDetailJson.content;
    console.log(this.modifyTitle);
    console.log(this.modifyContent);
    for (
      let index = 0;
      index < this.getNoticeDetailJson.fileInfos.length;
      index++
    ) {
      // console.log(this.getNoticeDetailJson.fileInfos[index].originalFile);
      this.tempShow.push(
        this.getNoticeDetailJson.fileInfos[index].originalFile
      );
    }
    // console.log("tempshow : "+this.tempShow);
  },
  computed: {
    tempShow() {},
    addInfos() {},
    modifyTitle() {},
    modifyContent() {},
    ...mapGetters(noticeStore, ["getNoticeDetailJson"]),
  },
  methods: {
    deleteFile(deleteItem) {
      console.log(deleteItem);
      // var deleteName = "";
      // if(deleteItem.originalFile){
      //     deleteName = deleteItem.originalFile;
      // }else{
      //     deleteName = deleteItem.name;
      // }
      // console.log(deleteName);
      console.log("추가된 데이터에 있는지 비교", this.addInfos);
      var q = -1;
      for (let index = 0; index < this.addInfos.length; index++) {
        console.log(deleteItem, this.addInfos[index].name);
        if (deleteItem == this.addInfos[index].name) {
          console.log("지워질 파일 존재함 ", deleteItem);
          q = index;
        }
      }
      console.log("q => " + q);
      if (q >= 0) {
        console.log("추가된 데이터에 있습니다.");
        this.addInfos.splice(q, 1);
        var tempdeleteidx = -1;
        for (let index = 0; index < this.tempShow.length; index++) {
          if (deleteItem == this.tempShow[index]) {
            console.log("지워질 파일 존재함 ", deleteItem);
            tempdeleteidx = index;
          }
        }
        this.tempShow.splice(tempdeleteidx, 1);
      } else {
        var p = -1;
        for (let index = 0; index < this.tempShow.length; index++) {
          // console.log(this.getNoticeDetailJson.fileInfos[index]);
          if (deleteItem == this.tempShow[index]) {
            console.log("지워질 파일 존재함 ", deleteItem);
            p = index;
          }
        }
        // var del = false;
        // for (let index = 0; index < this.deleteInfos.length; index++) {
        //     if(deleteName==deleteItem.saveFile){
        //         del = true;
        //     }
        // };
        this.deleteInfos.push(deleteItem);
        this.tempShow.splice(p, 1);
        console.log("지워짐");
      }

      console.log("지워질 데이터", this.deleteInfos);
      console.log(this.tempShow);
    },
    gotoDetail(no) {
      console.log(no);
      this.$router.push({ name: "NoticeDetail", params: { num: no } });
    },
    temp(data) {
      console.log("data", data);
      for (let index = 0; index < this.tempInfos.length; index++) {
        console.log("tempinfos 인덱스", this.tempInfos[index]);
        this.tempShow.push(this.tempInfos[index].name);
        this.addInfos.push(this.tempInfos[index]);
      }
      this.tempInfos = [];
      console.log("지워질 데이터", this.deleteInfos);
      console.log("추가될 데이터", this.addInfos);
      console.log("화면에 보여질 것", this.tempShow);
    },
    setDetail() {
      console.log("지워질 데이터", this.deleteInfos);
      console.log("추가될 데이터", this.addInfos);
      console.log("화면에 보여질 것", this.tempShow);
      console.log("수정할 title", this.modifyTitle);
      console.log("수정할 content", this.modifyContent);
      const formData = new FormData();
      formData.append("noticeNo", this.getNoticeDetailJson.noticeNo);
      formData.append("title", this.modifyTitle);
      formData.append("content", this.modifyContent);
      formData.append("deleteInfos", this.deleteInfos);
      // for (let index = 0; index < this.deleteInfos.length; index++) {
      //     formData.append("deleteInfos",this.deleteInfos[index]);
      // }
      for (let index = 0; index < this.addInfos.length; index++) {
        formData.append("addInfos", this.addInfos[index]);
      }
      // formData.append("file",this.addInfos);
      this.modifyNotice(formData);
      this.$router.push({ name: "NoticeDetail", params: { num: no } });
    },
    ...mapActions(noticeStore, [Constant.MODIFY_NOTICE]),
  },
};
</script>

<style></style>

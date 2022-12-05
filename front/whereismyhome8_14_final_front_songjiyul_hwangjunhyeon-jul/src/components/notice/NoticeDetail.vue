<template>
  <div style="min-height: 41rem">
    <v-row>
      <v-col cols="12" lg="12" xl="8">
        <div>
          <div>
            <v-card flat color="transparent">
              <v-card-text>
                
                <div class="text-h4 font-weight-bold primary--text pt-4">
                  <h4>[공지] {{ getNoticeDetailJson.title }}</h4>
                </div>
                

                <div class="d-flex align-center justify-space-between">
                  <div class="d-flex align-center">
                    <v-avatar :color="getCustomColor" size="36">
                      <v-icon dark>mdi-feather</v-icon>
                    </v-avatar>

                    <div class="pl-2 text-body-1">
                      관리자 {{ getNoticeDetailJson.wdate }}
                    </div>
                  </div>

                  <div class="d-flex align-center">
                    <div>
                      <v-chip small color="transparent">
                        <v-icon left>mdi-eye</v-icon
                        >{{ getNoticeDetailJson.hit }}
                      </v-chip>
                    </div>
                  </div>
                </div>

                <v-divider class="my-4"></v-divider>

                <div style="min-height: 20rem">
                  <p class="text-subtitle-1 primary--text font-weight-medium">
                    {{ getNoticeDetailJson.content }}
                  </p>
                </div>

                <div>
                  <v-row>
                    <v-col cols="3">
                      <v-btn color="accent" @click="gotoList()"
                        >목록으로 가기</v-btn
                      >
                    </v-col>
                    <v-col cols="3" v-if="getAuth.role == 'ROLE_ADMIN'">
                      <v-btn color="accent" @click="gotoModify()"
                        >수정하기</v-btn
                      >
                    </v-col>
                  </v-row>
                  <v-divider class="my-4"></v-divider>
                  [다운로드]
                <div
                  v-for="(index, i) in getNoticeDetailJson.fileInfos"
                  :key="i"
                >
                  <a @click="filedownload(index)">{{ index.originalFile }}</a>
                </div>
                </div>
              </v-card-text>
            </v-card>
          </div>
        </div>
      </v-col>

      <v-col>
        <div>
          <siderbar />
        </div>
      </v-col>
    </v-row>
  </div>
</template>
<script>
const noticeStore = "noticeStore";
const userStore = "userStore";
import Constant from "@/util/Constant";
import { mapActions, mapGetters } from "vuex";
export default {
  name: "NoticeDetail",
  computed: {
    ...mapGetters(userStore, ["getAuth", "getCustomColor"]),
    ...mapGetters(noticeStore, ["getNoticeDetailJson"]),
  },
  methods: {
    ...mapActions(noticeStore, [Constant.GET_NOTICE_DETAIL, Constant.DOWNLOAD]),
    gotoList() {
      this.$router.push({ name: "NoticeView" });
    },
    gotoModify() {
      this.$router.push({ name: "NoticeModify" });
    },
    filedownload(item) {
      console.log("item : ", item);
      this.download({
        saveFolder: item.saveFolder,
        originalFile: item.originalFile,
        saveFile: item.saveFile,
      });
      // const Formdata = new FormData();
      // Formdata.append()
    },
  },
  data() {
    return {
      no: 0,
    };
  },
};
</script>

<style></style>

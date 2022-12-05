<template>
  <v-dialog v-model="getQnaDialog" max-width="80%" persistent>
    <!-- {{ getQna }}
    
    🎇 modifyQna, registerAnswer 마무리 필요.
    modal에서 
    수정 버튼 클릭 시 title, content input 으로 변경 [v-if]

    등록 버튼 클릭 시  미응답 부분에 textarea로 만들기 [v-if]
    
     -->
    <v-card min-height="30rem">
      <v-container style="width: 75%">
        <v-row justify="center">
          <v-subheader>Q & A</v-subheader>
          <v-expansion-panels focusable>
            <v-expansion-panel>
              <v-expansion-panel-header>
                <v-row align="center" class="spacer" no-gutters>
                  <v-col cols="1">
                    <font-awesome-icon
                      v-if="getQna.privateFlag"
                      :color="$vuetify.theme.themes.light.warning"
                      icon="fa-solid fa-lock"
                    />
                    <font-awesome-icon
                      v-else
                      :color="getCustomColor"
                      icon="fa-solid fa-lock-open"
                    />
                  </v-col>

                  <v-col cols="2" class="hidden-xs-only">
                    <strong v-html="getQna.username"></strong>
                  </v-col>

                  <v-col class="text-no-wrap" cols="6">
                    <div>
                      <strong v-html="getQna.title"> </strong>
                    </div>
                  </v-col>

                  <v-col
                    cols="2"
                    class="grey--text text-truncate hidden-sm-and-down"
                  >
                    &mdash;
                    {{ getQna.regDate }}
                  </v-col>
                  <v-col cols="1">
                    <font-awesome-icon
                      v-if="getQna.answeredFlag"
                      :color="getCustomColor"
                      icon="fa-solid fa-check-double"
                    />
                    <font-awesome-icon
                      v-else
                      :color="$vuetify.theme.themes.light.warning"
                      icon="fa-solid fa-xmark"
                    />
                  </v-col>
                </v-row>
              </v-expansion-panel-header>

              <v-expansion-panel-content>
                <v-divider></v-divider>

                <v-card-text>
                  <div v-if="modifyClick">
                    <v-text-field
                      label="변경할 제목"
                      hide-details="auto"
                      v-model="title"
                    ></v-text-field>
                    <br />
                    <v-textarea
                      solo
                      name="input-7-4"
                      label="Answer"
                      prepend-icon="mdi-comment"
                      no-resize
                      v-model="content"
                    ></v-textarea>
                  </div>
                  <v-textarea
                    v-else
                    solo
                    name="input-7-4"
                    label="Answer"
                    prepend-icon="mdi-comment"
                    no-resize
                    readonly
                    v-model="getQna.content"
                  ></v-textarea>
                </v-card-text>
                <br />
                <hr />
                <br />
                <h4 stlye="color: accent">답변</h4>
                <br />
                <!-- 답변 수정하기 -->
                <div v-if="answerClick">
                  <v-textarea
                    v-if="getQna.answeredFlag"
                    solo
                    name="input-7-4"
                    prepend-icon="mdi-comment"
                    no-resize
                    v-model="answer"
                  ></v-textarea>
                  <v-textarea
                    v-else
                    solo
                    placeholder="답변을 달아주세요"
                    name="input-7-4"
                    prepend-icon="mdi-comment"
                    no-resize
                    v-model="answer"
                  ></v-textarea>
                </div>
                <!-- 답변 보기만하기 -->
                <div v-else>
                  <v-textarea
                    v-if="!getQna.answeredFlag"
                    solo
                    name="input-7-4"
                    label="Answer"
                    prepend-icon="mdi-comment"
                    no-resize
                    readonly
                    value="답변 대기"
                    :color="getCustomColor"
                  ></v-textarea>
                  <v-textarea
                    v-else-if="
                      getAuth.username != getQna.username && getQna.privateFlag
                    "
                    solo
                    name="input-7-4"
                    label="Answer"
                    prepend-icon="mdi-comment"
                    no-resize
                    readonly
                    value="비밀글입니다."
                    :color="$vuetify.theme.themes.light.warning"
                  ></v-textarea>
                  <v-textarea
                    v-else
                    solo
                    name="input-7-4"
                    label="Answer"
                    prepend-icon="mdi-comment"
                    no-resize
                    readonly
                    v-model="getQna.answer"
                  ></v-textarea>
                </div>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-row>
        <v-row>
          <v-col
            v-if="getAuth.role == 'ROLE_USER'"
            align="center"
            justify="center"
          >
            <v-btn
              v-if="
                getAuth.username == getQna.username &&
                !modifyClick &&
                !getQna.answeredFlag
              "
              color="primary"
              text
              @click="showModify"
            >
              <font-awesome-icon icon="fa-solid fa-eraser" />
              수정
            </v-btn>
            <v-btn
              v-if="
                getAuth.username == getQna.username &&
                modifyClick &&
                !getQna.answeredFlag
              "
              color="primary"
              text
              @click="modify"
            >
              <font-awesome-icon icon="fa-solid fa-eraser" />
              확인
            </v-btn>
          </v-col>
          <v-col
            v-if="getAuth.role == 'ROLE_USER'"
            align="center"
            justify="center"
          >
            <v-btn
              v-if="getAuth.username == getQna.username"
              color="primary"
              text
              @click="
                [
                  clickInit(),
                  closeQnaDialog(),
                  removeQna({ username: getAuth.username, id: getQna.id }),
                ]
              "
            >
              <font-awesome-icon
                class="trash-hover"
                icon="fa-solid fa-trash-can"
              />
              삭제
            </v-btn>
          </v-col>

          <v-col v-else align="center" justify="center">
            <v-btn
              v-if="!answerClick"
              color="primary"
              text
              @click="showComment"
            >
              <font-awesome-icon icon="fa-solid fa-comment" />
              답변
            </v-btn>

            <v-btn
              v-else
              color="primary"
              text
              @click="[closeQnaDialog(), comment()]"
            >
              <font-awesome-icon icon="fa-solid fa-location-arrow" />
              등록
            </v-btn>
          </v-col>

          <v-col align="center" justify="center">
            <v-btn
              color="primary"
              text
              @click="[closeQnaDialog(), clickInit()]"
            >
              <font-awesome-icon icon="fa-solid fa-xmark" />
              취소
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script>
import Constant from "@/util/Constant";
import { mapGetters, mapMutations, mapActions } from "vuex";

const qnaStore = "qnaStore";
const userStore = "userStore";
export default {
  name: "QnaDetailModal",
  components: {},
  data() {
    return {
      modifyClick: false,
      answerClick: false,
      title: "",
      content: "",
      answer: "",
    };
  },
  computed: {
    ...mapGetters(qnaStore, ["getQnaDialog", "getQna"]),
    ...mapGetters(userStore, ["getAuth", "getCustomColor"]),
  },
  methods: {
    ...mapMutations(qnaStore, [Constant.SELECT_QNA, Constant.CLOSE_QNA_DIALOG]),
    ...mapActions(qnaStore, [
      Constant.MODIFY_QNA,
      Constant.REGISTER_ANSWER,
      Constant.REMOVE_QNA,
    ]),
    clickInit() {
      this.modifyClick = false;
      this.answerClick = false;
    },

    showModify() {
      this.title = this.getQna.title;
      this.content = this.getQna.content;
      this.modifyClick = true;
    },

    showComment() {
      this.answerClick = true;
      this.answer = this.getQna.answer;
    },
    modify() {
      this.clickInit();
      let param = {
        id: this.getQna.id,
        title: this.title,
        content: this.content,
      };
      this.modifyQna(param);
    },
    comment() {
      this.clickInit();
      console.log(this.getQna.id, this.answer);
      this.registerAnswer({
        id: this.getQna.id,
        answer: this.answer,
      });
    },
  },
};
</script>

<style></style>

<template>
<v-container style="min-height: 41rem">

  <v-form>
    <v-text-field
      v-model="form.title"
      :counter="40"
      label="Title"
      required
    ></v-text-field>

    <v-textarea
      solo
      name="input-7-4"
      v-model="form.content"
      label="Quest? here you are❓"
    ></v-textarea>

    <v-checkbox v-model="form.privateFlag" label="비밀글"></v-checkbox>

    <v-btn color="error" class="mr-4" @click="reset"> 글 다시 쓰기 </v-btn>

    <v-btn color="warning" @click="registerQna"> 글 등록 </v-btn>
  </v-form>
</v-container>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Constant from "@/util/Constant";


const qnaStore = "qnaStore";
const userStore = "userStore";

export default {
  name: "QnaRegisterView",
  created() {
    this.form.username = this.getAuth.username;
  },
  computed: {
    ...mapGetters(userStore, ["getAuth"]),
  },
  data() {
    return {
      form: {
        title: "",
        content: "",
        privateFlag: false,
        username: "",
      },
    };
  },
  methods: {
    ...mapActions(qnaStore, [Constant.POST_QNA]),
    reset() {
      (this.form.title = ""),
        (this.form.content = ""),
        (this.form.privateFlag = false);
    },
    registerQna() {
      console.log(this.form);
      this.postQna(this.form);
      // this.$router.push("{name : 'QnaView'}");
    },
  },
};
</script>

<style></style>

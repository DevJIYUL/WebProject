<template>
  <router-view></router-view>
</template>

<script>
import NoticeList from "@/components/notice/NoticeList"
import NoticeDetail from "@/components/notice/NoticeDetail"
export default {
  name: "NoticeView",
  components : {NoticeList,NoticeDetail},
};
</script>

<style></style>

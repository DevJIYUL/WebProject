<template>
  <div>
    <v-card>
      <v-col>
        <v-col> 기본정보 </v-col>
      </v-col>
      <v-col>
        <v-card-title>
          <v-row>
            <v-col cols="6">
              <font-awesome-icon icon="fa-solid fa-address-card" />
              {{this.getUserInfo.username}}
            </v-col>
            <v-col cols="6">
              <font-awesome-icon icon=" fa-solid fa-envelope" />
              {{this.getUserInfo.email}}@{{this.getUserInfo.domain}}
            </v-col>
            <v-col cols="6">
              <font-awesome-icon icon="fa-solid fa-user" />
              {{this.getUserInfo.role}}
            </v-col>
            <v-col cols="6">
              <font-awesome-icon icon="fa-solid fa-calendar" />
              {{this.getUserInfo.joinDate}}
            </v-col>
            <v-col cols="6">
              <font-awesome-icon icon="fa-solid fa-house" />
              {{this.getUserInfo.ip}}
            </v-col>
            <v-col cols="6">
              <font-awesome-icon icon="fa-solid fa-house" />
              {{this.getUserInfo.sidoName}} {{this.getUserInfo.gugunName}} {{this.getUserInfo.dongName}}
            </v-col>
          </v-row>
          <v-row>
          </v-row>
        </v-card-title>
        <hr />
        <v-row justify="center" align="center">
          <v-col cols="6">
            <v-card>
              <v-card-actions>
                <v-btn color="accent" text @click="[getdong(),oppositeloc(favloc)]">
                  <v-card-title> 관심지역 </v-card-title>
                </v-btn>

                <v-spacer></v-spacer>
              </v-card-actions>
              <v-expand-transition>
                <div v-show="favloc">
                  <v-divider></v-divider>
                  <v-card-text>
                    <v-data-table
                    :headers="headers"
                    :items="getFavDongList"
                    hide-default-footer
                    no-data-text="관심 지역을 추가해주세요😎"
                    class="elevation-1"
                  ></v-data-table>
                    <!-- {{getFavDongList}} -->
                  </v-card-text>
                </div>
              </v-expand-transition>
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card>
              <v-card-actions>
                <v-btn color="accent" text @click="[getkey(),oppositekey(favkey)]">
                  <v-card-title> 관심 키워드 </v-card-title>
                </v-btn>

                <v-spacer></v-spacer>
              </v-card-actions>
              <v-expand-transition>
                <div v-show="favkey">
                  <v-divider></v-divider>

                  <v-card-text>
                    <v-data-table
                    :headers="headerskey"
                    :items="getFavKeyList"
                    hide-default-footer
                    no-data-text="관심 키워드를 추가해주세요😎"
                    class="elevation-1"
                  ></v-data-table>
                  </v-card-text>
                </div>
              </v-expand-transition>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-card>
  </div>
</template>

<script>
const userStore = "userStore";
import Constant from "@/util/Constant";
import { mapActions, mapGetters } from 'vuex';
export default {
  name: "MyPageView",
  created(){
    this.getUser(this.getAuth);
    console.log("마이페이시 create");
    console.log(this.getAuth.username);
    this.getUserinformation(this.getAuth.username);
  },
  // ,'getFavDongList'   ,Constant.GET_FAV_DONG
  computed:{
    ...mapGetters(userStore,['getAuth','getUserInfo','getFavDongList','getFavKeyList']),
  },
  data() {
    return {
      favloc: false,
      favkey:false,
      favdongCode:[],
      headers: [
          { text: '시',value: 'sidoName', sortable: false },
          { text: '구/군', value: 'gugunName', sortable: false },
          { text: '동', value: 'dongName', sortable: false },
      ],
      headerskey: [
          { text: '관심키워드',value: 'tag', sortable: false },
      ],
      // username: $cookies.get("auth").username
    }
  },
  methods: {
    ...mapActions(userStore,["getUser",Constant.GET_USER_INFORMATION,Constant.GET_FAV_DONG,Constant.GET_FAV_KEY]),
    oppositeloc(flag){
      this.favloc=!flag;
    },
    oppositekey(flag){
      this.favkey = !flag;
    },
    getdong(){
      console.log("동가져와라");
      this.getFavDong(this.getAuth.username);
    },
    getkey(){
      this.getFavKey(this.getAuth.username)
    }
  },
}
</script>

<style></style>

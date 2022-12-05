<template>
  <div>
    <div>
      <v-carousel
        cycle
        height="500px"
        hide-delimiter-background
        show-arrows-on-hover
        style="border-radius: 16px"
      >
        <v-carousel-item v-for="(item, i) in items" :key="i" :src="item.src">
          <v-card-text>
            <v-row class="fill-height d-flex align-end">
              <v-btn color="accent" to="apt"
                >내 관심 지역 추가해보기</v-btn
              >
            </v-row>
          </v-card-text>
        </v-carousel-item>
      </v-carousel>
    </div>

    <v-row>
      <v-col cols="12" >
        <div>
          <div class="pt-16">
            <h2 class="text-h4 font-weight-bold pb-4">Dev</h2>

            <v-row class="mb-6">
              <v-col cols="5">
                <v-card
                  flat
                  hover
                >
                  <v-img
                    :aspect-ratio="16 / 9"
                    class="elevation-2"
                    gradient="to top, rgba(25,32,72,.4), rgba(25,32,72,.0)"
                    height="300px"
                    src="https://avatars.githubusercontent.com/u/35020954?v=4"
                    style="border-radius: 16px"
                  >
                    <v-card-text>
                      <v-btn color="accent">JiYul</v-btn>
                    </v-card-text>
                  </v-img>

                  <v-card-text>
                    <div class="text-h5 font-weight-bold primary--text">
                      Backend Developer - Spring, Java
                    </div>

                    <div class="text-body-1 py-4">
                      <font-awesome-icon icon="fa-solid fa-envelope" /> lnacles@naver.com
                    </div>

                    <div class="d-flex align-center">
                      <v-avatar color="accent" size="36">
                        <v-icon dark>mdi-feather</v-icon>
                      </v-avatar>

                      <div class="pl-2">Song JiYul · 😎</div>
                    </div>
                  </v-card-text>
                </v-card>
              </v-col>
              <v-col cols="2"></v-col>
              <v-col cols="5">
                <v-card
                  flat
                  hover
                >
                  <v-img
                    :aspect-ratio="16 / 9"
                    class="elevation-2"
                    gradient="to top, rgba(25,32,72,.4), rgba(25,32,72,.0)"
                    height="300px"
                    src="https://junhyxxn.github.io/static/d1360ed2360089434fe05c8091ebc035/ab2c9/GithubProfile.webp"
                    style="border-radius: 16px"
                  >
                    <v-card-text>
                      <v-btn color="accent" >JunHyxxn</v-btn>
                    </v-card-text>
                  </v-img>

                  <v-card-text>
                    <div class="text-h5 font-weight-bold primary--text">
                      Backend Developer - Spring, Java
                    </div>

                    <div class="text-body-1 py-4">
                      <font-awesome-icon icon="fa-solid fa-envelope" /> tlaxh000@naver.com
                      
                    </div>

                    <div class="d-flex align-center">
                      <v-avatar color="accent" size="36">
                        <v-icon dark>mdi-feather</v-icon>
                      </v-avatar>

                      <div class="pl-2">Hwang JunHyxxn · 😀</div>
                    </div>
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </div>


        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "HomeView",
  components: {
    siderbar: () => import("@/components/details/sidebar"),
  },
  data() {
    return {
      items: [
        {
          src: "https://images.pexels.com/photos/1042594/pexels-photo-1042594.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
          text: "초 고품격 프리미엄 아파르 와르르 멘션",
        },
        {
          src: "https://images.pexels.com/photos/1571460/pexels-photo-1571460.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
          text: "잘 지내는 ",
        },
        {
          src: "https://images.pexels.com/photos/681331/pexels-photo-681331.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
          text: "살면서 한번 쯤은 살아볼만함 집",
        },
        {
          src: "https://images.pexels.com/photos/2030037/pexels-photo-2030037.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
          test: "글적글적",
        },
      ],
    };
  },
};
</script>

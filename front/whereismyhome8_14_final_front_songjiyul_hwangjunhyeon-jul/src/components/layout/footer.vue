<template>
  <div>
    <v-card absolute app color="primary" dark flat>
      <v-container>
        <v-row color="primary">
          <v-col v-for="(dev, i) in developers" :key="i" cols="6">
            <p>{{ dev.name }}</p>
            <p>{{ dev.text }}</p>
            <p>{{ dev.site }}</p>
          </v-col>
        </v-row>

        <div class="text-center mt-6">
          Copyright &copy;
          <span class="white--text"> Select Homes </span>
          {{ new Date().getFullYear() }}. All rights reserved.
        </div>
      </v-container>
    </v-card>
  </div>
</template>

<script>
export default {
  data: () => ({
    developers: [
      {
        text: "Github",
        site: "https://github.com/JunHyxxn",
        name: "JunHyxxn",
      },
      {
        text: "Naver",
        site: "Inacles@naver.com",
        name: "JiYul",
      },
    ],
  }),
};
</script>

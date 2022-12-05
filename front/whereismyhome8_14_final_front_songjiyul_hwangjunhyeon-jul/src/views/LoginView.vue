<template>
  <v-row
    class="d-flex justify-center align-center fill-height"
    style="min-height: 100vh"
  >
    <v-col cols="12" md="6">
      <v-card class="py-6">
        <v-card-title class="d-flex justify-center">
          <div class="text-h4">Login</div>
        </v-card-title>
        <v-card-text>
          <v-text-field label="ID" outlined v-model="username"></v-text-field>
          <v-text-field
            v-model="password"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.required, rules.min]"
            :type="show1 ? 'text' : 'password'"
            name="input-10-1"
            label="Normal with hint text"
            hint="Password"
            counter
            outlined
            @click:append="show1 = !show1"
          ></v-text-field>
          <div class="text-right">
            <v-btn  color="accent" @click="authenticate"> Login </v-btn>
            <router-link
              style="text-decoration: none"
              :to="{ name: 'SignupView' }"
            >
              <v-btn color="accent"> Signup </v-btn>
            </router-link>
          </div>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import {mapActions} from "vuex";
const userStore="userStore";
export default {
  name: "LoginView",
  data() {
    return {
      username: "",
      password: "",
      show1: false,
      rules: {
          required: value => !!value || 'Required.',
          min: v => v.length >= 4 || 'Min 4 characters',
          emailMatch: () => (`The email and password you entered don't match`),
        },
    }
  },
  methods: {
    ...mapActions(userStore, ["login"]),
    authenticate() {
      console.log({
        username: this.username,
        password: this.password
      });
      this.login({
        username: this.username,
        password: this.password
      });
    },
  },
};
</script>

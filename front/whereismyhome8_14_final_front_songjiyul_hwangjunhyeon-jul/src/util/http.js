import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:9999/myHome",
  headers: {
    "Content-Type": "application/json",
  },
});

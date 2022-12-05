<template>
  <v-container>
    <!-- <v-card>
      <v-card-title>
        아파트 이름 검색
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Apartment Name"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title> -->

    <!-- :search="search" -->
    <v-data-table
      class="elevation-1"
      :items="getAptDealList"
      :headers="headers"
      :items-per-page="itemPerPage"
      :page.sync="page"
      no-data-text="검색 결과가 없습니다😥"
      hide-default-footer
    >
      <template v-slot:item="{ item }">
        <tr
          @dblclick="
            selectApt({
              aptCode: item.aptCode,
              no: item.no,
            })
          "
        >
          <td align="center" justify="center">
            {{ item.dealYear }} - {{ item.dealMonth }}
          </td>
          <td align="center" justify="center">{{ item.dongName }}</td>
          <td align="center" justify="center">{{ item.apartmentName }}</td>
          <!-- <td align="center" justify="center">{{ item.area }}</td> -->
          <td align="center" justify="center">
            {{ parseInt(item.dealAmount.replace(",", "")) | price }}
          </td>
        </tr>
      </template>
    </v-data-table>

    <v-pagination
      class="mt-5"
      circle
      v-model="page"
      :total-visible="7"
      :length="parseInt(getAptListCount / 10) + 1"
    ></v-pagination>
    <!-- </v-card> -->
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Constant from "@/util/Constant";

const aptStore = "aptStore";
// const dongStore = "dongStore";
export default {
  name: "AptList",
  data() {
    return {
      // search: "",
      page: 1,
      itemPerPage: 7,
      headers: [
        {
          text: "거래날짜",
          sortable: false,
          width: "20%",
          align: "center",
        },
        {
          text: "동",
          sortable: false,
          width: "20%",
          align: "center",
        },
        {
          text: "건물명",
          sortable: false,
          width: "30%",
          align: "center",
        },
        // {
        //   text: "평",
        //   sortable: false,
        //   width: "10%",
        //   align: "center",
        // },
        {
          text: "금액",
          sortable: true,
          width: "20%",
          align: "center",
        },
      ],
    };
  },
  filters: {
    price(val) {
      if (!val) return val;
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "만";
    },
  },
  computed: {
    ...mapGetters(aptStore, ["getAptDealList", "getAptListCount"]),
    // ...mapGetters(dongStore, ['getDongCode']),
  },
  methods: {
    ...mapActions(aptStore, [Constant.SELECT_APT]),
    customSort: function (items, index, isDesc) {
      items.sort((a, b) => {
        if (index == "dealAmount") {
          console.log(index);
          if (isDesc) {
            return (
              parseInt(b.dealAmount.replace(",", "")) -
              parseInt(a.dealAmount.replace(",", ""))
            );
          } else {
            return (
              parseInt(a.dealAmount.replace(",", "")) -
              parseInt(b.dealAmount.replace(",", ""))
            );
          }
        }
      });
      return items;
    },
  },
};
</script>

<style></style>

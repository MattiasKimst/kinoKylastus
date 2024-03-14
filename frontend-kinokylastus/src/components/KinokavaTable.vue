<template>
  <div>
    <h1>Kinokava</h1>

    <div>
      <label>Žanr:</label>
      <select v-model="valitudZanr">
        <option value="">Kõik</option>
        <option v-for="zanr in zanrid" :value="zanr" :key="zanr">{{ zanr }}</option>
      </select>
    </div>

    <div>
      <label>Vanusepiirang:</label>
      <select v-model="valitudVanus">
        <option value="">Kõik</option>
        <option v-for="vanus in vanuseVahemikud" :value="vanus" :key="vanus">{{ vanus }}</option>
      </select>
    </div>

    <div>
      <label>Algus alates:</label>
      <input type="time" v-model="algusAeg" step="1">
    </div>

    <div>
      <label>Kuupäev alates:</label>
      <input type="date" v-model="algusKuupäev" @change="applyDateFilter">
    </div>

    <table class="table">
      <thead>
      <tr>
        <th>ID</th>
        <th>Pealkiri</th>
        <th>Žanr</th>
        <th>Keel</th>
        <th>Vanusepiirang</th>
        <th>Algusaeg</th>
        <th>Vali</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in filtreeritudKava" :key="item.id">
        <td>{{ item.id }}</td>
        <td>{{ item.film?.pealkiri }}</td>
        <td>{{ item.film?.žanr }}</td>
        <td>{{ item.film?.keel }}</td>
        <td>{{ item.film?.vanusepiirang }}</td>
        <td>{{ item.algusaeg }}</td>
        <td><button @click="navigateToFilmisaal(item)">Vali</button></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      kinokava: [],
      valitudZanr: "",
      valitudVanus: "",
      algusAeg: "",
      algusKuupäev: ""
    };
  },
  computed: {
    filtreeritudKava() {
      let filtreeritud = this.kinokava;

      if (this.valitudZanr) {
        filtreeritud = filtreeritud.filter(item => item.film?.žanr === this.valitudZanr);
      }

      if (this.valitudVanus) {
        filtreeritud = filtreeritud.filter(item => item.film?.vanusepiirang === this.valitudVanus);
      }

      if (this.algusAeg) {
        filtreeritud = filtreeritud.filter(item => item.algusaeg >= this.algusAeg);
      }

      if (this.algusKuupäev) {
        const selectedDate = new Date(this.algusKuupäev);
        filtreeritud = filtreeritud.filter(item => {
          const seanssKuupäev = new Date(item.algusaeg);
          return seanssKuupäev >= selectedDate;
        });
      }

      return filtreeritud;
    },
    zanrid() {
      return [...new Set(this.kinokava.map(item => item.film?.žanr))];
    },
    vanuseVahemikud() {
      return [...new Set(this.kinokava.map(item => item.film?.vanusepiirang))];
    }
  },
  mounted() {
    this.fetchKinokava();
  },
  methods: {
    async fetchKinokava() {
      try {
        const response = await fetch('http://localhost:8081/kinokava');
        const data = await response.json();
        this.kinokava = data;
      } catch (error) {
        console.error('Viga kinokava hankimisel back-endist:', error);
      }
    },
    applyDateFilter() {
      // ajafilter lähtestatakse kui valitakse uus kuupäev
      this.algusAeg = "";
    },
    navigateToFilmisaal(selectedSeanss) {
      // Suuna edasi filmisaali vaatesse, edastades valitud seansi andmed
      this.$router.push({ name: 'Filmisaal', params: { seanss: selectedSeanss } });
    }
  }
};
</script>

<style scoped>
.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.table th {
  background-color: #f2f2f2;
  color: #333;
}

.table tbody tr:hover {
  background-color: #f5f5f5;
}
</style>

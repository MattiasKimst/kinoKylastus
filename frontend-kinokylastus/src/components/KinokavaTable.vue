<template>
  <div class="kinokava-container">
    <h1>Kinokava</h1>

    <div class="filter-section">
      <label>Žanr:</label>
      <select v-model="valitudZanr" class="input-field">
        <option value="">Kõik</option>
        <option v-for="zanr in zanrid" :value="zanr" :key="zanr">{{ zanr }}</option>
      </select>
    </div>

    <div class="filter-section">
      <label>Vanusepiirang:</label>
      <select v-model="valitudVanus" class="input-field">
        <option value="">Kõik</option>
        <option v-for="vanus in vanuseVahemikud" :value="vanus" :key="vanus">{{ vanus }}</option>
      </select>
    </div>

    <div class="filter-section">
      <label>Algus alates:</label>
      <input type="time" v-model="algusAeg" step="1" class="input-field">
    </div>

    <div class="filter-section">
      <label>Kuupäev alates:</label>
      <input type="date" v-model="algusKuupäev" @change="applyDateFilter" class="input-field">
    </div>

    <div class="table-container">
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
          <td>
            <button @click="navigateToFilmisaal(item)">Vali</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <form @submit.prevent="submitKylastajaId">
      <label for="kylastajaId">Külastaja ID:</label>
      <input type="text" id="kylastajaId" v-model="kylastajaId" class="input-field">
      <button type="submit">Soovitus</button>
    </form>
    <div v-if="soovitusData">
      <h2>Soovitus:</h2>
      <table class="soovitus-table">
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
        <tr>
          <td>{{ soovitusData.id }}</td>
          <td>{{ soovitusData.film.pealkiri }}</td>
          <td>{{ soovitusData.film.žanr }}</td>
          <td>{{ soovitusData.film.keel }}</td>
          <td>{{ soovitusData.film.vanusepiirang }}</td>
          <td>{{ soovitusData.algusaeg }}</td>
          <td>
            <button @click="navigateToFilmisaal(soovitusData)">Vali</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
      kylastajaId: "",
      soovitusData: null,
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
    async submitKylastajaId() {
      try {
        const response = await fetch(`http://localhost:8081/soovitused/${this.kylastajaId}`);
        const data = await response.json();
        this.soovitusData = data;
      } catch (error) {
        console.error("Viga soovituse hankimisel back-endist:", error);
      }
    },
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
      this.$router.push({name: 'Filmisaal', params: {seanss: selectedSeanss}});
    }
  }
};
</script>

<style scoped>
.kinokava-container {
  max-width: 800px; /* Adjust the maximum width as needed */
  margin: 0 auto; /* Center the container */
}

.filter-section {
  margin-bottom: 10px;
}

.input-field {
  width: 100%; /* Fill the container */
  height: 30px;
  padding: 5px;
}

.table-container {
  width: 100%;
  overflow-x: auto; /* Add horizontal scroll if needed */
}

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

.soovitus-table {
  width: 100%;
  border-collapse: collapse;
}

.soovitus-table th,
.soovitus-table td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.soovitus-table th {
  background-color: #f2f2f2;
  color: #333;
}
</style>

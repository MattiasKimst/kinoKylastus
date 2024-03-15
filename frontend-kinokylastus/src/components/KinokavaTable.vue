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
      <input type="time" v-model="algusAeg" step="3600" class="input-field">
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
          <th>Alguskuupäev</th>
          <th>Alguskellaaeg</th>
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
          <td>{{ formatDate(item.algusaeg) }}</td>
          <td>{{ formatTime(item.algusaeg) }}</td>
          <td>
            <button @click="navigateToFilmisaal(item)">Vali</button>
          </td>
        </tr>
        </tbody>
      </table>
      <br>
      <h3>Sisesta oma ID varasemate vaatamiste põhjal filmi soovitamiseks</h3>
    </div>
    <form @submit.prevent="submitKylastajaId">
      <label for="kylastajaId">ID:</label>
      <input type="text" id="kylastajaId" v-model="kylastajaId" class="input-field">
      <button type="submit">Soovita</button>
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

      //valime välja filmid, mille žanr kattub valitud žanriga
      if (this.valitudZanr) {
        filtreeritud = filtreeritud.filter(item => item.film?.žanr === this.valitudZanr);
      }

      //valime kõik filmid, mille vanusepiirang <= dropdownis valitud piirang
      if (this.valitudVanus) {
        filtreeritud = filtreeritud.filter(item => {
          const vanusepiirang = item.film?.vanusepiirang;
          return !vanusepiirang || parseInt(vanusepiirang) <= parseInt(this.valitudVanus);
        });
      }

      //valime filmid, mille alguskellaaeg on alates valitud alguskellaajast
      if (this.algusAeg) {
        filtreeritud = filtreeritud.filter(item => item.algusaeg >= this.algusAeg);
      }

      // valime filmid, mille kuupäev on alates valitud kuupäevast
      if (this.algusKuupäev) {
        const selectedDate = new Date(this.algusKuupäev);
        filtreeritud = filtreeritud.filter(item => {
          const seanssKuupäev = new Date(item.algusaeg);
          return seanssKuupäev >= selectedDate;
        });
      }

      return filtreeritud;
    },
    //leiame kinoseanssides esinevate žanrite hulga
    zanrid() {
      return [...new Set(this.kinokava.map(item => item.film?.žanr))];
    },
    //leiame vanusevahemike hulga
    vanuseVahemikud() {
      return [...new Set(this.kinokava.map(item => item.film?.vanusepiirang))];
    }
  },
  mounted() {
    this.fetchKinokava();
  },
  methods: {
    //soovituse hankimise päring back-endi külastaja id järgi
    async submitKylastajaId() {
      try {
        const response = await fetch(`http://localhost:8081/soovitused/${this.kylastajaId}`);
        const data = await response.json();
        this.soovitusData = data;
      } catch (error) {
        console.error("Viga soovituse hankimisel back-endist:", error);
      }
    },
    //tulevaste seansside listi hankimine back-endist get päringuga
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
    //nupu "Vali" vajutamisel kohtade soovitamise vaatesse edasi suunamine
    navigateToFilmisaal(selectedSeanss) {
      // Suuna edasi filmisaali vaatesse, edastades valitud seansi andmed
      this.$router.push({name: 'Filmisaal', params: {seanss: selectedSeanss}});
    },
    formatDate(dateTimeString) {
      const date = new Date(dateTimeString);
      // Vormindame kuupäeva aasta-kuu-päev kujul
      return date.toISOString().split('T')[0];
    },
    // Meetod kellaaja vormindamiseks
    formatTime(dateTimeString) {
      const date = new Date(dateTimeString);
      // Vormindame kellaaja tund-minut kujul
      return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    },
  }
};
</script>

<style scoped>
.kinokava-container {
  max-width: 800px;
  margin: 20px auto;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
}

.filter-section label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

.input-field {
  width: calc(100% - 10px);
  height: 40px;
  padding: 5px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.table th,
.table td {
  padding: 12px;
  text-align: left;
  border-bottom: 2px solid #ddd;
}

.table th {
  background-color: #4CAF50;
  color: white;
}

.table tbody tr:hover {
  background-color: #f1f1f1;
}

button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
  border-radius: 4px;
}

button:hover {
  background-color: white;
  color: #4CAF50;
  border: 1px solid #4CAF50;
}

.soovitus-table th {
  background-color: #333;
  color: white;
}

</style>

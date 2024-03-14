<template>
  <div class="filmisaal">
    <div v-for="seat in istekohad" :key="seat.id" class="istekoht" :class="{ valitud: seat.valitud, reserveeritud: seat.reserveeritud }" >
      {{ seat.number }}
    </div>
    <div class="broneerimine">
      <label for="piletiteArv">Sisesta piletite arv:</label>
      <input type="number" id="piletiteArv" v-model="piletiteArv" min="1" max="10">
      <button @click="soovitaIstekohti">Soovita istekohti</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      istekohad: [],//selles massiivis hoiame istekohti
      piletiteArv: 1
    };
  },
  mounted() {
    this.looIsteKohad();
    this.märgiIstekohadVõetuks(); // märgib juba broneeritud istekohad
  },
  methods: {
    looIsteKohad() {
      var num=1;
      for (let i = 1; i <= 5; i++) {
        for (let j = 1; j <=10 ; j++) {
          this.istekohad.push({
            id: num,
            number: num++,
            rida: i,
            valitud: false,
            reserveeritud: false
          });
        }}
    },
    märgiIstekohadVõetuks() {
      // genereerime juhuslikult broneeritud istekohad
      const reserveeritudIstekohtad = new Set();
      while (reserveeritudIstekohtad.size < 10) {
        const juhuslikInt = Math.floor(Math.random() * 50) + 1;
        reserveeritudIstekohtad.add(juhuslikInt);
      }
      // märgime valitud istekohad broneerituks
      reserveeritudIstekohtad.forEach(id => {
        const seat = this.istekohad.find(seat => seat.id === id);
        seat.reserveeritud = true;
      });
    },

    soovitaIstekohti() {
      // sorteeri istekohad rea ja numbri järgi
      const sorteeritudIstekohad = [...this.istekohad].sort((a, b) => a.rida - b.rida || a.number - b.number);

      // leiame keskmise rea
      const keskmineRida = Math.ceil(this.istekohad.length / 20);

      // alustame keskmisest reast ja vajadusel liigume eesmisesse või tagumisse ritta
      for (let offset = 0; offset < keskmineRida; offset++) {
        const vaadeldavRida = keskmineRida - offset;
        const järgmineRida = keskmineRida + offset;

        // filtreerime välja vabad kohad vaadeldavas ja naaberridades
        const saadavalKohadVaadeldavasJaNaaberRidades = sorteeritudIstekohad.filter(koht => (koht.rida === vaadeldavRida || koht.rida === järgmineRida) && !koht.reserveeritud);

        // leiame vaadeldava ja naaberridade keskimise koha
        const keskmineIste = Math.floor(saadavalKohadVaadeldavasJaNaaberRidades.length / 2);

        // Leiame sobiva istekohtade grupi piletite kasutaja sisestatud istekohtade arvu järgi
        const poolKohti=Math.floor(this.piletiteArv);
        const soovitatudIstekohad = saadavalKohadVaadeldavasJaNaaberRidades.slice(keskmineIste-poolKohti, keskmineIste + this.piletiteArv-poolKohti);

        // kontrollime, kas soovitatudIstekohad seas on õige arv kohti ja abimeetodiga kontrollime, kas need on järjestikku
        if (soovitatudIstekohad.length === this.piletiteArv && this.kasKohadJärjestikused(soovitatudIstekohad)) {
          // märgime leitud kohad valituks
          soovitatudIstekohad.forEach(istekoht => {
            istekoht.valitud = true;
          });
          return;
        }
      }

      //kui ei leitud sobivaid kohti
    },

    kasKohadJärjestikused(seats) {
      // sorteerime istekohad numbri järgi
      const sorteeritudIstekohad = [...seats].sort((a, b) => a.number - b.number);

      // kontrollime for tsükkliga, kas etteantud istekohad asuvad kõik üksteise järel
      for (let i = 1; i < sorteeritudIstekohad.length; i++) {
        if (sorteeritudIstekohad[i].number !== sorteeritudIstekohad[i - 1].number + 1) {
          return false;
        }
      }

      return true;
    }



  }

};
</script>

<style scoped>
.filmisaal {
  display: grid;
  grid-template-columns: repeat(10, 40px);
  gap: 5px;
}

.istekoht {
  width: 40px;
  height: 40px;
  border: 1px solid #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.istekoht.valitud {
  background-color: #009688;
  color: white;
}

.istekoht.reserveeritud {
  background-color: #ff4d4d;
}

.broneerimine {
  margin-top: 20px;
}

.broneerimine button {
  margin-left: 10px;
}
</style>
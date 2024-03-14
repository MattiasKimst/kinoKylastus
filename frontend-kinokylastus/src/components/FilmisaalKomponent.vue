<template>
  <div class="filmisaal">
    <div v-for="seat in istekohad" :key="seat.id" class="istekoht" :class="{ selected: seat.selected, reserved: seat.reserved }" >
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
          row: i,
          selected: false,
          reserved: false
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
        seat.reserved = true;
      });
    },

    soovitaIstekohti() {

      const soovitatudIstekohad = [];
      const algusindex = 25; // keskelt alustamine
      let vasakIndeks = algusindex;
      let paremIndeks = algusindex;

      while (soovitatudIstekohad.length < this.piletiteArv) {
        // Kontrollime, kas saame vasakul ja paremal poolel rohkem istekohti
        const vasakKoht = this.istekohad.find(seat => seat.id === vasakIndeks);
        const paremKoht = this.istekohad.find(seat => seat.id === paremIndeks);

        if (vasakKoht && !vasakKoht.reserved && !vasakKoht.selected) {
          soovitatudIstekohad.push(vasakKoht.id);
        }

        if (paremKoht && !paremKoht.reserved && !paremKoht.selected) {
          soovitatudIstekohad.push(paremKoht.id);
        }
        // Liigume järgmisele istekohale
        vasakIndeks--;
        paremIndeks++;
      }

      // Kuva soovitatud istekohad rohelisena
      soovitatudIstekohad.forEach(id => {
        const seat = this.istekohad.find(seat => seat.id === id);
        seat.selected = true;
      });
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

.istekoht.selected {
  background-color: #009688;
  color: white;
}

.istekoht.reserved {
  background-color: #ff4d4d;
}

.broneerimine {
  margin-top: 20px;
}

.broneerimine button {
  margin-left: 10px;
}
</style>

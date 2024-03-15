<template>

  <div class="filmisaal-container"><h1>Filmisaal</h1>
    <div class="filmisaal">
      <div v-for="seat in istekohad" :key="seat.id" class="istekoht"
           :class="{ valitud: seat.valitud, reserveeritud: seat.reserveeritud }">
        {{ seat.number }}
      </div>
      <div class="broneerimine" v-if="!buttonClicked">
        <label for="piletiteArv">Sisesta piletite arv:</label>
        <input type="number" id="piletiteArv" v-model="piletiteArv" min="1" max="10">
        <button @click="soovitaIstekohti" >Soovita istekohti</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      istekohad: [],//selles massiivis hoiame istekohti
      piletiteArv: 1,
      buttonClicked: false
    };
  },
  mounted() {
    this.looIsteKohad();
    this.märgiIstekohadVõetuks(); // märgib juba broneeritud istekohad
  },
  methods: {
    looIsteKohad() {
      var num = 1;
      for (let i = 1; i <= 5; i++) {
        for (let j = 1; j <= 10; j++) {
          this.istekohad.push({
            id: num,
            number: num++,
            rida: i,
            valitud: false,
            reserveeritud: false
          });
        }
      }
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
      this.buttonClicked = true;
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
        const poolKohti = Math.floor(this.piletiteArv);
        const soovitatudIstekohad = saadavalKohadVaadeldavasJaNaaberRidades.slice(keskmineIste - poolKohti, keskmineIste + this.piletiteArv - poolKohti);

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
.filmisaal-container {
  max-width: 800px;
  margin: 20px auto;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.filmisaal {
  display: grid;
  grid-template-columns: repeat(10, 60px);
  gap: 10px;
  justify-content: center;
  margin-top: 20px;
}

.istekoht {
  width: 60px;
  height: 60px;
  border: 1px solid #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: transform 0.2s;
}

.istekoht:hover {
  transform: scale(1.1);
}

.istekoht.valitud {
  background-color: #4CAF50;
  color: white;
  border-color: #4CAF50;
}

.istekoht.reserveeritud {
  background-color: #ff4d4d;
  color: white;
  border-color: #ff4d4d;
}

.broneerimine {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.broneerimine label {
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.broneerimine input[type=number] {
  width: calc(100% - 10px);
  height: 40px;
  padding: 5px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.broneerimine button {
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

.broneerimine button:hover {
  background-color: white;
  color: #4CAF50;
  border: 1px solid #4CAF50;
}

</style>
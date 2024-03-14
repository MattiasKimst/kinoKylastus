-<template>
  <div class="filmisaal">
    <div v-for="seat in seats" :key="seat.id" class="seat" :class="{ selected: seat.selected }" @click="toggleSeat(seat.id)">
      {{ seat.number }}
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      seats: []
    };
  },
  mounted() {
    this.createSeats();
  },
  methods: {
    createSeats() {
      for (let i = 1; i <= 50; i++) {
        this.seats.push({
          id: i,
          number: i,
          selected: false
        });
      }
    },
    toggleSeat(seatId) {
      const seat = this.seats.find(seat => seat.id === seatId);
      seat.selected = !seat.selected;
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

.seat {
  width: 40px;
  height: 40px;
  border: 1px solid #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.seat.selected {
  background-color: #009688;
  color: white;
}
</style>

<template>
  <div>
    <h1>Kinokava</h1>
    <table class="table">
      <thead>
      <tr>
        <th>ID</th>
        <th>Pealkiri</th>
        <th>Žanr</th>
        <th>Keel</th>
        <th>Vanusepiirang</th>
        <th>Algusaeg</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in kinokava" :key="item.id">
        <td>{{ item.id }}</td>
        <td>{{ item.film.pealkiri }}</td>
        <td>{{ item.film.žanr }}</td>
        <td>{{ item.film.keel }}</td>
        <td>{{ item.film.vanusepiirang }}</td>
        <td>{{ item.algusaeg }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      kinokava: []
    };
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
        console.error('Error fetching kinokava:', error);
      }
    }
  }
};
</script>

<style scoped>
/* Add your styling here */
</style>

import { createRouter, createWebHistory } from 'vue-router';
import KinokavaView from './views/KinokavaView.vue';

const routes = [
    {
        path: '/',
        name: 'Kinokava',
        component: KinokavaView
    },
    {
        path: '/filmisaal',
        name: 'Filmisaal',
        component: () => import('./views/FilmisaalView.vue')
    }

];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;

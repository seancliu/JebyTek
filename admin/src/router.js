import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Admin from './views/admin.vue'
import Dashboard from './views/admin/dashboard.vue'
import Chapter from './views/admin/chapter.vue'

Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login",
    }, {
        path: '/login',
        component: Login
    }, {
        path: '/',
        name: "admin",
        component: Admin,
        children: [{
            path: 'dashboard',
            name: "dashboard",
            component: Dashboard,
        }, {
            path: 'business/chapter',
            name: "business/chapter",
            component: Chapter,
        }]
    }]
})

import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Admin from './views/admin.vue'
import Dashboard from './views/admin/dashboard.vue'
import Category from './views/admin/category.vue'
import Course from './views/admin/course.vue'
import Chapter from './views/admin/chapter.vue'
import Section from './views/admin/section.vue'
import Instructor from './views/admin/instructor.vue'
import File from "./views/admin/file.vue"

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
            path: 'business/category',
            name: "business/category",
            component: Category,
        }, {
            path: 'business/course',
            name: "business/course",
            component: Course,
        }, {
            path: 'business/chapter',
            name: "business/chapter",
            component: Chapter,
        }, {
            path: 'business/section',
            name: "business/section",
            component: Section,
        }, {
            path: 'business/instructor',
            name: "business/instructor",
            component: Instructor,
        }, {
            path: 'file/file',
            name: "file/file",
            component: File,
        }]
    }]
})

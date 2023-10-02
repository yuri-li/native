import {
    createRouter,
    createWebHashHistory
} from "vue-router"

const routes = [
    {path: "/", redirect: {name: "Counter"}},
    {path: "/counter", name: "Counter", component: () => import("@/components/Counter.vue"),},
    {path: "/user/profile", name: "UserProfile", component: () => import("@/components/UserProfile.vue"),},
    {path: "/user/ids", name: "UserIds", component: () => import("@/components/UserIds.vue"),},
]

const router = createRouter({
    history: createWebHashHistory(), routes,
})

export { router }
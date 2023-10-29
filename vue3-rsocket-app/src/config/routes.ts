import {
    createRouter,
    createWebHashHistory
} from "vue-router"

const routes = [
    {path: "/", redirect: {name: "Create"}},
    {path: "/counter", name: "Counter", component: () => import("@/components/Counter.vue"),},
    {path: "/user/profile", name: "UserProfile", component: () => import("@/components/userProfile/Index.vue"),},
    {path: "/user/ids", name: "UserIds", component: () => import("@/components/UserIds.vue"),},
    {path: "/cash/transfer", name: "Transfer", component: () => import("@/components/transfer/Index.vue"),},
    {path: "/crud/create", name: "Create", component: () => import("@/components/crud/create/Index.vue"),},
    {path: "/crud/read", name: "Read", component: () => import("@/components/crud/read/Index.vue"),},
]

const router = createRouter({
    history: createWebHashHistory(), routes,
})

export { router }
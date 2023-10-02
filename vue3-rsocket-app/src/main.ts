import { createApp } from "vue"
import "@/style.css"
import App from "@/App.vue"
import { router } from "@/config/routes"

const app = createApp(App)
app
    .use(router)
    .mount("#app")
app.config.errorHandler = (err, _, info) => {
    // report error to tracking services
    console.log(err, info)
}

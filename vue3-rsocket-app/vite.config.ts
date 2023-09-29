/// <reference types="vitest" />

import {defineConfig} from "vite"
import Vue from "@vitejs/plugin-vue"
import {resolve} from "path"

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [Vue()],
    build: {
        target: "esnext",
        // minify: false,
        // sourcemap: true,
    },
    resolve: {
        alias: {
            "@": resolve(__dirname, "src/"),
            "~": resolve(__dirname, "test/")
        }
    },
    test: {
        globals: true,
        environment: "jsdom",
        include: ["test/**/*.spec.ts"],
    },
})

/// <reference types="vitest" />

import {defineConfig} from "vite"
import {resolve} from "path"

import Vue from "@vitejs/plugin-vue"
import ViteYAML from "@modyfi/vite-plugin-yaml"

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [Vue(), ViteYAML()],
    build: {
        target: "esnext",
        minify: false,
        sourcemap: true,
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

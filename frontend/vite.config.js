import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      '/api/v1/clients': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/api/v1/products': {
        target: 'http://localhost:8083',
        changeOrigin: true,
      },
      '/api/v1/orders': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      '/api/v1/productsInCart': {
        target: 'http://localhost:8082',
        changeOrigin: true,
      },
    },
  },
})

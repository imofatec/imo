import axios from 'axios'
import { baseURL } from './environment'

const authAxiosInstance = axios.create({
  baseURL,
})

authAxiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')

  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }

  return config
})

export default authAxiosInstance

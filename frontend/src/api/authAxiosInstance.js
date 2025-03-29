import axios from 'axios'
import { baseURL } from './environment'

const authAxiosInstance = axios.create({ baseURL })

authAxiosInstance.interceptors.request.use((config) => {
  try {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`
    }
  } catch (err) {
    // console.log(err)
  }
  return config
})

export default authAxiosInstance

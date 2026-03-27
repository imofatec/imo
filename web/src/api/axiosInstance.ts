import axios from 'axios'
import { baseURL } from './environment'

const axiosInstance = axios.create({ baseURL })

export default axiosInstance

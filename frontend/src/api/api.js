import axios from 'axios'

const api = axios.create()
api.interceptors.request.use((config) => {
    try {
        config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`
    } catch (err) {
        // console.log(err)
    }
    return config
})

export default api

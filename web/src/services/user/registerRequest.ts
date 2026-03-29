import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type RegisterRequestData = {
  name: string
  email: string
  password: string
  confPassword: string
}

export async function registerRequest(data: RegisterRequestData) {
    const [error, response] = await safeAwait(axiosInstance.post('/api/user', data))

      if (error || !response) throw error

      return response.data
}
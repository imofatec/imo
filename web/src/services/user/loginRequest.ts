import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'


export async function loginRequest(data: { email: string; password: string }) {
  const [error, response] = await safeAwait(axiosInstance.post('/api/user/login', data))

  if (error || !response) {
    throw error
  }

  const { accessToken } = response.data
  localStorage.setItem('token', accessToken)

  return response.data
}

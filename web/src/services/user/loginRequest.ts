import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type LoginRequestData = {
  email: string
  password: string
}

export async function loginRequest(data: LoginRequestData) {
  const [error, response] = await safeAwait(axiosInstance.post('/api/user/login', data))

  if (error || !response) throw error

  const { accessToken } = response.data
  return { accessToken, ...response.data }
}

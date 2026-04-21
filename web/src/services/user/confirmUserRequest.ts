import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

export async function confirmUserRequest() {
  const [error, response] = await safeAwait(authAxiosInstance.put('/api/user/confirm'))

  if (error || !response) throw error

  return response.data
}

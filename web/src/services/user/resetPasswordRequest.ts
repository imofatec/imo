import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type ResetPasswordRequestData = {
  email: string
  newPassword: string
  code: string
}

export async function resetPasswordRequest(data: ResetPasswordRequestData) {
  const [error, response] = await safeAwait(
    axiosInstance.patch('/api/recovery/password/reset', data)
  )

  if (error || !response) throw error

  return response.data
}

import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

export async function resendConfirmationEmailRequest() {
  const [error, response] = await safeAwait(
    axiosInstance.post('/api/user/confirm/resend')
  )

  if (error || !response) throw error

  return response.data
}

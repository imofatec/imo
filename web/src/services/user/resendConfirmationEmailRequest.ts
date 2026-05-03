import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type ResendConfirmationEmailRequestData = {
  email: string
}

export async function resendConfirmationEmailRequest(data: ResendConfirmationEmailRequestData) {
  const [error, response] = await safeAwait(axiosInstance.post('/api/user/confirm/resend', data))

  if (error || !response) throw error

  return response.data
}

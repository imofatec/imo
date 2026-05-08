import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type SendRecoveryCodeRequestData = {
  email: string
}

export async function sendRecoveryCodeRequest(data: SendRecoveryCodeRequestData) {
  const [error, response] = await safeAwait(
    axiosInstance.post('/api/recovery/password/send-code', data)
  )

  if (error || !response) throw error

  return response.data
}

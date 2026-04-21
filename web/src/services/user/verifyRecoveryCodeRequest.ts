import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type VerifyRecoveryCodeRequestData = {
  email: string
  code: string
}

export async function verifyRecoveryCodeRequest(data: VerifyRecoveryCodeRequestData) {
  const [error, response] = await safeAwait(axiosInstance.post('/api/recovery/password/verify', data))

  if (error || !response) throw error

  return response.data
}

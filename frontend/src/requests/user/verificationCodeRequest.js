import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

export async function verificationCodeRequest(formData) {
  const code = formData.get('VerificationCode')
  const id = formData.get('UserId')

  const [error, result] = await safeAwait(
    axiosInstance.get(`/api/user/forget-password/verify/${id}/${code}`)
  )

  if (error) {
    return { error: error.response.data.message }
  }

  return { user: result.data , success: 'Código verificado com sucesso'}

}

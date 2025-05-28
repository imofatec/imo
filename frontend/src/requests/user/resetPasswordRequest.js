import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

import { resetPasswordSchema } from '@/schemas/resetPasswordSchema'

export async function resetPasswordRequest({ password, confPassword, userId, verificationCode }) {
  const validation = resetPasswordSchema.safeParse({
    password,
    confPassword,
    userid: userId,
    verificationcode: verificationCode
  })

  if (!validation.success) {
    const fieldErrors = {}
    validation.error.errors.forEach((error) => {
      fieldErrors[error.path[0]] = error.message
    })
    return { fieldErrors }
  }

  const code = verificationCode
  const UserId = userId

  const user = { password }

  const [error, result] = await safeAwait(
    axiosInstance.patch(`/api/user/${code}/${UserId}`, user)
  )

  if (error) {
    return { error: error.response?.data?.message || 'Erro ao redefinir senha' }
  }

  return { success: true }
}
import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { redirect } from 'react-router-dom'

export async function resetPasswordRequest(formData) {

  const code = formData.get('verificationcode')
  const UserId = formData.get('userid')
  const password = formData.get('newPassword')
  const confPassword = formData.get('confirmPassword')

  if (password !== confPassword) {
    return { error: 'As senhas estão diferentes.' }
  }
  const user = { password }

  const [error, result] = await safeAwait(
    axiosInstance.patch(`/api/user/${code}/${UserId}`, user)
  )

  if (error) {
    return { error: error.response.data.message }
  }

  if (result.status === 204) {
    return { success: 204 }
  }

  return redirect(`/login`)
}
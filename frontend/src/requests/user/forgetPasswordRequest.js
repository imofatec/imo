import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'

export async function forgetPasswordRequest(formData) {
  
  const email = formData.get('email')

  const [error, result] = await safeAwait(
    axiosInstance.get(`/api/user/forget-password/${email}`)
  )

  if (error) {
    return { error: error.response.data.message }
  }

  return { user: result.data}

}

import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type UploadPfpRequestData = {
  file: File
}

export async function uploadPfpRequest(data: UploadPfpRequestData) {
  const formData = new FormData()
  formData.append('file', data.file)

  const [error, response] = await safeAwait(
    authAxiosInstance.put('/api/user/profile-picture', formData)
  )

  if (error || !response) throw error

  return response.data
}

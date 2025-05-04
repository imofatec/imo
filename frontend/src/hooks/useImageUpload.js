import authAxiosInstance from '@/api/authAxiosInstance'
import { baseURL } from '@/api/environment'
import { safeAwait } from '@/lib/safeAwait'

const useImageUpload = (setUrlImage) => {
  const handleImageUpload = async (file) => {
    if (!file) {
      return { error: '' }
    }
    const formData = new FormData()
    formData.append('file', file)

    const [error, result] = await safeAwait(
      authAxiosInstance.put('/api/user/profile-picture', formData),
    )

    if (error) {
      return { error: error.response.data.message }
    }

    const newImagePath = `${baseURL}/uploads/${result.data.profilePicturePath}`
    setUrlImage(newImagePath)

    window.location.reload()
    return { error: null }
  }
  return { handleImageUpload }
}

export default useImageUpload

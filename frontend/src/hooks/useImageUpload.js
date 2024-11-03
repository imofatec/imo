import api from '@/api/api'
import { BASE_URL } from '@/api/environment'
import { safeAwait } from '@/lib/safeAwait'

const useImageUpload = (setUrlImage) => {
  const handleImageUpload = async (file) => {
    if (!file) {
      return { error: '' }
    }
    const formData = new FormData()
    formData.append('file', file)

    const [error, result] = await safeAwait(
      api.put('/api/user/upload/profile-picture', formData),
    )

    if (error) {
      return { error: error.response.data.message }
    }

    const newImagePath = `${BASE_URL}/uploads/${result.data.profilePicturePath}`
    setUrlImage(newImagePath)

    window.location.reload()
    return { error: null }
  }
  return { handleImageUpload }
}

export default useImageUpload

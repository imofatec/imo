import api from '@/api/api'
import { BASE_URL } from '@/api/environment'
import { safeAwait } from '@/lib/safeAwait'

const useImageUpload = (setUrlImage) => {
  const handleImageUpload = async (file) => {
    if (!file) {
      console.error('Nenhum arquivo selecionado')
      return
    }
    const formData = new FormData()
    formData.append('file', file)

    const [error, result] = await safeAwait(
      api.put('/api/user/upload/profile-picture', formData),
    )

    if (error) {
      console.error(
        'Erro ao enviar a imagem:',
        error.response ? error.response.data : error.message,
      )
      return
    }

    const newImagePath = `${BASE_URL}/uploads/${result.data.profilePicturePath}`
    setUrlImage(newImagePath)
    window.location.reload()
  }

  return { handleImageUpload }
}

export default useImageUpload

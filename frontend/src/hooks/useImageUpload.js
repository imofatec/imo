import { useState } from 'react'
import api from '@/api/api'

const useImageUpload = (setUrlImage) => {

  const handleImageUpload = async (file) => {
    if (file) {
      const formData = new FormData()
      formData.append('file', file)

      try {
        const result = await api.put(
          '/api/user/upload/profile-picture',
          formData,
        )

        const newImagePath = `http://localhost:8080/uploads/${result.data.profilePicturePath}`
        setUrlImage(newImagePath)
        window.location.reload();
        console.log('Imagem enviada com sucesso:', newImagePath)
      } catch (error) {
        console.error(
          'Erro ao enviar a imagem:',
          error.response ? error.response.data : error.message,
        )
      }
    } else {
      console.error('Nenhum arquivo selecionado')
    }
  }

  return {handleImageUpload}
}

export default useImageUpload

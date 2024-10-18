import { useState, useEffect } from 'react'
import api from '@/api/api'

const useFetchUserInfo = () => {
  const [urlImage, setUrlImage] = useState('')
  const [userInfo, setUserInfo] = useState(null)

  const fetchUserInfo = async () => {
    try {
      const result = await api.get('/api/user/profile')
      setUserInfo(result.data)
      setUrlImage(
        `http://localhost:8080/uploads/${result.data.profilePicturePath}`,
      )
    } catch (error) {
      console.error('Erro ao buscar informações do usuário:', error)
    }
  }

  useEffect(() => {
    fetchUserInfo()
  }, [])

  return { setUrlImage,userInfo, urlImage, fetchUserInfo }
}

export default useFetchUserInfo

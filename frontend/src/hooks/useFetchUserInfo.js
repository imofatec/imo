import { useState, useEffect } from 'react'
import { BASE_URL } from '@/api/environment'
import { safeAwait } from '@/lib/safeAwait'
import api from '@/api/api'

const useFetchUserInfo = () => {
  const [urlImage, setUrlImage] = useState('')
  const [userInfo, setUserInfo] = useState(null)

  const fetchUserInfo = async () => {
    const [error, result] = await safeAwait(await api.get('/api/user/profile'))
    if (error) {
      console.error('Erro ao buscar informações do usuário:', error)
      return
    }
    setUserInfo(result.data)
    setUrlImage(`${BASE_URL}/uploads/${result.data.profilePicturePath}`)
  }

  return { setUrlImage, userInfo, urlImage, fetchUserInfo }
}

export default useFetchUserInfo

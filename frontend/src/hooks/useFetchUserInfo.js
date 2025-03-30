import authAxiosInstance from '@/api/authAxiosInstance'
import { baseURL } from '@/api/environment'
import { safeAwait } from '@/lib/safeAwait'
import { useState } from 'react'

const useFetchUserInfo = () => {
  const [urlImage, setUrlImage] = useState('')
  const [userInfo, setUserInfo] = useState(null)

  const fetchUserInfo = async () => {
    const [error, result] = await safeAwait(
      await authAxiosInstance.get('/api/user/profile'),
    )
    if (error) {
      console.error('Erro ao buscar informações do usuário:', error)
      return
    }
    setUserInfo(result.data)
    setUrlImage(`${baseURL}/uploads/${result.data.profilePicturePath}`)
  }

  return { setUrlImage, userInfo, urlImage, fetchUserInfo }
}

export default useFetchUserInfo

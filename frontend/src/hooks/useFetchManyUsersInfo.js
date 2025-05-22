import authAxiosInstance from '@/api/authAxiosInstance'
import { baseURL } from '@/api/environment'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'

export const useFetchManyUsersInfo = (userIds) => {
  const [usersInfo, setUsersInfo] = useState({})
  const [images, setImages] = useState({})

  useEffect(() => {
    const fetchManyUsersInfo = async () => {
      if (!userIds || userIds.length === 0) return

      const queryString = userIds.map(id => `ids=${encodeURIComponent(id)}`).join('&')

      const [error, result] = await safeAwait(
        authAxiosInstance.get(`/api/user/ids?${queryString}`)
      )

      if (error) {
        console.error('Erro ao buscar informações dos usuários:', error)
        return
      }

      const infos = {}
      const urlImage = {}

      for (const user of result.data) {
        infos[user.id] = user
        urlImage[user.id] = user.profilePicturePath
          ? `${baseURL}/uploads/${user.profilePicturePath}`
          : null
      }

      setUsersInfo(infos)
      setImages(urlImage)
    }

    fetchManyUsersInfo()
  }, [JSON.stringify(userIds)])

  return { usersInfo, images }
}

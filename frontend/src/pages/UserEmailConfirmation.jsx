import authAxiosInstance from '@/api/authAxiosInstance'
import Spinner from '@/components/ui/spinner'
import { useAuth } from '@/context/useAuth'
import useFetchUserInfo from '@/hooks/useFetchUserInfo'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'

export default function UserEmailConfirmation() {
  const [isLoading, setIsLoading] = useState(true)
  const [updateResult, setUpdateResult] = useState(undefined)

  const { userInfo, fetchUserInfo } = useFetchUserInfo()

  const { authLoading } = useAuth()

  const updateUserAccess = async () => {
    const [error] = await safeAwait(authAxiosInstance.put('/api/user/confirm'))

    if (error) {
      setUpdateResult(error.response.data.message)
      return
    }

    fetchUserInfo()
  }

  useEffect(() => {
    if (!userInfo) {
      fetchUserInfo()
      return
    }

    userInfo.isConfirmed
      ? setUpdateResult('Email confirmado com sucesso')
      : updateUserAccess()

    setIsLoading(false)
  }, [fetchUserInfo])

  return (
    <>
      {isLoading || authLoading ? (
        <Spinner className=" w-8 h-8 mt-4 animate-spin" />
      ) : (
        <>{updateResult}</>
      )}
    </>
  )
}

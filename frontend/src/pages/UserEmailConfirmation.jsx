import authAxiosInstance from '@/api/authAxiosInstance'
import Spinner from '@/components/ui/spinner'
import { useAuth } from '@/context/useAuth'
import useFetchUserInfo from '@/hooks/useFetchUserInfo'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Button } from '@/components/ui/button'
import { CheckMark } from '@/components/ui/checkmark'

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
    <div className="flex flex-col items-center justify-center gap-6 h-screen">
      {isLoading || authLoading ? (
        <>
          <Spinner className="w-8 h-8 mt-4 animate-spin" />
          <p>Aguarde enquanto confirmamos sua conta!</p>
        </>
      ) : (
        <>
          <CheckMark className="w-24 h-24 text-green-500" />
          <h1 className="text-3xl">Tudo certo!</h1>
          <p>{updateResult}</p>
          <p className="text-sm text-gray-500">
            Agora você pode acessar todas as funcionalidades do sistema.
          </p>
          <Link to="/categorias">
            <Button className="mt-4 px-4 py-2 rounded bg-custom-header-cyan text-black" variant="default">
              Acessar cursos
            </Button>
          </Link>
        </>
      )}
    </div>
  )
}
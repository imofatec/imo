import { useEffect, useRef, useState } from 'react'
import { LoaderCircle } from 'lucide-react'
import { useNavigate } from 'react-router-dom'
import { toast } from 'sonner'
import { useUser } from '@/contexts/UserContext'
import { showRequestErrorToast } from '@/lib/requestToast'
import { confirmUserRequest } from '@/services/user/confirmUserRequest'

const REDIRECT_DELAY = 3000

export default function UserConfirmation() {
  const navigate = useNavigate()
  const { refetch } = useUser()
  const hasStartedConfirmation = useRef(false)
  const [isConfirming, setIsConfirming] = useState(true)
  const [statusMessage, setStatusMessage] = useState(
    'Estamos confirmando a sua conta. Aguarde um instante.'
  )

  useEffect(() => {
    if (hasStartedConfirmation.current) return

    hasStartedConfirmation.current = true

    let redirectTimeoutId: number | undefined

    async function confirmCurrentUser() {
      try {
        await confirmUserRequest()
        await refetch()

        setIsConfirming(false)
        setStatusMessage(
          'Sua conta foi confirmada com sucesso. Você será redirecionado para a página inicial.'
        )

        redirectTimeoutId = window.setTimeout(() => {
          toast.success('Conta confirmada', {
            id: 'user-confirmation-success',
            description: 'Sua conta foi confirmada com sucesso.',
            duration: 3000,
          })

          navigate('/', { replace: true })
        }, REDIRECT_DELAY)
      } catch (error) {
        setIsConfirming(false)
        setStatusMessage('Não foi possível confirmar a sua conta. Tente novamente mais tarde.')

        showRequestErrorToast(
          error,
          'Ocorreu um erro ao tentar confirmar a sua conta. Por favor, tente novamente.',
          { id: 'user-confirmation-error' }
        )
      }
    }

    void confirmCurrentUser()

    return () => {
      if (redirectTimeoutId) {
        window.clearTimeout(redirectTimeoutId)
      }
    }
  }, [navigate, refetch])

  return (
    <div className="flex flex-1 items-center justify-center px-4">
      <div className="w-full max-w-xl rounded-3xl border border-white/10 bg-white/5 px-8 py-10 text-center shadow-[0_20px_60px_rgba(0,0,0,0.35)] backdrop-blur-sm">
        <h1 className="text-2xl font-bold text-white">Confirmação de e-mail</h1>

        <div className="mt-6 flex justify-center">
          {isConfirming ? <LoaderCircle className="text-cyan h-10 w-10 animate-spin" /> : null}
        </div>

        <p className="mt-6 text-base leading-7 text-white/80">{statusMessage}</p>
      </div>
    </div>
  )
}

import { Github, Linkedin, LoaderCircle } from 'lucide-react'
import { useAuth } from '@/contexts/AuthContext'
import FormInput from '@/components/ui/FormInput'
import Button from '@/components/ui/Button'
import Divider from '@/components/ui/divider'
import SocialAccounts from '@/components/auth/SocialAccounts'
import AuthLinks from '@/components/auth/AuthLinksLogin'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import type { LoginData } from '@/schemas/auth/loginSchema'
import { loginSchema } from '@/schemas/auth/loginSchema'
import { loginRequest } from '@/services/user/loginRequest'
import { showRequestErrorToast } from '@/lib/requestToast'
import { useNavigate, useLocation } from 'react-router-dom'
import { useEffect, useRef } from 'react'
import { toast } from 'sonner'

type LoginLocationState = {
  from?: string
  registrationSuccess?: boolean
  registeredEmail?: string
}

export default function Login() {
  const navigate = useNavigate()
  const location = useLocation()
  const { login } = useAuth()
  const locationState = (location.state as LoginLocationState | null) ?? null
  const hasShownRegistrationToast = useRef(false)

  const from = locationState?.from || '/test'

  useEffect(() => {
    if (!locationState?.registrationSuccess || hasShownRegistrationToast.current) return

    hasShownRegistrationToast.current = true

    toast.success('E-mail de confirmacao enviado', {
      id: 'registration-success',
      description: locationState.registeredEmail
        ? `Enviamos um e-mail de confirmacao para ${locationState.registeredEmail}.`
        : 'Enviamos um e-mail de confirmacao para o seu e-mail cadastrado.',
      duration: 5000,
    })

    navigate(location.pathname, {
      replace: true,
      state: locationState.from ? { from: locationState.from } : null,
    })
  }, [location.pathname, locationState, navigate])

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<LoginData>({
    resolver: zodResolver(loginSchema),
    mode: 'onBlur',
    reValidateMode: 'onChange',
  })

  async function handleLogin(data: LoginData) {
    try {
      const response = await loginRequest(data)
      login(response.accessToken)
      reset()
      navigate(from, { replace: true })
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar fazer login. Por favor, tente novamente.',
        { id: 'login-error' }
      )
    }
  }

  return (
    <div className="flex flex-1 flex-col items-center justify-center text-white">
      <form onSubmit={handleSubmit(handleLogin)} className="flex w-96 flex-col gap-2 p-8">
        <div className="flex flex-col items-center">
          <h1 className="text-center text-xl font-bold">Login</h1>
        </div>

        <FormInput
          type="text"
          id="email"
          placeholder="Email"
          label="Email"
          error={errors.email?.message}
          {...register('email')}
        />
        <FormInput
          type="password"
          id="password"
          placeholder="Senha"
          label="Senha"
          error={errors.password?.message}
          {...register('password')}
        />
        <Button variant="cyanOutline" className="text-cyan" disabled={isSubmitting}>
          {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Entrar'}
        </Button>
      </form>

      <div className="flex flex-col gap-6">
        <Divider text="Entre com outras contas" />
        <SocialAccounts providers={[Github, Linkedin]} />
        <AuthLinks />
      </div>
    </div>
  )
}

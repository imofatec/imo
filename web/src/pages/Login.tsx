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
import { useNavigate, useLocation } from 'react-router-dom'
import { useState } from 'react'

export default function Login() {
  const navigate = useNavigate()
  const location = useLocation()
  const { login } = useAuth()
  const [errorMessage, setErrorMessage] = useState<string | null>(null)

  const from = location.state?.from || '/test'

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
    setErrorMessage(null)
    try {
      const response = await loginRequest(data)
      login(response.accessToken)
      reset()
      navigate(from, { replace: true })
    } catch (error: any) {
      setErrorMessage(
        error.response?.data?.message ||
          'Ocorreu um erro ao tentar fazer login. Por favor, tente novamente.'
      )
    }
  }

  return (
    <div className="flex flex-1 flex-col items-center justify-center text-white">
      <form
        onSubmit={handleSubmit(handleLogin)}
        className="flex w-96 flex-col items-center gap-2 p-8"
      >
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
        <Button className="bg-cyan mt-5 text-black w-full" disabled={isSubmitting}>
          {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Entrar'}
        </Button>
        {errorMessage && <p className="mt-1 text-sm text-red-500">{errorMessage}</p>}
      </form>

      <div className="flex flex-col gap-6">
        <Divider text="Entre com outras contas" />
        <SocialAccounts providers={[Github, Linkedin]} />
        <AuthLinks />
      </div>
    </div>
  )
}

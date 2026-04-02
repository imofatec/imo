import { Github, Linkedin, LoaderCircle } from 'lucide-react'
import FormInput from '@/components/ui/FormInput'
import Button from '@/components/ui/Button'
import Divider from '@/components/ui/divider'
import SocialAccounts from '@/components/auth/SocialAccounts'
import AuthLinks from '@/components/auth/AuthLinksRegister'
import type { RegisterData } from '@/schemas/auth/registerSchema'
import { registerSchema } from '@/schemas/auth/registerSchema'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { registerRequest } from '@/services/user/registerRequest'

export default function Register() {
  const navigate = useNavigate()
  const [errorMessage, setErrorMessage] = useState<string | null>(null)

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<RegisterData>({
    resolver: zodResolver(registerSchema),
    mode: 'onBlur',
    reValidateMode: 'onChange',
  })

  async function handleRegister(data: RegisterData) {
    setErrorMessage(null)
    try {
      await registerRequest(data)
      navigate('/login')
      reset()
    } catch (error: any) {
      setErrorMessage(
        error.response?.data?.message ||
          'Ocorreu um erro ao tentar fazer seu cadastro. Por favor, tente novamente.'
      )
    }
  }
  return (
    <div className="flex flex-1 flex-col items-center justify-center text-white">
      <form onSubmit={handleSubmit(handleRegister)} className="flex w-96 flex-col gap-2 p-8">
        <div className="flex flex-col items-center">
          <h1 className="text-center text-xl font-bold">Cadastro</h1>
        </div>

        <FormInput
          type="text"
          id="name"
          placeholder="Nome"
          label="Nome"
          {...register('name')}
          error={errors.name?.message}
        />

        <FormInput
          type="text"
          id="email"
          placeholder="Email"
          label="Email"
          {...register('email')}
          error={errors.email?.message}
        />

        <FormInput
          type="password"
          id="password"
          placeholder="Senha"
          label="Senha"
          {...register('password')}
          error={errors.password?.message}
        />

        <FormInput
          type="password"
          id="confirmPassword"
          placeholder="Confirmar senha"
          label="Confirmar senha"
          {...register('confPassword')}
          error={errors.confPassword?.message}
        />

        <Button className="bg-cyan text-black" disabled={isSubmitting}>
          {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Cadastrar'}
        </Button>
        {errorMessage && (
          <p role="alert" className="mt-1 text-sm text-red-500">
            {errorMessage}
          </p>
        )}
      </form>

      <div className="flex flex-col gap-6">
        <Divider text="Entre com outras contas" />
        <SocialAccounts providers={[Github, Linkedin]} />
        <AuthLinks />
      </div>
    </div>
  )
}

import { Button } from '@/components/ui/button'
import { InputLabel } from '@/components/ui/inputs/inputlabel'
import { Form, Link, useActionData } from 'react-router-dom'
import { Github, Linkedin } from 'lucide-react'
import { Titulo } from '@/components/ui/titulo'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { useEffect, useState } from 'react'
import { registerRequest } from '@/requests/user/registerRequest'
import { registerSchema } from '@/schemas/userRegisterSchema'
import { useFormValidator } from '@/hooks/useFormValidator'

export default function SignUp() {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const actionData = useActionData()

  const {
    formData,
    fieldErrors,
    handleChange,
    setFieldErrors
  } = useFormValidator(registerSchema, {
    email: '',
    name: '',
    password: '',
    confPassword: ''
  })

  useEffect(() => {
    if (actionData) {
      if (actionData.fieldErrors) {
        setFieldErrors(actionData.fieldErrors)
      }
  
      if (actionData.error) {
        setError(actionData.error)
      }
  
      setIsLoading(false)
    }
  }, [actionData])

  return (
    <>
      <Titulo titulo={'Cadastro / IMO'} />

      <div className="flex justify-center items-center bg-custom-dark-purple pb-8">
        <div className="h-screen mt-[3.125rem] text-white">
          <Form
            method="post"
            action={registerRequest}
            className="w-96 p-8 space-y-6"
          >
            <div className="flex flex-col items-center">
              <h1 className="text-xl font-bold text-center">Cadastro</h1>
            </div>

            <InputLabel
              type="text"
              id="email"
              name="email"
              placeholder="Email"
              label="Email"
              value={formData.email}
              onChange={handleChange}
              className={fieldErrors.email ? 'border-red-500 focus:border-red-500' : ''}
            />
            {fieldErrors.email && (
              <p className="text-sm text-red-500 !mt-0">{fieldErrors.email}</p>
            )}

            <InputLabel
              type="text"
              id="name"
              name="name"
              placeholder="Nome"
              label="Nome"
              value={formData.name}
              onChange={handleChange}
              className={fieldErrors.name ? 'border-red-500 focus:border-red-500' : ''}
            />
            {fieldErrors.name && (
              <p className="text-sm text-red-500 !mt-0">{fieldErrors.name}</p>
            )}

            <InputLabel
              type="password"
              id="password"
              name="password"
              placeholder="Senha"
              label="Senha"
              value={formData.password}
              onChange={handleChange}
              className={fieldErrors.password ? 'border-red-500 focus:border-red-500' : ''}
            />
            {fieldErrors.password && (
              <p className="text-sm text-red-500 !mt-0">{fieldErrors.password}</p>
            )}

            <InputLabel
              type="password"
              id="confPassword"
              name="confPassword"
              placeholder="Confirmar senha"
              label="Confirmar senha"
              value={formData.confPassword}
              onChange={handleChange}
              className={fieldErrors.confPassword ? 'border-red-500 focus:border-red-500' : ''}
            />
            {fieldErrors.confPassword && (
              <p className="text-sm text-red-500 !mt-0">{fieldErrors.confPassword}</p>
            )}

            <SpinnerButton
              children="Cadastrar"
              isLoading={isLoading}
              onClick={() => {
                setIsLoading(true)
                setError(null)
              }}
              className="w-full bg-custom-header-cyan text-black"
            />


            <div className="flex items-center justify-center h-8">
              <div className="h-[1px] w-12 bg-custom-border-gray"></div>
              <p className="text-custom-text-gray px-4">
                Entre com outras contas
              </p>
              <div className="h-[1px] w-12 bg-custom-border-gray"></div>
            </div>

            <div className="flex justify-center gap-3">
              <Github />
              <Linkedin />
            </div>

            <div className="flex justify-center">
              <p className="text-custom-text-gray">
                Já tem uma conta?{' '}
                <Link to="/login" className="text-white hover:underline">
                  Fazer login
                </Link>
              </p>
            </div>  
          </Form>
        </div>
      </div>
    </>
  )
}

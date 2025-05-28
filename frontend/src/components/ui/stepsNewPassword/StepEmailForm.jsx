import { Form, Link, useActionData } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { InputLabel } from '@/components/ui/inputs/inputlabel'
import { forgetPasswordRequest } from '@/requests/user/forgetPasswordRequest'
import { Github, Linkedin } from 'lucide-react'

export default function StepEmailForm({ onSuccess }) {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const actionData = useActionData()

  useEffect(() => {
    if (actionData?.user?.id) {
      onSuccess(actionData.user.id)
      setIsLoading(false)
    } else if (actionData?.error) {
      setError(actionData.error)
      setIsLoading(false)
    }
  }, [actionData, onSuccess])

  console.log('actionData', actionData)

  return (
    <div className="flex justify-center bg-custom-dark-purple h-screen text-white">
      <div className="h-screen mt-[3.125rem] text-white">
        <Form
          method="post"
          className="w-96 p-8 space-y-6"
          action={forgetPasswordRequest}
        >
          <div className="flex flex-col items-center">
            <h1 className="text-xl font-bold text-center">Recuperar senha</h1>
          </div>

          <InputLabel
            type="text"
            id="email"
            name="email"
            placeholder="Email"
            label="Email"
          />

          <SpinnerButton
            children="Enviar código de recuperação"
            isLoading={isLoading}
            onClick={() => {
              setIsLoading(true)
              setError(null)
            }}
            className="w-full bg-custom-header-cyan text-black"
          />

          {error && <div className="h-1 text-center text-red-500">{error}</div>}

          <div className="flex items-center justify-center h-8">
            <div className="h-[1px] w-full bg-custom-border-gray"></div>
          </div>

          <div className="flex justify-center ">
            <p className="text-custom-text-gray">
              <Link to="/login" className="text-white hover:underline">
                Voltar à pagina de login
              </Link>
            </p>
          </div>
        </Form>
      </div>
    </div>
  )
}

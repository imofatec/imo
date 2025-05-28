import { Form, Link, useActionData } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { InputLabel } from '@/components/ui/inputs/inputlabel'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { verificationCodeRequest } from '@/requests/user/verificationCodeRequest'
import { Github, Linkedin } from 'lucide-react'

export default function StepVerificationForm({ userId, onSuccess }) {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const [code, setCode] = useState('')
  const actionData = useActionData()

  useEffect(() => {
    if (actionData?.success) {
      onSuccess(code)
      setIsLoading(false)
    } else if (actionData?.error) {
      setError(actionData.error)
      setIsLoading(false)
    }
  }, [actionData, onSuccess])

  return (
    <div className="flex justify-center bg-custom-dark-purple h-screen text-white">
      <div className="h-screen mt-[3.125rem] text-white">
        <Form
          method="post"
          className="w-96 p-8 space-y-6"
          action={verificationCodeRequest}
        >
          <div className="flex flex-col items-center">
            <h1 className="text-xl font-bold text-center">Verificação</h1>
          </div>

          <InputLabel
            type="text"
            id="VerificationCode"
            name="VerificationCode"
            placeholder="Código de verificação"
            label="Código de verificação"
            value={code}
            onChange={(e) => setCode(e.target.value)}
          />
          <input type="hidden" name="UserId" id="UserId" value={userId} />

          <SpinnerButton
            children="Validar código"
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

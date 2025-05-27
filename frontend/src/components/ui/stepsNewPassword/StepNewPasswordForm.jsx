import { Form, useActionData, useNavigate, Link } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { InputLabel } from '@/components/ui/inputs/inputlabel'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { resetPasswordRequest } from '@/requests/user/resetPasswordRequest'

export default function StepNewPasswordForm({ userId, VerificationCode }) {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const actionData = useActionData()

  useEffect(() => {
    if (actionData?.error) {
      setError(actionData.error)
      setIsLoading(false)
    }
  }, [actionData])

  return (
    <div className="flex justify-center bg-custom-dark-purple h-screen text-white">
      <div className="h-screen mt-[3.125rem] text-white">
        <Form
          method="post"
          className="w-96 p-8 space-y-6"
          action={resetPasswordRequest}
        >
          <div className="flex flex-col items-center">
            <h1 className="text-xl font-bold text-center">Nova senha</h1>
          </div>

          <InputLabel
            type="password"
            id="newPassword"
            name="newPassword"
            placeholder="Digite sua nova senha"
            label="Nova senha"
          />

          <InputLabel
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            placeholder="Confirme sua nova senha"
            label="Confirme sua nova senha"
          />

          <input type="hidden" id="userid" name="userid" value={userId} />

          <input
            type="hidden"
            id="verificationcode"
            name="verificationcode"
            value={VerificationCode}
          />

          <SpinnerButton
            children="Redefinir senha"
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

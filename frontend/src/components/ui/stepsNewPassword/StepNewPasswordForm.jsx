// components/StepNewPasswordForm.jsx
import { Link, useNavigate } from 'react-router-dom'
import { InputLabel } from '@/components/ui/inputs/inputlabel'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { resetPasswordRequest } from '@/requests/user/resetPasswordRequest'
import { resetPasswordSchema } from '@/schemas/resetPasswordSchema'
import { useState } from 'react'
import { useFormValidator } from '@/hooks/useFormValidator'

export default function StepNewPasswordForm({ userId, VerificationCode }) {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const navigate = useNavigate()

  const {
    formData,
    fieldErrors,
    handleChange,
    setFieldErrors
  } = useFormValidator(resetPasswordSchema)

  return (
    <div className="flex justify-center bg-custom-dark-purple h-screen text-white">
      <div className="h-screen mt-[3.125rem] text-white">
        <div className="w-96 p-8 space-y-6">
          <div className="flex flex-col items-center">
            <h1 className="text-xl font-bold text-center">Nova senha</h1>
          </div>

          <InputLabel
            type="password"
            id="password"
            name="password"
            placeholder="Digite sua nova senha"
            label="Nova senha"
            value={formData.password || ''}
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
            placeholder="Confirme sua nova senha"
            label="Confirme sua nova senha"
            value={formData.confPassword || ''}
            onChange={handleChange}
            className={fieldErrors.confPassword ? 'border-red-500 focus:border-red-500' : ''}
          />
          {fieldErrors.confPassword && (
            <p className="text-sm text-red-500 !mt-0">
              {fieldErrors.confPassword === 'Required' ? 'Obrigatório' : fieldErrors.confPassword}
            </p>
          )}

          <input type="hidden" id="userid" name="userid" value={userId} />
          <input
            type="hidden"
            id="verificationcode"
            name="verificationcode"
            value={VerificationCode}
          />

          <SpinnerButton
            type="submit"
            isLoading={isLoading}
            className="w-full bg-custom-header-cyan text-black"
            onClick={async () => {
              setIsLoading(true)
              setError(null)
              setFieldErrors({})

              const result = await resetPasswordRequest({
                password: formData.password,
                confPassword: formData.confPassword,
                userId,
                verificationCode: VerificationCode
              })

              if (result?.error) {
                setError(result.error)
                setIsLoading(false)
              } else if (result?.fieldErrors) {
                setFieldErrors(result.fieldErrors)
                setIsLoading(false)
              } else if (result?.success) {
                navigate('/login')
              } else {
                setIsLoading(false)
              }
            }}
          >
            Redefinir senha
          </SpinnerButton>

          {error && <div className="h-1 text-center text-red-500">{error}</div>}

          <div className="flex items-center justify-center h-8">
            <div className="h-[1px] w-full bg-custom-border-gray"></div>
          </div>

          <div className="flex justify-center">
            <p className="text-custom-text-gray">
              <Link to="/login" className="text-white hover:underline">
                Voltar à página de login
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}
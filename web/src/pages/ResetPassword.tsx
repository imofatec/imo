import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import ResetPasswordCodeStep from '@/components/auth/ResetPasswordCodeStep'
import ResetPasswordEmailStep from '@/components/auth/ResetPasswordEmailStep'
import ResetPasswordNewPasswordStep from '@/components/auth/ResetPasswordNewPasswordStep'
import ResetPasswordStepIndicator from '@/components/auth/ResetPasswordStepIndicator'
import { showRequestErrorToast } from '@/lib/requestToast'
import { toast } from 'sonner'
import { resetPasswordRequest } from '@/services/user/resetPasswordRequest'
import { sendRecoveryCodeRequest } from '@/services/user/sendRecoveryCodeRequest'
import { verifyRecoveryCodeRequest } from '@/services/user/verifyRecoveryCodeRequest'

type EmailStepData = {
  email: string
}

type CodeStepData = {
  code: string
}

type NewPasswordStepData = {
  password: string
  confirmPassword: string
}

const steps = [
  {
    number: 1,
    title: 'Informe seu e-mail',
    description: 'Digite o e-mail cadastrado para iniciarmos a recuperação da sua senha.',
  },
  {
    number: 2,
    title: 'Digite o código',
    description: 'Insira o código de 6 dígitos que você recebeu.',
  },
  {
    number: 3,
    title: 'Defina uma nova senha',
    description: 'Escolha a nova senha para concluir a recuperação.',
  },
] as const

export default function ResetPasswordPage() {
  const navigate = useNavigate()
  const [currentStep, setCurrentStep] = useState(1)
  const [email, setEmail] = useState('')
  const [code, setCode] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [isSendingCode, setIsSendingCode] = useState(false)
  const [isVerifyingCode, setIsVerifyingCode] = useState(false)
  const [isResettingPassword, setIsResettingPassword] = useState(false)

  const currentStepContent = steps[currentStep - 1]

  function handleAdvanceStep() {
    setCurrentStep((step) => Math.min(step + 1, steps.length))
  }

  function handleGoBackStep() {
    setCurrentStep((step) => Math.max(step - 1, 1))
  }

  async function handleSendCode(data: EmailStepData) {
    try {
      setIsSendingCode(true)
      const trimmedEmail = data.email.trim()
      const response = await sendRecoveryCodeRequest({ email: trimmedEmail })
      setEmail(trimmedEmail)

      toast.success('Código enviado', {
        id: 'recovery-send-code-success',
        description: response.message,
        duration: 5000,
      })

      handleAdvanceStep()
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar enviar o código de recuperação. Por favor, tente novamente.',
        { id: 'recovery-send-code-error' }
      )
    } finally {
      setIsSendingCode(false)
    }
  }

  async function handleVerifyCode(data: CodeStepData) {
    try {
      setIsVerifyingCode(true)
      const trimmedCode = data.code.trim()
      const response = await verifyRecoveryCodeRequest({
        email: email.trim(),
        code: trimmedCode,
      })
      setCode(trimmedCode)

      toast.success('Código validado', {
        id: 'recovery-verify-code-success',
        description: response.message,
        duration: 5000,
      })

      handleAdvanceStep()
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar validar o código de recuperação. Por favor, tente novamente.',
        { id: 'recovery-verify-code-error' }
      )
    } finally {
      setIsVerifyingCode(false)
    }
  }

  async function handleResetPassword(data: NewPasswordStepData) {
    if (data.password !== data.confirmPassword) {
      toast.error('As senhas não coincidem', {
        id: 'recovery-password-mismatch',
        description: 'Verifique os campos de senha e tente novamente.',
        duration: 5000,
      })
      return
    }

    try {
      setIsResettingPassword(true)
      setPassword(data.password)
      setConfirmPassword(data.confirmPassword)

      const response = await resetPasswordRequest({
        email: email.trim(),
        code: code.trim(),
        newPassword: data.password,
      })

      toast.success('Senha redefinida', {
        id: 'recovery-reset-password-success',
        description: response.message,
        duration: 5000,
      })

      navigate('/login', { replace: true })
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar redefinir sua senha. Por favor, tente novamente.',
        { id: 'recovery-reset-password-error' }
      )
    } finally {
      setIsResettingPassword(false)
    }
  }

  function renderCurrentStepForm() {
    if (currentStep === 1) {
      return (
        <ResetPasswordEmailStep
          defaultEmail={email}
          onContinue={handleSendCode}
          isSubmitting={isSendingCode}
        />
      )
    }

    if (currentStep === 2) {
      return (
        <ResetPasswordCodeStep
          defaultCode={code}
          onContinue={handleVerifyCode}
          onBack={handleGoBackStep}
          isSubmitting={isVerifyingCode}
        />
      )
    }

    return (
      <ResetPasswordNewPasswordStep
        defaultPassword={password}
        defaultConfirmPassword={confirmPassword}
        onSubmit={handleResetPassword}
        onBack={handleGoBackStep}
        isSubmitting={isResettingPassword}
      />
    )
  }

  return (
    <div className="flex flex-1 flex-col items-center justify-center px-4 py-10 text-white">
      <div className="w-full max-w-md">
        <ResetPasswordStepIndicator currentStep={currentStep} steps={steps} />

        <div className="flex flex-col p-8">
          <div className="mb-2 flex flex-col items-center">
            <p className="text-cyan text-sm">Etapa {currentStep} de {steps.length}</p>
            <h1 className="text-center text-xl font-bold">{currentStepContent.title}</h1>
            <p className="mt-2 text-center text-sm text-white/70">
              {currentStepContent.description}
            </p>
          </div>

          <div className="flex min-h-70 flex-col gap-2">{renderCurrentStepForm()}</div>
        </div>

        <p className="text-center text-sm text-white/60">
          Lembrou sua senha?{' '}
          <Link to="/login" className="text-white hover:underline">
            Voltar para o login
          </Link>
        </p>
      </div>
    </div>
  )
}

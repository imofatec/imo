import { useState } from 'react'
import { Link } from 'react-router-dom'
import ResetPasswordCodeStep from '@/components/auth/ResetPasswordCodeStep'
import ResetPasswordEmailStep from '@/components/auth/ResetPasswordEmailStep'
import ResetPasswordNewPasswordStep from '@/components/auth/ResetPasswordNewPasswordStep'
import ResetPasswordStepIndicator from '@/components/auth/ResetPasswordStepIndicator'

const steps = [
  {
    number: 1,
    title: 'Informe seu e-mail',
    description: 'Digite o e-mail cadastrado para iniciarmos a recuperação da sua senha.',
  },
  {
    number: 2,
    title: 'Digite o código',
    description: 'Insira o código de 5 dígitos que você recebeu.',
  },
  {
    number: 3,
    title: 'Defina uma nova senha',
    description: 'Escolha a nova senha para concluir a recuperação.',
  },
] as const

export default function ResetPasswordPage() {
  const [currentStep, setCurrentStep] = useState(1)
  const [email, setEmail] = useState('')
  const [code, setCode] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const currentStepContent = steps[currentStep - 1]

  function handleAdvanceStep() {
    setCurrentStep((step) => Math.min(step + 1, steps.length))
  }

  function handleGoBackStep() {
    setCurrentStep((step) => Math.max(step - 1, 1))
  }

  function renderCurrentStepForm() {
    if (currentStep === 1) {
      return (
        <ResetPasswordEmailStep
          email={email}
          onEmailChange={setEmail}
          onContinue={handleAdvanceStep}
        />
      )
    }

    if (currentStep === 2) {
      return (
        <ResetPasswordCodeStep
          code={code}
          onCodeChange={setCode}
          onContinue={handleAdvanceStep}
          onBack={handleGoBackStep}
        />
      )
    }

    return (
      <ResetPasswordNewPasswordStep
        password={password}
        confirmPassword={confirmPassword}
        onPasswordChange={setPassword}
        onConfirmPasswordChange={setConfirmPassword}
        onBack={handleGoBackStep}
      />
    )
  }

  return (
    <div className="flex flex-1 flex-col items-center justify-center px-4 py-10 text-white">
      <div className="w-full max-w-md">
        <ResetPasswordStepIndicator currentStep={currentStep} steps={steps} />

        <form className="flex flex-col p-8">
          <div className="mb-2 flex flex-col items-center">
            <p className="text-cyan text-sm">Etapa {currentStep} de {steps.length}</p>
            <h1 className="text-center text-xl font-bold">{currentStepContent.title}</h1>
            <p className="mt-2 text-center text-sm text-white/70">
              {currentStepContent.description}
            </p>
          </div>

          <div className="flex min-h-70 flex-col gap-2">{renderCurrentStepForm()}</div>
        </form>

        <p className="text-center text-sm text-white/60">
          Lembrou sua senha?{'  '}
          <Link to="/login" className="text-white hover:underline">
            Voltar para o login
          </Link>
        </p>
      </div>
    </div>
  )
}

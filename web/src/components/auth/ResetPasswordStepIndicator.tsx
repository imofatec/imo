type Step = {
  number: number
}

type Props = {
  currentStep: number
  steps: readonly Step[]
}

export default function ResetPasswordStepIndicator({ currentStep, steps }: Props) {
  return (
    <div className="mb-6 flex gap-2">
      {steps.map((step) => (
        <div
          key={step.number}
          className={`h-1.5 flex-1 rounded-full ${
            step.number <= currentStep ? 'bg-cyan' : 'bg-white/10'
          }`}
        />
      ))}
    </div>
  )
}

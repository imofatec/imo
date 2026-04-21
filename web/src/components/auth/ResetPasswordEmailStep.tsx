import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'

type Props = {
  email: string
  onEmailChange: (value: string) => void
  onContinue: () => void
}

export default function ResetPasswordEmailStep({
  email,
  onEmailChange,
  onContinue,
}: Props) {
  return (
    <>
      <FormInput
        id="recovery-email"
        type="email"
        label="E-mail"
        placeholder="Digite seu e-mail"
        value={email}
        onChange={(event) => onEmailChange(event.target.value)}
      />

      <Button
        type="button"
        variant="cyanOutline"
        className="text-cyan"
        onClick={onContinue}
        disabled={!email.trim()}
      >
        Continuar
      </Button>
    </>
  )
}

import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'

type Props = {
  password: string
  confirmPassword: string
  onPasswordChange: (value: string) => void
  onConfirmPasswordChange: (value: string) => void
  onBack: () => void
}

export default function ResetPasswordNewPasswordStep({
  password,
  confirmPassword,
  onPasswordChange,
  onConfirmPasswordChange,
  onBack,
}: Props) {
  return (
    <>
      <FormInput
        id="new-password"
        type="password"
        label="Nova senha"
        placeholder="Digite a nova senha"
        value={password}
        onChange={(event) => onPasswordChange(event.target.value)}
      />

      <FormInput
        id="confirm-new-password"
        type="password"
        label="Confirmar nova senha"
        placeholder="Confirme a nova senha"
        value={confirmPassword}
        onChange={(event) => onConfirmPasswordChange(event.target.value)}
      />

      <Button
        type="button"
        variant="cyanOutline"
        className="text-cyan"
        disabled={!password.trim() || !confirmPassword.trim()}
      >
        Redefinir senha
      </Button>

      <Button
        type="button"
        variant="cyanOutline"
        className="mt-2 text-cyan"
        onClick={onBack}
      >
        Voltar
      </Button>
    </>
  )
}

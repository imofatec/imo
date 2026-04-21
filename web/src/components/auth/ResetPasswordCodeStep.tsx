import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'

type Props = {
  code: string
  onCodeChange: (value: string) => void
  onContinue: () => void
  onBack: () => void
}

export default function ResetPasswordCodeStep({
  code,
  onCodeChange,
  onContinue,
  onBack,
}: Props) {
  return (
    <>
      <FormInput
        id="recovery-code"
        type="text"
        label="Código"
        placeholder="Digite o código de 5 dígitos"
        value={code}
        onChange={(event) => onCodeChange(event.target.value.replace(/\D/g, '').slice(0, 5))}
        inputMode="numeric"
        maxLength={5}
      />

      <Button
        type="button"
        variant="cyanOutline"
        className="text-cyan"
        onClick={onContinue}
        disabled={code.length !== 5}
      >
        Continuar
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

import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import { LoaderCircle } from 'lucide-react'
import { useEffect } from 'react'
import { useForm } from 'react-hook-form'

type FormData = {
  code: string
}

type Props = {
  defaultCode: string
  onContinue: (data: FormData) => void | Promise<void>
  onBack: () => void
  isSubmitting: boolean
}

export default function ResetPasswordCodeStep({
  defaultCode,
  onContinue,
  onBack,
  isSubmitting,
}: Props) {
  const { register, handleSubmit, reset, setValue, watch } = useForm<FormData>({
    defaultValues: {
      code: defaultCode,
    },
  })

  const code = watch('code')

  useEffect(() => {
    reset({ code: defaultCode })
  }, [defaultCode, reset])

  return (
    <form onSubmit={handleSubmit(onContinue)} className="flex flex-col gap-2">
      <FormInput
        id="recovery-code"
        type="text"
        label="Código"
        placeholder="Digite o código de 6 dígitos"
        {...register('code')}
        onChange={(event) => setValue('code', event.target.value.replace(/\D/g, '').slice(0, 6))}
        inputMode="numeric"
        maxLength={6}
      />

      <Button
        type="submit"
        variant="cyanOutline"
        className="text-cyan"
        disabled={code.length !== 6 || isSubmitting}
      >
        {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Continuar'}
      </Button>

      <Button
        type="button"
        variant="cyanOutline"
        className="mt-2 text-cyan"
        onClick={onBack}
        disabled={isSubmitting}
      >
        Voltar
      </Button>
    </form>
  )
}

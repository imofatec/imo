import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import { LoaderCircle } from 'lucide-react'
import { useEffect } from 'react'
import { useForm } from 'react-hook-form'

type FormData = {
  email: string
}

type Props = {
  defaultEmail: string
  onContinue: (data: FormData) => void | Promise<void>
  isSubmitting: boolean
}

export default function ResetPasswordEmailStep({ defaultEmail, onContinue, isSubmitting }: Props) {
  const { register, handleSubmit, reset, watch } = useForm<FormData>({
    defaultValues: {
      email: defaultEmail,
    },
  })

  const email = watch('email')

  useEffect(() => {
    reset({ email: defaultEmail })
  }, [defaultEmail, reset])

  return (
    <form onSubmit={handleSubmit(onContinue)} className="flex flex-col gap-2">
      <FormInput
        id="recovery-email"
        type="email"
        label="E-mail"
        placeholder="Digite seu e-mail"
        {...register('email')}
      />

      <Button
        type="submit"
        variant="cyanOutline"
        className="text-cyan"
        disabled={!email.trim() || isSubmitting}
      >
        {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Continuar'}
      </Button>
    </form>
  )
}

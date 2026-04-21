import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import { LoaderCircle } from 'lucide-react'
import { useEffect } from 'react'
import { useForm } from 'react-hook-form'

type FormData = {
  password: string
  confirmPassword: string
}

type Props = {
  defaultPassword: string
  defaultConfirmPassword: string
  onSubmit: (data: FormData) => void | Promise<void>
  onBack: () => void
  isSubmitting: boolean
}

export default function ResetPasswordNewPasswordStep({
  defaultPassword,
  defaultConfirmPassword,
  onSubmit,
  onBack,
  isSubmitting,
}: Props) {
  const { register, handleSubmit, reset, watch } = useForm<FormData>({
    defaultValues: {
      password: defaultPassword,
      confirmPassword: defaultConfirmPassword,
    },
  })

  const password = watch('password')
  const confirmPassword = watch('confirmPassword')

  useEffect(() => {
    reset({
      password: defaultPassword,
      confirmPassword: defaultConfirmPassword,
    })
  }, [defaultConfirmPassword, defaultPassword, reset])

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-2">
      <FormInput
        id="new-password"
        type="password"
        label="Nova senha"
        placeholder="Digite a nova senha"
        {...register('password')}
      />

      <FormInput
        id="confirm-new-password"
        type="password"
        label="Confirmar nova senha"
        placeholder="Confirme a nova senha"
        {...register('confirmPassword')}
      />

      <Button
        type="submit"
        variant="cyanOutline"
        className="text-cyan"
        disabled={!password.trim() || !confirmPassword.trim() || isSubmitting}
      >
        {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Redefinir senha'}
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

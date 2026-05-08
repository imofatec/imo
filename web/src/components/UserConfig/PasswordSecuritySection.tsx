import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import type { UpdateUserPasswordData } from '@/schemas/user/updateUserSchema'
import { LoaderCircle } from 'lucide-react'
import type { FormEventHandler } from 'react'
import type { FieldErrors, UseFormRegister } from 'react-hook-form'

type Props = {
  register: UseFormRegister<UpdateUserPasswordData>
  errors: FieldErrors<UpdateUserPasswordData>
  isSubmitting: boolean
  onSubmit: FormEventHandler<HTMLFormElement>
}

export default function PasswordSecuritySection({
  register,
  errors,
  isSubmitting,
  onSubmit,
}: Props) {
  return (
    <form onSubmit={onSubmit} className="space-y-5">
      <div className="p-4">
        <p className="text-xl text-white/80">
          Informe sua senha atual e depois escolha a nova senha antes de salvar.
        </p>
      </div>

      <div>
        <FormInput
          id="old-password"
          type="password"
          label="Senha atual"
          placeholder="Digite a senha atual"
          className="h-12 rounded-xl bg-white/5"
          error={errors.oldPassword?.message}
          {...register('oldPassword')}
        />
      </div>

      <div className="grid gap-5 md:grid-cols-2">
        <FormInput
          id="new-password"
          type="password"
          label="Nova senha"
          placeholder="Digite a nova senha"
          className="h-12 rounded-xl bg-white/5"
          error={errors.password?.message}
          {...register('password')}
        />

        <FormInput
          id="confirm-new-password"
          type="password"
          label="Confirmar nova senha"
          placeholder="Confirme a nova senha"
          className="h-12 rounded-xl bg-white/5"
          error={errors.confPassword?.message}
          {...register('confPassword')}
        />
      </div>

      <div className="flex justify-end">
        <Button
          type="submit"
          variant="cyanOutline"
          disabled={isSubmitting}
          className="border-cyan/40 text-cyan mt-0! w-auto rounded-full border px-4 py-2 text-sm"
        >
          {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Atualizar senha'}
        </Button>
      </div>
    </form>
  )
}

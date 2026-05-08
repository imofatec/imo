import TextBoxInput from '@/components/CreateCourses/TextBoxInput'
import Button from '@/components/ui/Button'
import type { UpdateUserBioData } from '@/schemas/user/updateUserSchema'
import { LoaderCircle } from 'lucide-react'
import type { FormEventHandler } from 'react'
import type { FieldErrors, UseFormRegister } from 'react-hook-form'

type Props = {
  register: UseFormRegister<UpdateUserBioData>
  errors: FieldErrors<UpdateUserBioData>
  isSubmitting: boolean
  bioValue: string
  onSubmit: FormEventHandler<HTMLFormElement>
}

export default function BioSection({
  register,
  errors,
  isSubmitting,
  bioValue,
  onSubmit,
}: Props) {
  return (
    <form onSubmit={onSubmit} className="space-y-5">
      <TextBoxInput
        id="user-bio"
        label=""
        placeholder="Digite algo sobre você"
        className="rounded-xl bg-white/5"
        maxLength={300}
        value={bioValue}
        error={errors.bio?.message}
        {...register('bio')}
      />

      <div className="flex justify-end">
        <Button
          type="submit"
          variant="cyanOutline"
          disabled={isSubmitting}
          className="border-cyan/40 text-cyan mt-0! w-auto rounded-full border px-4 py-2 text-sm"
        >
          {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Salvar bio'}
        </Button>
      </div>
    </form>
  )
}

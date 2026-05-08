import SelectInput from '@/components/CreateCourses/SelectInput'
import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import { categoryOptions } from '@/constants/courseOptions'
import {
  academicDegreeOptions,
  availableTimeOptions,
  experienceOptions,
} from '@/constants/userOptions'
import type { UpdateUserProfileData } from '@/schemas/user/updateUserSchema'
import type { CurrentUserProfile } from '@/types/user'
import { LoaderCircle } from 'lucide-react'
import type { FormEventHandler } from 'react'
import type { FieldErrors, UseFormRegister } from 'react-hook-form'

type Props = {
  user: CurrentUserProfile | null
  register: UseFormRegister<UpdateUserProfileData>
  errors: FieldErrors<UpdateUserProfileData>
  isSubmitting: boolean
  onSubmit: FormEventHandler<HTMLFormElement>
}

function getOptionLabel(
  value: string | null | undefined,
  options: { label: string; value: string }[],
  fallback: string
) {
  if (!value) return fallback

  return options.find((option) => option.value === value)?.label || value
}

export default function ProfileInfoSection({
  user,
  register,
  errors,
  isSubmitting,
  onSubmit,
}: Props) {
  return (
    <form onSubmit={onSubmit} className="space-y-5">
      <div className="grid gap-5 md:grid-cols-2">
        <FormInput
          id="user-name"
          label="Nome completo"
          placeholder={user?.name || 'Digite seu nome'}
          className="h-12 rounded-xl bg-white/5"
          error={errors.name?.message}
          {...register('name')}
        />

        <FormInput
          id="user-email"
          type="email"
          label="E-mail"
          placeholder={user?.email || 'Digite seu e-mail'}
          className="h-12 rounded-xl bg-white/5"
          error={errors.email?.message}
          {...register('email')}
        />
      </div>

      <div className="grid gap-5 md:grid-cols-2">
        <FormInput
          id="user-birth-date"
          label="Data de nascimento"
          type="date"
          placeholder={user?.birthDate || ''}
          className="h-12 rounded-xl bg-white/5"
          error={errors.birthDate?.message}
          {...register('birthDate')}
        />

        <SelectInput
          id="user-academic-degree"
          label="Formação acadêmica"
          options={academicDegreeOptions}
          placeholder={getOptionLabel(
            user?.academicDegree,
            academicDegreeOptions,
            'Selecione uma formação'
          )}
          className="h-12 rounded-xl bg-white/5"
          error={errors.academicDegree?.message}
          {...register('academicDegree')}
        />
      </div>

      <div className="grid gap-5 md:grid-cols-2">
        <SelectInput
          id="user-experience-level"
          label="Nível de experiência"
          options={experienceOptions}
          placeholder={getOptionLabel(
            user?.experienceLevel,
            experienceOptions,
            'Selecione um nível'
          )}
          className="h-12 rounded-xl bg-white/5"
          error={errors.experienceLevel?.message}
          {...register('experienceLevel')}
        />

        <SelectInput
          id="user-available-time"
          label="Tempo disponível por dia"
          options={availableTimeOptions}
          placeholder={getOptionLabel(
            user?.availableTimePerDay,
            availableTimeOptions,
            'Selecione um tempo'
          )}
          className="h-12 rounded-xl bg-white/5"
          error={errors.availableTimePerDay?.message}
          {...register('availableTimePerDay')}
        />
      </div>

      <div className="grid gap-5 md:grid-cols-2">
        <SelectInput
          id="user-category-1"
          label="Categoria de interesse 1"
          options={categoryOptions}
          placeholder={getOptionLabel(
            user?.categoriesOfInterest?.[0],
            categoryOptions,
            'Selecione uma categoria'
          )}
          className="h-12 rounded-xl bg-white/5"
          error={errors.categoryOfInterest1?.message}
          {...register('categoryOfInterest1')}
        />

        <SelectInput
          id="user-category-2"
          label="Categoria de interesse 2"
          options={categoryOptions}
          placeholder={getOptionLabel(
            user?.categoriesOfInterest?.[1],
            categoryOptions,
            'Selecione uma categoria'
          )}
          className="h-12 rounded-xl bg-white/5"
          error={errors.categoryOfInterest2?.message}
          {...register('categoryOfInterest2')}
        />
      </div>

      <div className="flex justify-end">
        <Button
          type="submit"
          variant="cyanOutline"
          disabled={isSubmitting}
          className="border-cyan/40 text-cyan mt-0! w-auto rounded-full border px-4 py-2 text-sm"
        >
          {isSubmitting ? <LoaderCircle className="animate-spin" /> : 'Salvar informações'}
        </Button>
      </div>
    </form>
  )
}

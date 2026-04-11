import SelectInput from '@/components/CreateCourses/SelectInput'
import TextBoxInput from '@/components/CreateCourses/TextBoxInput'
import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import type { User } from '@/types/user'

type Props = {
  user: User | null
}

const experienceOptions = [
  { label: 'Iniciante', value: 'BEGINNER' },
  { label: 'Intermediário', value: 'INTERMEDIATE' },
  { label: 'Avançado', value: 'ADVANCED' },
]

const availableTimeOptions = [
  { label: 'Menos de 1h por dia', value: 'LESS_THAN_1H' },
  { label: '1h a 2h por dia', value: 'BETWEEN_1H_2H' },
  { label: '2h a 4h por dia', value: 'BETWEEN_2H_4H' },
  { label: 'Mais de 4h por dia', value: 'MORE_THAN_4H' },
]

export default function ProfileInfoSection({ user }: Props) {
  const categoriesText = user?.categoriesOfInterest?.join(', ') || ''

  return (
    <div className="space-y-5">
      <div className="grid gap-5 md:grid-cols-2">
        <FormInput
          id="user-name"
          label="Nome completo"
          placeholder={user?.name || 'Digite seu nome'}
          className="h-12 rounded-xl bg-white/5"
        />

        <FormInput
          id="user-email"
          type="email"
          label="E-mail"
          placeholder={user?.email || 'Digite seu e-mail'}
          className="h-12 rounded-xl bg-white/5"
        />
      </div>

      <div className="grid gap-5 md:grid-cols-2">
        <FormInput
          id="user-birth-date"
          label="Data de nascimento"
          type="date"
          defaultValue={user?.birthDate ?? ''}
          className="h-12 rounded-xl bg-white/5"
        />

        <FormInput
          id="user-academic-degree"
          label="Formação academica"
          placeholder={user?.academicDegree || 'Ex: Graduação em Sistemas'}
          className="h-12 rounded-xl bg-white/5"
        />
      </div>

      <div className="grid gap-5 md:grid-cols-2">
        <SelectInput
          id="user-experience-level"
          label="Nível de experiência"
          options={experienceOptions}
          placeholder={user?.experienceLevel || 'Selecione um nível'}
          className="h-12 rounded-xl bg-white/5"
        />

        <SelectInput
          id="user-available-time"
          label="Tempo disponível por dia"
          options={availableTimeOptions}
          placeholder={user?.availableTimePerDay || 'Selecione um tempo'}
          className="h-12 rounded-xl bg-white/5"
        />
      </div>

      <TextBoxInput
        id="user-categories"
        label="Categorias de interesse"
        placeholder="Ex: IA, Dados, Desenvolvimento web"
        defaultValue={categoriesText}
        maxLength={500}
      />

      <div className="flex justify-end">
        <Button
          type="button"
          variant="cyanOutline"
          className="border-cyan/40 text-cyan mt-0! w-auto rounded-full border px-4 py-2 text-sm"
        >
          Salvar informações
        </Button>
      </div>
    </div>
  )
}

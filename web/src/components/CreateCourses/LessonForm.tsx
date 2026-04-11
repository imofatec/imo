import FormInput from '@/components/ui/FormInput'
import TextBoxInput from './TextBoxInput'
import type { UseFormRegister } from 'react-hook-form'
import type { CreateCourseData } from '@/schemas/courses/CreateCourseSchema'

type Props = {
  index: number
  register: UseFormRegister<CreateCourseData>
  nameLessonError?: string
  linkError?: string
  descriptionError?: string
}

export default function LessonForm({
  index,
  register,
  nameLessonError,
  linkError,
  descriptionError,
}: Props) {
  return (
    <div>
      <h3 className="mb-4 text-lg font-semibold text-white">Aula {index + 1}</h3>

      <div className="space-y-4">
        <FormInput
          label="Título da Aula"
          placeholder="Ex: Introdução"
          className="h-12 rounded-xl bg-white/5"
          error={nameLessonError}
          {...register(`lessons.${index}.nameLesson`)}
        />

        <FormInput
          label="Link do Vídeo"
          placeholder="https://youtube.com/..."
          className="h-12 rounded-xl bg-white/5"
          error={linkError}
          {...register(`lessons.${index}.link`)}
        />

        <TextBoxInput
          label="Descrição da Aula"
          placeholder="Descreva a aula..."
          error={descriptionError}
          maxLength={500}
          {...register(`lessons.${index}.descriptionL`)}
        />
      </div>
    </div>
  )
}

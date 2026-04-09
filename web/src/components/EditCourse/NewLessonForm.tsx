import TextBoxInput from '@/components/CreateCourses/TextBoxInput'
import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import type { EditCourseData } from '@/types/edit'
import type { UseFormRegister } from 'react-hook-form'

type Props = {
  index: number
  register: UseFormRegister<EditCourseData>
  nameError?: string
  linkError?: string
  descriptionError?: string
  onCancel: () => void
  onCreate: () => void
}

export default function NewLessonForm({
  index,
  register,
  nameError,
  linkError,
  descriptionError,
  onCancel,
  onCreate,
}: Props) {
  return (
    <div className="space-y-4 rounded-2xl border border-white/10 bg-white/5 p-5">
      <h3 className="text-lg font-semibold text-white">Nova aula</h3>

      <FormInput
        label="Título da aula"
        placeholder="Ex: Introdução"
        className="h-12 rounded-xl bg-white/5"
        error={nameError}
        {...register(`lessons.${index}.nameLesson` as const)}
      />

      <FormInput
        label="Link do vídeo"
        placeholder="https://youtube.com/..."
        className="h-12 rounded-xl bg-white/5"
        error={linkError}
        {...register(`lessons.${index}.link` as const)}
      />

      <TextBoxInput
        label="Descrição da aula"
        placeholder="Descreva a aula..."
        error={descriptionError}
        maxLength={500}
        {...register(`lessons.${index}.descriptionL` as const)}
      />

      <div className="flex flex-wrap justify-end gap-3">
        <Button
          type="button"
          onClick={onCancel}
          className="w-auto rounded-full border border-red-400/40 bg-red-500/10 px-4 py-2 text-sm font-medium text-white/60! hover:bg-red-500/15 hover:text-white!"
        >
          Cancelar
        </Button>

        <Button
          type="button"
          onClick={onCreate}
          variant="cyanOutline"
          className="border-cyan/40 text-cyan w-auto rounded-full border px-4 py-2 text-sm"
        >
          Criar aula
        </Button>
      </div>
    </div>
  )
}

import SelectInput from '@/components/CreateCourses/SelectInput'
import TextBoxInput from '@/components/CreateCourses/TextBoxInput'
import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import { categoryOptions, levelOptions } from '@/constants/courseOptions'
import type { EditCourseData } from '@/types/edit'
import type { FieldErrors, UseFormRegister } from 'react-hook-form'

type CoursePreview = {
  name: string
  category: string
  level: string
  description: string
}

type Props = {
  register: UseFormRegister<EditCourseData>
  errors: FieldErrors<EditCourseData>
  course: CoursePreview
  onSaveCourse: () => void
}

export default function CourseFormEdit({ register, errors, course, onSaveCourse }: Props) {
  return (
    <div className="space-y-5">
      <div className="grid gap-5 md:grid-cols-2">
        <FormInput
          label="Nome do curso"
          placeholder={course.name}
          className="h-12 rounded-xl bg-white/5"
          error={errors.nameCourse?.message}
          {...register('nameCourse')}
        />

        <SelectInput
          id="edit-category"
          label="Categoria"
          options={categoryOptions}
          placeholder={course.category}
          className="h-12 rounded-xl bg-white/5"
          error={errors.category?.message}
          {...register('category')}
        />
      </div>

      <SelectInput
        id="edit-level"
        label="Nível"
        options={levelOptions}
        placeholder={course.level}
        className="h-12 rounded-xl bg-white/5"
        error={errors.level?.message}
        {...register('level')}
      />

      <TextBoxInput
        id="edit-description"
        label="Descrição"
        placeholder={course.description}
        error={errors.description?.message}
        maxLength={500}
        {...register('description')}
      />

      <div className="flex justify-end">
        <Button
          type="button"
          onClick={onSaveCourse}
          variant="cyanOutline"
          className="border-cyan/40 text-cyan w-auto rounded-full border px-4 py-2 text-sm"
        >
          Salvar alterações
        </Button>
      </div>
    </div>
  )
}

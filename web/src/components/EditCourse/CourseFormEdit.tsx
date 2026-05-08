import SelectInput from '@/components/CreateCourses/SelectInput'
import SkillsSelectField from '@/components/CreateCourses/SkillsSelectField'
import TextBoxInput from '@/components/CreateCourses/TextBoxInput'
import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'
import { categoryOptions, levelOptions } from '@/constants/courseOptions'
import type { CreateCourseData } from '@/schemas/courses/CreateCourseSchema'
import type { CourseDetails } from '@/types/course'
import type { Skill } from '@/types/skill'
import type { Control, FieldErrors, UseFormRegister } from 'react-hook-form'

type Props = {
  register: UseFormRegister<CreateCourseData>
  control: Control<CreateCourseData>
  errors: FieldErrors<CreateCourseData>
  course: CourseDetails | null
  selectedCategorySlug?: string
  skills: Skill[]
  skillsLoading: boolean
  onSaveCourse: () => void
}

export default function CourseFormEdit({
  register,
  control,
  errors,
  course,
  selectedCategorySlug,
  skills,
  skillsLoading,
  onSaveCourse,
}: Props) {
  return (
    <div className="space-y-5">
      <div className="grid gap-5 md:grid-cols-2">
        <FormInput
          label="Nome do curso"
          placeholder={course?.course.name.name ?? ''}
          className="h-12 rounded-xl bg-white/5"
          error={errors.nameCourse?.message}
          minLength={15}
          {...register('nameCourse')}
        />

        <SelectInput
          id="edit-category"
          label="Categoria"
          options={categoryOptions}
          placeholder={course?.course.category.name ?? 'Selecione uma categoria'}
          className="h-12 rounded-xl bg-white/5"
          error={errors.category?.message}
          {...register('category')}
        />
      </div>

      <div className="grid gap-5 md:grid-cols-2">
        <SelectInput
          id="edit-level"
          label="Nível"
          options={levelOptions}
          placeholder={course?.course.level.name ?? 'Selecione um nível'}
          className="h-12 rounded-xl bg-white/5"
          error={errors.level?.message}
          {...register('level')}
        />

        <SkillsSelectField
          control={control}
          error={errors.skillIds?.message}
          hasCategorySelected={Boolean(selectedCategorySlug)}
          loading={skillsLoading}
          skills={skills}
        />
      </div>

      <TextBoxInput
        id="edit-description"
        label="Descrição"
        placeholder={course?.course.description ?? ''}
        error={errors.description?.message}
        minLength={25}
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

import FormSection from '@/components/CreateCourses/FormSection'
import LessonListEdit from '@/components/EditCourse/LessonListEdit'
import NewLessonForm from '@/components/EditCourse/NewLessonForm'
import Button from '@/components/ui/Button'
import type { CreateCourseData } from '@/schemas/courses/CreateCourseSchema'
import type { CourseDetails } from '@/types/course'
import type { FieldError, FieldErrors, UseFormRegister } from 'react-hook-form'

type LessonFieldError = {
  nameLesson?: FieldError
  link?: FieldError
  descriptionL?: FieldError
}

type Props = {
  course: CourseDetails | null
  register: UseFormRegister<CreateCourseData>
  errors: FieldErrors<CreateCourseData>
  newLessonIndex: number | null
  newLessonError?: LessonFieldError
  onSaveLesson: (lessonId: string, index: number) => void
  onDeleteLesson: (lessonId: string) => void
  onShowNewLessonForm: () => void
  onHideNewLessonForm: () => void
  onCreateNewLesson: () => void
}

export default function EditCourseLessonsSection({
  course,
  register,
  errors,
  newLessonIndex,
  newLessonError,
  onSaveLesson,
  onDeleteLesson,
  onShowNewLessonForm,
  onHideNewLessonForm,
  onCreateNewLesson,
}: Props) {
  return (
    <FormSection title="Aulas">
      {errors.lessons?.message && (
        <p className="mb-4 text-sm text-red-500">{errors.lessons.message}</p>
      )}

      <LessonListEdit
        lessons={course?.lessons ?? []}
        register={register}
        errors={errors.lessons}
        onSaveLesson={onSaveLesson}
        onDeleteLesson={onDeleteLesson}
      />

      {newLessonIndex !== null ? (
        <div className="mt-6">
          <NewLessonForm
            index={newLessonIndex}
            register={register}
            nameError={newLessonError?.nameLesson?.message}
            linkError={newLessonError?.link?.message}
            descriptionError={newLessonError?.descriptionL?.message}
            onCancel={onHideNewLessonForm}
            onCreate={onCreateNewLesson}
          />
        </div>
      ) : (
        <div className="mt-6 flex justify-end">
          <Button
            type="button"
            onClick={onShowNewLessonForm}
            variant="cyanOutline"
            className="border-cyan/40 text-cyan w-auto rounded-full border px-4 py-2 text-sm"
          >
            Adicionar aula
          </Button>
        </div>
      )}
    </FormSection>
  )
}

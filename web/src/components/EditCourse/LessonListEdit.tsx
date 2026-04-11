import LessonFormEdit from '@/components/EditCourse/LessonFormEdit'
import type { CreateCourseData } from '@/schemas/courses/CreateCourseSchema'
import type { CourseDetailsLesson } from '@/types/course'
import type { FieldErrors, UseFormRegister } from 'react-hook-form'

type Props = {
  lessons: CourseDetailsLesson[]
  register: UseFormRegister<CreateCourseData>
  errors?: FieldErrors<CreateCourseData>['lessons']
  onSaveLesson: (lessonId: string, index: number) => void
  onDeleteLesson: (lessonId: string) => void
}

export default function LessonListEdit({
  lessons,
  register,
  errors,
  onSaveLesson,
  onDeleteLesson,
}: Props) {
  return (
    <div className="space-y-1">
      {lessons.map((lesson, index) => (
        <div key={lesson.id}>
          {index > 0 ? <div className="h-px w-full bg-white/10" aria-hidden /> : null}

          <LessonFormEdit
            index={index}
            register={register}
            namePlaceholder={lesson.title}
            linkPlaceholder={lesson.youtubeLink}
            descriptionPlaceholder={lesson.description}
            nameError={errors?.[index]?.nameLesson?.message}
            linkError={errors?.[index]?.link?.message}
            descriptionError={errors?.[index]?.descriptionL?.message}
            onSaveLesson={() => onSaveLesson(lesson.id, index)}
            onDeleteLesson={() => onDeleteLesson(lesson.id)}
          />
        </div>
      ))}
    </div>
  )
}

import LessonFormEdit from '@/components/EditCourse/LessonFormEdit'
import type { EditCourseData } from '@/components/EditCourse/types'
import type { UseFormRegister } from 'react-hook-form'

export type LessonPreview = {
  id: string
  title: string
  youtubeLink: string
  description: string
  nameError?: string
  linkError?: string
  descriptionError?: string
}

type Props = {
  lessons: LessonPreview[]
  register: UseFormRegister<EditCourseData>
  onSaveLesson: (lessonId: string, index: number) => void
  onDeleteLesson: (lessonId: string) => void
}

export default function LessonListEdit({ lessons, register, onSaveLesson, onDeleteLesson }: Props) {
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
            nameError={lesson.nameError}
            linkError={lesson.linkError}
            descriptionError={lesson.descriptionError}
            onSaveLesson={() => onSaveLesson(lesson.id, index)}
            onDeleteLesson={() => onDeleteLesson(lesson.id)}
          />
        </div>
      ))}
    </div>
  )
}

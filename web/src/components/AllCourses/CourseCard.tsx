import { useState } from 'react'
import Button from '../ui/Button'
import CourseModal from './CourseModal'
import type { Course } from '@/types/course'

type Props = {
  course: Course
  onClick?: () => void
  actionLabel?: string
  modalActionLabel?: string
  modalActionTo?: string
  modalActionHref?: string
}

export default function CourseCard({
  course,
  onClick,
  actionLabel = 'Ver mais',
  modalActionLabel,
  modalActionTo,
  modalActionHref,
}: Props) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  const videoId = course.firstLessonYoutubeLink
  const resolvedModalLabel = modalActionLabel ?? actionLabel

  function handleOpenModal() {
    onClick?.()
    setIsModalOpen(true)
  }

  return (
    <>
      <div
        onClick={handleOpenModal}
        className="group flex h-full cursor-pointer flex-col overflow-hidden rounded-2xl border border-white/10 bg-white/5 text-left shadow-sm transition hover:-translate-y-1 hover:bg-white/10"
      >
        <img
          src={`https://img.youtube.com/vi/${videoId}/maxresdefault.jpg`}
          alt={course.name.name}
          className="h-44 w-full object-cover"
        />

        <div className="flex flex-1 flex-col p-4">
          <span className="bg-cyan/20 text-cyan inline-block self-start rounded-full px-3 py-1 text-xs font-medium">
            {course.category.name}
          </span>

          <h2 className="mt-3 text-lg font-semibold text-white">{course.name.name}</h2>

          <p className="mt-3 mb-4 [display:-webkit-box] h-6 overflow-hidden text-sm leading-6 text-white [-webkit-box-orient:vertical] [-webkit-line-clamp:1]">
            {course.description}
          </p>

          <Button variant="cyanOutline" className="text-cyan group-hover:bg-cyan/20 mt-auto!">
            {actionLabel}
          </Button>
        </div>
      </div>

      <CourseModal
        course={isModalOpen ? course : null}
        onClose={() => setIsModalOpen(false)}
        actionLabel={resolvedModalLabel}
        actionTo={modalActionTo}
        actionHref={modalActionHref}
      />
    </>
  )
}

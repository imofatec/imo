import { Link } from 'react-router-dom'
import { Separator } from '../separator'
import { Checkbox } from '../dropdown/checkbox'
import { useState, useEffect } from 'react'

export default function LessonPlaylist({
  thumbLesson,
  title,
  lessonDuration,
  codeCourse,
  codeLesson,
  onFinished,
  idAula,
  isEnabled,
  isChecked,
}) {
  const [isLessonChecked, setIsLessonChecked] = useState(isChecked)

  useEffect(() => {
    setIsLessonChecked(isChecked)
  }, [isChecked])

  const handleCheckboxChange = (checked) => {
    if (!isChecked && checked && isEnabled) {
      onFinished()
      setIsLessonChecked(true)
    }
  }

  return (
    <div className="flex flex-col px-2 py-2 overflow items-center">
      <div className="flex flex-row  items-center place-content-evenly">
        <div
          className={` flex items-center justify-center w-4 h-4 ${isLessonChecked ? 'bg-custom-header-cyan' : 'bg-transparent'}`}
        >
          <Checkbox
            id={idAula}
            className="hover:scale-105 duration-200"
            checked={isLessonChecked}
            onCheckedChange={handleCheckboxChange}
            disabled={!isEnabled || isLessonChecked}
          />
        </div>
        <Link
          to={`/cursos/${codeCourse}/${codeLesson}`}
          className="object-contain p-3 rounded-xl hover:scale-105"
        >
          <img className="rounded-xl" src={thumbLesson}></img>{' '}
        </Link>
      </div>

      <div className="flex flex-row justify-between mx-4">
        <div className="flex flex-col mx-2 ">
          <h3 className="mb-2">{title}</h3>
        </div>
        <div className="flex flex-col mx-2 ">
          <p className="">{lessonDuration} </p>
        </div>
      </div>
      <Separator className="m-4 w-full"></Separator>
    </div>
  )
}

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
  indexLesson,
  progress,
  loadingProgress
}) {
  const [isEnabled, setIsEnabled] = useState()
  const [isChecked, setIsChecked] = useState()

  useEffect(() => {
    console.log(progress)
    if (!progress || loadingProgress ) return

    setIsEnabled((indexLesson - 1) <= progress.lessonsWatched)
    setIsChecked((indexLesson - 1) < progress.lessonsWatched)
  }, [progress, indexLesson, loadingProgress])

  const handleCheckboxChange = (checked) => {
    if (!isChecked && checked && isEnabled) {
      setIsChecked(true)
      onFinished()
    }
  }

  return (
    <div className="flex flex-col px-2 py-2 overflow items-center">
      <div className="flex flex-row  items-center place-content-evenly">
        <div
          className={` flex items-center justify-center w-6 h-6 ${isChecked ? 'bg-custom-header-cyan' : 'bg-transparent'}`}
        >
          <Checkbox
            id={indexLesson}
            className="hover:scale-105 duration-200 w-6 h-6"
            checked={isChecked}
            onCheckedChange={handleCheckboxChange}
            disabled={!isEnabled || isChecked}
          />
        </div>
        <Link
          to={`/cursos/${codeCourse}/${codeLesson}`}
          className="object-contain p-3 rounded-xl"
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

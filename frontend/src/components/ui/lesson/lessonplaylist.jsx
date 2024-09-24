import { Link } from 'react-router-dom'
import { Separator } from '../separator'
import { Checkbox } from '../dropdown/checkbox'

export default function LessonPlaylist({ thumbLesson, title, lessonDuration }) {
  return (
    <div className="px-2 py-2 overflow ">
      <div className="flex flex-row  items-center place-content-evenly">
        <Checkbox id={''} className=" hover:scale-110"></Checkbox>
        <Link className="object-contain p-3 rounded-xl hover:scale-105">
          <img className="rounded-xl" src={thumbLesson}></img>{' '}
          {/*Apagar essa linha ao implementar backend*/}
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
      <Separator className="m-4 w-60"></Separator>
    </div>
  )
}

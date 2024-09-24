import { Link } from 'react-router-dom'
import { Arrow } from '../arrow'
import UserPicture from '../userpicture'

export default function LessonInfo({ profilePic, lessonName, channelName }) {
  lessonName = 'Titulo da Aula'
  channelName = 'Professor fulano'

  return (
    <>
      <div className="flex flex-row py-4  text-2xl  justify-between">
        <div className="flex flex-col font-semibold">
          <h1 className="">{lessonName}</h1>
        </div>

        <div className="flex flex-row justify-end underline mx-2">
          <Arrow></Arrow>
          <div className="flex flex-col " id="preview">
            <p className="mr-8">Anterior</p>
          </div>
          <div className="flex flex-col" id="next">
            <p className="">Próximo</p>
          </div>
          <Arrow orientation={'right'}></Arrow>
        </div>
      </div>

      <Link>
        <div className="flex flex-row items-center mb-4 ml-4">
          <UserPicture profilePic={profilePic}></UserPicture>
          <label className="text-sm mx-4">{channelName}</label>
        </div>
      </Link>
    </>
  )
}

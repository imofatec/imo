import { Link, useNavigate } from 'react-router-dom'
import { Arrow } from '../arrow'
import previous_arrow from '@/assets/previous-arrow.png'
import next_arrow from '@/assets/next-arrow.png'
import UserPicture from '../userpicture'

export default function LessonInfo({
  profilePic,
  lessonName,
  channelName,
  slugCourse,
  lessonData,
  IdLesson,
}) {
  channelName = 'Professor fulano'

  const navigate = useNavigate()

  const currentLessonIndex = lessonData.findIndex(
    (lesson) => lesson.youtubeLink === IdLesson,
  )

  const nextLesson = lessonData[currentLessonIndex + 1]
  const previousLesson = lessonData[currentLessonIndex - 1]

  const handleNext = () => {
    if (nextLesson) {
      navigate(`/cursos/${slugCourse}/${nextLesson.youtubeLink}`)
    }
  }

  const handlePrevious = () => {
    if (previousLesson) {
      navigate(`/cursos/${slugCourse}/${previousLesson.youtubeLink}`)
    }
  }
  return (
    <>
      <div className="flex flex-row py-4  text-2xl  justify-between">
        <div className="flex flex-col font-semibold">
          <h1 className="">{lessonName}</h1>
        </div>

        <div className="flex flex-row gap-2 justify-end mx-2">
          {previousLesson && (
            <>
                <img src={previous_arrow} alt="" width="28px" height="28px" className='cursor-pointer'/>
              <div
                className="flex flex-col"
                id="preview"
                onClick={handlePrevious}
              >
                <p className="cursor-pointer mr-8">Voltar</p>
              </div>
            </>
          )}

          {nextLesson && (
            <>
              <div className="flex flex-col" id="next" onClick={handleNext}>
                <p className="cursor-pointer">Avançar</p>
              </div>
              <img src={next_arrow} alt="" width="28px" height="28px" className='cursor-pointer'/>
            </>
          )}
        </div>
      </div>

      {/* <Link>
        <div className="flex flex-row items-center mb-4 ml-4">
          <UserPicture profilePic={profilePic}></UserPicture>
          <label className="text-sm mx-4">{channelName}</label>
        </div>
      </Link> */}
    </>
  )
}

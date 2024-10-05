import LessonDescription from '@/components/ui/lesson/lessondescription'
import thumbLesson from '@/assets/thumb.jpg'
import LessonPlaylist from '@/components/ui/lesson/lessonplaylist'
import { Titulo } from '@/components/ui/titulo'
import LessonComment from '@/components/ui/lesson/lessoncomment'
import LessonInfo from '@/components/ui/lesson/lessoninfo'
import { useParams, useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import axios from 'axios'

export default function VerAula() {
  const { slugCourse } = useParams()
  const { IdLesson } = useParams()

  const [lessonData, setLessonData] = useState([])
  const [error, setError] = useState(false)
  const [loading, setLoading] = useState(true)

  const navigate = useNavigate()

  const fetchData = async () => {
    setError(false)
    setLoading(true)
    try {
      const response = await axios.get(
        `/api/courses/get-all/lessons/${slugCourse}`,
      )
      setLessonData(response.data)
    } catch (error) {
      setError(true)
      console.error(error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchData()
  }, [slugCourse])

  useEffect(() => {
    if (!loading && !currentLesson) {
      navigate('/404')
    }
  }, [loading, navigate])

  const currentLesson = lessonData.find(
    (lesson) => lesson.youtubeLink === IdLesson,
  )

  let commentContent =
    'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit ratione veritatis vitae a voluptas quibusdam vero veniam, molestiae, ducimus natus quasi alias laboriosam officia eos minus. Est temporibus dolores hic.'
  let commentTitle = 'Lorem ipsum dolor sit amet'
  let profileName = 'Nome'

  let commentData = [
    {
      profileName: profileName,
      commentTitle: commentTitle,
      commentContent: commentContent,
    },
    {
      profileName: profileName,
      commentTitle: commentTitle,
      commentContent: commentContent,
    },
  ]

  return (
    <div className="max-w-full max-h-fit">
      <Titulo titulo={currentLesson?.title}></Titulo>

      <div className="flex flex-row">
        <div className="flex flex-col w-3/4 p-8">
          <iframe
            loading="lazy"
            width="w-full"
            height="560"
            src={`https://www.youtube.com/embed/${IdLesson}`}
            title="YouTube video player"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
            referrerPolicy="strict-origin-when-cross-origin"
            allowFullScreen
          ></iframe>

          {currentLesson && (
            <LessonInfo
              lessonName={currentLesson.title}
              lessonData={lessonData}
              IdLesson={IdLesson}
              slugCourse={slugCourse}
            ></LessonInfo>
          )}

          <LessonDescription
            descr={currentLesson?.description}
          ></LessonDescription>
          <div className="">
            <h2 className="font-semibold text-xl">Comentários</h2>
            {/*
                caso tenham comentarios, os dados virão num .json
                caso não tenham comentarios, vai dar uma mensagem
                ******* quanto implementado no back-end, verificar se a mensagem virá do back ou ficará a da página**********
                */}
            {commentData
              ? commentData.map((item, i) => {
                  return (
                    <LessonComment
                      key={i}
                      profilePic={thumbLesson}
                      profileName={item.profileName}
                      commentContent={item.commentContent}
                      commentTitle={item.commentTitle}
                    ></LessonComment>
                  )
                })
              : 'Seja o primeiro a comentar!'}
          </div>
        </div>

        <div className="flex flex-col w-1/4 pl-4 bg-custom-dark-blue p-6">
          <h2 className="text-xl mb-6 text-center">Aulas do curso</h2>
          {lessonData.map((item, i) => {
            return (
              <LessonPlaylist
                key={i}
                thumbLesson={`https://img.youtube.com/vi/${item.youtubeLink}/maxresdefault.jpg`}
                title={item.title}
                lessonDuration="30:23"
                author={item.author}
                codeCourse={slugCourse}
                codeLesson={item.youtubeLink}
              ></LessonPlaylist>
            )
          })}
        </div>
      </div>
    </div>
  )
}

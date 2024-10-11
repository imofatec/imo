import LessonDescription from '@/components/ui/lesson/lessondescription'
import api from '@/api/api'
import thumbLesson from '@/assets/thumb.jpg'
import LessonPlaylist from '@/components/ui/lesson/lessonplaylist'
import { Titulo } from '@/components/ui/titulo'
import LessonComment from '@/components/ui/lesson/lessoncomment'
import LessonInfo from '@/components/ui/lesson/lessoninfo'
import { useParams, useNavigate } from 'react-router-dom'
import { useLessonData } from '@/hooks/useLessonData'
import { useLessonProgress } from '@/hooks/useLessonProgress'
import { useEffect } from 'react'

export default function VerAula() {
  const { slugCourse, IdLesson } = useParams()
  const { lessonData, courseID, error, loading } = useLessonData(slugCourse)
  const { progress, fetchProgress } = useLessonProgress(
    courseID ? courseID : null,
  )
  const navigate = useNavigate()

  const currentLesson = lessonData.find(
    (lesson) => lesson.youtubeLink === IdLesson,
  )

  useEffect(() => {
    if (!loading && !currentLesson) {
      navigate('/404')
    }
  }, [loading, currentLesson, navigate])

  const handleFinishedLesson = async () => {
    try {
      await api.put(`/api/user/update-progress/${courseID}`)
      fetchProgress()
    } catch (error) {
      console.error('Erro ao marcar a aula como concluída:', error)
    }
  }

  let commentData = [
    {
      profileName: 'João',
      commentTitle: 'Essa aula mudou minha vida',
      commentContent:
        'Essa aula mudou minha vida! Eu sempre tive dificuldades em entender esse assunto, mas a forma clara e prática como foi apresentada me ajudou a superar meus desafios. Agora me sinto mais confiante e preparado para aplicar esse conhecimento no meu dia a dia. Agradeço ao instrutor pela dedicação e por compartilhar essas lições valiosas!',
    },
  ]

  console.log(progress)
  return (
    <div className="max-w-full min-h-screen">
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

        <div className="flex flex-col w-1/4 pl-4 bg-custom-dark-blue p-6 max-h-[calc(100vh-4rem)] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-500 scrollbar-track-gray-800">
          <h2 className="text-xl mb-6 text-center">Aulas do curso</h2>
          {lessonData.map((item, i) => {
            const isEnabled = i <= progress?.lessonsWatched
            const isChecked = i < progress?.lessonsWatched
            return (
              <LessonPlaylist
                key={i}
                idAula={item.index}
                thumbLesson={`https://img.youtube.com/vi/${item.youtubeLink}/maxresdefault.jpg`}
                title={item.title}
                lessonDuration="30:23"
                author={item.author}
                codeCourse={slugCourse}
                codeLesson={item.youtubeLink}
                onFinished={() => handleFinishedLesson(item.index)}
                isEnabled={isEnabled}
                isChecked={isChecked}
              ></LessonPlaylist>
            )
          })}
        </div>
      </div>
    </div>
  )
}

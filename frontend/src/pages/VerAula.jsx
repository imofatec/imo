import authAxiosInstance from '@/api/authAxiosInstance'
import thumbLesson from '@/assets/thumb.jpg'
import SkeletonLoading from '@/components/ui/curso/skeletonLoading'
import LessonComment from '@/components/ui/lesson/lessoncomment'
import LessonDescription from '@/components/ui/lesson/lessondescription'
import LessonInfo from '@/components/ui/lesson/lessoninfo'
import LessonPlaylist from '@/components/ui/lesson/lessonplaylist'
import { Titulo } from '@/components/ui/titulo'
import { useLessonData } from '@/hooks/useLessonData'
import { useLessonProgress } from '@/hooks/useLessonProgress'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import CommentForm from '@/components/ui/lessons/commentForm'
import { useCommentsData } from '@/hooks/useCommentsData'
import { Arrow } from '@/components/ui/arrow'

export default function VerAula() {
  const { slugCourse, idLesson } = useParams()
  const { lessonData, courseId, error, loading } = useLessonData(slugCourse)
  const { fetchProgress, progress, cansei, loadingProgress, updateProgress } =
    useLessonProgress(courseId ? courseId : null)
  const navigate = useNavigate()
  const [showComments, setShowComments] = useState(true)

  const currentLesson =
    lessonData && lessonData.length > 0
      ? lessonData.find((lesson) => lesson.youtubeLink === idLesson)
      : null

  const { commentsData, error: commentsError, loading: commentsLoading } =
    useCommentsData(currentLesson?.id ?? null)
  useEffect(() => {
    if (!loading && error) {
      navigate('/404')
    }
  }, [loading, error, navigate, progress])

  const handleFinishedLesson = async () => {
    await updateProgress()
    await fetchProgress()
  }

  const handleGetCertificate = async () => {
    const [error, result] = await safeAwait(
      authAxiosInstance.get(`/api/user/course/certificate/${courseId}`, {
        responseType: 'blob',
      })
    )
    if (error) {
      console.error('Erro ao gerar certificado:', error)
      return
    }
    const contentDisposition = result.headers['content-disposition']
    const fileName = contentDisposition
      ? contentDisposition.split('filename=')[1].replace(/['"]/g, '')
      : 'certificado.pdf'

    const url = window.URL.createObjectURL(new Blob([result.data]))
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', fileName)
    link.click()
  }

  return (
    <div className="max-w-full min-h-screen">
      <Titulo titulo={`IMO / ${currentLesson?.title}`}></Titulo>

      <div className="flex flex-row">
        <div className="flex flex-col w-3/4 p-8">
          <iframe
            loading="lazy"
            width="w-full"
            height="560"
            src={`https://www.youtube.com/embed/${idLesson}`}
            title="YouTube video player"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
            referrerPolicy="strict-origin-when-cross-origin"
            allowFullScreen
          ></iframe>

          {currentLesson && (
            <LessonInfo
              lessonName={currentLesson.title}
              lessonData={lessonData}
              idLesson={idLesson}
              slugCourse={slugCourse}
            />
          )}

          <LessonDescription descr={currentLesson?.description} />

          {currentLesson && <CommentForm lessonId={currentLesson?.id} />}
          <div>
            {commentsData && commentsData.length > 0 ? (
              <>
                <div
                  className="flex items-center gap-2 cursor-pointer mb-4"
                  onClick={() => setShowComments(!showComments)}
                >
                  <span className="underline">
                    {showComments
                      ? 'Fechar todos os comentários'
                      : 'Exibir todos os comentários'}
                  </span>
                  <Arrow
                    size="sm"
                    orientation={showComments ? 'up' : 'down'}
                  />
                </div>
                {showComments && (
                  <div>
                    {commentsData.map((comentario) => (
                      <LessonComment
                        key={comentario.id}
                        profilePic={thumbLesson}
                        profileName={comentario.username}
                        commentContent={comentario.comment}
                        commentTitle={`${comentario.username} comentou:`}
                      />
                    ))}
                  </div>
                )}
              </>
            ) : (
              <p>Seja o primeiro a comentar!</p>
            )}
          </div>
        </div>
        <div className="flex flex-col w-1/4 pl-4 bg-custom-dark-blue p-6 max-h-[calc(100vh-4rem)] overscroll-auto overflow-y-auto scrollbar-thin scrollbar-thumb-gray-500 scrollbar-track-gray-800">
          <h2 className="text-xl mb-6 text-center">Aulas do curso</h2>
          {loading &&
            Array.from({ length: 4 }).map((_, index) => (
              <SkeletonLoading key={index} />
            ))}
          {!loadingProgress && (
            <>
              {lessonData.map((item, i) => (
                <LessonPlaylist
                  key={i}
                  indexLesson={item.index}
                  thumbLesson={`https://img.youtube.com/vi/${item.youtubeLink}/maxresdefault.jpg`}
                  title={item.title}
                  lessonDuration="30:23"
                  author={item.author}
                  codeCourse={slugCourse}
                  codeLesson={item.youtubeLink}
                  onFinished={handleFinishedLesson}
                  progress={progress}
                  loadingProgress={loadingProgress}
                />
              ))}
            </>
          )}
          <button
            onClick={handleGetCertificate}
            disabled={progress.lessonsWatched < lessonData.length}
            className={`mt-4 px-4 py-2 rounded ${progress.lessonsWatched < lessonData.length || cansei
                ? 'bg-gray-500 cursor-not-allowed text-white'
                : 'bg-custom-header-cyan text-black'
              }`}
          >
            Gerar certificado
          </button>
        </div>
      </div>
    </div>
  )
}

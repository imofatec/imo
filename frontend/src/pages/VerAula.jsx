import LessonDescription from '@/components/ui/lesson/lessondescription'
import thumbLesson from '@/assets/thumb.jpg'
import LessonPlaylist from '@/components/ui/lesson/lessonplaylist'
import { Titulo } from '@/components/ui/titulo'
import LessonComment from '@/components/ui/lesson/lessoncomment'
import LessonInfo from '@/components/ui/lesson/lessoninfo'

export default function VerAula() {
  let commentContent =
    'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit ratione veritatis vitae a voluptas quibusdam vero veniam, molestiae, ducimus natus quasi alias laboriosam officia eos minus. Est temporibus dolores hic.'
  let commentTitle = 'Lorem ipsum dolor sit amet'
  let profileName = 'Nome'
  let linkVideo =
    'https://www.youtube.com/embed/PbkwqVZsUgs?si=PiMYL7VNdUxoOAjf'

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
  let playlist = [
    {
      thumbLesson: thumbLesson,
      title: 'Instalando React',
      duration: '38:11',
    },
    {
      thumbLesson: thumbLesson,
      title: 'Criando componentes',
      duration: '20:32',
    },
    {
      thumbLesson: thumbLesson,
      title: 'Utilização de useState e useEffect no React',
      duration: '12:56',
    },
    {
      thumbLesson: thumbLesson,
      title: 'Explicando SPA',
      duration: '2:56',
    },
  ]
  return (
    <div className="max-w-full max-h-fit">
      <Titulo titulo={'Ver aula'}></Titulo>

      <div className="flex flex-row">
        <div className="flex flex-col w-3/4 p-8">
          <iframe
            width="w-full"
            height="560"
            src={linkVideo}
            title="YouTube video player"
            frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
            referrerpolicy="strict-origin-when-cross-origin"
            allowfullscreen
          ></iframe>
          <LessonInfo></LessonInfo>

          <LessonDescription></LessonDescription>
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
          {playlist.map((item, i) => {
            return (
              <LessonPlaylist
                thumbLesson={thumbLesson}
                title={item.title}
                lessonDuration={item.duration}
                author={item.author}
              ></LessonPlaylist>
            )
          })}
        </div>
      </div>
    </div>
  )
}

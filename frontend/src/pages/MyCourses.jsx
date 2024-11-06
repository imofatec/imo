import CardCurso from '@/components/ui/curso/cardcurso'
import { Dropdown } from '@/components/ui/dropdown/dropdown'
import { Seletor } from '@/components/ui/dropdown/seletor'
import { DropdownSelect } from '@/components/ui/dropdownselect'
import Pagination from '@/components/ui/pagination'
import StatusMessage from '@/components/ui/statusMessage'
import { Titulo } from '@/components/ui/titulo'

export default function MyCourses() {

  let categories = [
    { name: 'Cursos Concluídos' },
    { name: 'Cursos que Realizei Upload' },
    { name: 'Cursos em Andamento' }
  ]
  let tipoCurso = 'Meus Cursos'
  let courses = [
    {
      id: 1,
      name: 'The Decorator Pattern Explained in Java',
      firstLessonYoutubeId: 'v6tpISNjHf8',
      description: '',
      totalLessons: 1,
      slugCourse: 2,
    },
    {
      id: 2,
      name: 'Estrutura de Dados com Java ',
      firstLessonYoutubeId: 'RW0oD2L_tSg',
      description: '',
      totalLessons: 1,
      slugCourse: 2,
    },
    {
      id: 3,
      name: 'Automatizando Relatórios com Python',
      firstLessonYoutubeId: 'diLns814no0',
      description: '',
      totalLessons: 1,
      slugCourse: 2,
    },
    {
      id: 4,
      name: 'Desenvolvendo Doom em Python',
      firstLessonYoutubeId: 'KdYTvqZmyBk',
      description: '',
      totalLessons: 1,
      slugCourse: 2,
    },
  ]

  return (
    <>
      <Titulo titulo={`IMO / Meus Cursos`} />
      <div className="min-h-screen flex flex-row w-full">
        <div className="w-1/3 p-12">
          <Seletor
            id="allLessons"
            label={'text-xl font-semibold'}
            conteudo={'Todos os cursos'}
          ></Seletor>
          <Dropdown categorias={categories}></Dropdown>
        </div>

        <div className="w-2/3 p-12">
          <div className="flex flex-row w-full">
            <div className="flex flex-col w-1/2">
              <h5 className="font-semibold text-xl mb-10 text-white">
                {tipoCurso}
              </h5>
            </div>
            <div className="flex flex-col w-1/2 items-end">
              <DropdownSelect></DropdownSelect>
            </div>
          </div>

          <div className="flex flex-row flex-wrap p-18 w-auto max-w-full">
            {/*<StatusMessage loading={loading} error={error} />
            {!loading &&
              !error &&
              */}
            {courses.map((curso) => (//apagar esta chave ao implementar a resposta de carregamento
              <CardCurso
                key={curso.id}
                idCurso={curso.id}
                nomeCurso={curso.name}
                notaCurso="5.0"
                avaliacoesCurso="80"
                fotoCurso={`https://img.youtube.com/vi/${curso.firstLessonYoutubeId}/maxresdefault.jpg`}
                descricaoCurso={curso.description}
                conteudo={curso.name}
                quantidade={curso.totalLessons}
                codigo={curso.slugCourse}
                codAula={curso.firstLessonYoutubeId}
                //onStart={handleStartCourse}
              />
            ))}
          </div>
          {/*
           > Implementar

          <Pagination
            page={page}
            hasMoreCourses={hasMoreCourses}
            onPrevious={handlePreviousPage}
            onNext={handleNextPage}
          />
          */}
        </div>
      </div>
    </>
  )
}

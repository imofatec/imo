import { useState } from 'react'
import CourseCard from '../components/AllCourses/CourseCard'
import CourseModal from '../components/AllCourses/CourseModal'
import FilterDrawer from '../components/AllCourses/FilterDrawer'
import PaginationControls from '../components/AllCourses/PaginationControls'
import SkeletonCourseCard from '../components/AllCourses/SkeletonCourseCard'

type Course = {
  id: string
  title: string
  image: string
  description: string
  category: string
  status: 'Em andamento' | 'Concluído' | 'Contribuições'
  buttonLabel: string
}

const mockCourses: Course[] = [
  {
    id: '1',
    title: 'TypeScript na Prática',
    image:
      'https://assets.dio.me/FV3XVabllLqNMPpWnxtpGda6rrmOZUhH40sf8HwHkrc/f:webp/q:80/L2FydGljbGVzL2NvdmVyLzA1M2EwODFlLTY0N2UtNGM3OS1iOTJhLWU5ZTA4MWJlY2UyOS5wbmc',
    description: 'Use tipagem estática para escrever código mais seguro.',
    category: 'Programação',
    status: 'Contribuições',
    buttonLabel: 'Iniciar curso',
  },
  {
    id: '2',
    title: 'React do Zero ao Avançado',
    image: 'https://miro.medium.com/v2/1*5PxGgx_aOWpTkul_D3nnbw.jpeg',
    description: 'Aprenda React criando interfaces modernas.',
    category: 'Front-end',
    status: 'Em andamento',
    buttonLabel: 'Continuar aula',
  },
  {
    id: '3',
    title: 'Node.js para APIs',
    image: 'https://miro.medium.com/v2/1*1UBNwRFaslvqt_G3Njw3pg.jpeg',
    description: 'Construa APIs escaláveis.',
    category: 'Back-end',
    status: 'Concluído',
    buttonLabel: 'Revisitar curso',
  },
]

const statusCategories = ['Todos', 'Em andamento', 'Concluído', 'Contribuições'] as const

export default function MyCoursesPage() {
  const [selectedCourse, setSelectedCourse] = useState<Course | null>(null)
  const [selectedCategory, setSelectedCategory] =
    useState<(typeof statusCategories)[number]>('Todos')
  const loading = false
  const error = false
  const page = 1
  const size = 6
  const isPrevDisabled = true
  const isNextDisabled = false

  const getTitle = () => {
    switch (selectedCategory) {
      case 'Em andamento':
        return 'Cursos em Andamento'
      case 'Concluído':
        return 'Cursos concluídos'
      case 'Contribuições':
        return 'Minhas Contribuições'
      default:
        return 'Meus Cursos'
    }
  }

  const filteredCourses =
    selectedCategory === 'Todos'
      ? mockCourses
      : mockCourses.filter((course) => course.status === selectedCategory)

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10">
        <div className="max-w-8xl mx-auto flex w-full items-center px-4 py-5 sm:px-6 lg:px-8">
          <h1 className="pl-3 text-2xl font-bold text-white">{getTitle()}</h1>
          <FilterDrawer
            selectedCategory={selectedCategory}
            setSelectedCategory={setSelectedCategory}
            categories={statusCategories}
          />
        </div>
      </section>

      <section className="flex justify-center px-4 py-8 sm:px-6 lg:px-8">
        <div className="max-w-8xl w-full items-center justify-center">
          <div className="min-h-130 items-center justify-center">
            {loading && (
              <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-5">
                {Array.from({ length: size }).map((_, index) => (
                  <SkeletonCourseCard key={index} />
                ))}
              </div>
            )}

            {!loading && !error && (
              <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-5">
                {filteredCourses.map((course) => (
                  <CourseCard
                    key={course.id}
                    course={course}
                    onClick={() => setSelectedCourse(course)}
                    actionLabel={course.buttonLabel}
                  />
                ))}
              </div>
            )}

            {!loading && !error && filteredCourses.length === 0 && (
              <div className="flex h-80 items-center justify-center rounded-2xl border border-white/10 bg-white/5">
                <p className="text-center text-sm text-white/70">
                  Nenhum curso encontrado nessa categoria.
                </p>
              </div>
            )}
            {!loading && error && (
              <div className="rounded-2xl border border-red-400/30 bg-red-500/10 p-6 text-center text-sm text-red-300">
                Ocorreu um erro ao carregar os cursos.
              </div>
            )}
          </div>
          <PaginationControls
            page={page}
            totalPages={7}
            isPrevDisabled={isPrevDisabled}
            isNextDisabled={isNextDisabled}
            onPrev={() => console.log('anterior')}
            onNext={() => console.log('próxima')}
            onPageChange={(newPage) => console.log(newPage)}
          />
        </div>
      </section>

      <CourseModal
        course={selectedCourse}
        onClose={() => setSelectedCourse(null)}
        actionLabel={selectedCourse?.buttonLabel}
      />
    </main>
  )
}

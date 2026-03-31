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
}

const mockCourses: Course[] = [
  {
    id: '1',
    title: 'React do Zero ao Avançado',
    image: 'https://miro.medium.com/v2/1*5PxGgx_aOWpTkul_D3nnbw.jpeg',
    description: 'Aprenda React criando interfaces modernas e componentizadas.',
    category: 'Front-end',
  },
  {
    id: '2',
    title: 'Node.js para APIs',
    image: 'https://miro.medium.com/v2/1*1UBNwRFaslvqt_G3Njw3pg.jpeg',
    description: 'Construa APIs organizadas, escaláveis e fáceis de manter.',
    category: 'Back-end',
  },
  {
    id: '3',
    title: 'TypeScript na Prática',
    image:
      'https://assets.dio.me/FV3XVabllLqNMPpWnxtpGda6rrmOZUhH40sf8HwHkrc/f:webp/q:80/L2FydGljbGVzL2NvdmVyLzA1M2EwODFlLTY0N2UtNGM3OS1iOTJhLWU5ZTA4MWJlY2UyOS5wbmc',
    description: 'Use tipagem estática para escrever código mais seguro.',
    category: 'Programação',
  },
  {
    id: '4',
    title: 'UI Design para Devs',
    image:
      'https://img.freepik.com/free-vector/ux-ui-typographic-header-app-interface-improvement-user-interface-design-user-experience-development-modern-technology-concept-flat-vector-illustration_613284-1484.jpg?semt=ais_incoming&w=740&q=80',
    description: 'Aprenda noções visuais para criar interfaces mais bonitas.',
    category: 'Design',
  },
  {
    id: '5',
    title: 'Banco de Dados SQL',
    image: 'https://www.wikitechy.com/interview-questions/wp-content/uploads/2022/08/sql.jpeg',
    description: 'Domine consultas, relacionamentos e modelagem de dados.',
    category: 'Banco de Dados',
  },
  {
    id: '6',
    title: 'Git e GitHub Essencial',
    image: 'https://hermes.dio.me/articles/cover/febfe489-d027-4d8c-83a0-3da59310283b.png',
    description: 'Versione projetos e trabalhe em equipe com mais segurança.',
    category: 'Ferramentas',
  },
  {
    id: '7',
    title: 'Git e GitHub',
    image: 'https://hermes.dio.me/articles/cover/d2489f96-d56f-4b82-bc7f-84fbc9fb1368.jpg',
    description: 'Versione projetos e trabalhe em equipe com mais segurança.',
    category: 'Ferramentas',
  },
]

export default function AllCoursesPage() {
  const [selectedCourse, setSelectedCourse] = useState<Course | null>(null)
  const [selectedCategory, setSelectedCategory] = useState('Todos')
  const loading = false
  const error = false
  const page = 1
  const size = 6
  const isPrevDisabled = true
  const isNextDisabled = false
  const filteredCourses =
    selectedCategory === 'Todos'
      ? mockCourses
      : mockCourses.filter((course) => course.category === selectedCategory)

  const getTitle = () => 'Todos os Cursos'

  return (
    <main className="min-h-screen bg-[#0C0424]">
      <section className="border-b border-white/10">
        <div className="max-w-8xl mx-auto flex w-full items-center px-4 py-5 sm:px-6 lg:px-8">
          <h1 className="pl-3 text-2xl font-bold text-white">{getTitle()}</h1>
          <FilterDrawer
            selectedCategory={selectedCategory}
            setSelectedCategory={setSelectedCategory}
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
                  />
                ))}
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

      <CourseModal course={selectedCourse} onClose={() => setSelectedCourse(null)} />
    </main>
  )
}

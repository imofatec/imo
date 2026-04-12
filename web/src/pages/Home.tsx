import CategoryChip from '@/components/Home/CategoryChip'
import ContinueLearningCard from '@/components/Home/ContinueLearningCard'
import HomeSectionHeader from '@/components/Home/HomeSectionHeader'
import CourseCard from '@/components/AllCourses/CourseCard'
import { categoryOptions } from '@/constants/courseOptions'
import LinkButton from '@/components/ui/LinkButton'
import type { Course } from '@/types/course'

function toSlug(value: string) {
  return value
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .toLowerCase()
    .replace(/[^a-z0-9\s-]/g, '')
    .trim()
    .replace(/[\s_]+/g, '-')
}

function getCategoryTerm(value: string) {
  const option = categoryOptions.find((category) => category.value === value)
  const name = option?.label ?? value

  return {
    name,
    slug: toSlug(name),
  }
}

function getCategoryPath(value: string) {
  return `/categorias/${getCategoryTerm(value).slug}`
}

const continueCourse = {
  title: 'Fundamentos de IA aplicada a produtos',
  instructor: 'Nome do instrutor',
  progress: 40,
  imageUrl: 'https://img.odcdn.com.br/wp-content/uploads/2024/06/ia_cursos-1024x683.jpg',
}

const recommendedCourses: Course[] = [
  {
    id: 'recommended1',
    name: { name: 'React do básico ao avançado', slug: 'react-basico-avancado' },
    level: { name: 'Intermediário', slug: 'intermediario' },
    category: getCategoryTerm('DEV_WEB'),
    description: 'Aprenda React com foco em componentes, hooks e arquitetura para projetos reais.',
    firstLessonYoutubeLink: 'dGcsHMXbSOA',
    lessonsCount: 18,
    isActive: true,
  },
  {
    id: 'recommended2',
    name: { name: 'Introdução a IA generativa', slug: 'introducao-ia-generativa' },
    level: { name: 'Iniciante', slug: 'iniciante' },
    category: getCategoryTerm('AI'),
    description: 'Conceitos essenciais de IA generativa e uso pratico em tarefas do dia a dia.',
    firstLessonYoutubeLink: 'mEsleV16qdo',
    lessonsCount: 10,
    isActive: true,
  },
  {
    id: 'recommended3',
    name: { name: 'Cloud fundamentals para devs', slug: 'cloud-fundamentals-devs' },
    level: { name: 'Intermediário', slug: 'intermediario' },
    category: getCategoryTerm('CLOUD'),
    description: 'Servicos de nuvem, arquitetura basica e boas praticas para deploy moderno.',
    firstLessonYoutubeLink: 'M988_fsOSWo',
    lessonsCount: 12,
    isActive: true,
  },
  {
    id: 'recommended4',
    name: { name: 'Segurança web essencial', slug: 'seguranca-web-essencial' },
    level: { name: 'Iniciante', slug: 'iniciante' },
    category: getCategoryTerm('SECURITY'),
    description: 'Proteção de aplicações web, autenticação segura e prevenção de vulnerabilidades.',
    firstLessonYoutubeLink: 'nzZkKoREEGo',
    lessonsCount: 16,
    isActive: true,
  },
]

const trendingCourses: Course[] = [
  {
    id: 'trending1',
    name: { name: 'Python para análise de dados', slug: 'python-analise-dados' },
    level: { name: 'Intermediário', slug: 'intermediario' },
    category: getCategoryTerm('DATA'),
    description: 'Pandas, visualização e exploração de dados para resolver problemas de negócio.',
    firstLessonYoutubeLink: 'vmEHCJofslg',
    lessonsCount: 9,
    isActive: true,
  },
  {
    id: 'trending2',
    name: { name: 'React Native para apps mobile', slug: 'react-native-apps-mobile' },
    level: { name: 'Iniciante', slug: 'iniciante' },
    category: getCategoryTerm('DEV_MOBILE'),
    description: 'Construa apps multiplataforma com componentes nativos e navegação.',
    firstLessonYoutubeLink: '0-S5a0eXPoc',
    lessonsCount: 14,
    isActive: true,
  },
  {
    id: 'trending3',
    name: { name: 'Modelos de ML em produção', slug: 'modelos-ml-producao' },
    level: { name: 'Avançado', slug: 'avancado' },
    category: getCategoryTerm('AI'),
    description: 'Pipeline de treinamento, deploy e monitoramento de modelos em ambiente real.',
    firstLessonYoutubeLink: 'qFJeN9V1ZsI',
    lessonsCount: 11,
    isActive: true,
  },
]

export default function HomePage() {
  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10 bg-linear-to-r from-[#130738] via-[#0E0530] to-[#0B0326]">
        <div className="mx-auto w-full max-w-6xl px-4 py-10 sm:px-6 lg:px-8">
          <p className="text-cyan text-xs font-medium tracking-[0.2em] uppercase">Início</p>
          <h1 className="mt-3 max-w-2xl text-3xl leading-tight font-bold text-white md:text-4xl">
            Seu aprendizado continua aqui
          </h1>
          <p className="mt-3 max-w-2xl text-sm text-white/70 md:text-base">
            Explore cursos, continue seu aprendizado em andamento e descubra conteúdos de alta
            qualidade!
          </p>
          <div className="mt-6">
            <LinkButton
              to="/categorias"
              variant="cyanOutline"
              className="text-cyan border-cyan/40 mt-0! w-auto rounded-full border px-5 py-2.5"
            >
              Ver todos os cursos
            </LinkButton>
          </div>
        </div>
      </section>

      <section className="mx-auto w-full max-w-6xl space-y-10 px-4 py-8 sm:px-6 lg:px-8">
        <div>
          <HomeSectionHeader title="Continue seu aprendizado" />
          <ContinueLearningCard {...continueCourse} />
        </div>

        <div>
          <HomeSectionHeader title="Categorias" to="/categorias" />
          <div className="flex flex-wrap gap-2">
            {categoryOptions.map((category) => (
              <CategoryChip
                key={category.value}
                label={category.label}
                to={getCategoryPath(category.value)}
              />
            ))}
          </div>
        </div>

        <div>
          <HomeSectionHeader title="Cursos recomendados" to="/categorias" />
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
            {recommendedCourses.map((course) => (
              <CourseCard
                key={course.id}
                course={course}
                actionLabel="Ver mais"
                modalActionLabel="Inscrever-se"
              />
            ))}
          </div>
        </div>

        <div>
          <HomeSectionHeader title="Em alta" to="/categorias" />
          <div className="flex gap-4 overflow-x-auto pb-2">
            {trendingCourses.map((course) => (
              <div key={course.id} className="w-72 shrink-0">
                <CourseCard
                  course={course}
                  actionLabel="Ver mais"
                  modalActionLabel="Inscrever-se"
                />
              </div>
            ))}
          </div>
        </div>

        <section className="border-cyan/20 bg-cyan/10 rounded-3xl border p-6">
          <p className="text-sm text-white/80">Quer explorar mais conteúdos e cursos completos?</p>
          <div className="mt-4">
            <LinkButton
              to="/categorias"
              variant="cyanOutline"
              className="text-cyan border-cyan/40 mt-0! w-auto rounded-full border px-4 py-2"
            >
              Acesse o catálogo completo de todos os cursos!
            </LinkButton>
          </div>
        </section>
      </section>
    </main>
  )
}

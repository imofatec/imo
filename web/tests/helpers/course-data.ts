export type CourseLessonInput = {
  title: string
  description: string
  youtubeLink: string
}

export type CourseInput = {
  name: string
  category: string
  level: string
  description: string
  skillIds: string[]
  lessons: CourseLessonInput[]
}

type CourseInputOverrides = Partial<Omit<CourseInput, 'lessons'>> & {
  lessons?: CourseLessonInput[]
}

function createUniqueSuffix() {
  return `${Date.now()}${Math.random().toString(36).slice(2, 8)}`.slice(-10)
}

function createYoutubeId(seed: string) {
  return seed
    .replace(/[^a-zA-Z0-9]/g, '')
    .padEnd(11, 'x')
    .slice(0, 11)
}

export function createLessonInput(seed: string, overrides: Partial<CourseLessonInput> = {}) {
  const suffix = createUniqueSuffix()
  const title = `Aula ${seed} muito importante ${suffix}`

  return {
    title,
    description: `Descricao completa da aula ${seed} com detalhes suficientes para passar na validacao ${suffix}.`,
    youtubeLink: createYoutubeId(`${seed}${suffix}`),
    ...overrides,
  }
}

export function createCourseInput(overrides: CourseInputOverrides = {}): CourseInput {
  const suffix = createUniqueSuffix()

  return {
    name: `Curso de testes E2E ${suffix}`,
    category: 'AI',
    level: 'beginner',
    description: `Descricao completa para o curso de testes automatizados ${suffix}, cobrindo fluxos reais da plataforma.`,
    skillIds: [],
    lessons: [createLessonInput('fundamentos'), createLessonInput('pratica')],
    ...overrides,
  }
}

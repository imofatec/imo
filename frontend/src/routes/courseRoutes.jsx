import Cursos from '@/pages/Cursos'
import VerAula from '@/pages/VerAula'

const categories = [
  {
    path: '/categorias',
    element: <Cursos />,
  },
  {
    path: '/categorias/:slug',
    element: <Cursos />,
  },
]

const lessons = {
  path: '/veraula',
  element: <VerAula />,
}

const courseRoutes = [...categories, lessons]

export default courseRoutes

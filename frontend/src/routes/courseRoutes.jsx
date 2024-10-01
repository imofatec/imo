import Cursos from '@/pages/Cursos'

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



const courseRoutes = [...categories]

export default courseRoutes

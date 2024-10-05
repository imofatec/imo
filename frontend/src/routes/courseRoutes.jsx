import Cursos from '@/pages/Cursos'
import ErrorPage from '@/pages/ErrorPage';

const categories = [
  {
    path: '/categorias',
    element: <Cursos />,
  },
  {
    path: '/categorias/:slug',
    element: <Cursos />,
  },
  {
    path: '*',
    element: <ErrorPage />,
  },
]



const courseRoutes = [...categories]

export default courseRoutes

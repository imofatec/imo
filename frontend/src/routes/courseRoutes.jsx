import Cursos from '@/pages/Cursos'
import ErrorPage from '@/pages/ErrorPage';
import MyCourses from '@/pages/MyCourses';
import ValidateCertificate from '@/pages/ValidateCertificate';

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
  {
    path: '/validar-certificado',
    element: <ValidateCertificate />,
  },
  {
    path: '/meus-cursos',
    element: <MyCourses />,
  }
  /*
      path: '/meus-cursos/:slug',
    element: <MyCourses />,
  }
  */
]



const courseRoutes = [...categories]

export default courseRoutes

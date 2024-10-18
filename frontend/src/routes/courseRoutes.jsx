import Cursos from '@/pages/Cursos'
import ErrorPage from '@/pages/ErrorPage';
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
]



const courseRoutes = [...categories]

export default courseRoutes

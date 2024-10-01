import CreateCourses from '@/pages/createCourses'
import ProtectedRoute from '@/auth/ProtectedRoutes'
import TestPage from '@/pages/TestPage'
import VerAula from '@/pages/VerAula'
import { createCourse as createCourseRequest } from '@/requests/createCourse'

const teste = {
  path: '/private',
  element: <TestPage />,
}

const createCourse = {
  path: '/criarcurso',
  element: <CreateCourses />,
  action: createCourseRequest,
}

const lessons = {
  path: '/cursos/:slugCourse/:IdLesson',
  element: <VerAula />,
}
const authRoutes = [
  {
    element: <ProtectedRoute />,
    children: [teste, createCourse,lessons],
  },
]

export default authRoutes

import CreateCourses from '@/pages/createCourses'
import ProtectedRoute from '@/auth/ProtectedRoutes'
import TestPage from '@/pages/TestPage'
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

const authRoutes = [
  {
    element: <ProtectedRoute />,
    children: [teste, createCourse],
  },
]

export default authRoutes

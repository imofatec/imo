import CreateCourses from '@/pages/createCourses'
import ProtectedRoute from '@/auth/ProtectedRoutes'
import TestPage from '@/pages/TestPage'
import { createCourse as createCourseRequest } from '@/requests/createCourse'
import AccountSettings from '@/pages/AccountSettings'

const teste = {
  path: '/private',
  element: <TestPage />,
}

const createCourse = {
  path: '/criarcurso',
  element: <CreateCourses />,
  action: createCourseRequest,
}

const accountSettings = {
  path: '/configurarconta',
  element: <AccountSettings />,
  action: editProfile,
}
const authRoutes = [
  {
    element: <ProtectedRoute />,
    children: [teste, createCourse, accountSettings],
  },
]

export default authRoutes

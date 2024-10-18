import AccountSettings from '@/pages/AccountSettings'
import CreateCourses from '@/pages/createCourses'
import ProtectedRoute from '@/auth/ProtectedRoutes'
import TestPage from '@/pages/TestPage'
import VerAula from '@/pages/VerAula'
import { createCourse as createCourseRequest } from '@/requests/createCourse'
import { updateUser } from '@/requests/user/updateUser'

const teste = {
  path: '/private',
  element: <TestPage />,
}

const createCourse = {
  path: '/criar-curso',
  element: <CreateCourses />,
  action: createCourseRequest,
}

const lessons = {
  path: '/cursos/:slugCourse/:IdLesson',
  element: <VerAula />,
}

const accountSettings = {
  path: '/user/configurar-conta',
  element: <AccountSettings />,
  action: updateUser,
}
const authRoutes = [
  {
    element: <ProtectedRoute />,
    children: [teste, createCourse, lessons, accountSettings],
  },
]

export default authRoutes

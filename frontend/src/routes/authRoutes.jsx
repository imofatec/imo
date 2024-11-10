import AccountSettings from '@/pages/AccountSettings'
import CreateCourses from '@/pages/createCourses'
import ProtectedRoute from '@/auth/ProtectedRoutes'
import TestPage from '@/pages/TestPage'
import MyCourses from '@/pages/MyCourses'
import VerAula from '@/pages/VerAula'
import { createCourse as createCourseRequest } from '@/requests/createCourse'
import { updateUserRequest } from '@/requests/user/updateUserRequest'

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
  path: '/cursos/:slugCourse/:idLesson',
  element: <VerAula />,
}

const accountSettings = {
  path: '/user/configurar-conta',
  element: <AccountSettings />,
  action: updateUserRequest,
}
const myCourses = {
  path: '/user/cursos',
  element: <MyCourses />,
}
const myCoursesRoute = {
  path: '/user/cursos/:route',
  element: <MyCourses />,
}
const authRoutes = [
  {
    element: <ProtectedRoute />,
    children: [
      teste,
      createCourse,
      lessons,
      accountSettings,
      myCourses,
      myCoursesRoute,
    ],
  },
]

export default authRoutes

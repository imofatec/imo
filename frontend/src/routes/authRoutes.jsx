import AccountSettings from '@/pages/AccountSettings'
import CreateCourses from '@/pages/createCourses'
import ProtectedRoute from '@/context/useAuth'
import TestPage from '@/pages/TestPage'
import MyCourses from '@/pages/MyCourses'
import VerAula from '@/pages/VerAula'
import { createCourse as createCourseRequest } from '@/requests/createCourse'
import { updateUserRequest } from '@/requests/user/updateUserRequest'
import { createComment } from '@/requests/courses/createComment'
import { updateCourse as updateCourseRequest } from '@/requests/courses/updateCourse'
import UserEmailConfirmation from '@/pages/UserEmailConfirmation'
import EditCourse from '@/pages/EditCourse'

const teste = {
  path: '/private',
  element: <TestPage />,
}

const createCourse = {
  path: '/criar-curso',
  element: <CreateCourses />,
  action: createCourseRequest,
}

const updateCourse = {
  path: '/editar-curso/:id',
  element: <EditCourse />,
}

const lessons = {
  path: '/cursos/:slugCourse/:idLesson',
  element: <VerAula />,
  action: createComment,
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

const userEmailConfirmation = {
  path: '/user/confirmar-email',
  element: <UserEmailConfirmation />,
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
      userEmailConfirmation,
      updateCourse
    ],
  },
]

export default authRoutes

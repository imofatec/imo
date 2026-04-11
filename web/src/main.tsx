import './index.css'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import App from '@/App'
import Home from '@/pages/Home'
import Login from '@/pages/Login'
import Register from '@/pages/Register'
import AllCourses from '@/pages/AllCourses'
import NotFoundPage from '@/pages/NotFound'
import { AuthProvider } from '@/contexts/AuthContext'
import { UserProvider } from '@/contexts/UserContext'
import ProtectedRoutes from './components/auth/ProtectedRoutes'
import GuestRoutes from './components/auth/GuestRoutes'
import MyCourses from './pages/MyCourses'
import CreateCoursePage from './pages/CreateCourse'
import WatchPage from './pages/Watch'
import EditCoursePage from './pages/EditCourse'
import UserConfigPage from './pages/UserConfig'

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <Home /> },
      { path: 'categorias/:categorySlug?', element: <AllCourses /> },

      {
        element: <GuestRoutes redirectTo="/" />,
        children: [
          { path: 'home', element: <Home /> },
          { path: 'login', element: <Login /> },
          { path: 'cadastro', element: <Register /> },
        ],
      },

      {
        element: <ProtectedRoutes />,
        children: [
          { path: 'user/cursos', element: <MyCourses /> },
          { path: 'user/configuracoes', element: <UserConfigPage /> },
          { path: 'cursos/:courseId/:idLesson', element: <WatchPage /> },
          { path: 'criar-curso', element: <CreateCoursePage /> },
          { path: 'editar-curso/:courseId', element: <EditCoursePage /> },
        ],
      },
      { path: '*', element: <NotFoundPage /> },
    ],
  },
])

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AuthProvider>
      <UserProvider>
        <RouterProvider router={router} />
      </UserProvider>
    </AuthProvider>
  </React.StrictMode>
)

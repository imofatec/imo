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

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <Home /> },
      { path: 'categories/:categorySlug?', element: <AllCourses /> },

      {
        element: <GuestRoutes redirectTo="/" />,
        children: [{ path: 'home', element: <Home /> }],
      },

      {
        element: <GuestRoutes redirectTo="/" />,
        children: [
          { path: 'login', element: <Login /> },
          { path: 'register', element: <Register /> },
        ],
      },

      {
        element: <ProtectedRoutes />,
        children: [
          { path: 'user/courses', element: <MyCourses /> },
          { path: 'user/courses/:courseSlug/watch', element: <WatchPage /> },
        ],
      },
      {
        element: <ProtectedRoutes />,
        children: [{ path: 'createcourse', element: <CreateCoursePage /> }],
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

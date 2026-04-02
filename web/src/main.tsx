import './index.css'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import App from '@/App'
import Home from '@/pages/Home'
import Login from '@/pages/Login'
import Register from '@/pages/Register'
import AllCourses from '@/pages/AllCourses'
import { AuthProvider } from '@/contexts/AuthContext'
import ProtectedRoutes from './components/auth/ProtectedRoutes'
import GuestRoutes from './components/auth/GuestRoutes'
import MyCourses from './pages/MyCourses'
import CreateCoursePage from './pages/CreateCourse'

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <Home /> },
      { path: 'allcourses', element: <AllCourses /> },

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
        children: [{ path: 'mycourses', element: <MyCourses /> }],
      },
      {
        element: <ProtectedRoutes />,
        children: [{ path: 'createcourse', element: <CreateCoursePage /> }],
      },
    ],
  },
])

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
)

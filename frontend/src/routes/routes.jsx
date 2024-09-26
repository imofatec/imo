import authRoutes from './authRoutes'
import courseRoutes from './courseRoutes'
import Index from '@/pages/Index'
import Layout from '@/layouts/Layout'
import loginRoutes from './loginRoutes'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'

export default function Routes() {
  const router = createBrowserRouter([
    {
      element: <Layout />,
      children: [
        { path: '/', element: <Index /> },
        ...loginRoutes,
        ...authRoutes,
        ...courseRoutes,
      ],
    },
  ])

  return <RouterProvider router={router} />
}

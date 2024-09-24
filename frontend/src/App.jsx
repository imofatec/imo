import Cursos from "./pages/Cursos"
import Layout from "./layouts/Layout"
import Index from "./pages/Index"
import SignUp from "./pages/SignUp"
import SignIn from "./pages/SignIn"
import CreateCourses from "./pages/createCourses"
import ProtectedRoute from "./auth/ProtectedRoutes"
import TestPage from "./pages/TestPage"
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import { signUp } from "./requests/user/signup"
import { signIn } from "./requests/user/signin"
import { createCourse } from "./requests/createCourse"

export default function App() {
  const router = createBrowserRouter([
    {
      element: <Layout />,
      children: [
        {
          path: "/",
          element: <Index />,
        },
        {
          path: "/cadastro",
          element: <SignUp />,
          action: signUp,
        },
        {
          path: "/login",
          element: <SignIn />,
          action: signIn,
        },
        {
          path: "/criarcurso",
          element: <CreateCourses />,
          action: createCourse,
        },
        {
          path: "/categorias",
          element: <Cursos />
        },
        {
          path: "/categorias/:slug", 
          element: <Cursos />
        },
        {
          element: <ProtectedRoute />,
          children: [
            {
              path: "/private",
              element: <TestPage />,
            },
          ],
        },
      ],
    },
  ])
  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}

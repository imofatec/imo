import SignUp from "./pages/SignUp"
import SignIn from "./pages/SignIn"
import Index from "./pages/Index"
import Layout from "./layouts/Layout"
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import Cursos from "./pages/Cursos"

export default function App() {
  const router = createBrowserRouter([
    {
      element: <Layout />, /* As rotas devem ser inseridas como filhos do módulo Layout (tem o Header e o Footer nele) */
      children: [
        {
          path: '/',
          element: <Index />,
        },
        {
          path: "/cadastro",
          element: <SignUp />
        },
        {
          path: "/login",
          element: <SignIn />
        },
        {
          path: "/cursos",
          element: <Cursos />
        },
      ]
    },
  
  ])
  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}



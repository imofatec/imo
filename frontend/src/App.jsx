import Layout from "./layouts/Layout"
import Index from "./pages/Index"
import SignUp from "./pages/SignUp"
import SignIn from "./pages/SignIn"
import ProtectedRoute from "./auth/ProtectedRoutes"
import TestPage from "./pages/TestPage"
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import { signUp } from "./requests/user/signup"
import { signIn } from "./requests/user/signin"

export default function App() {

  const router = createBrowserRouter([{
    element: <Layout />, /* As rotas devem ser inseridas como filhos do módulo Layout (tem o Header e o Footer nele) */
    children: [
      {
        path: '/',
        element: <Index />,
      },
      {
        path: "/cadastro",
        element: <SignUp />,
        action: signUp
      },
      {
        path: "/login",
        element: <SignIn />,
        action: signIn
      },
      {
        element: <ProtectedRoute />,
        children: [
          {
            path: "/private",
            element: <TestPage />
          }
        ]
      }
    ]
  }])
  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}

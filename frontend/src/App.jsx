import SignUp from "./pages/SignUp"
import SignIn from "./pages/SignIn"
import Index from "./pages/Index"
import Header from "./layouts/Header"
import { createBrowserRouter, RouterProvider } from "react-router-dom"

function App() {
  const router = createBrowserRouter([
    {
      element: <Header />,
      children: [
        {
          path: "/",
          element: <Index />
        }
      ]
    },
    {
      path: "/cadastro",
      element: <SignUp />
    },
    {
      path: "/login",
      element: <SignIn />
    }
  ])
  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}

export default App

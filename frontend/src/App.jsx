import SignUp from "./pages/SignUp"
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
    }
  ])
  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}

export default App

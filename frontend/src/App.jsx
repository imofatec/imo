import SignUp from "./pages/SignUp"
import { createBrowserRouter, RouterProvider } from "react-router-dom"

function App() {
  const router = createBrowserRouter([
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
